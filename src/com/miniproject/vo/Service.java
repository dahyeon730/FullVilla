package com.miniproject.vo;

import java.time.LocalDateTime;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Service {
	private int serviceId;
	private String serviceName;
	private int servicePrice;
	
	public Service() {}
	public Service(int serviceId, String serviceName, int servicePrice) {
		super();
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.servicePrice = servicePrice;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(int servicePrice) {
		this.servicePrice = servicePrice;
	}
	
}
