package com.miniproject.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.miniproject.dao.StatisticsDAO;
import com.miniproject.exception.RecordNotFoundException;
import com.miniproject.vo.Customer;
import com.miniproject.vo.Pair;
import com.miniproject.vo.Review;

import config.ServerInfo;

public class StatisticsDAOImpl implements StatisticsDAO{

	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("DB Connect....");
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(ps, conn);

	}

	
	@Override
	public void printMonthlyRevenue() throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pair> pairs = new ArrayList<Pair>();

		try {
			conn = getConnect();
			String query = "SELECT to_char(reserv_time, 'MM'), sum(total_price) FROM Reservation GROUP BY to_char(reserv_time, 'MM')";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();
			if (rs.next()) {
				pairs.add(new Pair(rs.getString(1), rs.getInt(2)));
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		if(pairs.size()==0) {
			throw new RecordNotFoundException("기록이 없습니다.");
		}
		for(int i = 0; i<pairs.size(); i++) {
			System.out.println(pairs.get(i).getDate()+"월은 총 합 "+pairs.get(i).getRevenue()+"원입니다.");
		}
		
	}

	@Override
	public void printDailyRevenue() throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pair> pairs = new ArrayList<Pair>();

		try {
			conn = getConnect();
			String query = "SELECT to_char(reserv_time, 'DD'), sum(total_price) FROM Reservation GROUP BY to_char(reserv_time, 'DD')";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();
			if (rs.next()) {
				pairs.add(new Pair(rs.getString(1), rs.getInt(2)));
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		if(pairs.size()==0) {
			throw new RecordNotFoundException("기록이 없습니다.");
		}
		for(int i = 0; i<pairs.size(); i++) {
			System.out.println(pairs.get(i).getDate()+"일은 총 합 "+pairs.get(i).getRevenue()+"원입니다.");
		}
		
	}
	
	@Override
	public void printRatingByMonthAndTheme() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Review> list = new ArrayList<>();
		conn = getConnect();
		try {
			String query = 
					"SELECT to_char(v.chkin,'YY-MM') 연월, m.room_genre 테마 ,avg(r.genre_rating) 평균별점, RANK() over(ORDERY BY avg(r.genre_rating) RANK "
					+ "FROM review r, room m, reservation v "
					+ "WHERE r.room_id = m.room_id AND m.room_genre = v.room_genre"
					+ "GROUP BY to_char(v.chkin, 'YY-MM'), m.room_genre";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1) + "에" + rs.getString(2) + "테마의 평균 별점은" + rs.getInt(3) + "이며, " + rs.getInt(4) + "등 입니다.");
			}
			
		} finally {
			closeAll(ps, conn);
		}
	}
}
