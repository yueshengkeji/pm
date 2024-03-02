package com.yuesheng.pm.util;

import java.util.Objects;

/**
 * Created by 96339 on 2017/3/8.
 * @author XiaoSong
 * @date 2017/03/08
 * 数组工具类
 */
public class ArrayUtil {
    /**
     * 判断数组中是否存在某个值
     */
    public static boolean isExists(String[] array,String value){
        try {
            for (String temp : array){
                if(temp.equals(value)){
                    return true;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static String join(String[] str,String seq,String sep){
        if(Objects.isNull(str)){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String s : str){
            sb.append(sep);
            sb.append(s);
            sb.append(sep);
            sb.append(seq);
        }
        if(sb.length() > 0){
            return sb.substring(0,sb.length() - 1).toString();
        }else{
            return null;
        }
    }
}
