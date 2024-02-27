package com.yuesheng.pm.annotation;

import java.lang.annotation.*;

/**
 * Excel导出属性注解，用于导出数据的实体类上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Excel {

    /**
     * 是否将此字段格式化为表格数据下标输出
     *
     * @return
     */
    boolean index() default false;

    /**
     * 表格宽度
     *
     * @return
     */
    float width() default 6912;

    /**
     * 导出列名称
     *
     * @return
     */
    String name();

    /**
     * 格式化 :"1=标识1,2=标识2..."
     *
     * @return
     */
    String coverFormat() default "";

    boolean noBreak() default false;
}
