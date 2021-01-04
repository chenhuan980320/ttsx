package com.ttsx.biz;

import java.util.List;

import com.ttsx.bean.AdminInfo;
import com.ttsx.dto.JsonObject;








public interface IAdminInfoBiz {
	/**
	 * 
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public JsonObject findByPage(int page,int rows);

	
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
	
	
	public int reg(AdminInfo ai);
	/**
	 * 登陆
	 * @param account
	 * @param pwd
	 * @return
	 */
	public AdminInfo login(String account,String pwd); 
	
}
