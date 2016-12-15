package com.df.dlogger.logger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.df.dlogger.constant.DLevel;
import com.df.dlogger.constant.DSegment;
import com.df.dlogger.constant.DZoneOffset;
import com.df.dlogger.util.SysUtils;

import java.util.regex.Pattern;

/**
 * 日志本地保留设置
 *
 * @author dongfang
 * @date 2016/7/26
 */
public class DSetting {

    /** 是否需存储到本地 */
    private boolean isNeedStore;
    /** 需要保存或者显示的消息的等级 */
    private DLevel mLevel;
    /** 过滤正则表达式规则 */
    private String mPatternStr;
    /** 正则表达式对象 */
    private Pattern mPattern;
    /** 字符集. */
    private String mCharset;
    /** 时间格式. */
    private String mTimeFormat;
    /** 时区偏移时间. */
    private DZoneOffset mZoneOffset;
    /** 日志保存的目录. */
    private String mLogDir;
    /** 日志文件的前缀. */
    private String mLogPrefix;
    /** 日志文件的后缀,可以当文件类型来用 */
    private String mLogSuffix;
    /** 切片间隔，单位小时. */
    private DSegment mLogSegment;
    /** 保留日志的天数 ,小于1则永远保留 */
    private int retainDays;
    /** 产生日志文件时的初始化信息. */
    private String info;

    /**
     * 保存'全部日志','不设置正则过滤条件'<br>
     * 保存'UTF-8'，东八区时间戳，目录为'.dlogger', 后缀名为 '.log',每'一天'一个分片文件，日志保留'7'天
     */
    public DSetting() {
        isNeedStore = true;
        mLevel = DLevel.ALL;
        mPatternStr = "";
        mPattern = null;

        mCharset = "UTF-8";
        mTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
        mZoneOffset = DZoneOffset.P0800;
        mLogDir = "dlogger";
        mLogPrefix = "";
        mLogSuffix = ".log";
        mLogSegment = DSegment.TWENTY_FOUR_HOURS;
        retainDays = 7;
        info = SysUtils.genInfo();
    }

    /**
     * @return 日志文件时的初始化信息
     */
    public String getInfo() {
        return info;
    }

    /**
     * 自定义日志文件时的初始化信息
     *
     * @param info
     * @return
     */
    public DSetting setInfo(String info) {
        this.info = info;
        return this;
    }

    /**
     * 增加日志文件时的初始化信息
     *
     * @param info
     * @return
     */
    public DSetting addInfo(String info) {
        this.info = SysUtils.genInfo() + SysUtils.getLineSeparator() + info;
        return this;
    }

    /**
     * 获取日志文件编码格式
     *
     * @return
     */
    public String getCharset() {
        return mCharset;
    }

    /**
     * 设定日志文件编码格式
     *
     * @param charset 编码格式
     * @return
     */
    public DSetting setCharset(@NonNull String charset) {
        mCharset = charset;
        return this;
    }

    /**
     * 获取日志的时间戳格式
     *
     * @return
     */
    public String getTimeFormat() {
        return mTimeFormat;
    }

    /**
     * 设定日志的时间戳格式
     *
     * @param timeFormat 时间戳格式
     * @return
     */
    public DSetting setTimeFormat(@NonNull String timeFormat) {
        mTimeFormat = timeFormat;
        return this;
    }

    /**
     * 获取日志的时间戳时区偏移量
     *
     * @return
     */
    public DZoneOffset getZoneOffset() {
        return mZoneOffset;
    }

    /**
     * 设定日志的时间戳时区偏移量
     *
     * @param zoneOffset 时区偏移量
     * @return
     */
    public DSetting setZoneOffset(@NonNull DZoneOffset zoneOffset) {
        mZoneOffset = zoneOffset;
        return this;
    }

    /**
     * 获取日志保存的文件夹名称
     *
     * @return
     */
    public String getLogDir() {
        return mLogDir;
    }

    /**
     * 设置日志保存的文件夹名称
     *
     * @param logDir 文件目录
     * @return
     */
    public DSetting setLogDir(@NonNull String logDir) {
        mLogDir = logDir;
        return this;
    }

    /**
     * 获取日志保存的文件的前缀
     *
     * @return
     */
    public String getLogPrefix() {
        return mLogPrefix;
    }

    /**
     * 设置日志保存的文件的前缀
     *
     * @param logPrefix 前缀
     * @return
     */
    public DSetting setLogPrefix(@NonNull String logPrefix) {
        mLogPrefix = logPrefix;
        return this;
    }

    /**
     * 获取日志文件的时间切片
     *
     * @return
     */
    public DSegment getLogSegment() {
        return mLogSegment;
    }

    /**
     * 设定日志文件的时间切片
     *
     * @param logSegment 时间切片单位
     * @return
     */
    public DSetting setLogSegment(@NonNull DSegment logSegment) {
        mLogSegment = logSegment;
        return this;
    }

    /**
     * 获取日志文件的保存天数
     *
     * @return
     */
    public int getRetainDays() {
        return retainDays;
    }

    /**
     * 设定日志文件的保存时间
     *
     * @param retainDays 天
     * @return
     */
    public DSetting setRetainDays(int retainDays) {
        this.retainDays = retainDays;
        return this;
    }

    /**
     * 获取日志文件的后缀名
     *
     * @return
     */
    public String getLogSuffix() {
        return mLogSuffix;
    }

    /**
     * 设定日志文件的后缀名
     *
     * @param logSuffix 后缀名，如'.log'
     * @return
     */
    public DSetting setLogSuffix(String logSuffix) {
        mLogSuffix = logSuffix;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////


    public DLevel getLevel() {
        return mLevel;
    }

    public DSetting setLevel(@NonNull DLevel level) {
        isNeedStore = DLevel.NONE != level;
        mLevel = level;
        return this;
    }

    public boolean isNeedStore() {
        return isNeedStore;
    }

    public DSetting setNeedStore(boolean needStore) {
        isNeedStore = needStore;
        return this;
    }

    public String getPattern() {
        return mPatternStr;
    }

    public DSetting setPattern(@NonNull String pattern) {
        mPatternStr = pattern;
        if (!TextUtils.isEmpty(pattern)) {
            mPattern = Pattern.compile(pattern);
        } else {
            mPattern = null;
        }
        return this;
    }


    public DSetting setPattern(Pattern pattern) {
        mPattern = pattern;
        return this;
    }

    public String getPatternStr() {
        return mPatternStr;
    }

    public DSetting setPatternStr(String patternStr) {
        mPatternStr = patternStr;
        return this;
    }


    /**
     * 判断是否需要保存日志到文件
     *
     * @param level 日志等级
     * @param tag   日志标签
     * @return 需要保存且符合日志等级要求，且符合正则过滤条件 则返回true，否则false;
     */
    public boolean checkStore(@NonNull DLevel level, String tag) {
        return isNeedStore && mLevel.isEnable(level)
                && (null == mPattern || (!TextUtils.isEmpty(tag) && mPattern.matcher(tag).find()));
    }

}
