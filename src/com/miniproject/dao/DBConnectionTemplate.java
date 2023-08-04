package com.miniproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.ServerInfo;

public interface DBConnectionTemplate {
	public Connection getConnect() throws SQLException ;
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException ;
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException ;
}
