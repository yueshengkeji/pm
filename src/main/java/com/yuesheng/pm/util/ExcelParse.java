package com.yuesheng.pm.util;

import com.yuesheng.pm.annotation.Excel;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.Row;
import org.apache.commons.io.IOUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * Created by 宋正根 on 2016/9/19.
 *
 * @author XiaoSong
 * @date 2016/09/19
 * 对象导出excel
 */
public class ExcelParse<T> {

    public static void writeExcel(List<Row> rows, OutputStream os) {
        ExcelParse parse = new ExcelParse();
        XSSFWorkbook hssfWorkbook = parse.parseObject(rows, new XSSFWorkbook());

        try {
            hssfWorkbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(hssfWorkbook);
        }
    }

    public static String writeExcel(List<Row> rows, String fileName) {
        ExcelParse parse = new ExcelParse();
        XSSFWorkbook hssfWorkbook = parse.parseObject(rows, new XSSFWorkbook());

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(WebParam.assetsPath + fileName);
            hssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(hssfWorkbook);
        }
        return WebParam.TEMP_FOLDER + fileName;
    }

    /**
     * 将数据写入到表格文件
     *
     * @param data        数据
     * @param fileName    临时文件名称
     * @param headerName  表格列名
     * @param headerField 数据值（通过反射调用get{headerField}获取）
     * @return excel存储路径
     */
    public static <T> String writeExcel(List<T> data,
                                        String fileName,
                                        String[] headerName,
                                        String[] headerField) {
        List<Row> rows = new ArrayList<>();
        //设置 header
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader(headerName));
        rows.add(header);
        for (int j = 0; data.size() > j; j++) {
            Row row = new Row();
            row.setIndex(j + 1);
            row.setCell(getCells(data.get(j), headerField));
            rows.add(row);
        }
        return writeExcel(rows, fileName);
    }

    /**
     * 导出数据为表格
     *
     * @param data        数据列表
     * @param fileName    输出路径
     * @param headerField 导出数据字段
     * @return
     */
    public static <T> String writeExcel(List<T> data, String fileName, String[] headerField, Class clas) {
        List<Row> rows = new ArrayList<>();
        //设置 header
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader(headerField, clas));
        rows.add(header);
        for (int j = 0; data.size() > j; j++) {
            Row row = new Row();
            row.setIndex(j + 1);
            row.setCell(getCells(data.get(j), headerField));
            rows.add(row);
        }
        return writeExcel(rows, fileName);
    }

    public static List<Cell> getHeader(String[] headerField, Class clas) {
        ArrayList<Cell> arrayList = new ArrayList<>();
        for (int i = 0; i < headerField.length; i++) {
            Excel excel = EntityUtils.getAno(headerField[i], clas, Excel.class);
            if (!Objects.isNull(excel)) {
                Cell cell = new Cell();
                cell.setIndex(i);
                cell.setName(excel.name());
                cell.setWidth(excel.width());
                cell.setRowIndex(excel.index());
                cell.setFormat(excel.coverFormat());
                cell.setNoBreak(excel.noBreak());
                arrayList.add(cell);
            }
        }
        return arrayList;
    }

    public static List<Cell> getCells(Object cellData, String[] fields) {
        return EntityUtils.getCells(cellData, fields);
    }

    private static List<Cell> getHeader(String[] headerName) {
        ArrayList<Cell> arrayList = new ArrayList<>();
        for (int i = 0; i < headerName.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(headerName[i]);
            if (i == 2 || i == 4) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 3 || i == 5) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            arrayList.add(cell);
        }
        return arrayList;
    }


    /**
     * 转换对象中指定的属性集合
     *
     * @return 转换后的输出流对象
     */
    public XSSFWorkbook parseObject(List<Row> row, XSSFWorkbook workbook) {

        XSSFSheet sheet = workbook.createSheet();
        // 设置表格默认列宽度为15个字节

        sheet.setDefaultColumnWidth(15);
//        Map<String, XSSFCellStyle> styles = createStyles(workbook);
//        String[] tempData = null;
        //循环行
        XSSFRow hr;
        XSSFCell hc;
        XSSFCellStyle hcs;
        for (Row r : row) {
//            初始化行
            hr = sheet.createRow(r.getIndex());
            List<Cell> cell = r.getCell();
            if (cell != null) {
                for (Cell c : cell) {
                    //跨行开始，跨行截止，跨列开始，跨列截止
                    if (c.getNumber() > 1 && c.getCellSpan() > 1) {
//                        如果当前列占用多行或者多列才跨行跨列
                        sheet.addMergedRegion(new CellRangeAddress(r.getIndex(), (c.getNumber() - 1), c.getIndex(), (c.getIndex() + c.getCellSpan())));
                    } else if (c.getNumber() > 1) {
//                        跨行
                        sheet.addMergedRegion(new CellRangeAddress(r.getIndex(), (c.getNumber() - 1), c.getIndex(), c.getIndex()));
                    } else if (c.getCellSpan() > 1) {
//                        跨列
                        sheet.addMergedRegion(new CellRangeAddress(r.getIndex(), r.getIndex(), c.getIndex(), (c.getIndex() + (c.getCellSpan() - 1))));
                    }

                    hc = hr.createCell(c.getIndex());
                    if (c.getWidth() > 0) {
                        sheet.setColumnWidth(c.getIndex(), (int) c.getWidth());
                    }
                    XSSFCellStyle style = null;
                    if (r.getIndex() == 0) {
                        style = createStyle(workbook, true, !c.isNoBreak());
                    } else {
                        style = createStyle(workbook, false, !c.isNoBreak());
                    }
                    //自定义字体样式
                    Short color = c.getFontColor();
                    HorizontalAlignment alignment = c.getAlign();
                    if (!Objects.isNull(color) || !Objects.isNull(alignment)) {
                        hc.setCellStyle(getStyle(workbook, color, alignment));
                    } else {
                        hc.setCellStyle(style);
                    }


                    if (c.isLink()) {
                        //XSSFHyperlink
                        String[] linkAdnName = c.getName().split(";");
                        CreationHelper createHelper = workbook.getCreationHelper();
                        Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.URL);
                        hyperlink.setAddress(linkAdnName[0]);
                        hc.setHyperlink(hyperlink);
                        hc.setCellValue(linkAdnName[1]);

                        //设置超链接样式
                        XSSFCellStyle linkStyle = workbook.createCellStyle();
                        XSSFFont cellFont = workbook.createFont();
                        cellFont.setUnderline((byte) 1);
                        cellFont.setColor(HSSFColor.BLUE.index);
                        linkStyle.setFont(cellFont);
                        hc.setCellStyle(linkStyle);

                    } else if (c.isRowIndex() && r.getIndex() > 0) {
                        hc.setCellValue(r.getIndex());
                    } else {
                        try {
                            hc.setCellValue(Double.parseDouble(c.getName()));
                        } catch (NumberFormatException e) {
                            hc.setCellValue(c.getName().trim());
                        } catch (NullPointerException e) {
                            hc.setCellValue("");
                        }

                    }

                }
            }
        }
        return workbook;
    }

    private XSSFCellStyle getStyle(XSSFWorkbook workbook, Short color, HorizontalAlignment alignment) {
        XSSFCellStyle styleHeader = workbook.createCellStyle();
        if (Objects.isNull(alignment)) {
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
        } else {
            styleHeader.setAlignment(alignment);
        }
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setWrapText(true);
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        if (!Objects.isNull(color)) {
            font.setColor(color);
        }
        styleHeader.setFont(font);
        return styleHeader;
    }

    private static XSSFCellStyle createStyle(XSSFWorkbook workbook, boolean bold, boolean wrapText) {
        XSSFCellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        styleHeader.setWrapText(wrapText);
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setFontName("宋体");
        styleHeader.setFont(font);
        return styleHeader;
    }

    public void closeParse(XSSFWorkbook workbook, XSSFSheet sheet) {
        sheet = null;
        workbook = null;
        System.gc();
    }


    /**
     * 只读string类型
     *
     * @param file
     * @return
     */
    public Map<String, List> readExcel(File file, int startIndex, int endIndex, int rowStartIndex) {
        Map<String, List> result = new HashMap<>(16);
        boolean isE2007 = false;
//        判断是否是excel2007格式
        if (file.getName().endsWith("xlsx")) {
            isE2007 = true;
        }
        try {
//            建立输入流
            InputStream input = new FileInputStream(file);
            Workbook wb = null;
//            根据文件格式(2003或者2007)来初始化
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
//            获得第一个表单
            Sheet sheet = wb.getSheetAt(0);
//            获得第一个表单的迭代器
            Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.rowIterator();
            List<Object> rowValue;
            while (rows.hasNext()) {
//                获得行数据
                org.apache.poi.ss.usermodel.Row row = rows.next();
                if (rowStartIndex > row.getRowNum()) {
                    continue;
                }
//                获得行号从0开始
                rowValue = new ArrayList<>();
                result.put(row.getRowNum() + "", rowValue);
                for (int x = startIndex; x <= endIndex; x++) {
                    fillValue(rowValue, row.getCell(x));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 只读string类型
     *
     * @param input 文件流对象
     * @return
     */
    public Map<String, List> readExcelByInput(InputStream input, int startIndex, int endIndex, int rowStartIndex, boolean isE2007) {
        Map<String, List> result = new HashMap<>(16);
        int rowIndex = 0;
        try {
            Workbook wb = null;
            //根据文件格式(2003或者2007)来初始化
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            int si = wb.getNumberOfSheets() - 1;
            while (si != -1) {
//                从最后一个开始获取
                Sheet sheet = wb.getSheetAt(si);
//                获得第一个表单的迭代器
                Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.rowIterator();
                List<Object> rowValue;
                while (rows.hasNext()) {
//                    获得行数据
                    org.apache.poi.ss.usermodel.Row row = rows.next();
                    if (rowStartIndex > row.getRowNum()) {
                        continue;
                    }
                    rowValue = new ArrayList<>();
                    result.put(rowIndex + "", rowValue);
                    rowIndex++;
                    for (int x = startIndex; x <= endIndex; x++) {
                        fillValue(rowValue, row.getCell(x));
                    }
                }
                si--;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void fillValue(final List<Object> rowValue, org.apache.poi.ss.usermodel.Cell cell) {
        int type = 0;
        try {
            type = cell.getCellType();
        } catch (NullPointerException e) {
            rowValue.add("");
            return;
        }
        switch (type) {
//            根据cell中的类型来输出数据
            case HSSFCell.CELL_TYPE_NUMERIC:
                rowValue.add(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_STRING:
                rowValue.add(cell.getStringCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                rowValue.add(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                rowValue.add(cell.getCellFormula());
                break;
            default:
                rowValue.add(cell.getStringCellValue());
                break;
        }
    }
}
