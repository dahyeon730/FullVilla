package com.miniproject.vo;

import java.time.LocalDateTime;

/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Review {
	private int reviewId;
	private int roomId; // room PK
	private String phoneNum; // customer PK
	private int themeRating; // 별
	private String contents; // 텍스트 리

	public Review() {

	}

	public Review(int reviewId, int roomId, String phoneNum, int themeRating, String contents) {
		super();
		this.reviewId = reviewId;
		this.roomId = roomId;
		this.phoneNum = phoneNum;
		this.themeRating = themeRating;
		this.contents = contents;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
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

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

}
