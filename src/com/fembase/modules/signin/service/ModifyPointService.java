package com.fembase.modules.signin.service;

public interface ModifyPointService {
	
	public int addPoint(String uid,String point,String type);
	public int subPoint(String uid,String point);

}
