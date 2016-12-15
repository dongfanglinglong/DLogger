package com.df.dlogger.constant;

import android.support.annotation.NonNull;

/**
 * 日志等级
 *
 * @author dongfang
 * @date 2016/7/20
 */
public enum DLevel {

    ALL(0),
    VERBOSE(1),
    DEBUG(1 << 1),
    INFO(1 << 2),
    WARN(1 << 3),
    ERROR(1 << 4),
    WTF(1 << 5),
    NONE(1 << 10);

    private int mLevel;

    DLevel(int level) {
        mLevel = level;
    }

    public int getLevel() {
        return mLevel;
    }


    public boolean isEnable(@NonNull DLevel level) {
        return this.mLevel <= level.getLevel();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Level
    ///////////////////////////////////////////////////////////////////////////
//    /** 不保存日志 */
//    public static final int NONE = 1 << 10;
//
//    public static final int VERBOSE = 1;
//
//    public static final int DEBUG = 1 << 2;
//
//    public static final int INFO = 1 << 3;
//
//    public static final int WARN = 1 << 4;
//
//    public static final int ERROR = 1 << 5;
//
//    public static final int WTF = 1 << 6;
//
//    /** 保存所有日志 */
//    public static final int ALL = 0;
//
//
//    @IntDef({DLevel.NONE, DLevel.VERBOSE, DLevel.DEBUG, DLevel.INFO, DLevel.WARN, DLevel.ERROR, DLevel.WTF, DLevel.ALL})
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface DLogLevel {}

}
