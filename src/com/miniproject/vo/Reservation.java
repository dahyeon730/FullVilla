package com.miniproject.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Reservation {
	private int reservID;
	private String phone;
	private int roomNum;
	private int totalPrice;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private LocalDateTime reservTime;
	private int headCnt;
	
	private ArrayList<ReservService> serviceList;

	public Reservation(int reservID, String phone, int roomNum, int totalPrice, LocalDateTime checkIn,
			LocalDateTime checkOut, LocalDateTime reservTime, int headCnt) {
		super();
		this.reservID = reservID;
		this.phone = phone;
		this.roomNum = roomNum;
		this.totalPrice = totalPrice;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.reservTime = reservTime;
		this.headCnt = headCnt;
	}
	
	public int getReservID() {
		return reservID;
	}

	public void setReservID(int reservID) {
		this.reservID = reservID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
	}

	public LocalDateTime getReservTime() {
		return reservTime;
	}

	public void setReservTime(LocalDateTime reservTime) {
		this.reservTime = reservTime;
	}

	public int getHeadCnt() {
		return headCnt;
	}

	public void setHeadCnt(int headCnt) {
		this.headCnt = headCnt;
	}

	public ArrayList<ReservService> getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList<ReservService> serviceList) {
		this.serviceList = serviceList;
	}

	
	
	
}
