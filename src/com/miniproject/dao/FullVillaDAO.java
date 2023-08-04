package com.miniproject.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


import com.miniproject.exception.*;
import com.miniproject.vo.Customer;
import com.miniproject.vo.ReservService;
import com.miniproject.vo.Reservation;
import com.miniproject.vo.Review;
import com.miniproject.vo.Room;
import com.miniproject.vo.Service;

public interface FullVillaDAO {
	
	Connection getConnect() throws SQLException;
	void closeAll(PreparedStatement ps, Connection conn)throws SQLException;
	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn)throws SQLException;
	
	
	// Customer
	void addCustomer(Customer customer) throws SQLException, DuplicateIDException;
	Customer getACustomer(String phone) throws SQLException;
	
	// Reservation
	void addReservation(Reservation reserv);
	void updateReservation(Reservation reserv);
	void deletReservation(int reservId);
	
	ArrayList<Reservation> getMonthlyReservationList(String month) throws SQLException, RecordNotFoundException;
	ArrayList<Reservation> getDailyReservationList(String day) throws SQLException, RecordNotFoundException;
	
	Reservation getAReservation(int reservId);
	ArrayList<Reservation> getAReservation(String phone);
	
	// ReserveService
	ArrayList<ReservService> getServiceList(int reservId);
	
	
	// Review
	void addReview(Review review) throws SQLException, ExistReviewException;
	void addReview(int themeRating);
	void updateReview(Review review) throws SQLException, RecordNotFoundException;
	void deleteReview(int reviewId) throws SQLException;
	
	ArrayList<Review> getReviewListByRoomId(int roomId) throws SQLException, RecordNotFoundException;
	void printRatingByMonthAndTheme();
	
	// Room
	void addRoom(Room room) throws SQLException, DuplicateIDException;
	void updateRoom(Room room) throws SQLException, RecordNotFoundException;
	void deleteRoom(int roomId) throws SQLException, RecordNotFoundException;
	
	//Service
	void addService(Service service) throws SQLException, DuplicateIDException;
	void deleteService(int serviceId) throws SQLException, RecordNotFoundException;
	void updateService(Service service) throws SQLException, RecordNotFoundException;
	
	ArrayList<Service> getServiceList();	
	
	//고급기능
	//int[N][3]
	//0 : 시작시간 1: 끝나는시간 2: 워크샵코드
	// 1은 임원, 2는 해외영업, 3은 국내영업, 4는 개발, 5는 생산, 6은 총무부, 7은 경리부
	// 8은 인사부, 9는 보안
	void makeGroupReservation(int[][] groupInfo) throws NumberFormatException, IOException;
}
