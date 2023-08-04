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

	private boolean isCustomerExists(String ssn, Connection conn) throws SQLException {
		String query = "SELECT phone FROM customer WHERE phone = ?";
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
			if (!isCustomerExists(customer.getPhone(), conn)) {// 추가하려는 고객이 없다면
				String query = "INSERT INTO customer(phone, name, password) VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, customer.getPhone());
				ps.setString(2, customer.getName());
				// 이미 Customer 테이블에서 생성시 기본값으로 ""을 주기 때문에 if절 필요없이 그냥 넣으면됨
				ps.setString(3, customer.getPassword());

			} else {
				throw new DuplicateIDException("해당하는 식별 번호로 이미 등록되어 있습니다");
			}
		} finally {
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
			if (rs.next()) {
				cust = new Customer(phone, rs.getString(1));
				// password에 뭔가 있으면 관리자니까 일단 공통된 생성자로 전번이랑 이름 넣어주고 set으로 값 지정해주기
				if (rs.getString(2) != "")
					cust.setPassword(rs.getString(2));
			}
		} finally {
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
			if (rs.next()) {
				reservList.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						LocalDateTime.parse(rs.getString(5)), LocalDateTime.parse(rs.getString(6)),
						LocalDateTime.parse(rs.getString(7)), rs.getInt(8)));
			}
		} finally {
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
	
	private boolean isExistReview(Review review, Connection conn) throws SQLException {
		String query = "SELECT * FROM review WHERE room_id = ? AND phone = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, review.getRoomId());
		ps.setString(2, review.getPhoneNum());

		ResultSet rs = ps.executeQuery();
		return rs.next();
	}

	// TODO: rlagkswn00 : review
	@Override
	public void addReview(Review review) throws SQLException, ExistReviewException {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConnect();
		if (isExistReview(review, conn))
			throw new ExistReviewException("이미 리뷰를 작성하였습니다.");

		try {
			String query = "INSERT INTO review (review_id, genre_rating, contents, room_id, phone) "
					+ "VALUES(seq_review.NEXTVAL,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, review.getThemeRating());
			ps.setString(2, review.getContents());
			ps.setInt(3, review.getRoomId());
			ps.setString(4, review.getPhoneNum());
			int row = ps.executeUpdate();
			if(row == 1)
				System.out.println("FullVillaDAOImpl.addReview() 정상 종료");
			else
				System.out.println("FullVillaDAOImpl.addReview() 비정상 종료");

		} finally {
			closeAll(ps, conn);
		}
	}

	

	@Override
	public void addReview(int themeRating) {
		//??? TODO ????
	}

	@Override
	public void updateReview(Review review) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConnect();
		if (!isExistReview(review, conn))
			throw new RecordNotFoundException("존재하지 않는 리뷰입니다.");

		try {
			String query = "UPDATE review SET review_id = ?, genre_rating = ?, contents = ?, rood_id = ?, phone = ? WHERE review_id = ?";
			ps = conn.prepareStatement(query);
			
			ps.setInt(1,review.getReviewId());
			ps.setInt(2, review.getThemeRating());
			ps.setString(3, review.getContents());
			ps.setInt(4, review.getRoomId());
			ps.setString(4, review.getPhoneNum());
			ps.setInt(6, review.getReviewId());
			
			int row = ps.executeUpdate();
			if(row == 1)
				System.out.println("FullVillaDAOImpl.updateReview() 정상 종료");
			else
				System.out.println("FullVillaDAOImpl.updateReview() 비정상 종");

		} finally {
			closeAll(ps, conn);
		}

	}

	@Override
	public void deleteReview(int reviewId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConnect();
		try {
			String query = "DELETE FROM review WHERE review_id = ?";
			ps = conn.prepareStatement(query);
			
			ps.setInt(1,reviewId);
			
			int row = ps.executeUpdate();
			if(row == 1)
				System.out.println("FullVillaDAOImpl.deleteReview() 정상 종");
			else
				System.out.println("FullVillaDAOImpl.deleteReview() 비정상 종");
			
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public ArrayList<Review> getReviewListByRoomId(int roomId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Review> list = new ArrayList<>();
		conn = getConnect();
		try {
			String query = "SELECT * FROM review WHERE rood_id = ?";
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, roomId);
			
			rs = ps.executeQuery();
			
			while(rs.next())
				list.add(new Review(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
			
			if(list.size() == 0)
				throw new RecordNotFoundException(roomId + "번호 방으로 등록된 리뷰가 없습니다.");
		} finally {
			closeAll(ps, conn);
		}
		return list;
	}

	// TODO: rlagkswn00 : review
	@Override
	public void printRatingByMonthAndTheme() {


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
