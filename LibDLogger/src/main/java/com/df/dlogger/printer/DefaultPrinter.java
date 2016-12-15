/*
 * Copyright JiongBull 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.df.dlogger.printer;


import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.df.dlogger.constant.DLevel;
import com.df.dlogger.constant.DSegment;
import com.df.dlogger.logger.DSetting;
import com.df.dlogger.util.FileUtils;
import com.df.dlogger.util.SysUtils;
import com.df.dlogger.util.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认输出，会输出到控制台和文件
 *
 * @author dongfnag
 * @date 2016-12-9
 */
public class DefaultPrinter extends ConsolePrinter {


    /** 行分割符号 */
    private static final String LINE_SEPARATOR = SysUtils.getLineSeparator();

    /** 文件中保存的内容格式. */
    private static final String PRINT_FILE_FORMAT = "%1$s-[%2$s %3$s]- %4$s" + LINE_SEPARATOR;

    /** 日志设定 */
    private DSetting mDSetting;

    public DefaultPrinter(@NonNull DSetting setting) {
        mDSetting = setting;
    }


    public void setDSetting(@NonNull DSetting DSetting) {
        mDSetting = DSetting;
    }

    @Override
    public void print(@NonNull DLevel level, String tag, String message) {
        super.print(level, tag, message);
        synchronized (Printer.class) {
            printFile(decorateMsgForFile(level, tag, message));
        }
    }

    /**
     * 装饰打印到文件的信息.
     *
     * @param level   级别
     * @param message 信息
     * @return 装饰后的信息
     */
    private String decorateMsgForFile(@NonNull DLevel level, @NonNull String tag, String message) {
        String time = TimeUtils.getCurTime();
        return String.format(PRINT_FILE_FORMAT, time, level, tag, message);
    }

    /**
     * 日志打印输出到文件.
     *
     * @param message 信息
     */
    private void printFile(@NonNull String message) {
        String dirPath = genDirPath();
        String fileName = genFileName();

        if (!FileUtils.isExist(dirPath + File.separator + fileName)) {
            message = mDSetting.getInfo() + message;
            delUselessLogs();
        }

        FileUtils.write(dirPath, fileName, message, false);
    }

    /**
     * 生成日志目录路径.
     *
     * @return 日志目录路径
     */
    private String genDirPath() {
        String dir = mDSetting.getLogDir();
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + dir;
    }

    /**
     * 生成日志文件名.
     *
     * @return 日志文件名
     */
    private String genFileName() {
        String logPrefix = mDSetting.getLogPrefix();
        String logSuffix = mDSetting.getLogSuffix();
        logPrefix = TextUtils.isEmpty(logPrefix) ? "" : logPrefix + "_";
        String curDate = TimeUtils.getCurDate();
        String fileName;
        if (mDSetting.getLogSegment() == DSegment.TWENTY_FOUR_HOURS) {
            fileName = logPrefix + curDate + logSuffix;
        } else {
            fileName = logPrefix + curDate + "_" + getCurSegment() + logSuffix;
        }
        return fileName;
    }

    /**
     * 根据切片时间获取当前的时间段.
     *
     * @return 比如“0001”表示00:00-01:00
     */
    private String getCurSegment() {
        int hour = TimeUtils.getCurHour();
        DSegment logSegment = mDSetting.getLogSegment();
        int segmentValue = logSegment.getValue();
        int start = hour - hour % segmentValue;
        int end = start + segmentValue;
        if (end == 24) {
            end = 0;
        }
        return getDoubleNum(start) + getDoubleNum(end);
    }

    /**
     * 对于1-9的数值进行前置补0.
     *
     * @param num 数值
     * @return num在[0, 9]时前置补0，否则返回原值
     */
    private String getDoubleNum(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }

    /**
     * 删除过期的日志文件
     * Touch -l 201511111111 Filename
     * 意思是时间修改为2015年11月11日11点11时
     */
    private void delUselessLogs() {
        int retainDays = mDSetting.getRetainDays();
        if (retainDays < 1) return;

        File fileDir = new File(genDirPath());
        if (!fileDir.exists() || fileDir.isFile()) return;

        File[] files = fileDir.listFiles();
        if (null == files || files.length < 1) return;

        long day = 24 * 3600 * 1000;
        long time = TimeUtils.getCurMillis() - day * retainDays;
        List<String> need2del = new ArrayList<>();
        for (int i = 0, size = files.length; i < size; i++) {
            if (files[i].lastModified() < time) {
                need2del.add(files[i].getAbsolutePath());
            }
        }

        FileUtils.delFile(need2del);
    }


}