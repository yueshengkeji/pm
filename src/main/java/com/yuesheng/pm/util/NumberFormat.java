package com.yuesheng.pm.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author XiaoSong
 * @date 2019/07/06
 */
public class NumberFormat {
    public static Double formatDouble(Double number) {
        return new Double(new DecimalFormat("#.00").format(number));
    }

    public static String createSeries(String series) {
        if (org.apache.commons.lang3.StringUtils.isBlank(series) || org.apache.commons.lang3.StringUtils.length(series) < 3) {
            series = DateUtil.format(new Date(), DateUtil.PATTER_IMAGE_DIRECTORY) + "001";
        } else {
            String temp = org.apache.commons.lang3.StringUtils.substring(series, series.length() - 3, series.length());
            series = org.apache.commons.lang3.StringUtils.substring(series, 0, series.length() - 3);
            int num = Integer.parseInt(temp) + 1;
            temp = String.valueOf(num);
            num = temp.length();
            for (int i = 0; i < (3 - num); i++) {
                temp = org.apache.commons.lang3.StringUtils.join("0", temp);
            }
            series = StringUtils.join(series, temp);
        }
        return series;
    }
}
