package com.yuesheng.pm.model;

import java.util.List;

/**
 * 行对象
 * @author XiaoSong
 * @date 2017/08/26
 */
public class Row {
    private int index;
    private List<Cell> cell;
    public List<Cell> getCell() {
        return cell;
    }

    public void setCell(List<Cell> cell) {
        this.cell = cell;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
