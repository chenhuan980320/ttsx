package com.ttsx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ttsx.bean.ShopInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.IShopInfoDao;
import com.ttsx.util.StringUtil;



public class ShopInfoDaoImpl  implements IShopInfoDao{
	//管理员版店铺 链表查询要什么直接加在sql语句   注意 ！！！  此方法  所有属性名均为小写
	@Override
	public List<Map<String, Object>> findByPages(int page, int rows) {
		DBHelper db = new DBHelper();
		String sql = "select sid, shopinfo.mid,nickName,date_format(regDate, '%Y-%m-%d %H:%i') regDate, sname,intro,pics,shopinfo.tel, shopinfo.status from shopinfo,memberinfo "
				+"where shopinfo.mid=memberinfo.mid  order by sid limit ?,?";
		//System.out.println(db.finds(sql,(page-1) * rows,rows));
		return db.finds(sql,(page-1) * rows,rows);
	}
	//管理员版 查询总记录数  配合 findByPages
	@Override
	public int totals() {
		DBHelper db = new DBHelper();
		String sql = "select count(sid) from shopinfo";
		return db.total(sql);
	}
	//商家版店铺
	@Override
	public List<ShopInfo> findByPage(int page, int rows,int mid) {
		DBHelper db = new DBHelper();
		String sql = "select sid,mid,date_format(regDate, '%Y-%m-%d %H:%i') regDate, sname,intro,pics,tel, status from shopinfo "
				+"where mid=? order by sid limit ?,?";
		return db.finds(ShopInfo.class,sql,mid,(page-1) * rows,rows);
	
	} 
	//商家 总点数  配合findByPage  
	@Override
	public int total(String mid) {
		DBHelper db = new DBHelper();
		String sql="select count(sid) from shopinfo where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtil.checkNull(mid)) {
			sql +=" and mid=?";
			params.add(mid);
		}
		return db.total(sql, params);
	}
	
	
	
	@Override
	public int add(ShopInfo sp) {
		DBHelper db = new DBHelper();
		String sql = "insert into shopinfo values(0,?,?,?,?,?,?, now(),?)";
		return db.update(sql,sp.getMid(),sp.getSname(),sp.getIntro(),sp.getPics(),sp.getTel(),sp.getLicense(),sp.getStatus());
	}

	@Override
	public int update(ShopInfo sp) {
		DBHelper db = new DBHelper();
		String sql="update shopinfo set intro=? ,pics=?, tel=?, license=?, status=? where sid=?";
		return db.update(sql,sp.getIntro(),sp.getPics(),sp.getTel(),sp.getLicense(),sp.getStatus(),sp.getSid());
	}

	@Override
	public List<ShopInfo> finds() {
		DBHelper db = new DBHelper();
		String sql="select sid,sname from shopinfo";
		return db.finds(ShopInfo.class, sql);
	}
	@Override
	public List<ShopInfo> findMid(String mid) {
		DBHelper db = new DBHelper();
		String sql="select sid,sname,mid from shopinfo where mid= ? ";
		return db.finds(ShopInfo.class,  sql,mid);
	}
	@Override
	public ShopInfo findBySid(String sid) {
		DBHelper db = new DBHelper();
		String sql = "select sid,mid,sname,intro,pics, tel,license, "
				+ "date_format(regDate, '%Y-%m-%d %H:%i') regDate,status from shopinfo where sid=?";
		//System.out.println(db.find(ShopInfo.class, sql, sid));
		return db.find(ShopInfo.class, sql, sid);
	}

	
	
	//商家版
	@Override
	public List<ShopInfo> findByCondition(String mid, String sname, String status, int page, int rows) {
		DBHelper db = new DBHelper();
		List<Object> params = new ArrayList<Object>();
		String sql="select sid,mid, sname,tel,date_format(regDate, '%Y-%m-%d %H:%i') regDate, "
				+ " status from shopinfo where 1=1 ";
		if(!StringUtil.checkNull(mid)) {
			sql +=" and mid=?";
			params.add(mid);
		}
		if(!StringUtil.checkNull(sname)) {
			sql +=" and sname like concat('%',?,'%')";  //like   '%源辰%'
			params.add(sname);
		}
		if(!StringUtil.checkNull(status)) {
			sql +=" and status=?";
			params.add(status);
		}
		sql +=" order by sid limit ?,? ";
		params.add((page - 1)* rows);
		params.add(rows);
		return db.finds(ShopInfo.class, sql,params);
	}
	//管理员版查询店铺
	@Override
	public List<Map<String, Object>>  findByConditions(String sname, String status, String nickName, String mid, String sid,int page, int rows) {
		DBHelper db = new DBHelper();
		List<Object> params = new ArrayList<Object>();
		//"select sid, shopinfo.mid,nickName,date_format(regDate, '%Y-%m-%d %H:%i') regDate, sname,intro,pics,shopinfo.tel, shopinfo.status from shopinfo,memberinfo "
		//+"where shopinfo.mid=memberinfo.mid  order by sid limit ?,?";
		String sql="select sid,shopinfo.mid,nickName, sname,shopinfo.tel,date_format(regDate, '%Y-%m-%d %H:%i') regDate, "
				+ " shopinfo.status from shopinfo,memberinfo where shopinfo.mid=memberinfo.mid ";
		if(!StringUtil.checkNull(sname)) {
			sql +=" and sname like concat('%',?,'%')";  //like   '%源辰%'
			params.add(sname);
		}
		if(!StringUtil.checkNull(status)) {
			sql +=" and shopinfo.status=?";
			params.add(status);
		}
		if(!StringUtil.checkNull(nickName)) {
			sql +=" and nickName like concat('%',?,'%')";
			params.add(nickName);
		}
		if(!StringUtil.checkNull(mid)) {
			sql +=" and shopinfo.mid=?";
			params.add(mid);
		}
		if(!StringUtil.checkNull(sid)) {
			sql +=" and sid=?";
			params.add(sid);
		}
		sql +=" order by sid limit ?,? ";
		params.add((page - 1)* rows);
		params.add(rows);
		return db.finds( sql,params);
	
	}
	//管理员 总店铺数  条件查询 配合 findByConditions
		@Override
		public int totalCs(String sname, String status, String nickName, String mid, String sid) {
			DBHelper db = new DBHelper();
			String sql="select count(sid) from shopinfo,memberinfo where shopinfo.mid=memberinfo.mid ";
			List<Object> params = new ArrayList<Object>();
			
			if(!StringUtil.checkNull(sname)) {
				sql +=" and sname like concat('%',?,'%')";  //like   '%源辰%'
				params.add(sname);
			}
			if(!StringUtil.checkNull(status)) {
				sql +=" and shopinfo.status=?";
				params.add(status);
			}
			if(!StringUtil.checkNull(nickName)) {
				sql +=" and nickName like concat('%',?,'%')";
				params.add(nickName);
			}
			if(!StringUtil.checkNull(mid)) {
				sql +=" and shopinfo.mid=?";
				params.add(mid);
			}
			if(!StringUtil.checkNull(sid)) {
				sql +=" and sid=?";
				params.add(sid);
			}
			return db.total(sql, params);
		}
		
		//管理员封禁
		@Override
		public int ban(String sid, String status) {
			DBHelper db = new DBHelper();
			String sql= "update shopinfo set status=? where sid=?";
			return db.update(sql, status,sid);
		}
		@Override
		public int alter(String sid, String sname, String intro, String pics, String tel, String status) {
			DBHelper db = new DBHelper();
			String sql= "update shopinfo set sname=?,tel=?,status=? ";
			List<Object> params = new ArrayList<Object>();
			params.add(sname);params.add(tel);params.add(status);
			if(!StringUtil.checkNull(intro)) {
				sql +=" ,intro=? ";
				params.add(intro);
			}
			if(!StringUtil.checkNull(pics)) {
				sql +=" ,pics=? ";
				params.add(pics);
			}
			sql+=" where sid=?";
			params.add(sid);
			return db.update(sql,params );
		}
		

}
