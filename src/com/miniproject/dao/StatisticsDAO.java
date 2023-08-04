package com.miniproject.dao;

import java.sql.SQLException;

import com.miniproject.exception.RecordNotFoundException;

public interface StatisticsDAO extends DBConnectionTemplate{
	// 월별 매출
	// 일별 매출 
	// 월별 테마별 별점 출력 - 분석함수
	
	void printMonthlyRevenue() throws SQLException, RecordNotFoundException;
	void printDailyRevenue() throws SQLException, RecordNotFoundException;
	void printRatingByMonthAndTheme() throws SQLException;
	
}
