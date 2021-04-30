package com.team_five.salthub.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 *
 * @date 2021/04/29
 */
@Configuration
@Profile({"dev"})
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.team_five.salthub";
    private static final String VERSION = "1.0.0";
    private static final Contact DEVELOPER = new Contact("后端开发人员", "", "");

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Salt Hub接口文档")
            .description("考研论坛α冲刺接口文档")
            .version(VERSION)
            .termsOfServiceUrl("https://8.140.10.177/doc.html")
            .contact(DEVELOPER)
            .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> keyList = new ArrayList<>();
        keyList.add(new ApiKey("身份验证请求头", "jwt", "jwt"));
        return keyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contextList = new ArrayList<>();
        contextList.add(SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("^(?!log).*$"))
            .build());
        return contextList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> referenceList = new ArrayList<>();
        referenceList.add(new SecurityReference("身份验证请求头", authorizationScopes));
        return referenceList;
    }
}
