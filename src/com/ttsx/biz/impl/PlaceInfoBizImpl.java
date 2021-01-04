package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.PlaceInfo;
import com.ttsx.biz.IPlaceInfoBiz;
import com.ttsx.dao.IPlaceInfoDao;
import com.ttsx.dao.impl.PlaceInfoDaoImpl;
import com.ttsx.util.StringUtil;

public class PlaceInfoBizImpl implements IPlaceInfoBiz{

	@Override
	public int update(PlaceInfo pi) {
		IPlaceInfoDao placeInfoDao = new PlaceInfoDaoImpl();
		
		return placeInfoDao.update(pi);
	}

	@Override
	public List<PlaceInfo> findAll(String mid) {
		if (StringUtil.checkNull(mid)) {
			return null;
		}
		IPlaceInfoDao placeInfoDao = new PlaceInfoDaoImpl();
		return placeInfoDao.findAll(mid);
	}

	@Override
	public PlaceInfo findPid(String pid) {
		if (StringUtil.checkNull(pid)) {
			return null;
		}
		IPlaceInfoDao placeInfoDao = new PlaceInfoDaoImpl();
		return placeInfoDao.findPid(pid);
	}

	@Override
	public int add(PlaceInfo pi) {
		IPlaceInfoDao placeInfoDao = new PlaceInfoDaoImpl();
		return placeInfoDao.add(pi);
	}

	@Override
	public int del(String pid) {
		if (StringUtil.checkNull(pid)) {
			return -1;
		}
		IPlaceInfoDao placeInfoDao = new PlaceInfoDaoImpl();
		return placeInfoDao.del(pid);
	}

	
	
}
