package com.miniproject.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

	private boolean isCustomerExists(String phone, Connection conn) throws SQLException {
		String query = "SELECT phone FROM customer WHERE phone = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, phone);
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
	public ArrayList<Reservation> getReservationList(LocalDate date) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		try {
			conn = getConnect();
			String query = "SELECT * FROM Reservation WHERE reserv_time like ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, date.toString());

			rs = ps.executeQuery();
			if (rs.next()) {
				reservList.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						LocalDate.parse(rs.getString(5)), LocalDate.parse(rs.getString(6)),
						LocalDate.parse(rs.getString(7)), rs.getInt(8)));
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		if (reservList.size() == 0)
			throw new RecordNotFoundException("해당 시간의 예약 정보는 비었습니다.");

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

	private boolean roomIDExists(Connection conn, int roomId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT id FROM room WHERE id = ?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, roomId);

		rs = ps.executeQuery();
		return rs.next();
	}

	@Override
	public void addRoom(Room room) throws SQLException, DuplicateIDException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnect();
			// TODO : room 체크 메소드 생성
			if (!roomIDExists(conn, room.getRoomId())) {

				String query = "INSERT INTO room (room_id, room_genre, s_cap, m_cap, s_price) VALUES(?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, room.getRoomId());
				ps.setString(2, room.getRoomTheme());
				ps.setInt(3, room.getsCap());
				ps.setInt(4, room.getmCap());
				ps.setInt(5, room.getsPrice());

				int row = ps.executeUpdate();
				if (row == 1)
					// 정상 입력
					System.out.println("새로운 객실 번호: " + room.getRoomId() + "(" + room.getRoomTheme() + ")가 등록되었습니다."); // https://mozi.tistory.com/26
			} else {
				throw new DuplicateIDException("중복되는 객실 번호가 존재합니다.");
			}
		} finally {
			closeAll(ps, conn);
		}

	}


	@Override
	public void updateRoom(Room room) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if(roomIDExists(conn, room.getRoomId())) {
				String query = "UPDATE room SET room_genre=?, s_cap=?, m_cap=?, s_price=? WHERE room_id=?";
				
				ps = conn.prepareStatement(query);
				ps.setString(1, room.getRoomTheme());
				ps.setInt(2, room.getsCap());
				ps.setInt(3, room.getmCap());
				ps.setInt(4, room.getsPrice());
				ps.setInt(5, room.getRoomId());
				
				int row = ps.executeUpdate();
				if (row == 1)
					//정상 업데이트
					System.out.println(ps.executeUpdate()+"개의 객실 정보를 업데이트 하였습니다.");
			}  else throw new RecordNotFoundException("해당 객실이 존재하지 않습니다.");
		}finally {
			closeAll(ps,conn);
			}
	}
	@Override
	public void deleteRoom(int roomId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnect();

			if (roomIDExists(conn, roomId)) {
				String query = "DELETE room WHERE room_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, roomId);
				
				int row = ps.executeUpdate();
				if(row == 1)
					System.out.println("객실 번호: " + roomId + "가 삭제되었습니다.");
				// System.out.println(ps.executeUpdate()+ " 개의 객실을 등록하였습니다.");
			} else {
				throw new RecordNotFoundException("해당 객실 번호가 존재하지 않습니다.");
			}
		} finally {
			closeAll(ps,conn);
		}
	}

	private boolean serviceIdExists(Connection conn, int serviceId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT id FROM member WHERE id = ?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, serviceId);

		rs = ps.executeQuery();
		return rs.next();
	}
	
	@Override
	public void addService(Service service) throws SQLException, DuplicateIDException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnect();
			if (!serviceIdExists(conn, service.getServiceId())) {

				String query = "INSERT INTO service (service_id, name, price) VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, service.getServiceId());
				ps.setString(2, service.getServiceName());
				ps.setInt(3, service.getServicePrice());
				
				int row = ps.executeUpdate();
				if (row == 1)
					// 정상 입력
					System.out.println("새로운 서비스 번호: " + service.getServiceId() + "(" + service.getServiceName() + ")가 등록되었습니다."); // https://mozi.tistory.com/26
			} else {
				throw new DuplicateIDException("중복되는 서비스 번호가 존재합니다.");
			}
		} finally {
			closeAll(ps,conn);;
		}

	}



	public void deleteService(int serviceId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnect();

			if (serviceIdExists(conn, serviceId)) {
				String query = "DELETE room WHERE service_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, serviceId);

				int row = ps.executeUpdate();
				if(row == 1)
					System.out.println("서비스 번호: " + serviceId + "가 삭제되었습니다.");
				
			} else {
				throw new RecordNotFoundException("해당 서비스 번호가 존재하지 않습니다.");
			}
		} finally {
			closeAll(ps,conn);
		}
	}

	@Override
	public void updateService(Service service) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if(serviceIdExists(conn, service.getServiceId())) {
				String query = "UPDATE service SET name=?, price=? WHERE service_id=?";
				
				ps = conn.prepareStatement(query);
				ps.setString(1, service.getServiceName());
				ps.setInt(2, service.getServicePrice());
				ps.setInt(3, service.getServiceId());
		
				System.out.println(ps.executeUpdate()+"개의 서비스 정보를 업데이트 하였습니다.");
			}  else throw new RecordNotFoundException("해당 서비스가 존재하지 않습니다.");
		}finally {
			closeAll(ps,conn);
		}
	}

	@Override
	public ArrayList<Service> getServiceList() {
		// TODO Auto-generated method stub
		return null;
	}

}
