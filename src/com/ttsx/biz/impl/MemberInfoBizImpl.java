package com.ttsx.biz.impl;

import java.util.List;


import com.ttsx.bean.MemberInfo;
import com.ttsx.biz.IMemberInfoBiz;
import com.ttsx.dao.IMemberInfoDao;
import com.ttsx.dao.impl.MemberInfoDaoImpl;
import com.ttsx.dto.JsonObject;
import com.ttsx.util.StringUtil;




public class MemberInfoBizImpl implements IMemberInfoBiz{

	@Override
	public JsonObject findByPage(int page, int rows) {
	IMemberInfoDao memberInfoDao = new MemberInfoDaoImpl();
	return new JsonObject(memberInfoDao.total(), memberInfoDao.findByPage(page, rows));
	} 


	@Override
	public int update(MemberInfo mi) {
		if (StringUtil.checkNull(mi.getEmail())) {
			return -1;
		}
		IMemberInfoDao memberInfoDao = new MemberInfoDaoImpl(); 
		return memberInfoDao.update(mi);
	}

	@Override
	public List<MemberInfo> findAll() {
		IMemberInfoDao memberInfoDao = new MemberInfoDaoImpl(); 
		return memberInfoDao.findAll();
	}


	@Override
	public int reg(MemberInfo mf) {
		if(StringUtil.checkNull(mf.getNickName(),mf.getEmail(),mf.getPwd())) {
			return -1;
		}
		IMemberInfoDao memberInfo = new MemberInfoDaoImpl();
		return memberInfo.reg(mf);
	}

	@Override
	public MemberInfo login(String account, String pwd,Integer sf) {
		if(StringUtil.checkNull(account,pwd)) {
			return null;
		}
		IMemberInfoDao memberInfo = new MemberInfoDaoImpl();
		return memberInfo.login(account, pwd,sf);
	}


	@Override
	public JsonObject findByCondition(String sf, String mid, String nickName, String status,String tel,String email,  int page, int rows) {
		IMemberInfoDao memberInfoDao = new MemberInfoDaoImpl();
		return new JsonObject(memberInfoDao.total( sf, mid, nickName, status, tel, email), memberInfoDao.findByCondition(sf, mid, nickName, status, tel, email, page, rows));
	}

	/**
	 * mid查询
	 */
	@Override
	public MemberInfo findByMid(String mid) {
		if (StringUtil.checkNull(mid)) {
			return null;
		}
		IMemberInfoDao memberInfo = new MemberInfoDaoImpl();
		return memberInfo.findByMid(mid);
	}
}
