package com.miniproject.dao;

import java.sql.SQLException;

public interface StatisticsDAO extends DBConnectionTemplate{
	// 월별 매출
	// 일별 매출 
	// 월별 테마별 별점 출력 - 분석함수
	
	void printMonthlyRevenue();
	void printDailyRevenue();
	void printRatingByMonthAndTheme() throws SQLException;
	
}
