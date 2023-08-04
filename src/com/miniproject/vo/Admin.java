package com.miniproject.vo;

public class Admin extends User{
	private String password;

	public Admin(String phone, String name, String password) {
		super(phone, name);
		this.password = password;
	}

	
	
	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "Admin [ Phone=" + getPhone() + ", Name=" + getName()
				+ " password=" + password +"]";
	}
	
	
}
