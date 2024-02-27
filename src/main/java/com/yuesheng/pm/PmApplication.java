package com.yuesheng.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(value = "com.yuesheng.pm.listener")
public class PmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmApplication.class, args);
    }

}
