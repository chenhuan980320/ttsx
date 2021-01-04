package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.ShopInfo;
import com.ttsx.biz.IShopInfoBiz;
import com.ttsx.dao.IShopInfoDao;
import com.ttsx.dao.impl.ShopInfoDaoImpl;
import com.ttsx.dto.JsonObject;
import com.ttsx.util.StringUtil;



public class ShopInfoBizImpl implements IShopInfoBiz {
	//管理员查店铺
	@Override
	public JsonObject findByPages(int page, int rows) {
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return new JsonObject(shopInfoDao.totals(),shopInfoDao.findByPages(page, rows));
	}
	//商家查店铺
	@Override
	public JsonObject findByPage(int page, int rows,int mid) {
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return new JsonObject(shopInfoDao.total(mid+""),shopInfoDao.findByPage(page, rows,mid));
	}

	@Override
	public int add(ShopInfo sp) {
		if (StringUtil.checkNull(sp.getSname(),sp.getLicense(),sp.getPics(),sp.getTel())) {
			return -1;
		}
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return shopInfoDao.add(sp);
	}

	@Override
	public int update(ShopInfo sp) {
		if (StringUtil.checkNull(sp.getSname(),sp.getLicense(),sp.getPics(),sp.getTel())) {
			return -1;
		}
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return shopInfoDao.update(sp);
	}

	@Override
	public List<ShopInfo> finds() {
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return shopInfoDao.finds();
	}

	@Override
	public ShopInfo findBySid(String sid) {
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return shopInfoDao.findBySid(sid);
	}

	@Override
	public JsonObject findByCondition(String mid, String sname, String status, int page, int rows) {
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return new JsonObject(shopInfoDao.totalCs(sname, status, "", mid, ""),shopInfoDao.findByCondition(mid, sname, status, page, rows));
	}
	@Override
	public Object findByConditions(String sname, String status, String nickName, String mid, String sid, int page, int rows) {
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return new JsonObject(shopInfoDao.totalCs(sname, status, nickName, mid, sid),shopInfoDao.findByConditions(sname, status, nickName, mid, sid, page, rows));
	}
	@Override
	public int ban(String sid, String status) {
		if (StringUtil.checkNull(sid, status)) {
			return -1;
		}
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return shopInfoDao.ban(sid, status);
	}
	@Override
	public int alter(String sid, String sname, String intro, String pics, String tel, String status) {
		
		if (StringUtil.checkNull(sid, tel, sname,status)) {
			return -1;
		}
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		return shopInfoDao.alter(sid,sname,intro,pics,tel,status);
	}
	@Override
	public List<ShopInfo> findMid(String mid) {
		if (StringUtil.checkNull(mid)) {
			return null;
		}
		IShopInfoDao shopInfoDao = new ShopInfoDaoImpl(); 
		System.out.println(shopInfoDao.findMid(mid));
		return shopInfoDao.findMid(mid);
	}
	

	

}
