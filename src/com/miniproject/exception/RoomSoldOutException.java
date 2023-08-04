package com.miniproject.exception;

public class RoomSoldOutException extends Exception{
	public RoomSoldOutException(){
		this("이미 예약된 방입니다");
	}
	public RoomSoldOutException(String message){
		super(message);
	}
}