package com.ttsx.biz;


import java.util.List;

import com.ttsx.bean.CartInfo;

public interface ICartInfoBiz {
	public int add(Integer mid,String gid, String nums);
	public List<CartInfo> findByMid(Integer mid);
	
	public int update(String cid, String nums);
	public int del(String cid);
	public List<CartInfo> res(Integer mid,String gid);
	
	//提交页面查询 3表   
	public List<CartInfo> findByCids(String cids);
	//批量删除
	public int dels(String cids);
}
