package com.ttsx.biz;

import java.util.List;

import com.ttsx.bean.TypeInfo;
import com.ttsx.dto.JsonObject;

public interface ITypeInfoBiz {

	/**
	 * 添加商品类型信息
	 * 
	 * @param tf
	 * @return
	 */
	public int add(TypeInfo tf);

	/**
	 * 修改商品类型信息
	 * 
	 * @param tf
	 * @return
	 */
	public int update(TypeInfo tf);

	/**
	 * 查询所有商品类型信息
	 * 
	 * @return
	 */
	public List<TypeInfo> findAll();

	/**
	 * 获取未下架的商品类型信息
	 * 
	 * @return
	 */
	public List<TypeInfo> finds();

	
	/**
	 * 分页查询
	 * 
	 * @param page 查询第几页
	 * @param rows 每页显示多少行
	 * @return
	 */
	public JsonObject findByPage(int page, int rows);
}
