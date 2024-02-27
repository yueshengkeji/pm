package com.yuesheng.pm.util;

import com.yuesheng.pm.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by 96339 on 2017/4/21 spring初始化完成后调用.
 * @author XiaoSong
 * @date 2017/04/21
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener {
    @Autowired
    private MaterialService materialService;
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("spring 初始化完成，开始自检系统必要数据："+event.toString());
        /**
         * 判断材料的系统目录是否有创建
         */
        if(event.getSource() instanceof ContextRefreshedEvent){

        }
    }
}
