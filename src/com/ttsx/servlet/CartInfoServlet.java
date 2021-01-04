package com.ttsx.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttsx.bean.CartInfo;
import com.ttsx.bean.MemberInfo;
import com.ttsx.biz.ICartInfoBiz;
import com.ttsx.biz.impl.CartInfoBizImpl;
import com.ttsx.util.SessionKeyConstant;


@WebServlet("/cart")
public class CartInfoServlet extends BaseServlet{

	private static final long serialVersionUID = -5403349650762535777L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		switch (op) {
		case "findByMid": findByMid(request,response);break;
		case "findByCids": findByCids(request,response);break;
		case "add": add(request,response);break;
		case "del": del(request,response);break;
		case "dels": dels(request,response);break;
		case "update": update(request,response);break;
		case "res": res(request,response);break;
		default: this.error(request, response);break;
		}
	}
	private void dels(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cids = request.getParameter("cids");
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		if(cartInfoBiz.dels(cids) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
	}
	private void findByCids(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cids = request.getParameter("cids");
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		List<CartInfo> list = cartInfoBiz.findByCids(cids);
		if(list ==null) {
			this.send(response, 500,null);
			return;
		}
		this.send(response, 200,list);
	}
	private void res(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.INFOLOGIN);
		if(obj == null) {
			this.send(response, 501, "未登录");
			return;
		}
		
		MemberInfo mf = (MemberInfo) obj;
		
		String gid = request.getParameter("gid");
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		//System.out.println(cartInfoBiz.res(mf.getMid(),  gid) == null);
		
		if(cartInfoBiz.res(mf.getMid(),  gid).size() !=0||!cartInfoBiz.res(mf.getMid(),  gid).isEmpty()) {
			this.send(response, 500, "找到了");			
			return;
		}
		this.send(response, 200, "没找到");
		
	}
	private void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cid = request.getParameter("cid");
		
		
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		if(cartInfoBiz.del(cid) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
		
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cid = request.getParameter("cid");
		String num = request.getParameter("num");
		
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		if(cartInfoBiz.update(cid, num) > 0) {
			this.send(response, 200, "成功");
			return;
		}
		this.send(response, 500, "失败");
		
	}
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.INFOLOGIN);
		if(obj == null) {
			this.send(response, 501, "未登录");
			return;
		}
		
		MemberInfo mf = (MemberInfo) obj;
		String gid = request.getParameter("gid");
		String num = request.getParameter("num");
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		//System.out.println(mf.getMid());
		if(cartInfoBiz.add(mf.getMid(), gid, num) > 0) {
			this.send(response, 200, "成功");			
			return;
		}
		this.send(response, 500, "失败");
		
		
	}
	private void findByMid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute(SessionKeyConstant.INFOLOGIN);
		if(obj == null) {
			this.send(response, 501, "未登录");
			return;
		}
		MemberInfo mf = (MemberInfo) obj;
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		
		List<CartInfo> list = cartInfoBiz.findByMid(mf.getMid());
		if(list != null && !list.isEmpty()) {
			this.send(response, 200, list);
			return;
		}
		this.send(response, 500, "失败");
		
	}
	
	
}
