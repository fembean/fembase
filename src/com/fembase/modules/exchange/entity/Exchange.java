package com.fembase.modules.exchange.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fembase.common.persistence.DataEntity;

/**
 * 兑换Entity
 * @author dong
 * @version 2015-09-25
 */
public class Exchange extends DataEntity<Exchange> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// uid
	private String account;		// account
	private String realName;		// real_name
	private String phone;		// phone
	private String email;		// email
	private Integer cash;		// cash
	private Integer needIntegral;		// need_integral
	private Integer state;		// 兑换状态：（0为为处理，1为成功兑换，2为拒绝兑换）
	private String detail;		// detail
	private Date createTime;		// create_time
	private Date updateTime;		// update_time
	private Date beginCreateTime;		// 开始 create_time
	private Date endCreateTime;		// 结束 create_time
	private Date beginUpdateTime;		// 开始 update_time
	private Date endUpdateTime;		// 结束 update_time
	
	public Exchange() {
		super();
	}

	public Exchange(String id){
		super(id);
	}

	@Length(min=1, max=11, message="uid长度必须介于 1 和 11 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=1, max=50, message="account长度必须介于 1 和 50 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=0, max=20, message="real_name长度必须介于 0 和 20 之间")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Length(min=0, max=20, message="phone长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=30, message="email长度必须介于 0 和 30 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getCash() {
		return cash;
	}

	public void setCash(Integer cash) {
		this.cash = cash;
	}
	
	public Integer getNeedIntegral() {
		return needIntegral;
	}

	public void setNeedIntegral(Integer needIntegral) {
		this.needIntegral = needIntegral;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Length(min=0, max=50, message="detail长度必须介于 0 和 50 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
		
	public Date getBeginUpdateTime() {
		return beginUpdateTime;
	}

	public void setBeginUpdateTime(Date beginUpdateTime) {
		this.beginUpdateTime = beginUpdateTime;
	}
	
	public Date getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}
		
}