package com.miniproject.vo;

public class User {
	private String phone;
	private String name;
	public User(String phone, String name) {
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
	
	
}
