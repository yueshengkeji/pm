package com.yuesheng.pm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class StrUtils {
    public static ObjectWriter objectWriter;

    static {
        objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    /**
     * url字符串解码
     *
     * @param str
     * @return
     */
    public static String decodeStr(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException | NullPointerException e) {
            return "";
        }
    }

    /**
     * 空字符串转为null
     *
     * @param str
     * @return
     */
    public static String emptyToNull(String str) {
        try {
            return "".equals(str.trim()) ? null : str;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 去除字符数组中：空格，换行符
     *
     * @param strList
     * @return
     */
    public static ArrayList<String> replaceEmpty(List<String> strList) {
        ArrayList<String> result = new ArrayList<>();
        strList.forEach(item -> {
            item = org.apache.commons.lang3.StringUtils.replaceChars(item, " ", "");
            item = org.apache.commons.lang3.StringUtils.replaceChars(item, "\n", "");
            item = org.apache.commons.lang3.StringUtils.replaceChars(item, "\r", "");
            result.add(item);
        });
        return result;
    }


    /**
     * 转换为String数组<br>
     *
     * @param str 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    /**
     * 转换为String数组<br>
     *
     * @param split 分隔符
     * @param split 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String split, String str) {
        return str.split(split);
    }

    public static String toString(Object jsonResult) {
        try {
            return objectWriter.writeValueAsString(jsonResult);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
