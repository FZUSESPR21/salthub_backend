package com.team_five.salthub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 地址映射
 *
 * @date 2021/05/07
 */
@Configuration
public class AddressMapping implements WebMvcConfigurer {
//    public static final String FILE_SAVE_ROOT = "C:/Users/Administrator/Desktop/salthub_backend/avatar/";
    public static final String FILE_SAVE_ROOT = "/root/salthub_backend/avatar/";
    public static final String ORIGINAL_ADDRESS = "/avatar/**";
    public static final String FILE_ATTACHMENT_SAVE_ROOT = "/root/salthub_backend/attachment/";
    public static final String ORIGINAL_ATTACHMENT_ADDRESS = "/attachment/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(ORIGINAL_ADDRESS).addResourceLocations("file:" + FILE_SAVE_ROOT);
        registry.addResourceHandler(ORIGINAL_ATTACHMENT_ADDRESS).addResourceLocations("file:" + FILE_ATTACHMENT_SAVE_ROOT);
    }
}
