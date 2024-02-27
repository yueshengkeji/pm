package com.yuesheng.pm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016-08-16 时间处理类.
 * @author XiaoSong
 * @date 2016/08/16
 */
public class DateFormat {

    /**
     * 获取最新的日期+时间
     * @return
     */
    public static String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public static String getDateForNumber(){
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    public static Date parseData(String data) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyy-MM-dd");
            return dateFormat.parse(data);
        } catch (ParseException e) {
            System.out.println("时间转换失败："+e.getMessage());
        }
        return new Date();
    }

    public static String parseString(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        return dateFormat.format(time);
    }

    public static String getFirstDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
//        设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = dateFormat.format(c.getTime());
        return first;
    }

    public static String getYear(){
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy");
        return dateFormat.format(new Date());
    }

    public static String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
