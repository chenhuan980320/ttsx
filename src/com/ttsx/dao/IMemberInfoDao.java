package com.ttsx.dao;

import java.util.List;


import com.ttsx.bean.MemberInfo;






public interface IMemberInfoDao {
	/**
	 * 
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<MemberInfo> findByPage(int page,int rows);

	
	/**
	 * 修改会员信息
	 * @param sp
	 * @return
	 */
	public int update(MemberInfo mi);
	/**
	 * 查询所有会员信息
	 * @return
	 */
	public List<MemberInfo> findAll();
	
	
	public int reg(MemberInfo mi);
	public MemberInfo login(String account,String pwd,Integer sf);
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public int total();
	/**
	 * 带条件查总记录数
	 * @return
	 */
	public int total(String sf,String mid,String nickName,String status,String tel,String email);
	/**
	 * 带条件查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<MemberInfo> findByCondition(String sf,String mid,String nickName,String status,String tel,String email,int page,int rows);
	/**
	 * mid 单条件查询
	 * @param mid
	 * @return
	 */
	public MemberInfo findByMid(String mid);
	
}
