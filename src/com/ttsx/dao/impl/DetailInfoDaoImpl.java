package com.ttsx.dao.impl;

import java.util.List;

import com.ttsx.bean.AdminInfo;
import com.ttsx.bean.Detailinfo;
import com.ttsx.bean.OrderInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.IDetailInfoDao;
import com.ttsx.dao.IOrderInfoDao;


public class DetailInfoDaoImpl implements IDetailInfoDao{

	@Override
	public List<Detailinfo> findAll(String oid) {
		DBHelper db = new DBHelper();
		String sql = "select did,oid, gid,gname,pics,price,nums from detailinfo where  oid=? ";
		return db.finds(Detailinfo.class, sql, oid);
		
	}

	@Override
	public int add(Detailinfo di) {
		DBHelper db = new DBHelper();
		String sql = "insert into detailinfo values(0, ?,?,?,?,?, ?)";
		return db.update(sql, di.getOid(), di.getGid(), di.getGname(), di.getPics(), di.getPrice(), di.getNums());
	}

	

	


/*
	@Override
	
	public List<AdminInfo> findByPage(int page, int rows) {
		
		String sql = "select aid,tel, aname,pwd,email,status from adminInfo where status != 100 order by aid limit ?, ? ";
		return db.finds(AdminInfo.class, sql, (page-1) * rows, rows);
	}

	@Override
	public int update(AdminInfo ai) {
		
		String sql = "update adminInfo set pwd=md5(?), tel=?, email=?, status=? where aid=?";
		return db.update(sql, ai.getPwd(),ai.getTel(), ai.getEmail(), ai.getStatus(),ai.getAid());
	}
	//查询所有
	@Override
	public List<AdminInfo> findAll() {
		DBHelper db = new DBHelper();
		String sql = "select aid,tel, aname,pwd,email,status from adminInfo  order by mid";
		return db.finds(AdminInfo.class, sql);
	}
//获取总记录数
	@Override
	public int total() {
		DBHelper db = new DBHelper();
		String sql = "select count(aid) from adminInfo";
		return db.total(sql);
	}
//	注册
	@Override
	public int reg(AdminInfo af) {
		DBHelper db = new DBHelper();
		String sql = "insert into adminInfo values(0, ?,?,md5(?), ?, 1)";
		
		return db.update(sql, af.getTel(), af.getAname(),af.getPwd(),af.getEmail());
	}
	//登陆
	@Override
	public AdminInfo login(String account, String pwd) {
		DBHelper db = new DBHelper();		
		String sql = "select aid, aname, tel, email,status from admininfo where status !=0 and pwd=md5(?) 	and (tel=? or email=?)";
		return db.find(AdminInfo.class, sql, pwd, account, account);
	}

	*/

}



