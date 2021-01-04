package com.ttsx.biz.impl;

import java.util.List;

import com.ttsx.bean.TypeInfo;
import com.ttsx.biz.ITypeInfoBiz;
import com.ttsx.dao.ITypeInfoDao;
import com.ttsx.dao.impl.TypeInfoDaoImpl;
import com.ttsx.dto.JsonObject;
import com.ttsx.util.StringUtil;

public class TypeInfoBizImpl implements ITypeInfoBiz {

	

	@Override
	public int update(TypeInfo tf) {
		if (StringUtil.checkNull(tf.getTname())) {
			return -1;
		}
		ITypeInfoDao typeInfoDao = new TypeInfoDaoImpl();
		return typeInfoDao.update(tf);
	}

	@Override
	public List<TypeInfo> findAll() {

		ITypeInfoDao typeInfoDao = new TypeInfoDaoImpl();
		return typeInfoDao.findAll();
	}

	@Override
	public List<TypeInfo> finds() {
		ITypeInfoDao typeInfoDao = new TypeInfoDaoImpl();
		return typeInfoDao.finds();
	}

	/**
	 * 针对easyui 中分页查询的  easyui 有分页组件  但必须安装这个分页组件中所有的数据格式返回数据
	 * {total:"总记录数"，rows:  [{}, {}]   }
	 */
	@Override
	public JsonObject findByPage(int page, int rows) {
		ITypeInfoDao typeInfoDao = new TypeInfoDaoImpl();
		//System.out.println( "JsonObject"+new JsonObject(typeInfoDao.total(), typeInfoDao.findByPage(page, rows)));
		return new JsonObject(typeInfoDao.total(), typeInfoDao.findByPage(page, rows));
	}						
	@Override
	public int add(TypeInfo tf) {
		if (StringUtil.checkNull(tf.getTname())) {
			return -1;
		}
		ITypeInfoDao typeInfoDao = new TypeInfoDaoImpl();
		return typeInfoDao.add(tf);
	}
	

	

	

}
