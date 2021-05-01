package com.team_five.salthub.saToken;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouterUtil;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 接口访问过滤
 *
 * @date 2021/05/01
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((httpServletRequest, httpServletResponse, o) -> {
            // 白名单
            SaRouterUtil.match(Collections.singletonList("/**"), getWhiteList(), StpUtil::checkLogin);
        })).addPathPatterns("/**");
    }

    private static List<String> getWhiteList() {
        List<String> whiteList = new ArrayList<>();
        // 登录
        whiteList.add("/account/login/*");
        whiteList.add("/oauth/**");
        // 注册
        whiteList.add("/account/register");
        // 验证码
        whiteList.add("/account/code");
        // druid数据库连接池
        whiteList.add("/druid/**");
        // knife4j
        whiteList.add("/swagger-resources/configuration/security");
        whiteList.add("/swagger-resources/configuration/ui");
        whiteList.add("/v2/api-docs");
        whiteList.add("/v2/api-docs-ext");
        whiteList.add("/v2/**");
        whiteList.add("/swagger-resources");
        whiteList.add("/webjars/**");
        whiteList.add("/doc.html");
        whiteList.add("/favicon.ico");
        return whiteList;
    }
}
