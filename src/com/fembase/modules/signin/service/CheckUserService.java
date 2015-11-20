package com.fembase.modules.signin.service;

import com.fembase.modules.signin.entity.TokenInfo;

public interface CheckUserService {

	public TokenInfo checkUser(String uid,String sessiontoken);
	
}
