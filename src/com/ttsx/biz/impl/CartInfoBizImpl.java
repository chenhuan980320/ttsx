package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.CartInfo;
import com.ttsx.biz.ICartInfoBiz;
import com.ttsx.dao.ICartInfoDao;
import com.ttsx.dao.impl.CartInfoDaoImpl;
import com.ttsx.util.StringUtil;



public class CartInfoBizImpl implements ICartInfoBiz {

	@Override
	public int add(Integer mid, String gid, String nums) {
		if (StringUtil.checkNull(gid, nums)) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.add(mid, gid, nums);
		}	
	

	@Override
	public List<CartInfo> findByMid(Integer mid) {
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.findByMid(mid);
	}

	@Override
	public int update(String cid, String nums) {
		if (StringUtil.checkNull(cid, nums)) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.update(cid, nums);
		}


	@Override
	public int del(String cid) {
		if (StringUtil.checkNull(cid)) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.del(cid);
	}


	@Override
	public List<CartInfo> res(Integer mid, String gid) {
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.res(mid,  gid);
	}


	@Override
	public List<CartInfo> findByCids(String cids) {
		if (StringUtil.checkNull(cids)) {
			return null;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.findByCids(cids);
	}


	@Override
	public int dels(String cids) {
		if (StringUtil.checkNull(cids)) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.dels(cids);
	}


	

	}

	

