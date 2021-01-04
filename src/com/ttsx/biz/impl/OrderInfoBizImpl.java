package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.OrderInfo;
import com.ttsx.biz.IOrderInfoBiz;
import com.ttsx.dao.IOrderInfoDao;
import com.ttsx.dao.impl.OrderInfoDaoImpl;
import com.ttsx.dto.JsonObject;
import com.ttsx.util.StringUtil;

public class OrderInfoBizImpl implements IOrderInfoBiz{

	@Override
	public JsonObject findByPage(String mid, int page, int rows) {
		if (StringUtil.checkNull(mid,page+"",rows+"")) {
			return null;
		}
			IOrderInfoDao orderInfoDao = new OrderInfoDaoImpl();
			return new JsonObject(orderInfoDao.total(mid), orderInfoDao.findByPage(mid, page, rows));
	}

	@Override
	public int update(String status) {
		if (StringUtil.checkNull(status)) {
			return -1;
		}
		IOrderInfoDao orderInfoDao = new OrderInfoDaoImpl();
		return orderInfoDao.update(status);
	}

	@Override
	public List<OrderInfo> findAll() {
			IOrderInfoDao orderInfoDao = new OrderInfoDaoImpl();
		return orderInfoDao.findAll();
	}

	@Override
	public int add(String oid, String mid, String sid, String pid, String total) {
			IOrderInfoDao orderInfoDao = new OrderInfoDaoImpl();
	
		return orderInfoDao.add(oid, mid, sid, pid, total);
	}

//	@Override
//	public JsonObject findByPage(int page, int rows) {
//		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
//		
//		return new JsonObject(adminInfoDao.total(), adminInfoDao.findByPage(page, rows));
//	}
//
//	@Override
//	public int update(AdminInfo ai) {
//		if (StringUtil.checkNull(ai.getPwd(),ai.getEmail())) {
//			return -1;
//		}
//		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
//		return adminInfoDao.update(ai);
//	}
//
//	@Override
//	public List<AdminInfo> findAll() {
//		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
//		return adminInfoDao.findAll();
//	}
//
//	@Override
//	public int reg(AdminInfo ai) {
//		if(StringUtil.checkNull(ai.getTel()+"",ai.getEmail(),ai.getPwd())) {
//			return -1;
//		}
//		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
//		return adminInfoDao.reg(ai);
//	}
//
//	@Override
//	public AdminInfo login(String account, String pwd) {
//		if(StringUtil.checkNull(account,pwd)) {
//			return null;
//		}
//		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
//		return adminInfoDao.login(account, pwd);
//	}
	
}
