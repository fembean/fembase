package com.fembase.modules.signin.service;

import java.util.List;

import com.fembase.common.persistence.Page;
import com.fembase.modules.signin.entity.SignIn;

/**
 * signService
 * @author zzb
 * @version 2015-09-19
 */

public interface SignInService {


	public SignIn get(String uid);	
	public List<SignIn> findList(SignIn SignIn);
	public Page<SignIn> findPage(Page<SignIn> page, SignIn SignIn);
	public boolean save(SignIn SignIn);
	public boolean delete(SignIn SignIn);
	public boolean update(SignIn SignIn);
	
}