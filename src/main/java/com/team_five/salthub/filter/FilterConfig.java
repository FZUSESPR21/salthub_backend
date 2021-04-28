package com.team_five.salthub.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @date 2021/04/26
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> registerFilter() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CorsFilter");
        registration.setOrder(1);
        return registration;
    }
}
