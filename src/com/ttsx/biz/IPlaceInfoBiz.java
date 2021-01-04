package com.ttsx.biz;

import java.util.List;

import com.ttsx.bean.PlaceInfo;




public interface IPlaceInfoBiz {
	
	/**
	 * 修改地址
	 * @param sp
	 * @return
	 */
	public int update(PlaceInfo pi);
	/**
	 * 查询所有会员地址
	 * @return
	 */
	public List<PlaceInfo> findAll(String mid);
	
	public PlaceInfo findPid(String pid);
	/**
	 * 添加地址
	 * @param mi
	 * @return
	 */
	public int add(PlaceInfo pi);
	/**
	 * 删除地址
	 * @param pid
	 * @return
	 */
	public int del(String pid);
	

	
	
	
}
