package com.yuesheng.pm.util;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-04.
 *
 * @author XiaoSong
 * @date 2017/07/04
 */
public class ExcelToHtml {
    private static Logger log = LoggerFactory.getLogger(ExcelToHtml.class);
    private static String[] bordesr = {"border-top:", "border-right:", "border-bottom:", "border-left:"};
    private static String[] borderStyles = {"solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid", "solid", "solid", "solid", "solid"};
    private static FormulaEvaluator evaluator;

    /**
     * xlsx转html
     *
     * @param is          xlsx数据流
     * @param filePath    文件的路径
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @return <table>...</table> 字符串
     */
    public synchronized static String readExcelToHtml(InputStream is,
                                                      boolean isWithStyle,
                                                      String filePath) throws IOException, InvalidFormatException {
        String htmlExcel = null;
        try {
            File sourcefile = new File(filePath);
            if (sourcefile.exists()) {
                return new String(IOUtils.toByteArray(new FileInputStream(sourcefile)));
            }
            Workbook wb = WorkbookFactory.create(is);
            evaluator = wb.getCreationHelper().createFormulaEvaluator();
            if (wb instanceof XSSFWorkbook) {
                XSSFWorkbook xWb = (XSSFWorkbook) wb;
                htmlExcel = getExcelInfo(xWb, isWithStyle);
            } else if (wb instanceof HSSFWorkbook) {
                HSSFWorkbook hWb = (HSSFWorkbook) wb;
                htmlExcel = getExcelInfo(hWb, isWithStyle);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return htmlExcel;
    }

    public static String getExcelInfo(Workbook wb, boolean isWithStyle) {
        StringBuffer sb = new StringBuffer();
        int sheetCount = wb.getNumberOfSheets();
        Sheet sheet = null;
        for (int i = 0; i < sheetCount; i++) {
//            获取第一个Sheet的内容
            sheet = wb.getSheetAt(i);
//            公式空间，删除
            if (sheet.isDisplayFormulas()) {
                wb.removeSheetAt(i);
                i--;
                sheetCount--;
                continue;
            }
            int lastRowNum = sheet.getLastRowNum();
            Map<String, String> map[] = getRowSpanColSpanMap(sheet);
            sb.append("<table style='border-collapse:collapse;' width='100%'>");
            Row row = null;
            Cell cell = null;
            for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
                if (rowNum >= 50) {
                    break;
                }
                row = sheet.getRow(rowNum);
                if (row == null) {
                    sb.append("<tr><td > &nbsp;</td></tr>");
                    continue;
                }
                sb.append("<tr>");
                int lastColNum = row.getLastCellNum();
                for (int colNum = 0; colNum < lastColNum; colNum++) {
                    if (colNum > 100) {
                        break;
                    }
                    cell = row.getCell(colNum);
                    if (cell == null) {
//                        特殊情况 空白的单元格会返回null
                        sb.append("<td>&nbsp;</td>");
                        continue;
                    }

                    String stringValue = getCellValue(cell);
                    if (map[0].containsKey(rowNum + "," + colNum)) {
                        String pointString = map[0].get(rowNum + "," + colNum);
                        map[0].remove(rowNum + "," + colNum);
                        int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                        int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                        int rowSpan = bottomeRow - rowNum + 1;
                        int colSpan = bottomeCol - colNum + 1;
                        sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
                    } else if (map[1].containsKey(rowNum + "," + colNum)) {
                        map[1].remove(rowNum + "," + colNum);
                        continue;
                    } else {
                        sb.append("<td ");
                    }

                    //判断是否需要样式
                    if (isWithStyle) {
//                        处理单元格样式
                        dealExcelStyle(wb, sheet, cell, sb);
                    }

                    sb.append(">");
                    if (stringValue == null || "".equals(stringValue.trim())) {
                        sb.append(" &nbsp; ");
                    } else {
                        // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                        sb.append(stringValue.replace(String.valueOf((char) 160), "&nbsp;"));
                    }
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }

            sb.append("</table>");
        }
        return sb.toString();
    }

    private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

        Map<String, String> map0 = new HashMap<String, String>();
        Map<String, String> map1 = new HashMap<String, String>();
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = {map0, map1};
        return map;
    }

    /**
     * * 获取表格单元格Cell内容
     * * @param cell
     * * @return
     */
    @SuppressWarnings("all")
    private static String getCellValue(Cell cell) {
        String result = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
//                数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    处理日期格式、时间格式
                    SimpleDateFormat sdf = null;

                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {
//                        日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if ("General".equals("temp")) {
                        format.applyPattern("#");
                    }
                    result = format.format(value);
                }
                break;
            case Cell.CELL_TYPE_STRING:
//                String类型
                result = cell.getRichStringCellValue().toString();
                break;
            case Cell.CELL_TYPE_BLANK:
                result = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                try {
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    result = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    /**
     * * 处理表格样式
     * * @param wb
     * * @param sheet
     * * @param cell
     * * @param sb
     */
    private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb) {

        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            short alignment = cellStyle.getAlignment();
            sb.append("align='" + convertAlignToHtml(alignment) + "' ");
//            单元格内容的水平对齐方式
            short verticalAlignment = cellStyle.getVerticalAlignment();
//            单元格中内容的垂直排列方式
            sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");

            if (wb instanceof XSSFWorkbook) {

                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
                short boldWeight = xf.getBoldweight();
                sb.append("style='");
//                字体加粗
                sb.append("font-weight:" + boldWeight + ";");
//                字体大小
                sb.append("font-size: " + xf.getFontHeight() / 2 + "%;");
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                sb.append("width:" + columnWidth + "px;");

                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
//                    字体颜色
                    sb.append("color:#" + xc.getARGBHex().substring(2) + ";");
                }

                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                if (bgColor != null && !"".equals(bgColor)) {
//                    背景颜色
                    sb.append("background-color:#" + bgColor.getARGBHex().substring(2) + ";");
                }
                sb.append(getBorderStyle(0, cellStyle.getBorderTop(), ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                sb.append(getBorderStyle(1, cellStyle.getBorderRight(), ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                sb.append(getBorderStyle(2, cellStyle.getBorderBottom(), ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                sb.append(getBorderStyle(3, cellStyle.getBorderLeft(), ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));

            } else if (wb instanceof HSSFWorkbook) {

                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append("style='");
//                类HSSFPalette用于求的颜色的国际标准形式
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
                HSSFColor hc = palette.getColor(fontColor);
//                字体加粗
                sb.append("font-weight:" + boldWeight + ";");
//                字体大小
                sb.append("font-size: " + hf.getFontHeight() / 2 + "%;");
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
//                    字体颜色
                    sb.append("color:" + fontColorStr + ";");
                }
                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                sb.append("width:" + columnWidth + "px;");
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
//                    背景颜色
                    sb.append("background-color:" + bgColorStr + ";");
                }
                sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTop(), cellStyle.getTopBorderColor()));
                sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRight(), cellStyle.getRightBorderColor()));
                sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeft(), cellStyle.getLeftBorderColor()));
                sb.append(getBorderStyle(palette, 2, cellStyle.getBorderBottom(), cellStyle.getBottomBorderColor()));
            }

            sb.append("' ");
        }
    }

    /**
     * * 单元格内容的水平对齐方式
     * * @param alignment
     * * @return
     */
    private static String convertAlignToHtml(short alignment) {

        String align = "left";
        switch (alignment) {
            case CellStyle.ALIGN_LEFT:
                align = "left";
                break;
            case CellStyle.ALIGN_CENTER:
                align = "center";
                break;
            case CellStyle.ALIGN_RIGHT:
                align = "right";
                break;
            default:
                break;
        }
        return align;
    }

    /**
     * * 单元格中内容的垂直排列方式
     * * @param verticalAlignment
     * * @return
     */
    private static String convertVerticalAlignToHtml(short verticalAlignment) {

        String valign = "middle";
        switch (verticalAlignment) {
            case CellStyle.VERTICAL_BOTTOM:
                valign = "bottom";
                break;
            case CellStyle.VERTICAL_CENTER:
                valign = "center";
                break;
            case CellStyle.VERTICAL_TOP:
                valign = "top";
                break;
            default:
                break;
        }
        return valign;
    }

    private static String convertToStardColor(HSSFColor hc) {
        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }

    private static String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    private static String getBorderStyle(HSSFPalette palette, int b, short s, short t) {

        if (s == 0) {
            return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
        } else {
            String borderColorStr = convertToStardColor(palette.getColor(t));
            borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr;
            return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
        }

    }

    private static String getBorderStyle(int b, short s, XSSFColor xc) {
        if (s == 0) {
            return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
        }
        if (xc != null && !"".equals(xc)) {
            String borderColorStr = xc.getARGBHex();
            borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr.substring(2);
            return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
        }

        return "";
    }

    /**
     * xls转html
     *
     * @param excelBook
     * @return
     * @throws IOException
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    public static String xlsToHtml(final HSSFWorkbook excelBook) throws IOException, TransformerException, ParserConfigurationException {
        int number = excelBook.getNumberOfSheets();
        HSSFSheet hs = null;
        for (int i = 0; i < number; i++) {
            hs = excelBook.getSheetAt(i);
            if (hs != null && hs.isDisplayFormulas()) {
                excelBook.removeSheetAt(i);
                i--;
                number--;
            }
        }
        ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        excelToHtmlConverter.setOutputColumnHeaders(false);
        excelToHtmlConverter.processWorkbook(excelBook);
        Document htmlDocument = excelToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        return new String(outStream.toByteArray());
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String html = readExcelToHtml(new FileInputStream("C:\\Users\\96339\\Desktop\\test.XLSX"), true, "C:\\Users\\96339\\Desktop\\test.html");
        org.apache.commons.io.IOUtils.write(html.getBytes("UTF-8"), new FileOutputStream("C:\\Users\\96339\\Desktop\\test.html"));
    }
}


