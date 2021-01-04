package com.ttsx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ttsx.bean.MemberInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.IMemberInfoDao;
import com.ttsx.util.StringUtil;

public class MemberInfoDaoImpl implements IMemberInfoDao{

	@Override
	public List<MemberInfo> findByPage(int page, int rows) {
		DBHelper db = new DBHelper();
		String sql = "select mid, nickName,tel,email,photo,status,sf from memberinfo order by mid limit ?, ?";
		return db.finds(MemberInfo.class, sql, (page-1) * rows, rows);
	}

	@Override
	public int update(MemberInfo mi) {//后台修改信息
		DBHelper db = new DBHelper();
//		String sql = "update memberinfo set 1=1 ";
//		List<Object> params = new ArrayList<Object>();
//		if(!StringUtil.checkNull(mi.getNickName())) {
//			sql +=" ,nickName=? ";
//			params.add(mi.getNickName());
//		}
//		if(!StringUtil.checkNull(mi.getTel()+"")) {
//			sql +=" ,tel=? ";
//			params.add(mi.getTel());
//		}
//		if(!StringUtil.checkNull(mi.getEmail())) {
//			sql +=",email=? ";
//			params.add(mi.getTel());
//		}
//		if(!StringUtil.checkNull(mi.getStatus()+"")) {
//			sql +=",status=? ";
//			params.add(mi.getStatus());
//		}
//		sql +=" where mid=? ";
//		params.add(mi.getMid());
//			return db.update(sql,params);
		
		if(mi.getPhoto()!="" && mi.getPhoto()!=null) {//不为空则有图片
			String sql = "update memberinfo set nickName=?,tel=?, email=?,photo=?, status=? where mid=?";
			return db.update(sql,mi.getNickName(),mi.getTel(), mi.getEmail(),mi.getPhoto(),mi.getStatus(),mi.getMid());
		}else {
			String sql = "update memberinfo set nickName=?,tel=?, email=?, status=? where mid=?";
			return db.update(sql,mi.getNickName(),mi.getTel(), mi.getEmail(),mi.getStatus(),mi.getMid());
		}
	}

	@Override
	public List<MemberInfo> findAll() {
		DBHelper db = new DBHelper();
		String sql = "select  mid, nickName, pwd,tel,email,photo,status,sf from memberinfo order by mid";
		return db.finds(MemberInfo.class, sql);
	}

	@Override
	public int total() {
		DBHelper db = new DBHelper();
		String sql = "select count(mid) from memberinfo";
		return db.total(sql);
	}
	
	@Override
	public int reg(MemberInfo mf) {
		DBHelper db = new DBHelper();
		String sql = "insert into memberinfo values(0, ?, md5(?), ?, ?, ?, ?,?)";
		//System.out.println(sql);
		return db.update(sql, mf.getNickName(),mf.getPwd(),mf.getTel(),mf.getEmail(),mf.getPhoto(),mf.getStatus(),mf.getSf());
	}

	@Override
	public MemberInfo login(String account, String pwd,Integer sf) {
		DBHelper db = new DBHelper();	 
		String sql = "select mid, nickName, tel, email,photo,status,sf from memberinfo where status !=0 and sf =? and pwd=md5(?) and (tel=? or nickName=? or email=?)";
	//	System.out.println(sf+"::"+pwd+"::"+account+"::"+db.find(MemberInfo.class, sql,sf, pwd, account,account, account));
		return db.find(MemberInfo.class, sql,sf, pwd, account,account, account);
	}

	@Override
	public int total(String sf, String mid, String nickName, String status,String tel,String email) {
		DBHelper db = new DBHelper();	 
		String sql="select count(mid) from memberinfo where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtil.checkNull(mid)) {
			sql +=" and mid=? ";
			params.add(mid);
		}
		if(!StringUtil.checkNull(sf)) {
			sql +=" and sf=? ";
			params.add(sf);
		}
		if(!StringUtil.checkNull(nickName)) {
			sql +=" and nickName like concat('%',?,'%') ";
			params.add(nickName);
		}
		if(!StringUtil.checkNull(status)) {
			sql +=" and status=? ";
			params.add(status);
		}
		if(!StringUtil.checkNull(tel)) {
			sql +=" and tel=? ";
			params.add(tel);
		}
		if(!StringUtil.checkNull(email)) {
			sql +=" and email=? ";
			params.add(email);
		}
		return  db.total(sql, params);
	}

	@Override
	public List<MemberInfo> findByCondition(String sf, String mid, String nickName, String status,String tel,String email, int page, int rows) {
		DBHelper db = new DBHelper();	 
		String sql="select mid, nickName,tel,email,photo,status,sf from memberinfo where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtil.checkNull(mid)) {
			sql +=" and mid=? ";
			params.add(mid);
		}
		if(!StringUtil.checkNull(sf)) {
			sql +=" and sf=? ";
			params.add(sf);
		}
		if(!StringUtil.checkNull(nickName)) {
			sql +=" and nickName like concat('%',?,'%') ";
			params.add(nickName);
		}
		if(!StringUtil.checkNull(status)) {
			sql +=" and status=? ";
			params.add(status);
		}
		if(!StringUtil.checkNull(tel)) {
			sql +=" and tel=? ";
			params.add(tel);
		}
		if(!StringUtil.checkNull(email)) {
			sql +=" and email=? ";
			params.add(email);
		}
		sql +=" order by mid limit ?,? ";
		params.add((page -1) * rows);
		params.add(rows);
		return  db.finds(MemberInfo.class,sql, params);
	}
/**
 *mid  查询
 */
	@Override
	public MemberInfo findByMid(String mid) {
		DBHelper db = new DBHelper(); 
		String sql = "select  mid, nickName, pwd,tel,email,photo,status,sf from memberinfo where mid=?";
		return db.find(MemberInfo.class, sql,mid);
	}
	
	

}



