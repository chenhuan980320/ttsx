package com.ttsx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttsx.bean.TypeInfo;
import com.ttsx.biz.ITypeInfoBiz;
import com.ttsx.biz.impl.TypeInfoBizImpl;
import com.ttsx.util.RequestParamUtil;

/**
 * 商品类型控制 company 逸恒科技
 * 
 * @author 胡66
 * @data 2020年10月26日 Email 906430016@qq.com
 */
@WebServlet("/type")
public class TypeInfoServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");// 取出请求的操作标识符

		switch (op) {
		case "finds":
			finds(request, response);
			break;// 查询所有可用类型信息
		case "findAll":
			findAll(request, response);
			break;// 查询所有类型信息
		case "findByPage":
			findByPage(request, response);
			break;// 分页查询
		case "add":
			add(request, response);
			break;// 添加类型
		case "update":
			update(request, response);
			break;// 修改类型
		default:
			this.error(request, response);
			break;
		}
	}

	private void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		this.send(response, typeInfoBiz.finds());
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TypeInfo typeInfo = RequestParamUtil.getParams(TypeInfo.class, request);

		// 调用业务模型层 处理
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		int result = typeInfoBiz.update(typeInfo);
		if (result > 0) {
			// 说明成功
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
		return;
	}

	/**
	 * 添加商品类型
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TypeInfo typeInfo = RequestParamUtil.getParams(TypeInfo.class, request);
		
		// 调用业务模型层 处理
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		int result = typeInfoBiz.add(typeInfo);
		if (result > 0) {
			// 说明成功
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
		return;

	}

	private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		this.send(response, typeInfoBiz.findByPage(page, rows));
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ITypeInfoBiz typeInfoBiz = new TypeInfoBizImpl();
		this.send(response, typeInfoBiz.findAll());
	}

}
