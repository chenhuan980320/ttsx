package com.ttsx.dao;

import java.util.List;
import java.util.Map;

import com.ttsx.bean.GoodsInfo;



public interface IGoodsInfoDao {
	
	
	
	//管理员 查商品 和对应 totals 查总记录数
	public int totals();
	public List<GoodsInfo> findByPages(int page,int rows);
	/**
	 * 禁用
	 * @param sid 
	 * @param status
	 * @return
	 */
	public int ban(String sid,String status);
	
	//管理员 商家 合并 多条件查询对应total
	public int totalC(String sid,String gid,String tid,String mid,String gname,String pricemin,String pricemax,String status);
	/**
	 * 多条件组合分页查询
	 * @param sid
	 * @param gname
	 * @param status
	 * @param page
	 * @param rows
	 * @return
	 */										// 						类型查找	商家后台查找	名字查找					价格范围查询			        是否销量排序			是否价格排序   		是否在架		库存是否足够
	public List<GoodsInfo> findByCondition(String sid,String gid,String tid,String mid,String gname,String pricemin,String pricemax,String isvolume,String isprice,String status,String nums,int page, int rows);
	public  List<Map<String, Object>> findByCs(String tid,String name,String pricemin,String pricemax,String paixu);	
	/****************************************************************************************************/
	//商家 查商品 和对应 totals 查总记录数
	public List<GoodsInfo> findByPage(int page,int rows,String mid);
	public int total(String mid);
	/**
	 * 添加商品信息
	 * @param sp
	 * @return
	 */
	public int add(GoodsInfo gf);
	/**
	 * 修改商品信息
	 * @param sp
	 * @return
	 */
	public int update(GoodsInfo gf);
	public int updateOder(String gid,String nums);
	
	
	
	
}
