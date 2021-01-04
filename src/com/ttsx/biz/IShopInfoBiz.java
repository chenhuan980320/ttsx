package com.ttsx.biz;

import java.util.List;

import com.ttsx.bean.ShopInfo;




public interface IShopInfoBiz<JsonObject> {
	/**
	 * 管理员封禁
	 * @param sid
	 * @param status
	 * @return
	 */
	public int ban(String sid, String status);
	/**
	 * 商家修改
	 * @param sid
	 * @param status
	 * @return
	 */
	public int alter(String sid, String sname, String intro,String pics,String tel,String status);
	/**
	 * 管理员查店铺
	 * @param page
	 * @param rows
	 * @return
	 */
	public JsonObject findByPages(int page,int rows);
	/**
	 * 商家查店铺
	 * @param page
	 * @param rows
	 * @param mid
	 * @return
	 */
	public JsonObject findByPage(int page,int rows,int mid);
	
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
	//商家条件搜索
	public JsonObject findByCondition(String tid,String sname,String status,int page, int rows);
	//管理员条件搜索
	public JsonObject findByConditions(String sname, String status, String nickName, String mid, String sid,  int page, int rows);
	public List<ShopInfo> findMid(String mid);
	
}
