package com.ttsx.dao;

import java.util.List;
import java.util.Map;

import com.ttsx.bean.ShopInfo;



public interface IShopInfoDao {
	/**
	 * 管理员封禁
	 * @param sid
	 * @param status
	 * @return
	 */
	public int ban(String sid, String status);
	//商家修改
	public int alter(String sid, String sname, String intro,String pics,String tel,String status);
	/**
	 * 管理员查询店铺
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> findByPages(int page,int rows);
	/**
	 * 商家查询店铺
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<ShopInfo> findByPage(int page,int rows,int mid);

	/**
	 * 添加店铺信息
	 * @param sp
	 * @return
	 */
	public int add(ShopInfo sp);
	/**
	 * 修改店铺信息
	 * @param sp
	 * @return
	 */
	public int update(ShopInfo sp);
	/**
	 * 查询所有正常营业的店铺的编号和名称
	 * @return
	 */
	public List<ShopInfo> finds();
	/**
	 * 根据店铺编号查询店铺详细
	 * @param sid
	 * @return
	 */
	public ShopInfo findBySid(String sid);
	//查记录数  管理员 findByPages
	public int totals();
	//查记录数  商家 findByPage
	public int total(String tid);
	
	public List<ShopInfo> findByCondition(String mid,String sname,String status,int page, int rows);
	public List<Map<String, Object>>  findByConditions(String sname, String status, String nickName, String mid, String sid, int page, int rows);
	public int totalCs(String sname, String status, String nickName, String mid, String sid);
	public List<ShopInfo> findMid(String mid);
}
