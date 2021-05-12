package com.team_five.salthub.saToken;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouterUtil;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

            // 角色验证
            SaRouterUtil.match("/test/**", () -> StpUtil.checkRoleOr("administrators"));
            SaRouterUtil.match("/test", () -> StpUtil.checkRoleOr("administrators"));
            SaRouterUtil.match("/admin/**", () -> StpUtil.checkRoleOr("administrators"));
            SaRouterUtil.match("/admin", () -> StpUtil.checkRoleOr("administrators"));
            SaRouterUtil.match("/ban/**", () -> StpUtil.checkRoleOr("administrators"));
            SaRouterUtil.match("/ban", () -> StpUtil.checkRoleOr("administrators"));
            SaRouterUtil.match("/collection/**", () -> StpUtil.checkRoleOr("normal", "administrators"));
            SaRouterUtil.match("/collection", () -> StpUtil.checkRoleOr("normal", "administrators"));

            // 公告权限管理
            SaRouterUtil.match("/notice", () -> {
                if (httpServletRequest.getMethod().equals(HttpMethod.GET.toString())) {
                    StpUtil.checkRoleOr("normal", "administrators");
                } else {
                    StpUtil.checkRoleOr("administrators");
                }
            });

            // 举报权限管理
            SaRouterUtil.match("/tipOff", () -> {
                if (httpServletRequest.getMethod().equals(HttpMethod.POST.toString())) {
                    StpUtil.checkRoleOr("normal", "administrators");
                } else {
                    StpUtil.checkRoleOr("administrators");
                }
            });

            // 博客权限管理
            SaRouterUtil.match("/blog", () -> {
                String method = httpServletRequest.getMethod();
                if (method.equals(HttpMethod.POST.toString())) {
                    StpUtil.checkLogin();
                    StpUtil.checkRoleOr("normal", "administrators");
                }
                if (method.equals(HttpMethod.DELETE.toString())) {
                    StpUtil.checkLogin();
                    StpUtil.checkRoleOr("normal", "administrators");
                }
                if (method.equals(HttpMethod.PUT.toString())) {
                    StpUtil.checkLogin();
                    StpUtil.checkRoleOr("normal", "administrators");
                }
            });
        })).addPathPatterns("/**");
    }

    private static List<String> getWhiteList() {
        List<String> whiteList = new ArrayList<>();
        // 登录
        whiteList.add("/account/login/*");
        whiteList.add("/oauth/**");
        whiteList.add("/avatar/**");
        // 注册
        whiteList.add("/account/register");
        whiteList.add("/account/name");
        whiteList.add("/account/email");
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
        // 博客
        whiteList.add("/blog/**");
        whiteList.add("/blog");
        // 标签
        whiteList.add("/tag");
        whiteList.add("/tag/all");
        // 附件
        whiteList.add("/attachment/**");
        // 评论
        whiteList.add("/comment/2");
        return whiteList;
    }
}
