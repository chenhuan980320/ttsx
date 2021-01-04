package com.ttsx.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttsx.bean.AdminInfo;
import com.ttsx.bean.Detailinfo;
import com.ttsx.bean.MemberInfo;
import com.ttsx.bean.OrderInfo;
import com.ttsx.biz.IAdminInfoBiz;
import com.ttsx.biz.IDetailInfoBiz;
import com.ttsx.biz.IOrderInfoBiz;
import com.ttsx.biz.impl.AdminInfoBizImpl;
import com.ttsx.biz.impl.DetailInfoBizImpl;
import com.ttsx.biz.impl.OrderInfoBizImpl;
import com.ttsx.util.RequestParamUtil;
import com.ttsx.util.SessionKeyConstant;

/**
 *  company 逸恒科技
 * 
 * @author 胡66
 * @data 2020年10月26日 Email 906430016@qq.com
 */
@WebServlet("/detail")
public class DetailInfoServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");// 取出请求的操作标识符

		switch (op) {
		
		case "add": add(request,response); break;
		case "findAll": findAll(request,response); break;
		default:this.error(request, response);break;
		}

	}
	/*
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
		
	}*/

	


	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//将信息变为一个Detailinfo对象
		Detailinfo df=RequestParamUtil.getParams(Detailinfo.class, request);
		//System.out.println(df);
		//System.out.println(request.getSession().getAttribute(SessionKeyConstant.UUIDOID));
		df.setOid(request.getSession().getAttribute(SessionKeyConstant.UUIDOID)+"");
		//System.out.println(df);
		IDetailInfoBiz detailInfoBiz = new DetailInfoBizImpl();
		if(detailInfoBiz.add(df)>0) {
			this.send(response, 200,"成功");
			return;
		}
		this.send(response, 500,"失败");
	}

	
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IDetailInfoBiz detailInfoBiz = new DetailInfoBizImpl();
		String oid=request.getParameter("oid");
		if(detailInfoBiz.findAll(oid)==null) {
			this.send(response, 500,"失败");	
		}
		this.send(response, 200,detailInfoBiz.findAll(oid));	
	}
}
