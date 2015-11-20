package com.fembase.common.config;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fembase.common.db.ResultObjectBuilder;


public class IdMapping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7348344536607966897L;
	private int gameuid;
	private int jodouid;
	private String puid;
	private String userType;
	private String insertTime;
	private String lastLoginTime;
	
	public IdMapping(){}

	public IdMapping(int gameuid, int jodouid, String puid,String userType) {
		super();
		this.gameuid = gameuid;
		this.jodouid = jodouid;
		this.puid = puid;
		this.userType = userType;
	}
	
	public static final ResultObjectBuilder<IdMapping> builder = new ResultObjectBuilder<IdMapping>() {

		@Override
		public IdMapping build(ResultSet rs) throws SQLException {
			IdMapping idmapping = new IdMapping();
			//ujl.opdate = DateUtil.formatYMDHMS(rs.getDate("opdate"));
			//ujl.opdate = DateUtil.formatYMD(rs.getDate("opdate"));
			idmapping.gameuid = rs.getInt("gameuid");
			idmapping.jodouid = rs.getInt("jodouid");
			idmapping.puid = rs.getString("puid");
			idmapping.userType = rs.getString("user_type");
			idmapping.insertTime = rs.getString("insert_time");
			idmapping.lastLoginTime = rs.getString("last_login_time");
			return idmapping;
		}
		
	};

	public int getGameuid() {
		return gameuid;
	}
	public void setGameuid(int gameuid) {
		this.gameuid = gameuid;
	}
	public int getJodouid() {
		return jodouid;
	}
	public void setJodouid(int jodouid) {
		this.jodouid = jodouid;
	}
	public String getPuid() {
		return puid;
	}
	public void setPuid(String puid) {
		this.puid = puid;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Override
	public String toString() {
		return "IdMapping [gameuid=" + gameuid + ", jodouid=" + jodouid
				+ ", puid=" + puid + ", userType=" + userType + ", insertTime="
				+ insertTime + ", lastLoginTime=" + lastLoginTime + "]";
	}

	
}
