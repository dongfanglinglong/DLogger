package com.df.dlogger.printer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.df.dlogger.constant.DLevel;

/**
 * 输出都控制台
 *
 * @author dongfang
 * date 2016/12/9
 */

public class ConsolePrinter implements Printer {
    @Override
    public void print(@NonNull DLevel level, String tag, String message) {
        log(level, tag, message);
    }

    /** logcat里日志的最大长度. */
    private static final int MAX_LOG_LENGTH = 4000;

    private static boolean LOG_V = true;
    private static boolean LOG_D = true;
    private static boolean LOG_I = true;
    private static boolean LOG_W = true;
    private static boolean LOG_E = true;
    private static boolean LOG_WTF = true;

    /**
     * 设置是否输出到控制台日志
     *
     * @param enable true or false
     */
    public static void setLog(boolean enable) {
        LOG_V = LOG_D = LOG_I = LOG_W = LOG_E = LOG_WTF = enable;
    }


    /**
     * 使用LogCat输出日志，字符长度超过4000则自动换行.
     *
     * @param level   级别
     * @param tag     标签
     * @param message 信息
     */
    public static void log(@NonNull DLevel level, @NonNull String tag, @NonNull String message) {
        int subNum = message.length() / MAX_LOG_LENGTH;
        if (subNum > 0) {
            int index = 0;
            for (int i = 0; i < subNum; i++) {
                int lastIndex = index + MAX_LOG_LENGTH;
                String sub = message.substring(index, lastIndex);
                logSub(level, tag, sub);
                index = lastIndex;
            }
            // logSub(level, tag, message.substring(index, message.length()));
        } else {
            logSub(level, tag, message);
        }
    }

    /**
     * 使用LogCat输出日志.
     *
     * @param level 级别
     * @param tag   标签
     * @param sub   信息
     */
    private static void logSub(@NonNull DLevel level, @NonNull String tag, @NonNull String sub) {
        switch (level) {
            case VERBOSE:
                v(tag, sub);
                break;
            case DEBUG:
                d(tag, sub);
                break;
            case INFO:
                i(tag, sub);
                break;
            case WARN:
                w(tag, sub);
                break;
            case ERROR:
                e(tag, sub);
                break;
            case WTF:
                wtf(tag, sub);
                break;
            default:
                break;
        }
    }


    public static void v(String tag, String mess) {
        if (LOG_V) { Log.v(tag, mess); }
    }

    public static void d(String tag, String mess) {
        if (LOG_D) { Log.d(tag, mess); }
    }

    public static void i(String tag, String mess) {
        if (LOG_I) { Log.i(tag, mess); }
    }

    public static void w(String tag, String mess) {
        if (LOG_W) { Log.w(tag, mess); }
    }

    public static void e(String tag, String mess) {
        if (LOG_E) { Log.e(tag, mess); }
    }

    public static void wtf(String tag, String mess) {
        if (LOG_WTF) { Log.wtf(tag, mess); }
    }

    public static void v(String tag, String mess, Throwable e) {
        if (LOG_V) { Log.v(tag, mess, e); }
    }

    public static void d(String tag, String mess, Throwable e) {
        if (LOG_D) { Log.d(tag, mess, e); }
    }

    public static void i(String tag, String mess, Throwable e) {
        if (LOG_I) { Log.i(tag, mess, e); }
    }

    public static void w(String tag, String mess, Throwable e) {
        if (LOG_W) { Log.w(tag, mess, e); }
    }

    public static void e(String tag, String mess, Throwable e) {
        if (LOG_E) { Log.e(tag, mess, e); }
    }

    public static void wtf(String tag, String mess, Throwable e) {
        if (LOG_WTF) { Log.wtf(tag, mess, e); }
    }
}
