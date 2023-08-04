package com.miniproject.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.miniproject.dao.StatisticsDAO;
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
	public void printMonthlyRevenue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printDailyRevenue() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void printRatingByMonthAndTheme() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Review> list = new ArrayList<>();
		conn = getConnect();
		try {
			String query = "SELECT m.room_genre, "
					+ "FROM (SELECT  FROM review r, room m WHERE r.room_id = m.room_id)";
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			while(rs.next())
				list.add(new Review(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
			
		} finally {
			closeAll(ps, conn);
		}

	}



}
