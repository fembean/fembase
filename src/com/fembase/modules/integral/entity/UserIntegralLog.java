package com.fembase.modules.integral.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fembase.common.persistence.DataEntity;

/**
 * 用户积分记录Entity
 * @author dong
 * @version 2015-09-19
 */
public class UserIntegralLog extends DataEntity<UserIntegralLog> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// uid
	private Integer changeNum;		// change_num
	private String reason;		// reason
	private String detail;		// detail
	private Date beginCreateDate;		// 开始 create_date
	private Date endCreateDate;		// 结束 create_date
	
	public UserIntegralLog() {
		super();
	}

	public UserIntegralLog(String id){
		super(id);
	}

	@Length(min=1, max=11, message="uid长度必须介于 1 和 11 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@NotNull(message="change_num不能为空")
	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}
	
	@Length(min=1, max=11, message="reason长度必须介于 1 和 11 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=50, message="detail长度必须介于 0 和 50 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}