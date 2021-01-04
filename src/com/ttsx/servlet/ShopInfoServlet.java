package com.ttsx.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.ttsx.bean.ShopInfo;
import com.ttsx.biz.IShopInfoBiz;
import com.ttsx.biz.impl.ShopInfoBizImpl;
import com.ttsx.util.FileUploadUtil;





@WebServlet("/shop")
public class ShopInfoServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		
		switch(op){
		case "findByPage": findBypage(request,response); break;
		case "findByPages": findBypages(request,response); break;
		case "add": add(request,response); break;
		case "ban": ban(request,response); break;
		case "alter": alter(request,response); break;
		case "finds": finds(request,response); break;
		case "findMid": findMid(request,response); break;
		case "findCondition": findCondition(request,response); break;
		case "findConditions": findConditions(request,response); break;
		case "findBySid": findBySid(request,response); break;
		default: this.error(request, response);break;
		}
	}

	
	private void findMid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		String mid = request.getParameter("mid");
		this.send(response, shopInfoBiz.findMid(mid));
		
	}


	private void alter(HttpServletRequest request, HttpServletResponse response)  {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		
		ShopInfo shopInfo;
		try {
			shopInfo = fileUploadUtil.uploads(ShopInfo.class, pageContext);
		
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		
		
		String status =shopInfo.getStatus();
		String tel = shopInfo.getTel();
		String sid = shopInfo.getSid()+"";
		String sname = shopInfo.getSname();
		String intro =shopInfo.getIntro();//简介图片	
		String pics =shopInfo.getPics();//店铺图片头像
		System.out.println("sid"+sid+" name"+sname+" intro"+intro+" pics"+pics+" tel"+tel+" status"+status);
	
		int result = shopInfoBiz.alter(sid,sname,intro,pics,tel,status);
		if(result>0) {
			this.send(response, 200,"成功");
			return;
		}
		this.send(response, 500,"失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	//管理员封禁和解封
		private void ban(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String sid = request.getParameter("sid");
			String status = request.getParameter("status");
			IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
			int result = shopInfoBiz.ban(sid, status);
			if(result>0) {
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
			
			
		}

	private void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.finds());
		
	}

	private void findBySid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid = request.getParameter("sid");
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		ShopInfo shopInfo = shopInfoBiz.findBySid(sid);
		if(shopInfo == null) {
			this.send(response, 400,null);
			return;
		}
		this.send(response, 200,shopInfo);
		
	}
	//管理员条件搜索店铺
	private void findConditions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sname = request.getParameter("sname");
		String status = request.getParameter("status");
		String nickName = request.getParameter("nickName");
		String mid = request.getParameter("mid");
		String sid = request.getParameter("sid");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		//System.out.println(shopInfoBiz.findByConditions(sname, status,nickName,mid,sid, page, rows));
		this.send(response, shopInfoBiz.findByConditions(sname, status,nickName,mid,sid, page, rows));
		
	}
	private void findCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mid = request.getParameter("mid");
		String sname = request.getParameter("sname");
		String status = request.getParameter("status");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		this.send(response, shopInfoBiz.findByCondition(mid, sname, status, page, rows));
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		try {
			ShopInfo shopInfo = fileUploadUtil.uploads(ShopInfo.class, pageContext);
			
			IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
			int result = shopInfoBiz.add(shopInfo);
			if(result>0) {
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
			//System.out.println(shopInfo);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void findBypages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		//System.out.println(shopInfoBiz.findByPages(page, rows));
		this.send(response, shopInfoBiz.findByPages(page, rows));
	}
	private void findBypage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		int mid = Integer.parseInt(request.getParameter("mid"));
		System.out.println(mid);
		IShopInfoBiz shopInfoBiz = new ShopInfoBizImpl();
		//System.out.println(shopInfoBiz.findByPage(page, rows,mid));
		this.send(response, shopInfoBiz.findByPage(page, rows,mid));
		
	}
}
