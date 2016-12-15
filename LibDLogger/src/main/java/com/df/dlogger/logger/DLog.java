package com.df.dlogger.logger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.df.dlogger.constant.DLevel;
import com.df.dlogger.printer.DefaultPrinter;
import com.df.dlogger.util.SysUtils;


/**
 * 日志输出控制类
 *
 * @author dongfang
 * @date 2016/7/20
 */
public class DLog {

    private DSetting mSetting;
    private DefaultPrinter mPrinter;

    private final String lineSeparator;

    private DLog() {
        mSetting = new DSetting();
        mPrinter = new DefaultPrinter(mSetting);
        lineSeparator = SysUtils.getLineSeparator();
    }

    private DLog(DSetting setting) {
        mSetting = setting;
        mPrinter = new DefaultPrinter(mSetting);
        lineSeparator = SysUtils.getLineSeparator();
    }


    private volatile static DLog sDLog;

    public static synchronized DLog getInstance() {
        if (null == sDLog) {
            synchronized (DLog.class) {
                if (null == sDLog) {
                    sDLog = new DLog();
                }
            }
        }
        return sDLog;
    }

    /**
     * 设置日志文件的信息
     *
     * @param setting
     * @return
     */
    public DLog setDSettings(@NonNull DSetting setting) {
        mSetting = setting;
        mPrinter.setDSetting(mSetting);
        return this;
    }

    public DSetting getSetting() {
        return mSetting;
    }


    ///////////////////////////////////////////////////////////////////////////
    // log print
    ///////////////////////////////////////////////////////////////////////////

    public void verbose(String tag, String content, Throwable tr) {
        if (mSetting.checkStore(DLevel.VERBOSE, tag)) {
            print(DLevel.VERBOSE, tag, content, tr);
        }
    }

    public void verbose(String tag, String content) {
        verbose(tag, content, null);
    }

    public void debug(String tag, String content, Throwable tr) {
        if (mSetting.checkStore(DLevel.DEBUG, tag)) {
            print(DLevel.DEBUG, tag, content, tr);
        }
    }

    public void debug(String tag, String content) {
        debug(tag, content, null);
    }

    public void info(String tag, String content, Throwable tr) {
        if (mSetting.checkStore(DLevel.INFO, tag)) {
            print(DLevel.INFO, tag, content, tr);
        }
    }

    public void info(String tag, String content) {
        info(tag, content, null);
    }

    public void warn(String tag, String content, Throwable tr) {
        if (mSetting.checkStore(DLevel.WARN, tag)) {
            print(DLevel.WARN, tag, content, tr);
        }
    }

    public void warn(String tag, String content) {
        warn(tag, content, null);
    }

    public void error(String tag, String content, Throwable tr) {
        if (mSetting.checkStore(DLevel.ERROR, tag)) {
            print(DLevel.ERROR, tag, content, tr);
        }
    }

    public void error(String tag, String content) {
        error(tag, content, null);
    }

    public void azzert(String tag, String content, Throwable tr) {
        if (mSetting.checkStore(DLevel.WTF, tag)) {
            print(DLevel.WTF, tag, content, tr);
        }
    }

    public void azzert(String tag, String content) {
        azzert(tag, content, null);
    }


    /**
     * 写入到文件同时输入到控制台
     *
     * @param level 消息等级
     * @param tag   标签
     * @param msg   需要写入的到日志的内容
     */
    private void print(@NonNull DLevel level, String tag, String msg, Throwable tr) {
        String message = null == tr ? msg : msg + lineSeparator + Log.getStackTraceString(tr);

        mPrinter.print(level, tag, message);
    }


}
