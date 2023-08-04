package com.miniproject.vo;

import java.time.LocalDateTime;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Room {
	private int roomId;
	private String roomTheme;
	private int sCap;
	private int mCap;
	private int sPrice;

	public Room() {
	}

	public Room(int roomId, String roomTheme, int sCap, int mCap, int sPrice) {
		super();
		this.roomId = roomId;
		this.roomTheme = roomTheme;
		this.sCap = sCap;
		this.mCap = mCap;
		this.sPrice = sPrice;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomTheme() {
		return roomTheme;
	}

	public void setRoomTheme(String roomTheme) {
		this.roomTheme = roomTheme;
	}

	public int getsCap() {
		return sCap;
	}

	public void setsCap(int sCap) {
		this.sCap = sCap;
	}

	public int getmCap() {
		return mCap;
	}

	public void setmCap(int mCap) {
		this.mCap = mCap;
	}

	public int getsPrice() {
		return sPrice;
	}

	public void setsPrice(int sPrice) {
		this.sPrice = sPrice;
	}

}
