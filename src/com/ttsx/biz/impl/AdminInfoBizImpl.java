package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.AdminInfo;
import com.ttsx.biz.IAdminInfoBiz;
import com.ttsx.dao.IAdminInfoDao;
import com.ttsx.dao.impl.AdminInfoDaoImpl;
import com.ttsx.dto.JsonObject;
import com.ttsx.util.StringUtil;

public class AdminInfoBizImpl implements IAdminInfoBiz{

	@Override
	public JsonObject findByPage(int page, int rows) {
		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
		
		return new JsonObject(adminInfoDao.total(), adminInfoDao.findByPage(page, rows));
	}

	@Override
	public int update(AdminInfo ai) {
		if (StringUtil.checkNull(ai.getPwd(),ai.getEmail())) {
			return -1;
		}
		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
		return adminInfoDao.update(ai);
	}

	@Override
	public List<AdminInfo> findAll() {
		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
		return adminInfoDao.findAll();
	}

	@Override
	public int reg(AdminInfo ai) {
		if(StringUtil.checkNull(ai.getTel()+"",ai.getEmail(),ai.getPwd())) {
			return -1;
		}
		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
		return adminInfoDao.reg(ai);
	}

	@Override
	public AdminInfo login(String account, String pwd) {
		if(StringUtil.checkNull(account,pwd)) {
			return null;
		}
		IAdminInfoDao adminInfoDao= new AdminInfoDaoImpl();
		return adminInfoDao.login(account, pwd);
	}
	
}
