package com.miniproject.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.miniproject.dao.FullVillaDAO;
import com.miniproject.exception.*;
import com.miniproject.vo.Customer;
import com.miniproject.vo.ReservService;
import com.miniproject.vo.Reservation;
import com.miniproject.vo.Review;
import com.miniproject.vo.Room;
import com.miniproject.vo.Service;

import config.ServerInfo;

public class FullVillaDAOImpl implements FullVillaDAO {

	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("DB Connect....");		
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if(rs!=null) rs.close();
		closeAll(ps,conn);	
		
	}

	private boolean isCustomerExists(String ssn, Connection conn) throws SQLException{
		String query ="SELECT phone FROM customer WHERE phone = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, ssn);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	@Override
	public void addCustomer(Customer customer) throws SQLException, DuplicateIDException {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnect();
            if(!isCustomerExists(customer.getPhone(), conn)) {//추가하려는  고객이 없다면
                String query = "INSERT INTO customer(phone, name, password) VALUES(?,?,?)";
                ps=  conn.prepareStatement(query);
                ps.setString(1, customer.getPhone());
                ps.setString(2, customer.getName());
                //고객은 비밀번호가 필요 없다. 
                //따라서 기본적으로 비밀번호는 공백으로 설정
                if(customer.getPassword() == "")
                	ps.setString(3, "");
                else
                	//관리자일때 패스워드가 무언가 있을 것이므로
                	ps.setString(3, customer.getPassword());
                //System.out.println(ps.executeUpdate()+" 명 INSERT 성공...addCustomer()..");
            }else {
                throw new DuplicateIDException("해당하는 식별 번호로 이미 등록되어 있습니다");
            }
        }finally {
            closeAll(ps, conn);
        }
		
	}
	
	@Override
	public Customer getACustomer(String phone) throws SQLException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Customer cust = null;
	    try {
	    	conn = getConnect();
	    	String query = "SELECT * FROM customer WHERE phone=?";
	    	ps = conn.prepareStatement(query);
	    	ps.setString(1, phone);
	    	
	    	rs = ps.executeQuery();
	    	if(rs.next()) {
	    		cust = new Customer(phone, rs.getString(1));
	    		//password에 뭔가 있으면 관리자니까 일단 공통된 생성자로 전번이랑 이름 넣어주고 set으로 값 지정해주기
	    		if(rs.getString(2)!="")
	    			cust.setPassword(rs.getString(2));
	    	}
	    }finally {
	    	closeAll(rs, ps, conn);
	    }
		return cust;
	}

	@Override
	public void addReservation(Reservation reserv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReservation(Reservation reserv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletReservation(int reservId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Reservation> getReservationList(LocalDateTime date) throws SQLException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		 try {
		    	conn = getConnect();
		    	String query = "SELECT * FROM Reservation WHERE reserv_time=?";
		    	ps = conn.prepareStatement(query);
		    	ps.setString(1, date.toString());
		    	
		    	rs = ps.executeQuery();
		    	if(rs.next()) {
		    		reservList.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 
		    				LocalDateTime.parse(rs.getString(5)),
		    				LocalDateTime.parse(rs.getString(6)), 
		    				LocalDateTime.parse(rs.getString(7)),
		    				rs.getInt(8))
		    				);
		    	}
		    }finally {
		    	closeAll(rs, ps, conn);
		    }
		 
		return reservList;
	}

	@Override
	public Reservation getAReservation(int reservId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Reservation> getAReservation(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReservService> getServiceList(int reservId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addReview(Review review) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReview(int themeRating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReview(Review review) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReview(int reservId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Review> getReviewListByRoomId(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printRatingByMonthAndTheme() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoom(Room room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoom(Room room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoom(int roomId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addService(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteService(int serviceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateService(Service service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Service> getServiceList() {
		// TODO Auto-generated method stub
		return null;
	}

}
