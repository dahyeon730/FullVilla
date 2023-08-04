package com.miniproject.vo;
/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Customer extends User{
	private String phone;
	private String name;
	
	//관리자 생성용
	public Customer(String phone, String name) {
		super(phone, name);
	}
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Customer [phone=" + phone + ", name=" + name + "]";
	}
	
	
	
	
	
}
