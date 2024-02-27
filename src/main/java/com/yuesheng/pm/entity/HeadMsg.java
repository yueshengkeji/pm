package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2017/3/4 人员首页title自定义信息 person_head.
 *
 * @author XiaoSong
 * @date 2017/03/04
 */
@Schema(name="员工自定义html模板信息")
public class HeadMsg extends BaseEntity {
    /**
     * 标签内容
     */
    @Schema(name="标签内容")
    private String tag = "";

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
