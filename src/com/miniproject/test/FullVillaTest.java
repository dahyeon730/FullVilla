package com.miniproject.test;

import java.sql.SQLException;

import com.miniproject.dao.impl.FullVillaDAOImpl;
import com.miniproject.dao.impl.StatisticsDAOImpl;
import com.miniproject.exception.DuplicateIDException;
import com.miniproject.vo.Admin;
import com.miniproject.vo.User;

public class FullVillaTest {
	public static void main(String[] args) throws Exception {
		// 객체 생성
		StatisticsDAOImpl statisticsDAO = new StatisticsDAOImpl();
		FullVillaDAOImpl fullVillaDAO = new FullVillaDAOImpl();		
		//관리자 
		// 1. 관리자 생성, 사용
		fullVillaDAO.addUser(new Admin("010-2298-4538", "김한주", "kb1234"));
		
		// 2. 숙소 추가, 생성, 변
		
		// 3. 서비스 추가, 생성, 삭제, 전체 조회
		
		//사용자
		
		// 1. 사용자 정보 입
		
		// 2. 예약, 예약취소, 예약 조회, 내 예약 조회 
		
		// 3. 리뷰 추가, 삭제, 변경, 객실별 리뷰 조회 
			
		//통계
		
		// 1. 월별 매출 / 일별 매출 출력
		// 2. 월별 테마별 랭킹 출력
		// 3. 
		
	}
}
