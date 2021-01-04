package com.ttsx.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.ttsx.bean.GoodsInfo;
import com.ttsx.biz.IGoodsInfoBiz;
import com.ttsx.biz.impl.GoodsInfoBizImpl;
import com.ttsx.util.FileUploadUtil;



@WebServlet("/goods")
public class GoodsInfoServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3497607437635460620L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		System.out.println(op);
		switch (op) {
		
		case "findByPage":findByPage(request,response);break;
		case "findConditions":findConditions(request,response);break;
		case "ban":ban(request,response);break;
		case "update":update(request,response);break;
		case "updateOder":updateOder(request,response);break;
		case "add":add(request,response);break;
		case "findCondition":findCondition(request,response);break;
		case "findCGN":findCGN(request,response);break;
		case "findByGid":findByGid(request,response);break;
		case "findByTid":findByTid(request,response);break;
		case "findByPages":findByPages(request,response);break;
		case "findBys":findBys(request,response);break;
		default:this.error(request, response);break;
		}
	}

	

	private void updateOder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gid = request.getParameter("gid");
		String nums = request.getParameter("nums");
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		if(goodInfobiz.updateOder(gid, nums)>0) {
			this.send(response,200,"成功");
		}
		this.send(response,500,"失败");
	}



	private void findCGN(HttpServletRequest request, HttpServletResponse response) throws IOException {
	System.out.println(111);
		String gid = request.getParameter("gid");
		String nums = request.getParameter("nums");
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		this.send(response,goodInfobiz.findCGN(gid, nums));
		
	}



	private void findByTid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tid = request.getParameter("tid");
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		List<GoodsInfo> gf= goodInfobiz.findByTid(tid);
		//System.out.println(gf);
		this.send(response,200, gf);
	}
	private void update(HttpServletRequest request, HttpServletResponse response) {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		try {
			GoodsInfo goodsInfo = fileUploadUtil.uploads(GoodsInfo.class,pageContext);
		
			IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
			int result = goodInfobiz.update(goodsInfo);
			
			if(result>0) {
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		
	}


	//添加商品
	private void add(HttpServletRequest request, HttpServletResponse response) {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		try {
			GoodsInfo goodsInfo = fileUploadUtil.uploads(GoodsInfo.class,pageContext);
		
			IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
			int result = goodInfobiz.add(goodsInfo);
			
			if(result>0) {
				this.send(response, 200,"成功");
				return;
			}
			this.send(response, 500,"失败");
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		
	}



	private void ban(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gid = request.getParameter("gid");
		String status = request.getParameter("status");
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		int result = goodInfobiz.ban(gid, status);
		if(result>0) {
			this.send(response, 200,"成功");
			return;
		}
		this.send(response, 500,"失败");
		
	}



	private void findByGid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gid = request.getParameter("gid");
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		//System.out.println(goodInfobiz.findByGid(gid));
		GoodsInfo gf= goodInfobiz.findByGid( gid);
		this.send(response,200, gf);
		
	}



	private void findConditions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid = request.getParameter("sid");
		String gid = request.getParameter("gid");
		String tid = request.getParameter("tid");
		String gname = request.getParameter("gname");
		String status = request.getParameter("status");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		this.send(response, goodInfobiz.findByCondition(sid, gid, tid, "", gname, "", "", "", "", status, page, rows));
		
	}
	private void findCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid = request.getParameter("sid");
		String tid = request.getParameter("tid");
		String gname = request.getParameter("gname");
		String status = request.getParameter("status");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		
		this.send(response, goodInfobiz.findByCondition(sid, "", tid, "", gname, "", "", "", "", status, page, rows));
		
	}
	private void findBys(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		String tid = request.getParameter("tid");
		String name = request.getParameter("name");
		String pricemin = request.getParameter("pricemin");
		String pricemax = request.getParameter("pricemax");
		String paixu = request.getParameter("paixu");
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		this.send(response, goodInfobiz.findByCs(tid, name, pricemin, pricemax, paixu));
		
		
	}
	

	

	private void findByPages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		this.send(response, goodInfobiz.findByPages(page, rows));	
		
	}
	private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String mid = request.getParameter("mid");
	
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		IGoodsInfoBiz goodInfobiz = new GoodsInfoBizImpl();
		this.send(response, goodInfobiz.findByPage(page, rows, mid));
		
	}

}
