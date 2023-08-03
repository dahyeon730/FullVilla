package com.miniproject.exception;

public class DuplicateIDException extends Exception{
	public DuplicateIDException(){
		this("이미 있는 회원이십니다. ");
	}
	public DuplicateIDException(String message){
		super(message);
	}
}
