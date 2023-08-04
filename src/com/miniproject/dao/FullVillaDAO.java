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
import com.miniproject.vo.User;



public interface FullVillaDAO extends DBConnectionTemplate{

    // Customer
    // TODO : Customer >> User
    void addUser(User customer) throws SQLException, DuplicateIDException;
    Customer getACustomer(String phone) throws SQLException;

    // Reservation
    // TODO : 총금액 -> DECODE
    void  addReservation(Reservation reserv, ArrayList<ReservService> reservService) throws SQLException, RoomSoldOutException;
    void updateReservation(Reservation reserv) throws SQLException, RoomSoldOutException, DuplicateIDException;
    public void deleteReservation(int reservId, String phone) throws SQLException, DuplicateIDException;

    // TODO : 월별 매출/일별 매출 -> 그룹함수
    ArrayList<Reservation> getMonthlyReservationList(String month) throws SQLException, RecordNotFoundException;
    ArrayList<Reservation> getDailyReservationList(String day) throws SQLException, RecordNotFoundException;

    Reservation getAReservation(int reservId) throws SQLException;
    ArrayList<Reservation> getAReservation(String phone) throws SQLException;

    // ReserveService
	public ArrayList<ReservService> getServiceListByReservId(int reservId) throws SQLException ;


    // Review
    void addReview(Review review) throws SQLException, ExistReviewException;
    public void addReview(int themeRating, int room_id, String phone) throws SQLException, ExistReviewException ;
    void updateReview(Review review) throws SQLException, RecordNotFoundException;
    void deleteReview(int reservId) throws SQLException;

    ArrayList<Review> getReviewListByRoomId(int roomId) throws SQLException, RecordNotFoundException;
    // TODO : 분석함수

    // Room
    void addRoom(Room room) throws SQLException, DuplicateIDException;
    void updateRoom(Room room) throws SQLException, RecordNotFoundException;
    void deleteRoom(int roomId) throws SQLException, RecordNotFoundException;

    //Service
    void addService(Service service) throws SQLException, DuplicateIDException;
    void deleteService(int serviceId) throws SQLException, RecordNotFoundException;
    void updateService(Service service) throws SQLException, RecordNotFoundException;

    ArrayList<Service> getServiceList() throws SQLException, RecordNotFoundException;


	//고급기능
	//int[N][3]
	//0 : 시작시간 1: 끝나는시간 2: 워크샵코드
	// 1은 임원, 2는 해외영업, 3은 국내영업, 4는 개발, 5는 생산, 6은 총무부, 7은 경리부
	// 8은 인사부, 9는 보안
	void makeGroupReservation(int[][] groupInfo) throws NumberFormatException, IOException;
}
