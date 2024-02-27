package com.yuesheng.pm.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by 96339 on 2017/12/21.本类用于对我们二维码进行参数的设定,生成我们的二维码
 * @author XiaoSong
 * @date 2017/12/21
 */
public class QrCodeFactory {
    private int bw = 5;

    /**
     * 给生成的二维码添加中间的logo
     *
     * @param matrixImage 生成的二维码
     * @param logUri      logo地址
     * @return 带有logo的二维码
     * @throws IOException logo地址找不到会有io异常
     */
    public BufferedImage setMatrixLogo(BufferedImage matrixImage, String logUri) throws IOException {
        /*
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /*
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(new File(logUri));

        //开始绘制图片
        g2.drawImage(logo, matrixWidth / bw * 2, matrixHeigh / bw * 2, matrixWidth / bw, matrixHeigh / bw, null);
        //绘制边框
        BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke);
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / bw * 2, matrixHeigh / bw * 2, matrixWidth / bw, matrixHeigh / bw, 20, 20);
        g2.setColor(Color.white);
        // 绘制圆弧矩形
        g2.draw(round);

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // 设置笔画对象
        g2.setStroke(stroke2);
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / bw * 2 + 2, matrixHeigh / bw * 2 + 2, matrixWidth / bw - 4, matrixHeigh / bw - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
//        绘制圆弧矩形
        g2.draw(round2);


        g2.dispose();
        matrixImage.flush();
        return matrixImage;

    }


    /**
     * 创建我们的二维码图片
     *
     * @param content    二维码内容
     * @param format     生成二维码的格式
     * @param outFileUri 二维码的生成地址
     * @param logUri     二维码中间logo的地址
     * @param size       用于设定图片大小（可变参数，宽，高）
     * @throws IOException     抛出io异常
     * @throws WriterException 抛出书写异常
     */
    public void CreatQrImage(String content, String format, String outFileUri, String logUri, int... size) throws IOException, WriterException {
//        二维码图片宽度 430
        int width = 430;
//         二维码图片高度430
        int height = 430;
        //如果存储大小的不为空，那么对我们图片的大小进行设定
        if (size.length == 2) {
            width = size[0];
            height = size[1];
        } else if (size.length == 1) {
            width = height = size[0];
        }
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
//        要编码的内容
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints);

        // 生成二维码图片文件
        File outputFile = new File(outFileUri);
        //指定输出路径
        System.out.println("输出文件路径为" + outputFile.getPath());
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile, logUri);
    }

}
