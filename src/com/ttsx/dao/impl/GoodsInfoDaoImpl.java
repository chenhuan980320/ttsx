package com.ttsx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ttsx.bean.GoodsInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.IGoodsInfoDao;
import com.ttsx.util.StringUtil;


public class GoodsInfoDaoImpl implements IGoodsInfoDao{

	@Override
	public int totals() {
		DBHelper db = new DBHelper();
		String sql = "select count(gid) from goodsinfo";
		return db.total(sql);
	}
	
	@Override
	public List<GoodsInfo> findByPages(int page, int rows) {
		DBHelper db = new DBHelper();
							//			      dintro详情   	             简介       单价		库存		销量
		String sql = "select gid,tid,sid,gname,pics,dintro,intro,price,inventory,volume,status  from goodsinfo order by gid desc limit ?,?";
		return db.finds(GoodsInfo.class, sql,(page-1)*rows, rows);
	}

	@Override
	public int ban(String gid, String status) {
		DBHelper db = new DBHelper();
		String sql= "update goodsinfo set status=? where gid=?";
		return db.update(sql, status,gid);
	}
	//多条件查询total
	@Override
	public int totalC(String sid,String gid,String tid,String mid,String gname,String pricemin,String pricemax,String status) {
		DBHelper db = new DBHelper();
		String sql = "select count(gid) from goodsinfo g,shopinfo s where g.sid=s.sid ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtil.checkNull(sid)) {
			sql += " and g.sid =?";
			params.add(sid);
		}
		if(!StringUtil.checkNull(gid)) {
			sql += " and g.gid =?";
			params.add(gid);
		}
		if(!StringUtil.checkNull(tid)) {
			sql += " and tid =?";
			params.add(tid);
		}
		if(!StringUtil.checkNull(mid)) {
			sql += " and mid =?";
			params.add(mid);
		}
		if(!StringUtil.checkNull(gname)) {
			sql += " and gname like concat('%',?,'%')";
			params.add(gname);
		}
		
		if(!StringUtil.checkNull(status)) {
			sql += " and g.status =?";
			params.add(status);
		}
		if(!StringUtil.checkNull(pricemin)) {
			sql += " and price > ? ";
			params.add(pricemin);
		}
		if(!StringUtil.checkNull(pricemax)) {
			sql += " and price < ? ";
			params.add(pricemax);
		}
		return db.total(sql, params);
	}
	@Override
	public List<Map<String, Object>> findByCs(String tid, String name, String pricemin, String pricemax,String paixu) {
		DBHelper db = new DBHelper();
		String sql="select gid,mid,tid,g.sid,gname,sname,g.pics,dintro,g.intro,price,inventory,volume,g.status  from goodsinfo g,shopinfo s where g.sid=s.sid and g.status = 1 and s.status = 1 ";
		List<Object> params = new ArrayList<Object>();
		
		if(!StringUtil.checkNull(tid)) {
			sql += " and tid =?";
			params.add(tid);
		}
		
		if(!StringUtil.checkNull(name)) {
			sql += " and ( gname like concat('%',?,'%') or sname like concat('%',?,'%') ) ";
			params.add(name);
			params.add(name);
		}
		
		
		if(!StringUtil.checkNull(pricemin)) {
			sql += " and price > ? ";
			params.add(pricemin);
		}
		if(!StringUtil.checkNull(pricemax)) {
			sql += " and price < ?  ";
			params.add(pricemax);
		}
		//是否按销量排序   1销量升序  2 销量降序 3价格升序4价格降序
		if(!StringUtil.checkNull(paixu)) {
			if(paixu.equals("1")) {
				sql +=" order by volume  "; //销量升序
			}else if(paixu.equals("2")){
				sql +=" order by volume desc  ";   //销量降序
			}else if(paixu.equals("3")){
				sql +=" order by price  ";    //价格升序
			}else if(paixu.equals("4")){
				sql +=" order by price  desc";    //价格降序
			}
			
			return db.finds(sql, params);
		}
		
		
		
		
		return db.finds(sql, params);
	}
	@Override
	public List<GoodsInfo> findByCondition(String sid,String gid,String tid,String mid,String gname,String pricemin,String pricemax,String isvolume,String isprice,String status,String nums,int page, int rows) {
		DBHelper db = new DBHelper();
		//select gid,mid,tid,g.sid,gname,g.pics,dintro,g.intro,price,inventory,volume,g.status  from goodsinfo g,shopinfo s where g.sid=s.sid and g.sid ='1' and gid='1' and mid = '24' and gname like concat('%','好','%')
		String sql="select gid,mid,tid,g.sid,gname,g.pics,dintro,g.intro,price,inventory,volume,g.status  from goodsinfo g,shopinfo s where g.sid=s.sid ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtil.checkNull(sid)) {
			sql += " and g.sid =?";
			params.add(sid);
		}
		if(!StringUtil.checkNull(gid)) {
			sql += " and gid =?";
			params.add(gid);
		}
		if(!StringUtil.checkNull(tid)) {
			sql += " and tid =?";
			params.add(tid);
		}
		if(!StringUtil.checkNull(mid)) {
			sql += " and mid =?";
			params.add(mid);
		}
		if(!StringUtil.checkNull(gname)) {
			sql += " and gname like concat('%',?,'%')";
			params.add(gname);
		}
		
		if(!StringUtil.checkNull(status)) {
			sql += " and g.status =?";
			params.add(status);
		}
		if(!StringUtil.checkNull(pricemin)) {
			sql += " and price >pricemin ";
			params.add(status);
		}
		if(!StringUtil.checkNull(pricemax)) {
			sql += " and price <pricemax ";
			params.add(pricemax);
		}
		if(!StringUtil.checkNull(nums)) {
			sql += " and inventory >= ? ";
			params.add(nums);
		}
		//是否按销量排序   1升序  2 为降序
		if(!StringUtil.checkNull(isvolume)) {
			if(isvolume.equals("1")) {
				sql +=" order by volume limit ?,? "; 
			}else {
				sql +=" order by volume desc  limit ?,? ";   //降序
			}
			params.add((page - 1)* rows);
			params.add(rows);
			return db.finds(GoodsInfo.class,sql, params);
		}
		
		//是否按价格排序
		if(!StringUtil.checkNull(isprice)) {
			if(isprice.equals("1")) {
				sql +=" order by price limit ?,? "; 
			}else {
				sql +=" order by price desc  limit ?,? ";   //降序
			}
			params.add((page - 1)* rows);
			params.add(rows);
			return db.finds(GoodsInfo.class,sql, params);
		}
		
		params.add((page - 1) * rows);
		params.add(rows);
		sql += " order by gid desc limit ?, ?";
		//System.out.println(db.finds(GoodsInfo.class,sql, params));
		return db.finds(GoodsInfo.class,sql, params);
	}
	//*****************************************************************************
	//商家 后台查询  
	@Override
	public List<GoodsInfo> findByPage(int page, int rows, String mid) {
		DBHelper db = new DBHelper();
				//			      					   dintro详情     简介       单价		库存		销量
		String sql = "select gid,tid,g.sid,gname,g.pics,dintro,g.intro,price,inventory,volume,g.status  from goodsinfo g,shopinfo s where g.sid=s.sid and mid=? order by gid desc limit ?,?";
		return db.finds(GoodsInfo.class, sql, mid,(page-1)*rows, rows);
	}
	
	@Override
	public int total(String mid) {
		//select count(gid) from goodsinfo g,shopinfo s where g.sid=s.sid and mid='24'
		DBHelper db = new DBHelper();
		String sql = "select count(gid) from goodsinfo g,shopinfo s where g.sid=s.sid and mid=?";
		return db.total(sql,mid);
	}

	@Override
	public int add(GoodsInfo gf) {
		DBHelper db = new DBHelper();		//	gid ,tid,sid,gname,pics,dintro,intro,price,inventory,volume,status
		String sql = "insert into goodsinfo values(0,  ?, ? ,?    ,?   ,?     ,    ?,    ?,        ?,     ?,1)";
		return db.update(sql, gf.getTid(),gf.getSid(),gf.getGname(),gf.getPics(),gf.getDintro(),gf.getIntro(),
				gf.getPrice(),gf.getInventory(),gf.getVolume());
	}

	@Override
	public int update(GoodsInfo gf) {
		DBHelper db = new DBHelper();
		String sql="update goodsinfo set sid=?,tid=?,gname=?,intro=?,price=?,inventory=?,status=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(gf.getSid());params.add(gf.getTid());params.add(gf.getGname());
		params.add(gf.getIntro());params.add(gf.getPrice());params.add(gf.getInventory());params.add(gf.getStatus());
		
		if(!StringUtil.checkNull(gf.getPics())) {
			sql += " and pics =? ";
			params.add(gf.getPics());
		}
		if(!StringUtil.checkNull(gf.getDintro())) {
			sql += " and dintro =? ";
			params.add(gf.getDintro());
		}
	 sql +=" where gid=? ";
	 params.add(gf.getGid());
		return db.update(sql,params);
	}

	@Override
	public int updateOder(String gid, String nums) {
		DBHelper db = new DBHelper();
		String sql="update goodsinfo set inventory=inventory- ? ,volume=volume + ? where gid=? ";
		return db.update(sql,nums,nums,gid);
	}




}
