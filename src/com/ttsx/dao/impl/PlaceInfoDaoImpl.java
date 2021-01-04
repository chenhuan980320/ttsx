package com.ttsx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.ttsx.bean.MemberInfo;
import com.ttsx.bean.PlaceInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.IPlaceInfoDao;
import com.ttsx.util.StringUtil;

public class PlaceInfoDaoImpl implements IPlaceInfoDao{

	@Override
	public int update(PlaceInfo pi) {
		DBHelper db = new DBHelper();
		String sql = "update placeinfo set pname=?,tel=?, province=?,city=?, area=?,addr=?,status=? where pid=?";
		return db.update(sql,pi.getPname(),pi.getTel(), pi.getProvince(),pi.getCity(),pi.getArea(),pi.getAddr(),pi.getStatus(),pi.getPid());
	}

	@Override
	public List<PlaceInfo> findAll(String mid) {
		DBHelper db = new DBHelper();
		String sql = "select  pid, pname, mid,tel,province,city,area,addr,status from placeinfo where mid=? order by status desc";
		return db.finds(PlaceInfo.class, sql,mid);
	}

	@Override
	public int add(PlaceInfo pi) {
		DBHelper db = new DBHelper();
		String sql = "insert into placeinfo values(0, ?, ?, ?, ?, ?, ?,?,?)";
		return db.update(sql,pi.getPname(),pi.getMid(),pi.getTel(),pi.getProvince(),pi.getCity(),pi.getArea(),pi.getAddr(),pi.getStatus());
	}

	@Override
	public int del(String pid) {
		DBHelper db = new DBHelper();
		String sql = "delete from placeinfo where pid=? ";
		return db.update(sql, pid);
	}

	@Override
	public PlaceInfo findPid(String pid) {
		DBHelper db = new DBHelper();
		String sql = "select  pid, pname, mid,tel,province,city,area,addr,status from placeinfo where pid=? ";
		return db.find(PlaceInfo.class, sql,pid);
	}


	

}



