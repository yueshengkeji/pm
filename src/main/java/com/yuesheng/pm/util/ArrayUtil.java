package com.yuesheng.pm.util;

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
}
