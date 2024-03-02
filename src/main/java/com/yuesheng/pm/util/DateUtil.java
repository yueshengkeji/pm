package com.yuesheng.pm.util;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: spc
 * @description: 日期工具类：用于日期相关的处理
 * @author: zcj
 * @create: 2018-03-01 10:42
 **/
public class DateUtil {
    // Grace style
    public static final String PATTERN_GRACE = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_GRACE_NORMAL = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_GRACE_SIMPLE = "yyyy/MM/dd";

    // Classical style
    public static final String PATTERN_CLASSICAL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_CLASSICAL_NORMAL = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_CLASSICAL_SIMPLE = "yyyy-MM-dd";

    public final static String PATTERN_ON_TIME = "HH:mm";

    /**
     * 图片命名格式
     */
    public static final String PATTERN_IMAGE_NAME = "yyyyMMddHHmmss";
    public static final String PATTER_IMAGE_DIRECTORY = "yyyyMMdd";

    /**
     * yyyy-MM-dd校验格式
     */
    public final static String PATTERN_CHECK_CLASSICAL_SIMPLE = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    /**
     * yyyy-MM-dd HH:mm:ss校验格式
     */
    public final static String PATTERN_CHECK_CLASSICAL = "((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))";
    public static final String PATTERN_ON_TIME2 = "HH:mm:ss";


    private DateUtil() {
        // Cannot be instantiated
    }

    /**
     * 根据默认格式将指定字符串解析成日期
     *
     * @param str 指定字符串
     * @return 返回解析后的日期
     */
    public static Date parse(String str) {
        return parse(str, PATTERN_CLASSICAL);
    }

    /**
     * 根据指定格式将指定字符串解析成日期
     *
     * @param str     指定日期
     * @param pattern 指定格式
     * @return 返回解析后的日期
     */
    public static Date parse(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 毫秒转换为指定格式的日期
     *
     * @param millis
     * @param patten
     * @return
     */
    public static String millisecondToDate(long millis, String patten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(patten);
        String dateString = format.format(date);
        return dateString;
    }

    /**
     * 根据默认格式将日期转格式化成字符串
     *
     * @param date 指定日期
     * @return 返回格式化后的字符串
     */
    public static String format(Date date) {
        return format(date, PATTERN_CLASSICAL);
    }

    /**
     * 根据指定格式将指定日期格式化成字符串
     *
     * @param date    指定日期
     * @param pattern 指定格式
     * @return 返回格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 获取时间date1与date2相差的秒数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的秒数
     */
    public static int getOffsetSeconds(Date date1, Date date2) {
        int seconds = (int) ((date2.getTime() - date1.getTime()) / 1000);
        return seconds;
    }

    /**
     * 获取时间date1与date2相差的分钟数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的分钟数
     */
    public static int getOffsetMinutes(Date date1, Date date2) {
        return getOffsetSeconds(date1, date2) / 60;
    }

    /**
     * 获取时间date1与date2相差的小时数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的小时数
     */
    public static int getOffsetHours(Date date1, Date date2) {
        return getOffsetMinutes(date1, date2) / 60;
    }

    /**
     * 获取时间date1与date2相差的天数数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的天数
     */
    public static int getOffsetDays(Date date1, Date date2) {
        return getOffsetHours(date1, date2) / 24;
    }

    /**
     * 获取时间date1与date2相差的天数数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的天数
     */
    public static int getOffsetDays(String date1, String date2) {
        return getOffsetHours(parse(date1), parse(date2)) / 24;
    }

    /**
     * 获取时间date1与date2相差的周数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     * @return 返回相差的周数
     */
    public static int getOffsetWeeks(Date date1, Date date2) {
        return getOffsetDays(date1, date2) / 7;
    }

    /**
     * 获取本周周一日期
     *
     * @return
     */
    public static LocalDate toWeekFirstDate() {
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 使用TemporalAdjusters获取本周一的日期
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        return monday;
    }

    public static String format(LocalDate localDate) {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_CLASSICAL_SIMPLE);

        return localDate.format(formatter);
    }

    public static String format(LocalDate localDate, String pattern) {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        return localDate.format(formatter);
    }

    /**
     * 获取重置指定日期的时分秒后的时间
     *
     * @param date   指定日期
     * @param hour   指定小时
     * @param minute 指定分钟
     * @param second 指定秒
     * @return 返回重置时分秒后的时间
     */
    public static Date getResetTime(Date date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, minute);
        cal.set(Calendar.MINUTE, second);
        return cal.getTime();
    }

    /**
     * 返回指定日期的起始时间
     *
     * @param date 指定日期（例如2014-08-01）
     * @return 返回起始时间（例如2014-08-01 00:00:00）
     */
    public static Date getIntegralStartTime(Date date) {
        return getResetTime(date, 0, 0, 0);
    }

    /**
     * 返回指定日期的结束时间
     *
     * @param date 指定日期（例如2014-08-01）
     * @return 返回结束时间（例如2014-08-01 23:59:59）
     */
    public static Date getIntegralEndTime(Date date) {
        return getResetTime(date, 23, 59, 59);
    }

    /**
     * 获取指定日期累加年月日后的时间
     *
     * @param date  指定日期
     * @param year  指定年数
     * @param month 指定月数
     * @param day   指定天数
     * @return 返回累加年月日后的时间
     */
    public static Date rollDate(Date date, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 获取指定日期累加分钟后的时间
     *
     * @param date   指定日期
     * @param minute 指定分钟数
     * @return 返回累加分钟后的时间
     */
    public static Date rollDate(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * 获取指定日期累加秒数后的时间
     *
     * @param date   指定日期
     * @param second 指定秒数
     * @return 返回累加分钟后的时间
     */
    public static Date rollDateToSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 获取指定日期累加指定月数后的时间
     *
     * @param date  指定日期
     * @param month 指定月数
     * @return 返回累加月数后的时间
     */
    public static Date rollMonth(Date date, int month) {
        return rollDate(date, 0, month, 0);
    }

    /**
     * 获取指定日期累加指定天数后的时间
     *
     * @param date 指定日期
     * @param day  指定天数
     * @return 返回累加天数后的时间
     */
    public static Date rollDay(Date date, int day) {
        return rollDate(date, 0, 0, day);
    }

    /**
     * 计算指定日期所在月份的天数
     *
     * @param date 指定日期
     * @return 返回所在月份的天数
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        int dayOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dayOfMonth;
    }

    /**
     * 获取当月第一天的起始时间，例如2014-08-01 00:00:00
     *
     * @return 返回当月第一天的起始时间
     */
    public static Date getMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }

    public static Calendar parse(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }


    public static Date getMonthStartTime(Date date) {
        // 创建 Calendar 对象并设置为当前时间
        Calendar calendar = Calendar.getInstance();

        Calendar than = parse(date);

        // 设置要查询的年、月（从0开始计数）
        int year = than.get(Calendar.YEAR);
        int month = than.get(Calendar.MONTH);

        // 将 Calendar 对象设置为指定的年、月
        calendar.set(year, month, 1);

        return calendar.getTime();
    }

    /**
     * 获取指定时间的月份中最后一天日期
     * @param date
     * @return
     */
    public static Date getMonthEndTime(Date date) {
        // 创建 Calendar 对象并设置为当前时间
        Calendar calendar = Calendar.getInstance();

        Calendar than = parse(date);

        // 设置要查询的年、月（从0开始计数）
        int year = than.get(Calendar.YEAR);
        int month = than.get(Calendar.MONTH);

        // 将 Calendar 对象设置为指定的年、月
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth(than.getTime()));

        return calendar.getTime();
    }

    /**
     * 获取当月最后一天的结束时间，例如2014-08-31 23:59:59
     *
     * @return 返回当月最后一天的结束时间
     */
    public static Date getMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }

    /**
     * 获取上个月第一天的起始时间，例如2014-07-01 00:00:00
     *
     * @return 返回上个月第一天的起始时间
     */
    public static Date getLastMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取上个月最后一天的结束时间，例如2014-07-31 23:59:59
     *
     * @return 返回上个月最后一天的结束时间
     */
    public static Date getLastMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }

    /**
     * 获取下个月第一天的起始时间，例如2014-09-01 00:00:00
     *
     * @return 返回下个月第一天的起始时间
     */
    public static Date getNextMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取下个月最后一天的结束时间，例如2014-09-30 23:59:59
     *
     * @return 返回下个月最后一天的结束时间
     */
    public static Date getNextMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }

    /**
     * 获取当前季度第一天的起始时间
     *
     * @return 返回当前季度第一天的起始时间
     */
    public static Date getQuarterStartTime() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        if (month < 3) {
            cal.set(Calendar.MONTH, 0);
        } else if (month < 6) {
            cal.set(Calendar.MONTH, 3);
        } else if (month < 9) {
            cal.set(Calendar.MONTH, 6);
        } else {
            cal.set(Calendar.MONTH, 9);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取当前季度最后一天的结束时间
     *
     * @return 返回当前季度最后一天的结束时间
     */
    public static Date getQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        if (month < 3) {
            cal.set(Calendar.MONTH, 2);
        } else if (month < 6) {
            cal.set(Calendar.MONTH, 5);
        } else if (month < 9) {
            cal.set(Calendar.MONTH, 8);
        } else {
            cal.set(Calendar.MONTH, 11);
        }
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }

    /**
     * 获取前一个工作日
     *
     * @return 返回前一个工作日
     */
    public static Date getPrevWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -2);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取下一个工作日
     *
     * @return 返回下个工作日
     */
    public static Date getNextWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 2);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取当周的第一个工作日
     *
     * @return 返回第一个工作日
     */
    public static Date getFirstWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 获取当周的最后一个工作日
     *
     * @return 返回最后一个工作日
     */
    public static Date getLastWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return getIntegralStartTime(cal.getTime());
    }

    /**
     * 判断指定日期是否是工作日
     *
     * @param date 指定日期
     * @return 如果是工作日返回true，否则返回false
     */
    public static boolean isWorkday(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return !(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    /**
     * 获取指定日期是星期几
     *
     * @param date 指定日期
     * @return 返回星期几的描述
     */
    public static String getWeekdayDesc(Date date) {
        final String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四",
                "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        return weeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取指定日期距离当前时间的时间差描述（如3小时前、1天前）
     *
     * @param date 指定日期
     * @return 返回时间差的描述
     */
    public static String getTimeOffsetDesc(Date date) {
        int seconds = getOffsetSeconds(date, new Date());
        if (Math.abs(seconds) < 60) {
            return Math.abs(seconds) + "秒" + (seconds > 0 ? "前" : "后");
        }
        int minutes = seconds / 60;
        if (Math.abs(minutes) < 60) {
            return Math.abs(minutes) + "分钟" + (minutes > 0 ? "前" : "后");
        }
        int hours = minutes / 60;
        if (Math.abs(hours) < 60) {
            return Math.abs(hours) + "小时" + (hours > 0 ? "前" : "后");
        }
        int days = hours / 24;
        if (Math.abs(days) < 7) {
            return Math.abs(days) + "天" + (days > 0 ? "前" : "后");
        }
        int weeks = days / 7;
        if (Math.abs(weeks) < 5) {
            return Math.abs(weeks) + "周" + (weeks > 0 ? "前" : "后");
        }
        int monthes = days / 30;
        if (Math.abs(monthes) < 12) {
            return Math.abs(monthes) + "个月" + (monthes > 0 ? "前" : "后");
        }
        int years = monthes / 12;
        return Math.abs(years) + "年" + (years > 0 ? "前" : "后");
    }

    public static Integer[] diff(Date startDate, Date endDate) {
        return diff(startDate.getTime(), endDate.getTime());
    }

    public static String dateDiff(Date startDate, Date endDate) {
        return dateDiff(startDate.getTime(), endDate.getTime());
    }

    public static Integer[] workDiff(Date startDate, Date endDate) {
        return d(startDate.getTime(), endDate.getTime(), 9);
    }

    private static Integer[] d(long startTime, long endTime, Integer dayHouse) {
        long nd = 1000 * dayHouse * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff = endTime - startTime;

        Integer day = Integer.valueOf(diff / nd + "");//计算差多少天
        Integer hour = Integer.valueOf(diff % nd / nh + "");//计算差多少小时
        Integer min = Integer.valueOf(diff % nd % nh / nm + "");//计算差多少分钟
        return new Integer[]{day, hour, min};
    }

    public static Integer[] diff(long startTime, long endTime) {
        return d(startTime, endTime, 24);

    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static String dateDiff(long startTime, long endTime) {
        Integer[] days = diff(startTime, endTime);
        Integer day = days[0];
        Integer hour = days[1];
        Integer min = days[2];
        String str = "";

        if (day > 0) {
            str += (day + "天");
        }

        if (hour > 0) {
            if (day > 0) {
                str += (hour + "时");
            } else {
                str += (hour + "小时");
            }
        }

        if (min > 0) {
            if (day > 0) {
                str += (min + "分");
            } else {
                str += (min + "分钟");
            }

        }

        if (StringUtils.isBlank(str)) {
            str = "< 1分钟";
        }

        return str;

    }

//    public static int getCycles(int cycHours, long begin, long end) {
//        int cycle = 1;
//        long nh = 1000 * 60 * 60;
//        long cycMis = cycHours*nh;
//        long diff = end - begin;
//
//        if(diff > cycMis) {
//            if(diff%cycMis == 0) {
//                cycle = (int)(diff/cycMis);
//            }else {
//                cycle = (int)(diff/cycMis) + 1;
//            }
//        }
//        return cycle;
//    }

//    public static void main(String[] args) {
//        System.out.println(getCycles(12, 1539964800000L, 1539964800000L));
//    }

    /**
     * 获取当前精确到秒的时间
     *
     * @return
     */
    public static Date getNowDate() {
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_CLASSICAL);
        String now = df.format(new Date());
        Date d;
        try {
            d = df.parse(now);
        } catch (ParseException e) {
            d = null;
            e.printStackTrace();
        }
        return d;
    }

    public static String getDatetime() {
        return format(getNowDate());
    }

    public static String getDate() {
        return format(getNowDate(), PATTERN_CLASSICAL_SIMPLE);
    }

    /**
     * 判断租期是否在过期前后5天以内
     *
     * @param end 租期结束时间
     * @return true:在5天以内，false:在5天以外
     */
    public static boolean isLessDays(Date end, Integer day) {
        if (day == null || day <= 0) {
            day = 5;
        }

        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //将时分秒域清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        now = calendar.getTime();
        long startTime = now.getTime();

        //将结束时间的时分秒设为24
        long endTime = end.getTime() + (3600 * 24 * 1000 - 1);

        //获取差值
        long diff;

        if (startTime > endTime)
            diff = startTime - endTime;
        else
            diff = endTime - startTime;

        int diffDay = (int) (diff / (1000 * 60 * 60 * 24));

        if (diffDay <= day) return true;
        else return false;
    }

    /**
     * 检测字符串是否符合日期格式书写
     *
     * @param dateStr 需要检测的字符串(可为空，若为空，则为false)
     * @param type    需要检测的格式的正则表达式,主要两种 yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
     * @return true:验证成功，false:验证失败
     */
    public static boolean checkDateStr(String dateStr, String type) {
        boolean flag = false;
        if (StringUtils.isNotBlank(dateStr)) {
            //创建正则表达式规则验证
            Pattern p = Pattern.compile(type);
            Matcher m = p.matcher(dateStr.trim());
            flag = m.find();
        }
        return flag;
    }

    public static String getWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
    }

    /**
     * 获取本年第一天日期
     *
     * @return
     */
    public static String getYearFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        return format(calendar.getTime(), PATTERN_CLASSICAL_SIMPLE);
    }

    public static String getYearFirstDay(String year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        return format(calendar.getTime(), PATTERN_CLASSICAL_SIMPLE);
    }

    public static String getYearEndDay(String year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 32);
        return format(calendar.getTime(), PATTERN_CLASSICAL_SIMPLE);
    }

    public static boolean isToDayBefore(Date workDate) {
        Integer date = Integer.parseInt(format(workDate, PATTER_IMAGE_DIRECTORY));
        Integer before = Integer.parseInt(format(new Date(), PATTER_IMAGE_DIRECTORY));
        return date >= before;
    }

    public static int getMonth() {
        return Integer.valueOf(format(new Date(), "M"));
    }

    public static String getProcurementDate() {
        Date d = new Date();
        Integer time = Integer.parseInt(format(d, "MMdd"));
        String date = null;
        if (time >= 1229 && time <= 1231) {
            //当前年份加1
            date = Integer.parseInt(DateUtil.format(d, "yyyy")) + 1 + "-01-01";
        } else {
            date = format(d, PATTERN_CLASSICAL_SIMPLE);
        }
        return date;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("");
        FileTime ft = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class).creationTime();

        System.out.println(DateUtil.format(Date.from(ft.toInstant()), DateUtil.PATTERN_CLASSICAL_SIMPLE));
    }

    public static String getYear() {
        return format(new Date(), "yyyy");
    }

    public static String getTime() {
        return format(new Date(), PATTERN_ON_TIME2);
    }

    public static int getHour(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d.getTime());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String formatDate(Date date) {
        return format(date, PATTERN_CLASSICAL_SIMPLE);
    }

    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month);
        // 获取某月最大天数
        int lastDay = cal.getMinimum(Calendar.DATE);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay - 1);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 判断两个日期是否在同一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth();
    }

    /**
     * 获取指定日期（号数）的时间
     *
     * @param payDay
     * @return
     */
    public static Date getDateByDay(String payDay) {
        LocalDate ld = LocalDate.now();
        ld = ld.withDayOfMonth(Integer.valueOf(payDay));
        return Date.from(Timestamp.valueOf(ld.atTime(LocalTime.MIDNIGHT)).toInstant());
    }

    public static Date setMonth(Date date, int month,int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getTime();
    }
}
