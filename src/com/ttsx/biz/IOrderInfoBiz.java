package com.ttsx.biz;

import java.util.List;

import com.ttsx.bean.AdminInfo;
import com.ttsx.bean.OrderInfo;
import com.ttsx.dto.JsonObject;








public interface IOrderInfoBiz {
	
	/**
	 * 
	 * 分页查询    mid  商家   或   会员
	 * @param page
	 * @param rows
	 * @return
	 */
	public JsonObject findByPage(String mid,int page,int rows);
	
	
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
