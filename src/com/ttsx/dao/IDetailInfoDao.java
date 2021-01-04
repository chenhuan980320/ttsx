package com.ttsx.dao;

import java.util.List;

import com.ttsx.bean.Detailinfo;




public interface IDetailInfoDao {
	
	
	
	/**
	 * 查询所有信息
	 * @return
	 */
	public List<Detailinfo> findAll(String oid);
	
	public int add(Detailinfo di);
}
