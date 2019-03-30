package com.guosh.web.config;

import org.springframework.beans.factory.annotation.Value;
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

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${sys.swagger.enable-swgger}")
    private Boolean enableSwgger;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwgger)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.guosh.web"))  //swgger插件作用范围
                 //.paths(PathSelectors.regex("/api/.*"))
                .paths(PathSelectors.any()) //过滤接口
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringSecurityDemo") //标题
                .description("API描述") //描述
                .contact(new Contact("guoshaohua", "http://www.guoshaohua.cn", ""))//作者
                .version("1.0")
                .build();
    }
}
