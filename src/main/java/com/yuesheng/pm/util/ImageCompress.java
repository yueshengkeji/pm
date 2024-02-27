package com.yuesheng.pm.util;

import com.yuesheng.pm.listener.WebParam;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by 96339 on 2016/12/23.
 * 缩略图实现，将图片(jpg、bmp、png、gif等等)真实的变成想要的大小
 * *******************************************************************************
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 *
 * @author XiaoSong
 * @date 2016/12/23
 */
public class ImageCompress {


    /**
     * 输出图路径
     */
    private String outputDir;
    /**
     * 输出图文件名
     */
    private String outputFileName;
    /**
     * 默认输出图片宽
     */
    private int outputWidth = 775;
    /**
     * 默认输出图片高
     */
    private int outputHeight = 1237;
    /**
     * 是否等比缩放标记(默认为等比缩放)
     */
    private boolean proportion = true;

    public static synchronized ImageCompress instanceImage() {
        ImageCompress ic = new ImageCompress();
        ic.outputDir = WebParam.webRootPath + "assets" + File.separator + "ftptemp" + File.separator;
        return ic;
    }

    private ImageCompress() {

    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
    }

    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
    }

    public void setWidthAndHeight(int width, int height) {
        this.outputWidth = width;
        this.outputHeight = height;
    }

    /**
     * 图片处理
     * 如果proportion为false时，大图片容易内存溢出（暂时没解决，解决方案是把int类型的高宽度改成long）
     *
     * @param fileStream
     * @return
     */
    private byte[] compressPic(InputStream fileStream) {
        File f = new File(this.outputDir + this.outputFileName);
        FileInputStream fis = null;
        try {
            //Thumbnails 框架压缩
            Thumbnails.of(fileStream).scale(0.5f).outputQuality(0.5f).toFile(f);
            fis = new FileInputStream(f);
            byte[] bytes = IOUtils.toByteArray(fis);
            return bytes;
        } catch (IOException ex) {
            LoggerFactory.getLogger(ImageCompress.class).error(ex.getMessage());
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(fileStream);
        }
        return new byte[0];
    }

    /**
     * 压缩图片
     *
     * @param outputFileName 输出文件名
     * @param stream         流对象
     * @return
     */
    public byte[] compressPic(String outputFileName, InputStream stream) {
        // 输出图文件名
        this.outputFileName = outputFileName;
//        setWidthAndHeight(width, height);
        // 是否是等比缩放 标记
//        this.proportion = gp;
        // 设置图片长宽
        return compressPic(stream);
    }

    public void compressMaxPic(String outputFileName, InputStream stream) {
        File f = new File(this.outputDir + outputFileName);
        try {
            //Thumbnails 框架压缩
            Thumbnails.of(stream).scale(0.1).outputQuality(0.1f).toFile(f);
        } catch (IOException ex) {
            LoggerFactory.getLogger(ImageCompress.class).error(ex.getMessage());
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public boolean isProportion() {
        return proportion;
    }

    public void setProportion(boolean proportion) {
        this.proportion = proportion;
    }

    public void compressMaxPic(ByteArrayOutputStream outputStream, byte[] imgBytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
        try {
            Thumbnails.of(bais).scale(0.6).outputQuality(0.6f).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(bais);
        }

    }
}
