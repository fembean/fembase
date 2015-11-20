package com.fembase.modules.signin.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * signEntity
 * @author zzb
 * @version 2015-09-19
 */
public class SignIn {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// uid
	private Integer signinCount;		// 统计连续签到次数
	private Date lastSignin;		// 上一次签到时间
	private String continuous;		// 是否连续签到
	private String canSignin;		// 今天是否可以签到
	
	public SignIn() {
		
	}



	@Length(min=1, max=11, message="uid长度必须介于 1 和 11 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=1, max=50, message="统计连续签到次数长度必须介于 1 和 50 之间")
	public Integer getSigninCount() {
		return signinCount;
	}

	public void setSigninCount(Integer signinCount) {
		this.signinCount = signinCount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="上一次签到时间不能为空")
	public Date getLastSignin() {
		return lastSignin;
	}

	public void setLastSignin(Date lastSignin) {
		this.lastSignin = lastSignin;
	}
	
	public String getContinuous() {
		return continuous;
	}

	public void setContinuous(String continuous) {
		this.continuous = continuous;
	}
	
	public String getCanSignin() {
		return canSignin;
	}

	public void setCanSignin(String canSignin) {
		this.canSignin = canSignin;
	}
	
}