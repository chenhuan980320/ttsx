package com.ttsx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;


import com.ttsx.bean.MemberInfo;
import com.ttsx.biz.IMemberInfoBiz;
import com.ttsx.biz.impl.MemberInfoBizImpl;
import com.ttsx.util.FileUploadUtil;
import com.ttsx.util.RequestParamUtil;
import com.ttsx.util.SessionKeyConstant;

@WebServlet("/member")
public class MemberInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 3497607437635460620L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		switch (op) {
		case "findAll":findAll(request,response);break;
		case "update": update(request,response); break;
		case "findByPage":findByPage(request,response);break;
		case "findByCondition":findByCondition(request,response);break;
		case "findByMid":findByMid(request,response);break;//单独查找mid  未做
		case "add":add(request,response);break;//修改  未做
		case "reg": reg(request,response); break;
		case "login": login(request,response); break;
		case "check1": check1(request,response); break;//会员
		case "check2": check2(request,response); break;
		case "excel": excel(request,response); break;
		default:this.error(request, response);break;
		}
	}
	
	//修改
	private void update(HttpServletRequest request, HttpServletResponse response) {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		try {
			MemberInfo mf = fileUploadUtil.uploads(MemberInfo.class, pageContext);
			//System.out.println(mf);
			IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
			int result = memberInfoBiz.update(mf);
			System.out.println(mf);
			//System.out.println(result);
			if(result>0) {
				String sfInfo=request.getSession().getAttribute(SessionKeyConstant.SFINFOLOGIN)+"";
				//	System.out.println(sfInfo);
					if(sfInfo.equals("huiyuan")) {
						MemberInfo mf2 = memberInfoBiz.findByMid(mf.getMid()+"");
						request.getSession().setAttribute(SessionKeyConstant.INFOLOGIN, mf2);
					}
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	//多条件查询
	private void findByCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mid = request.getParameter("mid");
		String nickName = request.getParameter("nickName");
		String status = request.getParameter("status");
		String sf = request.getParameter("sf");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		this.send(response, memberInfoBiz.findByCondition(sf, mid, nickName, status, tel, email, page, rows));
		
	}
	private void add(HttpServletRequest request, HttpServletResponse response)  {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		
		try {
			MemberInfo mf = fileUploadUtil.uploads(MemberInfo.class, pageContext);
			//System.out.println(mf);
			IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
			if(memberInfoBiz.reg(mf) > 0) {
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//单独查找mid
	private void findByMid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mid = request.getParameter("mid");
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		MemberInfo mf = memberInfoBiz.findByMid(mid);
		if(mf ==null) {
			this.send(response, 400,null);
			return;
		}
		this.send(response, 200,mf);
	}

	private void check1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.INFOLOGIN);
		String sfInfo=request.getSession().getAttribute(SessionKeyConstant.SFINFOLOGIN)+"";
	//	System.out.println(sfInfo);
		if(obj == null ||!sfInfo.equals("huiyuan")) {
			this.send(response, 500,"失败");
			return;
		}
		
		this.send(response, 200,obj,sfInfo);
	}
	private void check2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.INFOLOGIN);
		String sfInfo=request.getSession().getAttribute(SessionKeyConstant.SFINFOLOGIN)+"";
	//	System.out.println(sfInfo);
		if(obj == null ||!sfInfo.equals("shangjia")) {
			this.send(response, 500,"失败");
			return;
		}
		this.send(response, 200,obj,sfInfo);
	}
	//退出
	private void excel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().setAttribute(SessionKeyConstant.INFOLOGIN, null);
		request.getSession().setAttribute(SessionKeyConstant.SFINFOLOGIN, null);
		this.send(response, 200);
	}
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter("account");
		String pwd = request.getParameter("pwd");
		Integer sf =Integer.parseInt(request.getParameter("sf"));
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		MemberInfo mf = memberInfoBiz.login(account, pwd,sf);
	//	System.out.println(mf);
		if(mf !=null) {
			if(sf == 2) {
				request.getSession().setAttribute(SessionKeyConstant.INFOLOGIN, mf);
				request.getSession().setAttribute(SessionKeyConstant.SFINFOLOGIN, "shangjia");
				this.send(response, 200,"成功");
			}else if(sf==1) {
				request.getSession().setAttribute(SessionKeyConstant.INFOLOGIN, mf);
				request.getSession().setAttribute(SessionKeyConstant.SFINFOLOGIN, "huiyuan");
				this.send(response, 200,"成功");
			}
			this.send(response, 500,"禁用");
			
			return;
		}
		this.send(response, 500,"失败");
	}
	private void reg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MemberInfo mf = RequestParamUtil.getParams(MemberInfo.class, request);
		String code = String.valueOf(request.getSession().getAttribute(SessionKeyConstant.VERIFICATIONCODE));
//		if(!code.equalsIgnoreCase(mf.getRealName())) {
//			this.send(response, 501,"验证码错误");
//			System.out.println(mf.getRealName());
//			return;
//			
//		}
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		if(memberInfoBiz.reg(mf) > 0) {
			this.send(response, 200,"成功");
			return;
		}
		this.send(response, 500,"失败");
	
	}
	

	private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		this.send(response, memberInfoBiz.findAll());	
	}


	private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl();
		this.send(response, memberInfoBiz.findByPage(page, rows));
		
	}

}
