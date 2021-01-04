package com.ttsx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 自定义的基础Servelt   跨域访问、xml解析错误: 格式不佳、响应数据
 * company 源辰信息
 * @author navy
 * @date 2020年10月25日
 * Email haijunzhou@hnit.edu.cn
 */
public class BaseServlet extends HttpServlet{
	private static final long serialVersionUID = -4185946525105531064L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request,response);
	}

	/**
	 * 响应一个int状态码 
	 * @param response
	 * @param code   状态码  
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void send(HttpServletResponse response, int code) throws IOException {
		// Access-Control-Allow-Origin  解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 解决前端网页  xml解析错误: 格式不佳    
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.print(code);
		out.flush();
		out.close();
	}

	/**
	 * 响应一个字符串状态码 
	 * @param response
	 * @param code   状态码   
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void send(HttpServletResponse response, String code) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setContentType("text/plain"); // 调用支付包沙箱会出错 注释
		PrintWriter out = response.getWriter();
		out.print(code);
		out.flush();
		out.close();
	}

	/**
	 * 响应一个obj对象数据 
	 * @param response
	 * @param code   状态码  
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void send(HttpServletResponse response, Object obj) throws IOException {
		// Access-Control-Allow-Origin  解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().serializeNulls().create();
		out.print(gson.toJson(obj));
		out.flush();
		out.close();
	}

	/**
	 * 以JSON格式返回返回数据 :  状态码  和 提示信息
	 * @param response
	 * @param code	状态
	 * @param msg	返回的字符串提示信息
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void send(HttpServletResponse response, int code, String msg) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//解决前端网页  xml解析错误: 格式不佳
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().serializeNulls().create();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		out.print(gson.toJson(map));
		out.flush();
		out.close();
	}

	/**
	 * 以JSON格式返回返回数据 :  状态码  和  详细数据
	 * @param response
	 * @param code	状态码  
	 * @param data	返回的详细数据
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void send(HttpServletResponse response, int code, Object data) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//解决前端网页  xml解析错误: 格式不佳
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().serializeNulls().create();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		out.print(gson.toJson(map));
		out.flush();
		out.close();
	}
	
	protected void send(HttpServletResponse response, int code, Object data,String str) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//解决前端网页  xml解析错误: 格式不佳
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().serializeNulls().create();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		map.put("sfif",str);
		out.print(gson.toJson(map));
		out.flush();
		out.close();
	}
	protected void error(HttpServletRequest request,HttpServletResponse response ) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//解决前端乱码
		response.setContentType("text/html,charset=utf-8");
		PrintWriter out = response.getWriter();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		out.print("<script>alert('该请求不被支持...');location.href='" + basePath + "index.html;'</script>");
		out.flush();
		out.close();
	}
}
