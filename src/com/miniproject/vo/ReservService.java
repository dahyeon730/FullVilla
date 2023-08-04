package com.miniproject.vo;

import java.time.LocalDateTime;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class ReservService {
	private int reservServiceId;
	private int reservId;
	private int serviceId;
	private int quantity;
	
	public ReservService() {}

	public ReservService(int reservServiceId, int reservId, int serviceId, int quantity) {
		super();
		this.reservServiceId = reservServiceId;
		this.reservId = reservId;
		this.serviceId = serviceId;
		this.quantity = quantity;
	}
	
	public ReservService(int reservServiceId, int serviceId, int quantity) {
		super();
		this.reservServiceId = reservServiceId;
		this.reservId = reservId;
		this.serviceId = serviceId;
		this.quantity = quantity;
	}

	public int getReservServiceId() {
		return reservServiceId;
	}

	public void setReservServiceId(int reservServiceId) {
		this.reservServiceId = reservServiceId;
	}

	public int getReservId() {
		return reservId;
	}

	public void setReservId(int reservId) {
		this.reservId = reservId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ReservService [reservServiceId=" + reservServiceId + ", reservId=" + reservId + ", serviceId="
				+ serviceId + ", quantity=" + quantity + "]";
	}

	
}
