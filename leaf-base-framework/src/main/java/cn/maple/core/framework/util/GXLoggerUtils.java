package cn.maple.core.framework.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.maple.core.framework.constant.GXCommonConstant;
import org.slf4j.Logger;

@SuppressWarnings("unused")
public class GXLoggerUtils {
    /**
     * 私有构造函数
     */
    private GXLoggerUtils() {
    }

    /**
     * 记录日志
     *
     * @param logger 日志对象
     * @param desc   描述信息
     * @param data   记录数据
     * @author britton
     * @since 2021-10-19 17:40
     */
    public static void logInfo(Logger logger, String desc, Object data) {
        String threadName = Thread.currentThread().getName();
        String s = CharSequenceUtil.format(GXCommonConstant.LOGGER_FORMAT, threadName, desc, data);
        logger.info(s);
    }

    /**
     * 记录日志
     *
     * @param logger 日志对象
     * @param desc   描述信息
     * @param data   记录数据
     * @author britton
     * @since 2021-10-19 17:40
     */
    public static void logDebug(Logger logger, String desc, Object data) {
        String threadName = Thread.currentThread().getName();
        String format = CharSequenceUtil.format(GXCommonConstant.LOGGER_FORMAT, threadName, desc, data);
        logger.debug(format);
    }

    /**
     * 记录日志
     *
     * @param logger 日志对象
     * @param desc   描述信息
     * @param data   记录数据
     * @author britton
     * @since 2021-10-19 17:40
     */
    public static void logError(Logger logger, String desc, Object data) {
        String threadName = Thread.currentThread().getName();
        String format = CharSequenceUtil.format(GXCommonConstant.LOGGER_FORMAT, threadName, desc, data);
        logger.error(format);
    }

    /**
     * 记录日志
     *
     * @param logger 日志对象
     * @param desc   描述信息
     * @param t      异常信息
     * @author britton
     * @since 2021-10-19 17:40
     */
    public static void logError(Logger logger, String desc, Throwable t) {
        String threadName = Thread.currentThread().getName();
        String format = CharSequenceUtil.format("{} , {}", threadName, desc);
        logger.error(format, t);
    }

    /**
     * 记录日志
     *
     * @param logger 日志对象
     * @param desc   描述信息
     * @param data   记录数据
     * @author britton
     * @since 2021-10-19 17:40
     */
    public static void logWarn(Logger logger, String desc, Object data) {
        String threadName = Thread.currentThread().getName();
        String s = CharSequenceUtil.format(GXCommonConstant.LOGGER_FORMAT, threadName, desc, data);
        logger.warn(s);
    }
}
