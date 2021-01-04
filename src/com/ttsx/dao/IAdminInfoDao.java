package com.ttsx.dao;

import java.util.List;

import com.ttsx.bean.AdminInfo;








public interface IAdminInfoDao {
	/**
	 * 
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<AdminInfo> findByPage(int page,int rows);

	
	/**
	 * 修改管理员信息
	 * @param sp
	 * @return
	 */
	public int update(AdminInfo mi);
	/**
	 * 查询所有管理员信息
	 * @return
	 */
	public List<AdminInfo> findAll();
	/**
	 * 获取总记录数
	 * @param tid
	 * @param sname
	 * @param status
	 * @return
	 */
	
	public int total();
	
	public int reg(AdminInfo mi);
	/**
	 * 登陆
	 * @param account
	 * @param pwd
	 * @return
	 */
	public AdminInfo login(String account,String pwd); 
	
}
