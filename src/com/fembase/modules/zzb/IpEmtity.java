package com.fembase.modules.zzb;

public class IpEmtity {

	private int id;
	private String ip;
	private String port;
	private String area;
	private String type;
	private String requestType;
	private String requestTime;
	public IpEmtity(String ip, String port, String area, String type,
			String requestType, String requestTime) {
		super();
		this.ip = ip;
		this.port = port;
		this.area = area;
		this.type = type;
		this.requestType = requestType;
		this.requestTime = requestTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "IpEmtity [ip=" + ip + ", port=" + port
				+ ", area=" + area + ", type=" + type + ", requestType="
				+ requestType + ", requestTime=" + requestTime + "]";
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
}
