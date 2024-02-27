package com.yuesheng.pm.util;

import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.common.POIFSConstants;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by 96339 on 2016/12/23.
 * word(doc)文档转换成html
 *
 * @author XiaoSong
 * @date 2016/12/23
 */
public class WordToHtml {
    public static void writeFile(String content, String path) {
        writeFile(content, path, "iso-8859-1");
    }

    /**
     * 写入文本到指定路径
     *
     * @param content
     * @param path
     */
    public static void writeFile(String content, String path, String charset) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, charset));
            bw.write(content);
        } catch (FileNotFoundException fileError) {
            fileError.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ie) {
            }
        }
    }

    /**
     * word转成html并写入到指定的文件中
     *
     * @param file       word流对象
     * @param outPutFile 输出路径+文件名
     * @return
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public synchronized static String convert2Html(InputStream file, String outPutFile)
            throws TransformerException, IOException,
            ParserConfigurationException {
        ByteArrayOutputStream out = getHtmlContentStream(file);
        String content = new String(out.toByteArray());
        writeFile(content, outPutFile);
        return content;

    }

    private static ByteArrayOutputStream getHtmlContentStream(InputStream file) throws IOException, ParserConfigurationException, TransformerException {
        Document htmlDocument = null;
        try {
            htmlDocument = getDocument(file);
        } catch (NotOLE2FileException e) {
            throw e;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        out.close();
        return out;
    }

    /**
     * doc转成html字符串
     *
     * @param docFile doc文输入流
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public synchronized static String conver2Html(InputStream docFile) throws IOException, ParserConfigurationException, TransformerException {
        return new String(getHtmlContentStream(docFile).toByteArray());
    }

    /**
     * 流对象转换成文档对象
     *
     * @param file
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public synchronized static Document getDocument(final InputStream file) throws IOException, ParserConfigurationException {
        HWPFDocument hd = null;
        WordToHtmlConverter wordToHtmlConverter;
        try {
            hd = new HWPFDocument(new POIFSFileSystem(file));
            wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .newDocument());
            //subei jiucan  caigou qingdan
            wordToHtmlConverter.setPicturesManager((bytes, pictureType, imgPosName, v, v1) -> {
                String fileName = "word"+File.separator+imgPosName;
                File f = new File(WebParam.assetsPath + fileName);
                if(!f.exists()){
                    OutputStream os = null;
                    try {
                        os = Files.newOutputStream(f.toPath());
                        org.apache.commons.io.IOUtils.write(bytes, os);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }finally {
                        org.apache.commons.io.IOUtils.closeQuietly(os);
                    }
                }
                return "/assets/"+fileName;
            });
            wordToHtmlConverter.processDocument(hd);
        } catch (NotOLE2FileException e) {
            throw new NotOLE2FileException("this file is docx file 2007+,please use XWPFDocument object");
        }
        return wordToHtmlConverter.getDocument();
    }

    /**
     * word 2007+ 转成html
     *
     * @param file
     * @return
     * @throws IOException
     */
    public synchronized static String getDocumentByDocx(final InputStream file) throws IOException {
        HWPFDocumentCore core = WordToHtmlUtils.loadDoc(file);
        return core.getDocumentText();
    }

    /**
     * html转换成文档对象
     *
     * @param file 模板文件路径
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public synchronized static org.jsoup.nodes.Document getDocumentByHtml(File file) throws IOException, ParserConfigurationException, SAXException {
        org.jsoup.nodes.Document document = Jsoup.parse(file, "iso-8859-1");
        return document;
    }


    /**
     * word转成html并写入到指定的文件中
     *
     * @param fileStream word流对象
     * @param outPath    输出路径+文件名
     * @return
     */
    public synchronized static String convertToHtml(InputStream fileStream, String outPath) throws NotOLE2FileException {
        try {
            return convert2Html(fileStream, outPath);
        } catch (NotOLE2FileException e) {
            throw e;
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把is写入转换成word并返回字节数组
     * 不考虑异常的捕获，直接抛出
     * D:\2016work\Kailismart\target\kailismart.com\formviews\doctemp\
     *
     * @param bytes
     * @throws IOException
     */
    private byte[] inputStreamToWord(byte[] bytes, String path) throws IOException {
        OutputStream fos = null;
        InputStream is = new ByteArrayInputStream(bytes);
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        try {
            directory.createDocument("WordDocument", is);
            fos = new FileOutputStream(path);
            poifs.writeFilesystem(fos);
            fos.close();
            is.close();
        } catch (IOException e) {
            try {
                fos.close();
                is.close();
            } catch (IOException e1) {

            }
        }
        return IOUtils.toByteArray(new FileInputStream(path));
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream("d:\\create.doc"));
        String s = xmlDocToHtmlStr(bytes);
        OutputStream os = new FileOutputStream("d:\\json.html");
        org.apache.commons.io.IOUtils.write(s.getBytes(), os);
        os.close();
    }

    /**
     * xmlDoc文件转html串，poi转过的专用
     *
     * @param bytes
     * @return
     */
    public static String xmlDocToHtmlStr(byte[] bytes) {
        org.jsoup.nodes.Document temp = getDocumentByHtml(new ByteArrayInputStream(bytes));
        String s = temp.outerHtml();
        s = s.replaceAll("�", "");
        temp.body().text(s);

        return s;
    }

    /**
     * 通过流对象获取Document对象
     *
     * @param fileInputStream 文档流对象
     * @return
     */
    public static org.jsoup.nodes.Document getDocumentByHtml(InputStream fileInputStream) {
        try {
            return Jsoup.parse(fileInputStream, "UTF-8", "");
        } catch (IOException e) {

        }
        return null;
    }

    /**
     * 替换word中需要替换的特殊字符 2007以下
     *
     * @param map
     * @param disPath
     * @param srcPath
     */
    public static boolean replaceAndGenerateWord(String srcPath, String disPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        if ((sp.length > 0)) {// 判断文件有无扩展名
            // 比较文件扩展名
            // doc只能生成doc，如果生成docx会出错
            if ((sp[sp.length - 1].equalsIgnoreCase("doc"))) {
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(new FileInputStream(srcPath));
                    Range range = document.getRange();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        range.replaceText(entry.getKey(), entry.getValue());
                    }
                    OutputStream os = new FileOutputStream(disPath);
                    document.write(os);
                    os.close();
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 替换word中需要替换的特殊字符 2007以上
     *
     * @param srcPath  docx文件路径
     * @param destPath 目标路径
     * @param map      要替换的键值对
     * @return
     */
    public static boolean replaceDocxWord(String srcPath, String destPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
            try {
                XWPFDocument document = new XWPFDocument(
                        POIXMLDocument.openPackage(srcPath));
                // 替换段落中的指定文字
                Iterator<XWPFParagraph> itPara = document
                        .getParagraphsIterator();
                while (itPara.hasNext()) {
                    XWPFParagraph paragraph = itPara.next();
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (int i = 0; i < runs.size(); i++) {
                        String oneparaString = runs.get(i).getText(
                                runs.get(i).getTextPosition());
                        for (Map.Entry<String, String> entry : map
                                .entrySet()) {
                            oneparaString = oneparaString.replace(
                                    entry.getKey(), entry.getValue());
                        }
                        runs.get(i).setText(oneparaString, 0);
                    }
                }

                // 替换表格中的指定文字
                Iterator<XWPFTable> itTable = document.getTablesIterator();
                while (itTable.hasNext()) {
                    XWPFTable table = itTable.next();
                    int rcount = table.getNumberOfRows();
                    for (int i = 0; i < rcount; i++) {
                        XWPFTableRow row = table.getRow(i);
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            String cellTextString = cell.getText();
                            for (Map.Entry<String, String> e : map.entrySet()) {
                                if (cellTextString.contains(e.getKey())) {
                                    cellTextString = cellTextString
                                            .replace(e.getKey(),
                                                    e.getValue());
                                }
                            }
                            cell.removeParagraph(0);
                            cell.setText(cellTextString);
                        }
                    }
                }
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(destPath);
                document.write(outStream);
                outStream.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 写入文件到指定文件中
     *
     * @param filePath
     * @param bytes
     * @return
     */
    public static boolean writeStrToFile(String filePath, byte[] bytes) {
        try {
            FileUtils.writeByteArrayToFile(new File(filePath), bytes);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * word（docx）转html文本
     *
     * @param byteArrayInputStream
     * @return
     */
    public static String getDocumentByDocx2(ByteArrayInputStream byteArrayInputStream) {
        try {
            XWPFDocument document = new XWPFDocument(byteArrayInputStream);
            // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(new File(WebParam.webRootPath + File.separator+"assets"+File.separator+"images"+File.separator)));
            options.URIResolver(new BasicURIResolver( "/assets/images/"));
            // 3) 将 XWPFDocument转换成XHTML
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, baos, options);
            baos.close();
            return baos.toString();
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * html文档转word对象
     *
     * @param content
     * @throws Exception
     */
    public static DocumentInputStream exportWord(String content) {
        StringBuilder sb = new StringBuilder();
        POIFSFileSystem poifs = null;
        try {
            //word内容 拼接注意加上<html>
            sb.append("<html lang=\"en\">");
            sb.append(content);
            sb.insert(sb.toString().length(), "</html>");
            ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
            poifs = new POIFSFileSystem();
            poifs.getRoot();
            PushbackInputStream pis = new PushbackInputStream(bais);
            bais.close();
            poifs.createOrUpdateDocument(new ByteArrayInputStream(POIFSConstants.OOXML_FILE_HEADER), "WordDocument");
            poifs.createOrUpdateDocument(pis, "WordDocument");
            pis.close();
//            OutputStream os = new FileOutputStream("d:\\1234.doc");
            return poifs.createDocumentInputStream("WordDocument");
        } catch (Exception e) {
            LoggerFactory.getLogger(WordToHtml.class).error(e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 写入word文档到本地
     *
     * @param de
     * @param path
     */
    public static boolean writeWord(InputStream de, String path) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            //POIFSFileSytem创建，避免Your file appears not to be a valid OLE2 document读取错误
            org.apache.commons.io.IOUtils.write(IOUtils.toByteArray(POIFSFileSystem.createNonClosingInputStream(de)), bos);
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(WordToHtml.class).error(e.getLocalizedMessage());
            return false;
        } catch (IOException e) {
            LoggerFactory.getLogger(WordToHtml.class).error(e.getLocalizedMessage());
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    de.close();
                } catch (IOException e) {
                    LoggerFactory.getLogger(WordToHtml.class).error(e.getLocalizedMessage());
                }
            }
        }
        return true;
    }

    /**
     * 把docx转成html
     *
     * @param is docx 流对象
     * @throws Exception
     */
    public static String convertDocxToHtml(InputStream is) throws Exception {

        WordprocessingMLPackage wordMLPackage = Docx4J.load(is);

        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        htmlSettings.setImageDirPath("d:\\");
        htmlSettings.setImageTargetUri("images");
        htmlSettings.setWmlPackage(wordMLPackage);
        OutputStream os;
        os = new FileOutputStream("d:\\34.html");

        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);

        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
        return "";
    }

    /**
     * 写入文件到本地
     *
     * @param bis
     * @param resourcePath
     * @param fileName
     * @return
     */
    public static byte[] writeFile(BufferedInputStream bis, String resourcePath, String fileName) {
        OutputStream os = null;
        byte[] bytes = null;
        try {
            int length = fileName.lastIndexOf("/");
            length = length == -1 ? fileName.length() : length;
            File file = new File(resourcePath + fileName.substring(0, length));
            if (length != fileName.length() && !file.exists()) {
                file.mkdir();
            }
            file = new File(resourcePath + fileName);
            os = new FileOutputStream(file);
            bytes = IOUtils.toByteArray(bis);
            org.apache.commons.io.IOUtils.write(bytes, os);
        } catch (FileNotFoundException e) {
            return new byte[0];
        } catch (IOException e) {
            return new byte[0];
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(os);
            org.apache.commons.io.IOUtils.closeQuietly(bis);
        }
        return bytes;
    }

    /**
     * pdf转图片
     * @param inputStream pdf流
     * @return 图片路径数组
     */
    public static ArrayList<String> pdfToImage(InputStream inputStream){
        PDDocument pdf = null;
        try {

//            pdf = Loader.loadPDF(IOUtils.toByteArray(inputStream));
            pdf = PDDocument.load(IOUtils.toByteArray(inputStream));
            PDFRenderer pdfRenderer = new PDFRenderer(pdf);
            ArrayList<String> result = new ArrayList<>();
            String time = DateUtil.format(new Date(),DateUtil.PATTERN_IMAGE_NAME);
            for (int pageIndex = 0; pageIndex < pdf.getNumberOfPages(); pageIndex++) {

                BufferedImage image = pdfRenderer.renderImage(pageIndex);
                String fileName = time + (pageIndex+1)+".png";
                fileName = FtpUtil.getFileName(fileName,DateFormat.getDate());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "PNG", baos);
                FtpUtil.uploadFile(baos.toByteArray(),fileName);

                result.add(fileName);
                IOUtils.closeQuietly(baos);
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                IOUtils.closeQuietly(inputStream);
                pdf.close();
            } catch (Exception e) {
                //ignore this error
            }
        }
        return new ArrayList<>();
    }
}
