package com.yuesheng.pm.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.yuesheng.pm.model", "com.yuesheng.pm.entity"})
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("办公系统API")
                        .description("接入微信公众平台、职员/角色/职务/部门、项目、采购、仓库、财务等管理功能")
                        .version("0.0.1")
                        .license(new License().name("Apache license 2.0").url("")))
                .externalDocs(new ExternalDocumentation()
                        .description("")
                        .url(""));
    }

}
