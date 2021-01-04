package com.ttsx.biz.impl;

import java.util.List;
import java.util.Map;

import com.ttsx.bean.GoodsInfo;
import com.ttsx.biz.IGoodsInfoBiz;
import com.ttsx.dao.IGoodsInfoDao;
import com.ttsx.dao.impl.GoodsInfoDaoImpl;
import com.ttsx.dto.JsonObject;
import com.ttsx.util.StringUtil;

public class GoodsInfoBizImpl implements IGoodsInfoBiz{

	@Override
	public int add(GoodsInfo gf) {
		if (StringUtil.checkNull(gf.getTid()+"",gf.getSid()+"",gf.getGname(),gf.getDintro(),gf.getIntro(),gf.getPrice()+"",gf.getInventory()+"",gf.getStatus()+"")) {
			return -1;
		}
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return goodsInfoDao.add(gf);
		}	
	

	@Override
	public int update(GoodsInfo gf) {
		
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return goodsInfoDao.update(gf);
		}	

	


	@Override
	public JsonObject findByPages(int page, int rows) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return new JsonObject(goodsInfoDao.totals(),goodsInfoDao.findByPages(page, rows));
	}


	@Override
	public int ban(String gid, String status) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return goodsInfoDao.ban(gid, status);
	}

	@Override
	public JsonObject findByCondition(String sid, String gid, String tid, String mid, String gname, String pricemin,
			String pricemax, String isvolume, String isprice, String status, int page, int rows) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return new JsonObject(goodsInfoDao.totalC(sid, gid, tid, mid, gname, pricemin, pricemax, status),goodsInfoDao.findByCondition(sid, gid, tid, mid, gname, pricemin, pricemax, isvolume, isprice, status,"",page, rows));
	}
	@Override
	public GoodsInfo findByGid(String gid){
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		if( goodsInfoDao.findByCondition("", gid, "", "", "", "", "", "", "", "", "", 1, 1).size()<=0) {
			return null;
		}
		return  goodsInfoDao.findByCondition("", gid, "", "", "", "", "", "", "", "", "",1, 1).get(0);
	}

	@Override
	public JsonObject findByPage(int page, int rows, String mid) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		
		return new JsonObject(goodsInfoDao.total(mid),goodsInfoDao.findByPage(page, rows, mid));	
	}


	@Override
	public List<GoodsInfo> findByTid(String tid) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return  goodsInfoDao.findByCondition("", "", tid, "", "", "", "", "", "", "","", 1, 4);
		
	}


	@Override
	public List<Map<String, Object>> findByCs(String tid, String name, String pricemin, String pricemax, String paixu) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return goodsInfoDao.findByCs(tid, name, pricemin, pricemax, paixu);
	}


	@Override
	public GoodsInfo findCGN(String gid, String nums) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		if( goodsInfoDao.findByCondition("", gid, "", "", "", "", "", "", "", "", nums, 1, 1).size()<=0) {
			return null;
		}
		return  goodsInfoDao.findByCondition("", gid, "", "", "", "", "", "", "", "", nums, 1, 1).get(0);
	}


	@Override
	public int updateOder(String gid, String nums) {
		if (StringUtil.checkNull(gid,nums)) {
			return -1;
		}
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		return goodsInfoDao.updateOder(gid, nums);
		
	}


	
	
}



