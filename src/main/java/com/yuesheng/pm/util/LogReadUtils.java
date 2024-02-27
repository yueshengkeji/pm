package com.yuesheng.pm.util;

import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 96339 on 2017/8/2 日志查询工具.
 * @author XiaoSong
 * @date 2017/08/02
 */
public class LogReadUtils {

    /**
     * 查询
     *
     * @param str 查询的字符串
     * @return
     */
    @Deprecated
    public static List<String> getLogByLine(String str, String day, int number, int count) {
        /*List<String> result = new ArrayList<>();
        String temp;
        int i = 0;
        RandomAccessFile lnr = null;
        try {
            String fileName;
            if (StringUtils.isNotBlank(day)) {
                fileName = "_" + day + ".log";
                fileName = WebParam.LOG_INFO_BY_USER + fileName;
            } else {
                fileName = WebParam.LOG_INFO_BY_USER;
            }
            lnr = new RandomAccessFile(fileName, "r");
            lnr.seek(number);
            while ((temp = lnr.readLine()) != null && (i < 1000 || i == count)) {
                temp = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
                if (temp.contains(str)) {
                    result.add(temp);
                    i++;
                }
            }
        } catch (IOException ignored) {

        } finally {
            IOUtils.closeQuietly(lnr);
        }
        return result;*/
        return null;
    }

    /**
     * 获取日志集合
     *
     * @param day 查询的日期
     * @return
     */
    @Deprecated
    public static List<String> getLogByDay(String day, int number, int count) {
        /*String fileName;
        int i = 0;
        List<String> result = new ArrayList();
        if (day != null) {
            fileName = "_" + day + ".log";
            fileName = WebParam.LOG_INFO_BY_USER + fileName;
        } else {
            fileName = WebParam.LOG_INFO_BY_USER;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "r");
//            設置指針位置
            raf.seek(number);
            while ((day = raf.readLine()) != null && (i < 1000 || i == count)) {
                day = new String(day.getBytes("ISO-8859-1"), "UTF-8");
                result.add(day);
                i++;
            }
        } catch (FileNotFoundException e) {
            LogManager.getLogger("ERROR").error(e);
            return null;
        } catch (IOException ignore) {
            LogManager.getLogger("ERROR").error(ignore);
            return null;
        } finally {
            IOUtils.closeQuietly(raf);
        }
        return result;*/
        return null;
    }

    /**
     * 获取异常信息
     *
     * @param searchText 检索串
     * @return
     */
    public static List<String> getLogByError(String searchText, String day) {
        /*List<String> result = new ArrayList<>();
        String temp;
        LineNumberReader errorLnr = null;
        try {
            String fileName;
            if (StringUtils.isNotBlank(day)) {
                fileName = WebParam.LOG_ERROR_BY_SYSTEM + "." + day;
            } else {
                fileName = WebParam.LOG_ERROR_BY_SYSTEM;
            }
            errorLnr = new LineNumberReader(new FileReader(fileName));
            while ((temp = errorLnr.readLine()) != null) {
                temp = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
                result.add(disposeErrorLog(temp));
            }
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(errorLnr);
        }
        return result;*/
        return null;
    }

    /**
     * 处理异常信息
     *
     * @param temp 异常信息串
     * @return json串
     */
    private static String disposeErrorLog(String temp) {
        StringBuffer sb = new StringBuffer();
        int start = temp.indexOf("[");
        int end = temp.lastIndexOf("]");
        start = start == -1 ? 0 : start;
        end = end == -1 ? temp.length() : end;
        sb.append("{\"");
        sb.append("datetime\":");
        try {
            sb.append(temp.substring(0, 20));
        } catch (StringIndexOutOfBoundsException e) {
            sb.append(temp);
        }
        sb.append("\",");
        sb.append("\"params\":\"");
        sb.append(temp.substring(start, end));
        sb.append("\"}");
        return sb.toString();
    }
}
