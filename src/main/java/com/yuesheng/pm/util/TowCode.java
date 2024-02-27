package com.yuesheng.pm.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yuesheng.pm.listener.WebParam;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by 96339 on 2017/12/21.
 *
 * @author XiaoSong
 * @date 2017/12/21
 */
public class TowCode {

    private static int width = 300;
    private static int height = 300;
    private static String format = "png";

    /**
     * 生成二维码
     *
     * @throws WriterException
     * @throws IOException
     */
    public static void toEncode(String path, String content) throws WriterException, IOException {
//        定义二维码的参数
        HashMap hashMap = new HashMap(16);
//        字符编码
        hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        纠错等级
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hashMap.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hashMap);
            File file = new File(path);
            MatrixToImageWriter.writeToFile(bitMatrix, format, file, WebParam.webRootPath + "assets/adminex/images/zm_logo.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码
     *
     * @throws WriterException
     * @throws IOException
     */
    public static void toEncode(OutputStream os, String content, int width, int height) {
//        定义二维码的参数
        HashMap hashMap = new HashMap(16);
//        字符编码
        hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        纠错等级
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hashMap.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hashMap);
            MatrixToImageWriter.writeToStream(bitMatrix, format, os, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
