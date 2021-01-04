package com.ttsx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttsx.bean.AdminInfo;
import com.ttsx.bean.MemberInfo;
import com.ttsx.biz.IAdminInfoBiz;
import com.ttsx.biz.impl.AdminInfoBizImpl;
import com.ttsx.util.RequestParamUtil;
import com.ttsx.util.SessionKeyConstant;

/**
 * 商品类型控制 company 逸恒科技
 * 
 * @author 胡66
 * @data 2020年10月26日 Email 906430016@qq.com
 */
@WebServlet("/admin")
public class AdminInfoServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");// 取出请求的操作标识符

		switch (op) {
		case "findAll":findAll(request,response);break;
		case "findByPage":findByPage(request,response);break;
		case "reg": reg(request,response); break;
		case "excel": excel(request,response); break;
		case "login": login(request,response); break;
		case "check": check(request,response); break;
		case "update":update(request,response);break;
		default:this.error(request, response);break;
		}

	}
	//退出
	private void excel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().setAttribute(SessionKeyConstant.INFOLOGIN, null);
		request.getSession().setAttribute(SessionKeyConstant.SFINFOLOGIN, null);
		this.send(response, 200);
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
			AdminInfo adminInfo = RequestParamUtil.getParams(AdminInfo.class, request);
			
			//调用业务模型层，处理
			IAdminInfoBiz adminInfoBiz = new AdminInfoBizImpl();
			int result = adminInfoBiz.update(adminInfo);
			if(result > 0) {
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
			return;
		
	}

	private void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.INFOLOGIN);
		String sfInfo=request.getSession().getAttribute(SessionKeyConstant.SFINFOLOGIN)+"";
		if(obj == null||!sfInfo.equals("admin")) {
			this.send(response, 500,"失败");
			return;
		}
		this.send(response, 200,obj);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter("account");
		String pwd = request.getParameter("pwd");
		IAdminInfoBiz adminInfoBiz = new AdminInfoBizImpl();
		AdminInfo af = adminInfoBiz.login(account, pwd);
		if(af !=null) {
			request.getSession().setAttribute(SessionKeyConstant.INFOLOGIN, af);
			request.getSession().setAttribute(SessionKeyConstant.SFINFOLOGIN, "admin");
			this.send(response, 200,"成功");
			return;
		}
		this.send(response, 500,"失败");
		
	}

	private void reg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//将信息变为一个admininfo对象
		AdminInfo af=RequestParamUtil.getParams(AdminInfo.class, request);
		String code = String.valueOf(request.getSession().getAttribute(SessionKeyConstant.VERIFICATIONCODE));
//		if(!code.equalsIgnoreCase(af.getRealName())) {
//		this.send(response, 501,"验证码错误");
//		System.out.println(mf.getRealName());
//		return;
//		
//	}
		IAdminInfoBiz adminInfoBiz = new AdminInfoBizImpl();
		if(adminInfoBiz.reg(af) > 0) {
			this.send(response, 200,"成功");
			return;
		}
		this.send(response, 500,"失败");
	}

	private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IAdminInfoBiz adminInfoBiz = new AdminInfoBizImpl();
		this.send(response, adminInfoBiz.findByPage(page, rows));
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IAdminInfoBiz adminInfoBiz = new AdminInfoBizImpl();
		this.send(response, adminInfoBiz.findAll());	
	}
}
