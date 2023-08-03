package com.miniproject.vo;
/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Customer {
	private String phone;
	private String name;
	private String password;
	
	public Customer() {}
	//일반 고객용
	public Customer(String phone, String name) {
		super();
		this.phone = phone;
		this.name = name;
		this.password = "";
	}
	//관리자 생성용
	public Customer(String phone, String name, String password) {
		super();
		this.phone = phone;
		this.name = name;
		this.password = password;
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
	public String getPassword() {
		return password;
	}
	//password 주입은 set으로. 
	//고객은 password 설정할 필요 없으므로
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Custom [phone=" + phone + ", name=" + name + "]";
	}
	
	
	
	
	
}
