package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2017/4/21 常用目录实体 sdpm001.
 *
 * @author XiaoSong
 * @date 2017/04/21
 */
@Schema(name="目录对象")
public class Folder extends BaseEntity {
    /**
     * 目录名称
     */
    @Schema(name="目录名称")
    private String name;
    /**
     * 上级目录id
     */
    @Schema(name="上级目录id")
    private String parent;
    /**
     * 目录编码
     */
    @Schema(name="目录编码")
    private String code;
    /**
     * 当前目录主键+所有父目录id
     */
    @Schema(name="当前目录主键+所有父目录id")
    private String rootId;

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
