package com.miniproject.vo;

import java.time.LocalDateTime;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Review {
	private int RoomId; // room PK
	private String phoneNum; // customer PK
	private int themeRating; // 별
	private String contents; // 텍스트 리

	public Review() {

	}

	public Review(int roomId, String phoneNum, int themeRating, String contents) {
		super();
		RoomId = roomId;
		this.phoneNum = phoneNum;
		this.themeRating = themeRating;
		this.contents = contents;
	}

	public int getRoomId() {
		return RoomId;
	}

	public void setRoomId(int roomId) {
		RoomId = roomId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getThemeRating() {
		return themeRating;
	}

	public void setThemeRating(int themeRating) {
		this.themeRating = themeRating;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
