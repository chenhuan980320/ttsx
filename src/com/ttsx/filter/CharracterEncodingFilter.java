package com.ttsx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttsx.util.StringUtil;

/**
 * 字符串编码过滤器
 * @author 胡恒
 *
 */
@WebFilter(filterName = "CharracterEncodingFilter",value = "/*",
			initParams = @WebInitParam(name = "encoding",value = "utf-8"))
public class CharracterEncodingFilter implements Filter{
	
	private String encoding= "utf-8";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//转换
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resq = (HttpServletResponse)response;
		//设置编码�?
		req.setCharacterEncoding(encoding);
		resq.setCharacterEncoding(encoding);
		//链式调用下一个过滤器
		chain.doFilter(req, resq);
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//获取初始化参
		String temp=filterConfig.getInitParameter("encoding");
		if(!StringUtil.checkNull(temp)) {
			encoding=temp;
		}
	}
	
	
}
