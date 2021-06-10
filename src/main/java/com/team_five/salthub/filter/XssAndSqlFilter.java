package com.team_five.salthub.filter;

import com.alibaba.fastjson.JSONObject;
import com.team_five.salthub.exception.BaseException;
import com.team_five.salthub.exception.ExceptionInfo;
import com.team_five.salthub.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


/*** 
* @Description:  
* @Param:  
* @return:  
* @Author: top
* @Date: 2021/5/14 
*/
@Component
@Slf4j
@WebFilter(
		filterName = "xssFilter",
		urlPatterns = "/*",
		dispatcherTypes = DispatcherType.REQUEST
)
public class XssAndSqlFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
		// no init operation
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		XssAndSqlHttpServletRequestWrapper xssRequest = new XssAndSqlHttpServletRequestWrapper(
				request);

		String method = ((HttpServletRequest) request).getMethod();
//		System.out.println("请求方式："+method);
		String param = "";
		if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
			param = this.getBodyString(xssRequest.getReader());

			if (StringUtils.isNotBlank(param)) {
				if (xssRequest.checkXSSAndSql(param)) {
					servletResponse.setCharacterEncoding("UTF-8");
					servletResponse.setContentType("application/json;charset=UTF-8");
					PrintWriter out = servletResponse.getWriter();


					out.write(JSONObject.toJSONString(ResponseMessage.fail(new BaseException(ExceptionInfo.ILLEGAL_REQUESTBODY))));
					return;
				}
			}
		}
		if (xssRequest.checkParameter()) {
			servletResponse.setCharacterEncoding("UTF-8");
			servletResponse.setContentType("application/json;charset=UTF-8");
			PrintWriter out = servletResponse.getWriter();

			out.write(JSONObject.toJSONString(ResponseMessage.fail(new BaseException(ExceptionInfo.ILLEGAL_REQUESTBODY))));
			return;
		}

		if (xssRequest == null) {
			filterChain.doFilter(request, servletResponse);
		} else {
			filterChain.doFilter(xssRequest, servletResponse);
		}
	}

	// 获取request请求body中参数
	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}

		return str;
	}

	@Override
	public void destroy() {
		// no destroy operation
	}
}