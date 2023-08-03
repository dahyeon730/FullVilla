package com.miniproject.exception;

public class ExistReviewException extends Exception{
	public ExistReviewException(){
		this("이미 리뷰를 작성했습니다.");
	}
	public ExistReviewException(String message){
		super(message);
	}
}
