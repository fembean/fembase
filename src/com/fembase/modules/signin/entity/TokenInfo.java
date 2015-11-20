package com.fembase.modules.signin.entity;

import java.io.Serializable;

public class TokenInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3390706839683089186L;
	
	private String uid;
	private String sessionToken;
	private String userType;
	private String localsecret;
	public String getLocalsecret() {
		return localsecret;
	}
	public void setLocalsecret(String localsecret) {
		this.localsecret = localsecret;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

}
