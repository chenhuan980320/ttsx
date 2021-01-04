package com.ttsx.dao;

import java.util.List;


import com.ttsx.bean.OrderInfo;



public interface IOrderInfoDao {
	/**
	 * 
	 * 分页查询    mid  商家   或   会员
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<OrderInfo> findByPage(String mid,int page,int rows);
	public int total(String mid);
	
	/**
	 * 修改订单信息
	 * @param sp
	 * @return
	 */
	public int update(String status);
	/**
	 * 查询所有信息
	 * @return
	 */
	public List<OrderInfo> findAll();
	
	public int add(String oid,String mid,String sid,String pid,String total);
}
