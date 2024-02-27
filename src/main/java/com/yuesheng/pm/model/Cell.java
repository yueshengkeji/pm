package com.yuesheng.pm.model;


import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 列对象
 * @author XiaoSong
 * @date 2016/08/26
 */
public class Cell{
    private int index;
    private String name;
    /**
     * 当前跨列数
     */
    private int number;
    /**
     * 当前跨行数
     */
    private int cellSpan;
    /**
     * 列宽
     */
    private float width;
    /**
     * 列高
     */
    private float height;
    /**
     * 是否超链接
     */
    private boolean link = false;
    /**
     * 是否标记为行序号
     */
    private boolean rowIndex;
    /**
     * 格式化表达式
     */
    private String format;
    /**
     * 自顶一个文字颜色
     */
    private Short fontColor;
    /**
     * 自定义文字方向
     */
    private HorizontalAlignment align;
    private boolean noBreak = false;

    public boolean isNoBreak() {
        return noBreak;
    }

    public void setNoBreak(boolean noBreak) {
        this.noBreak = noBreak;
    }

    public boolean isRowIndex() {
        return rowIndex;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getCellSpan() {
        return cellSpan;
    }

    public void setCellSpan(int cellSpan) {
        this.cellSpan = cellSpan;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRowIndex(boolean rowIndex) {
        this.rowIndex = rowIndex;
    }

    public boolean getRowIndex() {
        return rowIndex;
    }

    public Short getFontColor() {
        return fontColor;
    }

    public void setFontColor(Short fontColor) {
        this.fontColor = fontColor;
    }

    public HorizontalAlignment getAlign() {
        return align;
    }

    public void setAlign(HorizontalAlignment align) {
        this.align = align;
    }
}
