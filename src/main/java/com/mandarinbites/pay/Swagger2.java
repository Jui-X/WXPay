package com.mandarinbites.pay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-14 21:26
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * @Description: swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.tyrantx.kingj.controller"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * @Description: 构建api文档的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("使用swagger2构建后端api接口文档")
                .contact(new Contact("KingJ", "https://github.com/TyrantX", "scsse_hhw2016@126.com"))
                .description("欢迎访问接口文档，这里是描述信息")
                .version("0.0.1").build();
    }
}
