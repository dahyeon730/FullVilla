package com.miniproject.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
	private LocalDate checkIn;
	private LocalDate checkOut;
	private LocalDate reservTime;
	private int headCnt;
	
	private ArrayList<ReservService> serviceList;
	
	
	public Reservation() {}



	public Reservation(int reservID, String phone, int roomNum, int totalPrice, LocalDate checkIn, LocalDate checkOut,
			LocalDate reservTime, int headCnt, ArrayList<ReservService> serviceList) {
		super();
		this.reservID = reservID;
		this.phone = phone;
		this.roomNum = roomNum;
		this.totalPrice = totalPrice;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.reservTime = reservTime;
		this.headCnt = headCnt;
		this.serviceList = serviceList;
	}





	public Reservation(int reservID, String phone, int roomNum, int totalPrice, LocalDate checkIn,
			LocalDate checkOut, LocalDate reservTime, int headCnt) {
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

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public LocalDate getReservTime() {
		return reservTime;
	}

	public void setReservTime(LocalDate reservTime) {
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



	@Override
	public String toString() {
		return "Reservation [reservID=" + reservID + ", phone=" + phone + ", roomNum=" + roomNum + ", totalPrice="
				+ totalPrice + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", reservTime=" + reservTime
				+ ", headCnt=" + headCnt + ", serviceList=" + serviceList + "]";
	}
	
	

	
	
	
}
