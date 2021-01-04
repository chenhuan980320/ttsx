package com.ttsx.servlet;




import java.io.IOException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ttsx.util.MyUtil;
import com.ttsx.util.SessionKeyConstant;


@WebServlet("/email")
public class Email extends BaseServlet{

	private static final long serialVersionUID = 2239944883563044057L;
	public String code;

	private void emailCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code = request.getSession().getAttribute(SessionKeyConstant.EMAIL)+"";
		System.out.println(code);
		this.code = code;
		MyUtil mu = new MyUtil();
		String e = mu.sendEmail(code);
		this.send(response, 200,e);
		
	}
}