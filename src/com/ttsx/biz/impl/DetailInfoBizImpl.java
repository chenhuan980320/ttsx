package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.Detailinfo;
import com.ttsx.biz.IDetailInfoBiz;
import com.ttsx.dao.IDetailInfoDao;
import com.ttsx.dao.impl.DetailInfoDaoImpl;
import com.ttsx.util.StringUtil;

public class DetailInfoBizImpl implements IDetailInfoBiz{

	
	@Override
	public List<Detailinfo> findAll(String oid) {
		if (StringUtil.checkNull(oid)) {
			return null;
		}
		IDetailInfoDao detailInfoDao = new DetailInfoDaoImpl();
		return detailInfoDao.findAll(oid);
	}

	@Override
	public int add(Detailinfo di) {
		IDetailInfoDao detailInfoDao = new DetailInfoDaoImpl();
		return detailInfoDao.add(di);
	}


	
}
