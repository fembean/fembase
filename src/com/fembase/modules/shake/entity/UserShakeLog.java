package com.fembase.modules.shake.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fembase.common.persistence.DataEntity;

/**
 * 用户摇奖记录Entity
 * @author dong
 * @version 2015-09-19
 */
public class UserShakeLog extends DataEntity<UserShakeLog> {
	
	private static final long serialVersionUID = 1L;
	private String uid;
	private String awardId;		// award_id
	private Integer cost;		// cost
	private double result;		// result
	private Date createTime;		// create_time
	private Date beginCreateTime;		// 开始 create_time
	private Date endCreateTime;		// 结束 create_time
	
	public UserShakeLog() {
		super();
	}

	public UserShakeLog(String id){
		super(id);
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Length(min=1, max=11, message="award_id长度必须介于 1 和 11 之间")
	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	
	@NotNull(message="cost不能为空")
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	@NotNull(message="result不能为空")
	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="create_time不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		
}