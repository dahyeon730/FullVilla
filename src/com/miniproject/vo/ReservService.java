package com.miniproject.vo;

import java.time.LocalDateTime;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class ReservService {
	private int reservOptionId;
	private int reservId;
	private int optionId;
	private int quantity;
	
	public ReservService() {}
	
	public ReservService(int reservOptionId, int reservId, int optionId, int quantity) {
		super();
		this.reservOptionId = reservOptionId;
		this.reservId = reservId;
		this.optionId = optionId;
		this.quantity = quantity;
	}

	public int getReservOptionId() {
		return reservOptionId;
	}

	public void setReservOptionId(int reservOptionId) {
		this.reservOptionId = reservOptionId;
	}

	public int getReservId() {
		return reservId;
	}

	public void setReservId(int reservId) {
		this.reservId = reservId;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ReservService [reservOptionId=" + reservOptionId + ", reservId=" + reservId + ", optionId=" + optionId
				+ ", quantity=" + quantity + "]";
	}
	
	
	
	
	
}
