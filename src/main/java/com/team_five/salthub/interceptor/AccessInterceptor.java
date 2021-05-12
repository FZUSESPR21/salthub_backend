package com.team_five.salthub.interceptor;

import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.util.IPUtil;
import com.team_five.salthub.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口防刷拦截器
 *
 * @date 2021/05/07
 */
@Component
@Slf4j
public class AccessInterceptor implements HandlerInterceptor {
    private static final String PREFIX = "ACCESS_";

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestLimit requestLimit = handlerMethod.getMethodAnnotation(RequestLimit.class);
            if (requestLimit == null) {
                return true;
            }
            long limitTime = requestLimit.limitTime();
            int limitCount = requestLimit.limitCount();
            String key = PREFIX + IPUtil.getIpAddr(request).replace(":", "*");
            if (redisUtil.hasKey(key)) {
                int nowCount = (int) redisUtil.get(key) + 1;
                if (nowCount > limitCount) {
                    throw new BaseException(ExceptionInfo.WAIT);
                }
                redisUtil.increase(key, 1);
            } else {
                redisUtil.set(key, 0, limitTime);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
