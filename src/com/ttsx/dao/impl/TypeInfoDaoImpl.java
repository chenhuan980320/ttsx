package com.ttsx.dao.impl;

import java.util.List;

import com.ttsx.bean.TypeInfo;
import com.ttsx.dao.DBHelper;
import com.ttsx.dao.ITypeInfoDao;
/**
 * 商品类型数据模型层实现
 * company 逸恒科技
 * @author 胡66
 * @data 2020年10月26日
 * Email 906430016@qq.com
 */
public class TypeInfoDaoImpl implements ITypeInfoDao{
	//添加
	@Override
	public int add(TypeInfo tf) {
		
		DBHelper db = new DBHelper();
		String sql="insert into typeinfo values(0,?,?)";
		return db.update(sql, tf.getTname(),tf.getStatus());
	}
	//修改
	@Override
	public int update(TypeInfo tf) {
		DBHelper db = new DBHelper();
		String sql="update typeinfo set tname=?,status=? where tid=?";
		return db.update(sql, tf.getTname(),tf.getStatus(),tf.getTid());
	}
	/**
	 * 查询所有商品类型信息
	 *返回一个list对象集合
	 * @return
	 */
	@Override
	public List<TypeInfo> findAll() {
		DBHelper db = new DBHelper();
		String sql = "select tid ,tname,status from typeinfo order by tid";
		//System.out.println(db.finds(TypeInfo.class, sql));
		return db.finds(TypeInfo.class, sql);
	}
	/**
	 * 获取未下架的商品类型信息
	 * @return
	 */
	@Override
	public List<TypeInfo> finds() {
		DBHelper db = new DBHelper();
		String sql="select tid ,tname,status from typeinfo where status !=0 order by tid";
		return db.finds(TypeInfo.class, sql);
	}
	/**
	 * 获取总记录
	 * @return
	 */
	@Override
	public int total() {
		DBHelper db = new DBHelper();
		String sql = "select count(tid) from typeinfo";
		return db.total(sql);
	}
	/**
	 * 分页查询
	 * @param page  查询第几页
	 * @param rows	每页显示多少行
	 * @return
	 */
	@Override
	public List<TypeInfo> findByPage(int page, int rows) {
		DBHelper db = new DBHelper();
		String sql = "select tid,tname,status from typeinfo order by tid limit ?,? ";
		
		return db.finds(TypeInfo.class,sql,(page-1) * rows,rows);
	}

}
