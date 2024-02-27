package com.yuesheng.pm.model;

import java.util.List;

/**
 * Created by 宋正根 on 2016/9/20.
 * 导出excel model
 * @author XiaoSong
 * @date 2016/09/20
 */
public class ExportMap {

    /**
     * 文件名称
     */
    private String key;
    /**
     * 导出行map
     */
    private List<Row> rows;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
