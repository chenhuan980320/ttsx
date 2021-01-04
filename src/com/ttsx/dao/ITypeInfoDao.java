package com.ttsx.dao;

import java.util.List;

import com.ttsx.bean.TypeInfo;

/**
 * 商品类型数据模型层接口
 * company 逸恒科技
 * @author 胡66
 * @data 2020年10月26日
 * Email 906430016@qq.com
 */
public interface ITypeInfoDao {
	/**
	 * 添加商品类型信息
	 * @param tf
	 * @return
	 */
	public int add(TypeInfo tf);
	/**
	 * 修改商品类型信息
	 * @param tf
	 * @return
	 */
	public int update(TypeInfo tf);
	/**
	 * 查询所有商品类型信息
	 * @return
	 */
	public List<TypeInfo> findAll();
	/**
	 * 获取未下架的商品类型信息
	 * @return
	 */
	public List<TypeInfo> finds();
	/**
	 * 获取总记录
	 * @return
	 */
	public int total();
	/**
	 * 分页查询
	 * @param page  查询第几页
	 * @param rows	每页显示多少行
	 * @return
	 */
	public List<TypeInfo> findByPage(int page,int rows);
}
