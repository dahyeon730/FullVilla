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
	
}
