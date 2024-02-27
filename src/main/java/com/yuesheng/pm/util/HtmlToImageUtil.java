package com.yuesheng.pm.util;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import com.yuesheng.pm.entity.ProMaterial;
import com.yuesheng.pm.entity.Procurement;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

/**
 * html转image工具
 */
public class HtmlToImageUtil {

    private static ResourceLoader resourceLoader;
    private static int rowLength = 30;



    /**
     * 转换为imag
     *
     * @throws IOException
     */
    public static byte[] convertToImage(org.w3c.dom.Document document, int imageWidth, int imageHeight) throws IOException {
        Java2DRenderer renderer = new Java2DRenderer(document, imageWidth, imageHeight);
        final BufferedImage img = renderer.getImage();
        final FSImageWriter imageWriter = new FSImageWriter();
        ByteOutputStream bos = new ByteOutputStream();
        imageWriter.write(img, bos);
        IOUtils.closeQuietly(bos);
        return bos.getBytes();
    }

    public static byte[] convertProcurementToImage(Procurement procurement, int imageWidth, int imageHeight) throws IOException {

        Document document = null;
        try{
            if(resourceLoader == null){
                resourceLoader = WebParam.webApplicationContext.getBean(ResourceLoader.class);
            }
            document = Jsoup.parse(resourceLoader.getResource(WebParam.assetsPath + "formviews" + File.separator + "pmDetail.html").getFile(),"UTF-8","http://127.0.0.1:8081");
        }catch (Exception e){
            LogManager.getLogger(HtmlToImageUtil.class).error("未找到采购凭证模版文件");
            return new byte[0];
        }
        Element element = document.body();

        element.getElementById("company-msg").text(procurement.getCompany().getName());
        if (Objects.isNull(procurement.getContract())) {
            element.getElementById("settleType-msg").text("-");
        } else {
            element.getElementById("settleType-msg").text(procurement.getContract().getName());
        }
        if (!Objects.isNull(procurement.getCity())) {
            element.getElementById("detail-address").text(procurement.getCity().getName());
        } else {
            element.getElementById("detail-address").text("-");
        }
        element.getElementById("tax-msg").text(String.valueOf(procurement.getTax()));
        element.getElementById("pro-data").text(procurement.getPmDate());
        element.getElementById("detailStaff").text(procurement.getStaff().getName());
        element.getElementById("saleMoney").text(procurement.getSaleMoney());
        byte[] afb = null;
        FileInputStream fis;
        if (procurement.getPm01326().equals("")) {
            // element.getElementById("approve-flag-img").attr("src","/api/assets/img/approve.png");
            fis = new FileInputStream(resourceLoader.getResource(WebParam.assetsPath + "img" + File.separator + "approve.png").getFile());
            afb = IOUtils.toByteArray(fis);
            element.getElementById("pro-company").text("默认采购");
        } else {
            fis = new FileInputStream(resourceLoader.getResource(WebParam.assetsPath + "img" + File.separator+"approve1.png").getFile());
            afb = IOUtils.toByteArray(fis);
            element.getElementById("pro-company").text("其他采购");
        }
        IOUtils.closeQuietly(fis);
        int nh = 0, mh = 0, rowHeightSum = 0;
        if (!Objects.isNull(procurement.getMaterial())) {
            Double moneySum = 0.0;
            int x = 1;
            Element materialBody = element.getElementById("material");
            StringBuffer sb = new StringBuffer();
            String projectName = "";
            for (ProMaterial proMaterial : procurement.getMaterial()) {
                moneySum += proMaterial.getMoneyTax();
                sb.append("<tr>");
                sb.append("<td>");
                sb.append(x);
                sb.append("</td>");
                String mn = proMaterial.getMaterial().getName();
                sb.append("<td>");
                nh = setHeight(sb, mn);
                sb.append("</div>");
                sb.append("</td>");
                sb.append("<td>");
                mh = setHeight(sb, proMaterial.getMaterial().getModel());
                if (mh > nh) {
                    nh = mh;
                }

                if (nh <= 0) {
                    rowHeightSum += 24;
                } else {
                    rowHeightSum += nh;
                }
                sb.append("</td>");
                sb.append("<td>");
                sb.append(proMaterial.getMaterial().getUnit().getName());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(proMaterial.getMaterial().getBrand());
                sb.append("</td>");

                sb.append("<td>");
                sb.append(proMaterial.getSum());
                sb.append("</td>");

                sb.append("<td>");
                sb.append(proMaterial.getPriceTax());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(proMaterial.getMoneyTax());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(proMaterial.getTaxMoney());
                sb.append("</td>");
                sb.append("</tr>");
                materialBody.append(sb.toString());
                sb.delete(0, sb.length());
                imageHeight += 35;
                if (x == procurement.getMaterial().size()) {
                    if (!Objects.isNull(proMaterial.getProject()) && StringUtils.isNotBlank(proMaterial.getProject().getName())) {
                        projectName = proMaterial.getProject().getName();
                    }
                }
                x++;
            }
            element.getElementById("project-remark").text(projectName);
            element.getElementById("msg-count").text(moneySum + "元");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TowCode.toEncode(baos, WebParam.NOTIFY_IP + WebParam.VUETIFY_BASE+"/procurement/express/" + procurement.getId(), 200, 200);
        byte[] codeByte = baos.toByteArray();
        rowHeightSum += 400 + (procurement.getMaterial().size() / 10 * 200);
        byte[] sourceImage = convertToImage(new W3CDom().fromJsoup(document), imageWidth, rowHeightSum);
        byte[] approveImg = mergeImg(sourceImage, codeByte, 210, 210);
        approveImg = mergeImg(approveImg, afb, 600, 150);
        approveImg = drawText("扫码上传物流、发票等信息", approveImg, 180, 18);
        return approveImg;
    }

    private static byte[] drawText(String text, byte[] sourceImg, int offsetX, int offsetY) {
        ByteArrayInputStream bais1 = new ByteArrayInputStream(sourceImg);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = null;
        try {
            image = ImageIO.read(bais1);
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setColor(Color.black);
            graphics2D.drawString(text, image.getWidth() - offsetX, image.getHeight() - offsetY);
            graphics2D.dispose();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bais1);
            IOUtils.closeQuietly(baos);
        }
        return sourceImg;
    }

    private static int setHeight(StringBuffer sb, String mn) {
        int h = (mn.length() / rowLength) * 24;
        sb.append("<div style='max-width:100%;height:" + h + "px'>");
        for (int i = 0; i < mn.length(); i = i + rowLength) {
            if ((i + rowLength) >= mn.length()) {
                sb.append(mn.substring(i));
            } else {
                sb.append(mn.substring(i, i + rowLength) + "</br>");
            }
        }
        return h;
    }

    private static byte[] mergeImg(byte[] sourceImg, byte[] codeImg, int offsetX, int offsetY) {
        ByteArrayInputStream bais1 = new ByteArrayInputStream(sourceImg);
        ByteArrayInputStream bais2 = new ByteArrayInputStream(codeImg);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi = ImageIO.read(bais1);
            BufferedImage bi2 = ImageIO.read(bais2);
            Graphics2D graphics2D = bi.createGraphics();
            int width = bi.getWidth();
            int height = bi.getHeight();
            graphics2D.drawImage(bi2, width - offsetX, height - offsetY, bi2.getWidth(), bi2.getHeight(), null);
            graphics2D.dispose();
            ImageIO.write(bi, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bais1);
            IOUtils.closeQuietly(bais2);
            IOUtils.closeQuietly(baos);
        }
        return new byte[0];
    }
}

