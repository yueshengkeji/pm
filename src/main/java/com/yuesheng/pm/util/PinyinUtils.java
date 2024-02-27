package com.yuesheng.pm.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.StringUtils;

public class PinyinUtils {

    public static String getPinYinHeadChar(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            }
            else {
                convert += word;
            }
        }
        convert = StringUtils.trim(convert);
        return StringUtils.upperCase(convert);
    }
}
