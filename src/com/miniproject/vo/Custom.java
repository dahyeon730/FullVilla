package com.miniproject.vo;
/*
 Custom 테이블의 정보를 저장하는 클래스
 Value Object
 
 Custom 테이블의 컬럼이 클래스의 필드~!!
 */

public class Custom {
	private String phone;
	private String name;
	
	public Custom() {}
	public Custom(String phone, String name) {
		super();
		this.phone = phone;
		this.name = name;
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
		return "Custom [phone=" + phone + ", name=" + name + "]";
	}
	
	
	
	
	
}
