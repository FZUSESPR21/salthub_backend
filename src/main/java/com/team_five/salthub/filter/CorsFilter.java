package com.team_five.salthub.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器
 *
 * @date 2021/04/26
 */
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma," +
            "Content-Type,Token, Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "360000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String method = request.getMethod();
//        if ("OPTIONS".equalsIgnoreCase(method)) {
//            servletResponse.getOutputStream().write("Success".getBytes(StandardCharsets.UTF_8));
//        } else {
        filterChain.doFilter(servletRequest, servletResponse);
//        }
    }
}
