package com.fembase.modules.signin.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fembase.common.persistence.Page;
import com.fembase.modules.signin.dao.SignInDao;
import com.fembase.modules.signin.entity.SignIn;
import com.fembase.modules.signin.service.SignInService;

@Service
@Transactional
public class SignInServiceImpl implements SignInService{
	protected Log log = LogFactory.getLog(getClass());
	@Autowired
	private SignInDao signinDao;
	@Override
	public SignIn get(String uid) {
		// TODO Auto-generated method stub
		SignIn signIn=null;
		if(null==uid||"".equals(uid)){
			log.error("ID为空 ");
			return null;
		}
		try {
			signIn=signinDao.get(uid);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return signIn;
	}

	@Override
	public List<SignIn> findList(SignIn SignIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SignIn> findPage(Page<SignIn> page, SignIn SignIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(SignIn SignIn) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(null==SignIn){
			log.error("signin 对象为空");
			return flag;
		}
		try {
			signinDao.insert(SignIn);
			flag= true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		return flag;
		
	}

	@Override
	public boolean delete(SignIn SignIn) {
		// TODO Auto-generated method stub
		boolean flag=false;
		try {
			signinDao.delete(SignIn);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	@Override
	public boolean update(SignIn SignIn) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(null==SignIn){
			log.error("signin 对象为空");
			return flag;
		}
		try {
			signinDao.update(SignIn);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

}
