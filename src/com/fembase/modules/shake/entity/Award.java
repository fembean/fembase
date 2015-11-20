package com.fembase.modules.shake.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fembase.common.persistence.DataEntity;

/**
 * 奖品Entity
 * @author dong
 * @version 2015-09-19
 */
public class Award extends DataEntity<Award> {
	
	private static final long serialVersionUID = 1L;
	private double mul;		// mul
	private String detail;		// detail
	private int rate;		// rate
	private String state;		// 奖品状态（1为正常，0为下架）
	private Date createTime;		// create_time
	private Date updateTime;		// update_time
	private Date beginCreateTime;		// 开始 create_time
	private Date endCreateTime;		// 结束 create_time
	private Date beginUpdateTime;		// 开始 update_time
	private Date endUpdateTime;		// 结束 update_time
	
	public Award() {
		super();
	}

	public Award(String id){
		super(id);
	}

	public double getMul() {
		return mul;
	}

	public void setMul(double mul) {
		this.mul = mul;
	}
	
	@Length(min=1, max=255, message="detail长度必须介于 1 和 255 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	@Length(min=1, max=4, message="奖品状态（1为正常，0为下架）长度必须介于 1 和 4 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="create_time不能为空")
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

	@Override
	public String toString() {
		return "Award [mul=" + mul + ", detail=" + detail + ", rate=" + rate
				+ ", state=" + state + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", beginCreateTime="
				+ beginCreateTime + ", endCreateTime=" + endCreateTime
				+ ", beginUpdateTime=" + beginUpdateTime + ", endUpdateTime="
				+ endUpdateTime + "]";
	}
	
}