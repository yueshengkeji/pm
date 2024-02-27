package com.yuesheng.pm.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 96339 on 2017/4/25.
 * @author XiaoSong
 * @date 2017/04/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface SameUrlData {

}

