package com.miniproject.dao;

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
	
	ArrayList<Reservation> getMonthlyReservationList(String month);
	ArrayList<Reservation> getDailyReservationList(String day);
	
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
	void printRatingByMonthAndTheme() throws SQLException;
	
	// Room
	void addRoom(Room room) throws SQLException, DuplicateIDException;
	void updateRoom(Room room) throws SQLException, RecordNotFoundException;
	void deleteRoom(int roomId) throws SQLException, RecordNotFoundException;
	
	//Service
	void addService(Service service) throws SQLException, DuplicateIDException;
	void deleteService(int serviceId) throws SQLException, RecordNotFoundException;
	void updateService(Service service) throws SQLException, RecordNotFoundException;
	
	ArrayList<Service> getServiceList();	
}
