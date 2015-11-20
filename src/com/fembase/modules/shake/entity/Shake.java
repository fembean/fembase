package com.fembase.modules.shake.entity;

import com.fembase.common.persistence.DataEntity;

public class Shake extends DataEntity<Shake>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cost;
	private String uid;

	public Shake() {
		super();
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
