package com.ttsx.biz;

import java.util.List;


import com.ttsx.bean.MemberInfo;
import com.ttsx.dto.JsonObject;



public interface IMemberInfoBiz {
	/**
	 * 分页查询
	 * 
	 * @param page 查询第几页
	 * @param rows 每页显示多少行
	 * @return
	 */
	public JsonObject findByPage(int page,int rows);
		
	/**
	 * 修改会员信息
	 * @param mi
	 * @return
	 */
	public int update(MemberInfo mi);
	/**
	 * 查询所有会员信息
	 * @return
	 */
	public List<MemberInfo> findAll();
	
	public int reg(MemberInfo mf);
	public MemberInfo login(String account, String pwd,Integer sf);
	public JsonObject findByCondition(String sf,String mid,String nickName,String status,String tel,String email, int page,int rows);
	/**
	 * 
	 */
	public MemberInfo findByMid(String mid);
}
