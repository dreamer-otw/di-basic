package com.dreamer.basic.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * >
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/24 20:41
 */
public class DateUtil {
    /**日志记录*/
    private final static Logger LOGGER                  = LoggerFactory.getLogger(DateUtil.class);
    /**一天的秒数*/
    public final static long    ONE_DAY_SECONDS         = 86400;
    /**一天的毫秒数*/
    public final static long    ONE_DAY_MILL_SECONDS    = 86400000;
    /**yyyyMMdd时间格式*/
    public final static String  SHORT_FORMAT            = "yyyyMMdd";
    /**yyyyMMddHHmmss时间格式*/
    public final static String  LONG_FORMAT             = "yyyyMMddHHmmss";
    /** 比long少秒的日期格式 yyyyMMddHHmm */
    public final static String  LOWER_LONG_FORMAT       = "yyyyMMddHHmm";
    /**yyyy-MM-dd时间格式*/
    public final static String  WEB_FORMAT              = "yyyy-MM-dd";
    /**HHmmss时间格式*/
    public final static String  TIME_FORMAT             = "HHmmss";
    /**yyyyMM时间格式*/
    public final static String  MONTH_FORMAT            = "yyyyMM";
    /**yyyy年MM月dd日时间格式*/
    public final static String  CHINESE_DT_FORMAT       = "yyyy年MM月dd日";
    /**yyyy-MM-dd HH:mm:ss时间格式*/
    public final static String  NEW_FORMAT              = "yyyy-MM-dd HH:mm:ss";
    /**yyyy-MM-dd HH:mm时间格式*/
    public final static String  NO_SECOND_FORMAT        = "yyyy-MM-dd HH:mm";
    /**yyyy时间格式*/
    public static String        YEAR_FORMAT             = "yyyy";
    /**yyyy年MM月dd日HH点mm分ss秒*/
    public final static String  CHINESE_ALL_DATE_FORMAT = "yyyy年MM月dd日HH点mm分ss秒";
    /**HH:mm时间格式*/
    public final static String  HOURS_FORMAT            = "HH:mm";
    /**HH:mm时间格式*/
    public final static String  SECOND_FORMAT           = "HH:mm:ss";
    /**yyyy.MM.dd 时间格式*/
    public final static String  DATE_PICKER_FORMAT      = "yyyy.MM.dd";
    /** 零时零分零秒 */
    public static final String  START_TIME              = " 00:00:00";
    /** 23:59:59 */
    public static final String  END_TIME                = " 23:59:59";

    /**
     * 得到新的时间格式
     * @param pattern   匹配表达式
     * @return          新的时间格式
     */
    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);

        df.setLenient(false);
        return df;
    }

    /**
     * 时间格式
     * @param date      格式化前的时间
     * @param format    需要转换的格式
     * @return          格式化后的时间
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }
    /**
     * 将时间yyyy-MM-dd HH:mm:ss转换成Date格式
     * @param sDate 格式化前的日期
     * @return      格式化后的日期
     */
    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(NEW_FORMAT);
        Date d = null;
        if (sDate != null) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                throw new RuntimeException("将时间转换为yyyy-MM-dd HH:mm:ss格式异常 格式转化前字符串=" + sDate, ex);
            }
        }
        return d;
    }


}
