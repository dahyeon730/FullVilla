package com.miniproject.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
	
	private boolean isRoomSoldOut(int roomNum, LocalDate checkIn, LocalDate checkOut, Connection conn) throws SQLException{
	    String query ="SELECT room_id FROM reservation WHERE chkin Between ? AND ? OR chkout Between ? AND ? B";
	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, roomNum);
	    ps.setDate(2, Date.valueOf(checkIn));
	    ps.setDate(3, Date.valueOf(checkOut));
	    ps.setDate(4, Date.valueOf(checkIn));
	    ps.setDate(5, Date.valueOf(checkOut));
	    ResultSet rs = ps.executeQuery();
	    return rs.next();
	}
	
	private boolean isReservationExists(int reservId, String phone, Connection conn) throws SQLException{
		String query = "SELECT reserv_id FROM reservation WHERE reserv_id=? AND phone=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, reservId);
		ps.setString(2, phone);
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
	public void addReservation(Reservation reserv) throws SQLException, RoomSoldOutException {
		Connection conn = null;
	    PreparedStatement ps = null;

		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		 try {
			 conn = getConnect();
			 if(!isRoomSoldOut(reserv.getRoomNum(), reserv.getCheckIn(), reserv.getCheckOut(), conn)) {
			    	String query = "INSERT INTO reservation (reserv_id, phone, room_id, total_price, chkin, chkout, reserv_time, head_cnt) VALUES (seq_id.nextVal ,?, ?, ?, ?, ?, ?, ?)";
			    	ps = conn.prepareStatement(query);
			    	ps.setString(1, reserv.getPhone());
			    	ps.setInt(2, reserv.getRoomNum());
			    	ps.setInt(3, reserv.getTotalPrice());
			    	ps.setDate(4, Date.valueOf(reserv.getCheckIn()));
			    	ps.setDate(5, Date.valueOf(reserv.getCheckOut()));
			    	ps.setDate(6, Date.valueOf(LocalDate.now()));
			    	ps.setInt(7, reserv.getHeadCnt());
				 
			 } else throw new RoomSoldOutException("이미 예약 완료된 방입니다.");
		    }finally {
		    	closeAll(ps, conn);
		    }

	}

	@Override
	public void updateReservation(Reservation reserv) throws SQLException, RoomSoldOutException, DuplicateIDException {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = getConnect();
	        if(isReservationExists(reserv.getReservID(), reserv.getPhone(), conn)) {
	        if(!isRoomSoldOut(reserv.getRoomNum(), reserv.getCheckIn(), reserv.getCheckOut(), conn)) {
	        String query = "UPDATE reservation SET room_id=?, total_price=?, chkin=?, chkout=?, reserv_time=?, head_cnt=? WHERE reserv_id=? AND phone=?";
	        ps = conn.prepareStatement(query);
	       
	        ps.setInt(1, reserv.getRoomNum());
	        ps.setInt(2, reserv.getTotalPrice());
	        ps.setDate(3, Date.valueOf(reserv.getCheckIn()));
	        ps.setDate(4, Date.valueOf(reserv.getCheckOut()));
	        ps.setDate(5, Date.valueOf(LocalDate.now()));
	        ps.setInt(6, reserv.getHeadCnt());
	        ps.setInt(7, reserv.getReservID());
	        ps.setString(8, reserv.getPhone());

	        ps.executeUpdate();
	        } else throw new RoomSoldOutException("이미 예약 완료된 방입니다.");
	    } else throw new DuplicateIDException("예약 내역이 없습니다.");
	    } finally {
	        closeAll(ps, conn);
	    }

	}

	@Override
	public void deleteReservation(int reservId, String phone) throws SQLException, DuplicateIDException {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	    	if(isReservationExists(reservId, phone, conn)) {
	        conn = getConnect();
	        String query = "DELETE FROM reservation WHERE reserv_id=? AND phone=?";
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, reservId);
	        ps.setString(2,  phone);

	        ps.executeUpdate();
	    	} else throw new DuplicateIDException("예약 내역이 없습니다.");
	    } finally {
	        closeAll(ps, conn);
	    }

	}

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
	public Reservation getAReservation(int reservId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Reservation reservation = null;

	    try {
	        conn = getConnect();
	        String query = "SELECT * FROM reservation WHERE reserv_id=?";
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, reservId);

	        rs = ps.executeQuery();
	        if(rs.next()) {
	        	reservation = new Reservation(rs.getInt("reserv_id"), rs.getString("phone"), rs.getInt("room_id"), rs.getInt("total_price"), rs.getDate("chkin").toLocalDate(), rs.getDate("chkout").toLocalDate(), rs.getDate("reserv_time").toLocalDate(), rs.getInt("head_cnt"));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return reservation;
	}

	@Override
	public ArrayList<Reservation> getAReservation(String phone) throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    ArrayList<Reservation> reservList = new ArrayList<>();

	    try {
	        conn = getConnect();
	        String query = "SELECT * FROM reservation WHERE phone=?";
	        ps = conn.prepareStatement(query);
	        ps.setString(1, phone);

	        rs = ps.executeQuery();
	        while(rs.next()) {
	            reservList.add(new Reservation(rs.getInt("reserv_id"), rs.getString("phone"), rs.getInt("room_id"), rs.getInt("total_price"), rs.getDate("chkin").toLocalDate(), rs.getDate("chkout").toLocalDate(), rs.getDate("reserv_time").toLocalDate(), rs.getInt("head_cnt")));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return reservList;
	}

	@Override
	public ArrayList<ReservService> getServiceListByReservId(int reservId) throws SQLException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    ArrayList<ReservService> reservServiceList = new ArrayList<>();

	    try {
	        conn = getConnect();
	        String query = "SELECT * FROM reservService WHERE reservId=?";
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, reservId);

	        rs = ps.executeQuery();
	        while(rs.next()) {
	        	reservServiceList.add(new ReservService(rs.getInt("ro_id"), reservId, rs.getInt("service_id"), rs.getInt("quantity")));
	        }
	    } finally {
	        closeAll(rs, ps, conn);
	    }
	    return reservServiceList;
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
	public void addReview(int themeRating, int room_id, String phone) throws SQLException, ExistReviewException {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = getConnect();
		if (isExistReview(new Review(-1, room_id, phone, -1, ""), conn))
			throw new ExistReviewException("이미 리뷰를 작성하였습니다.");
		try {
			String query = "INSERT INTO review (review_id, genre_rating, contents, room_id, phone) "
					+ "VALUES(seq_review.NEXTVAL,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, themeRating);
			ps.setString(2, "");
			ps.setInt(3, room_id);
			ps.setString(4, phone);
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

	@Override
	public ArrayList<Reservation> getMonthlyReservationList(String month) throws SQLException, RecordNotFoundException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		 try {
		    	conn = getConnect();
		    	String query = "SELECT * "
		    			+ "FROM Reservation "
		    			+ "WHERE to_char(reserv_time, 'MM') = ?";
		    	ps = conn.prepareStatement(query);
		    	ps.setString(1, month);
		    	
		    	rs = ps.executeQuery();
		    	if(rs.next()) {
		    		reservList.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 
		    				LocalDate.parse(rs.getString(5)),
		    				LocalDate.parse(rs.getString(6)), 
		    				LocalDate.parse(rs.getString(7)),
		    				rs.getInt(8))
		    				);
		    	}
		    }finally {
		    	closeAll(rs, ps, conn);
		    }
		 if(reservList.size()==0)
			 throw new RecordNotFoundException("해당 시각의 예약 정보는 비었습니다.");
		 
		return reservList;
	}

	@Override
	public ArrayList<Reservation> getDailyReservationList(String day) throws SQLException, RecordNotFoundException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		 try {
		    	conn = getConnect();
		    	String query = "SELECT * "
		    			+ "FROM Reservation "
		    			+ "WHERE to_char(reserv_time, 'DD') = ?";
		    	ps = conn.prepareStatement(query);
		    	ps.setString(1, day);
		    	
		    	rs = ps.executeQuery();
		    	if(rs.next()) {
		    		reservList.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 
		    				LocalDate.parse(rs.getString(5)),
		    				LocalDate.parse(rs.getString(6)), 
		    				LocalDate.parse(rs.getString(7)),
		    				rs.getInt(8))
		    				);
		    	}
		    }finally {
		    	closeAll(rs, ps, conn);
		    }
		 if(reservList.size()==0)
			 throw new RecordNotFoundException("해당 시각의 예약 정보는 비었습니다.");
		 
		return reservList;
	}

	@Override
	public void makeGroupReservation(int[][] groupInfo) throws NumberFormatException, IOException {
		//int[N][3]; N은 9개보다 많음.
		//int[][0] = chkin
		//int[][1] = chkout
		//int[][2] = 1,2,3,4로 구성, 각 숫자는 각기 다른 워크샵 코드를 의미함
		// 1은 임원, 2는 해외영업, 3은 국내영업, 4는 개발, 5는 생산, 6은 총무부, 7은 경리부
		// 8은 인사부, 9는 보안
		
		
		// 끝나는 시간을 기준으로 정렬하기 위해 compare 재정의 
		Arrays.sort(groupInfo, (x,y) -> x[1] == y[1]? x[0]-y[0] :x[1]-y[1]);

		//워크샵 종류 저장할 리스트
		ArrayList<Integer> workshops = new ArrayList<Integer>();
		
		int count = 0;
		int lastEndTime = 0;
		
		for(int i = 0; i < groupInfo.length; i++) {
			
			// 직전 종료시간이 다음 회의 시작 시간보다 작거나 같다면 갱신 
			if(lastEndTime <= groupInfo[i][0]) {
				lastEndTime = groupInfo[i][1];
				//여기다가 현재 end_time으로 식별할수 있는 워크샵 종류를 2차원 배열 time에서 
				//읽어오고 마지막에 예약정보 테이블 컬럼에 맞춰서
				//삽입한 뒤 
				//콘솔로는 count와 워크샵 종류를 팝업으로 출력하고
				//골라진 워크샵중 가장 빠른 시작 시간과 가장 늦은 끝나는 시간을 받아와서
				//그 기간동안의 select * from Reservation 으로 예약 정보 확인
				count++;
			}
		}
		
		//여기에 삽입 쿼리 날리기
		
		//출력파트
		System.out.println(count);
		
	}



}
