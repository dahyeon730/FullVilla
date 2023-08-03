package com.miniproject.exception;

//ID 중복시 체크
public class DuplicateIDException extends Exception{
	public DuplicateIDException(){
		this("이미 있는 식별번호 입니다. ");
	}
	public DuplicateIDException(String message){
		super(message);
	}
}
