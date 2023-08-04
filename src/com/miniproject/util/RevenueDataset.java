package com.miniproject.util;

//자료구조 Pair 회계기능
//날짜, 금액
public class RevenueDataset {
	private String date;
	private int revenue;
	public RevenueDataset(String date, int revenue) {
		super();
		this.date = date;
		this.revenue = revenue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
}
