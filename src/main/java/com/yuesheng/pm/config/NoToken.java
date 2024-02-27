package com.yuesheng.pm.config;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoToken {
    /**
     * 是否直接通过，无需验证token
     *
     * @return
     */
    boolean pass() default true;
}
