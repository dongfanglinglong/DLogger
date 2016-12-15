package com.df.dlogger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.df.dlogger.logger.DLog;
import com.df.dlogger.printer.ConsolePrinter;


/**
 * Log工具，类似android.util.Log。 tag自动产生，格式:
 * customTagPrefix:className.methodName(L:lineNumber)
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 *
 * @author dongfang
 */
public final class ULog {

    private ULog() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置控制台是否输出日志
     *
     * @param islog
     */
    public static void setConsoleStatus(boolean islog) {
        ConsolePrinter.setLog(islog);
    }

    private static boolean allowD = true;
    private static boolean allowE = true;
    private static boolean allowI = true;
    private static boolean allowV = true;
    private static boolean allowW = true;
    private static boolean allowWtf = true;

    /**
     * 设置是否输出日志，无论是控制台还是本地持有化
     *
     * @param islog
     */
    public static void setULog(boolean islog) {
        allowD = allowE = allowI = allowV = allowW = allowWtf = islog;
    }

    private static String generateTag(StackTraceElement caller) {
        // String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        int index = callerClazzName.indexOf("$");
        if (index > 0) {
            callerClazzName = callerClazzName.substring(0, index);
        }
        // tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        // tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return "(" + callerClazzName + ".java:" + caller.getLineNumber() + ")" + "#" + caller.getMethodName();
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }


    ///////////////////////////////////////////////////////////////////////////
    // verbose
    ///////////////////////////////////////////////////////////////////////////
    public static void v(@NonNull String content) {
        if (!allowV) return;
        ver(generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void v(@NonNull String tag, @NonNull String content) {
        if (!allowV) return;
        ver(tag + "/" + generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void v(@NonNull String tag, @NonNull String content, @NonNull Throwable tr) {
        if (!allowV) return;
        ver(tag + "/" + generateTag(getCallerStackTraceElement()), content, tr);
    }

    private static void ver(@NonNull String tag, @NonNull String content, Throwable tr) {
        DLog.getInstance().verbose(tag, null == tr ? content : content + "\n" + Log.getStackTraceString(tr));
    }

    ///////////////////////////////////////////////////////////////////////////
    // debug
    ///////////////////////////////////////////////////////////////////////////

    public static void d(@NonNull String content) {
        if (!allowD) return;
        debug(generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void d(@NonNull String tag, @NonNull String content) {
        if (!allowD) return;
        debug(tag + "/" + generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void d(@NonNull String content, @NonNull Throwable tr) {
        if (!allowD) return;
        debug(generateTag(getCallerStackTraceElement()), content, tr);
    }

    public static void d(@NonNull String tag, @NonNull String content, @NonNull Throwable tr) {
        if (!allowD) return;
        debug(tag + "/" + generateTag(getCallerStackTraceElement()), content, tr);
    }

    private static void debug(@NonNull String tag, @NonNull String content, Throwable tr) {
        DLog.getInstance().debug(tag, null == tr ? content : content + "\n" + Log.getStackTraceString(tr));
    }

    ///////////////////////////////////////////////////////////////////////////
    // info
    ///////////////////////////////////////////////////////////////////////////
    public static void i(@NonNull String content) {
        if (!allowI) return;
        info(generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void i(String tag, @NonNull String content) {
        if (!allowI) return;
        info(tag + "/" + generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void i(@NonNull String content, @NonNull Throwable tr) {
        if (!allowI) return;
        info(generateTag(getCallerStackTraceElement()), content, tr);
    }

    public static void i(String tag, @NonNull String content, @NonNull Throwable tr) {
        if (!allowI) return;
        info(tag + "/" + generateTag(getCallerStackTraceElement()), content, tr);
    }

    private static void info(@NonNull String tag, @NonNull String content, Throwable tr) {
        DLog.getInstance().info(tag, null == tr ? content : content + "\n" + Log.getStackTraceString(tr));
    }

    ///////////////////////////////////////////////////////////////////////////
    // warn
    ///////////////////////////////////////////////////////////////////////////
    public static void w(@NonNull String content) {
        if (!allowW) return;
        warn(generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void w(String tag, @NonNull String content) {
        if (!allowW) return;
        warn(tag + "/" + generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void w(@NonNull String content, @NonNull Throwable tr) {
        if (!allowW) return;
        warn(generateTag(getCallerStackTraceElement()), content, tr);
    }

    public static void w(@NonNull Throwable tr) {
        if (!allowW) return;
        warn(generateTag(getCallerStackTraceElement()), Log.getStackTraceString(tr), null);
    }

    public static void w(@NonNull String tag, @NonNull String content, @NonNull Throwable tr) {
        if (!allowW) return;
        warn(tag + "/" + generateTag(getCallerStackTraceElement()), content, tr);
    }

    private static void warn(@NonNull String tag, @NonNull String content, Throwable tr) {
        DLog.getInstance().warn(tag, null == tr ? content : content + "\n" + Log.getStackTraceString(tr));
    }

    ///////////////////////////////////////////////////////////////////////////
    // error
    ///////////////////////////////////////////////////////////////////////////
    public static void e(@NonNull String content) {
        if (!allowE) return;
        error(generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void e(@NonNull String tag, @NonNull String content) {
        if (!allowE) return;
        error(tag + "/" + generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void e(@NonNull String content, @NonNull Throwable tr) {
        if (!allowE) return;
        error(generateTag(getCallerStackTraceElement()), content, tr);
    }

    public static void e(@NonNull Throwable tr) {
        if (!allowE) return;
        error(generateTag(getCallerStackTraceElement()), Log.getStackTraceString(tr), null);
    }

    public static void e(@NonNull String tag, @NonNull String content, @NonNull Throwable tr) {
        if (!allowE) return;
        error(tag + "/" + generateTag(getCallerStackTraceElement()), content, tr);

    }

    public static void e() {
        if (!allowE) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        error(tag, caller.getMethodName(), null);
    }


    private static void error(@NonNull String tag, @NonNull String content, Throwable tr) {
        DLog.getInstance().error(tag, null == tr ? content : content + "\n" + Log.getStackTraceString(tr));
    }

    ///////////////////////////////////////////////////////////////////////////
    // wtf
    ///////////////////////////////////////////////////////////////////////////
    public static void wtf(@NonNull String content) {
        if (!allowWtf) return;
        azzert(generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void wtf(String tag, @NonNull String content) {
        if (!allowWtf) return;
        azzert(tag + "/" + generateTag(getCallerStackTraceElement()), content, null);
    }

    public static void wtf(@NonNull Throwable tr) {
        if (!allowWtf) return;
        azzert(generateTag(getCallerStackTraceElement()), Log.getStackTraceString(tr), null);
    }

    public static void wtf(@NonNull String tag, @NonNull String content, @NonNull Throwable tr) {
        if (!allowWtf) return;
        azzert(tag + "/" + generateTag(getCallerStackTraceElement()), content, tr);
    }

    private static void azzert(@NonNull String tag, @NonNull String content, Throwable tr) {
        DLog.getInstance().azzert(tag, null == tr ? content : content + "\n" + Log.getStackTraceString(tr));
    }
}
