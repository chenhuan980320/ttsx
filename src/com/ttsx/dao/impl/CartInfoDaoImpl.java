package com.ttsx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ttsx.bean.CartInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.ICartInfoDao;




public class CartInfoDaoImpl implements ICartInfoDao{

	@Override
	public int add(Integer mid, String gid, String nums) {
		DBHelper db = new DBHelper();
		String sql = "insert into cartinfo values(0, ?, ?, ?)";
		
		return db.update(sql, mid, gid, nums);
	}

	@Override
	public List<CartInfo> findByMid(Integer mid) {
		DBHelper db = new DBHelper();	
		String sql = "select cid, c.gid, gname, price, nums,pics "
				+"from goodsinfo g, cartinfo c where g.gid= c.gid and mid =?";
		return db.finds(CartInfo.class, sql, mid);
	}

	@Override
	public int update(String cid, String nums) {
		DBHelper db = new DBHelper();
		String sql = "update cartinfo set nums=? where cid=?";
		return db.update(sql, nums, cid);
	}

	@Override
	public int del(String cid) {
		DBHelper db = new DBHelper();
		String sql = "delete from cartinfo where cid=? ";
		return db.update(sql, cid);
	}

	@Override
	public List<CartInfo> res(Integer mid, String gid) {
		DBHelper db = new DBHelper();
		String sql = "select cid, gid from cartinfo where mid=? and gid=? ";
		return db.finds(CartInfo.class, sql, mid,gid);
	}

	@Override
	public List<CartInfo> findByCids(String cids) {
		DBHelper db = new DBHelper();
		List<Object> params = new ArrayList<Object>();
		String sql="select  c.cid,c.mid,c.gid,c.nums,s.sid,g.price,g.gname,s.sname,g.pics from "
				+ " cartinfo c,goodsinfo g,shopinfo s where c.gid=g.gid and g.sid=s.sid and ( ";
		String strs[]=cids.split(";");
		sql += " cid=? ";
		params.add(strs[0]);
		for(int i=1;i<strs.length;i++) {
			sql += " or cid=? ";
			params.add(strs[i]);
		}
		sql += " ) ORDER BY s.sid ";
		return db.finds(CartInfo.class, sql, params);
	}

	@Override
	public int dels(String cids) {
		DBHelper db = new DBHelper();
		String sql = "delete from cartinfo where ( ";
		List<Object> params = new ArrayList<Object>();
		String strs[]=cids.split(";");
		sql += " cid=? ";
		params.add(strs[0]);
		for(int i=1;i<strs.length;i++) {
			sql += " or cid=? ";
			params.add(strs[i]);
		}
		sql += " ) ";
		return db.update(sql, params);
	}

	

}
