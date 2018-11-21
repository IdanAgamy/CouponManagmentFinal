package com.idan.coupons.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.idan.coupons.dao.CompanyDao;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.JdbcUtils;

public class ControllerTest {

	public static void main(String[] args) throws SQLException, InterruptedException, ApplicationException {
		Connection conection = JdbcUtils.getConnection();
		//Thread.sleep(3000);
		

		couponControllerTest();
//		companyControllerTest();
//		customernControllerTest();
		
		resetAllAI();
		conection.close();
		System.out.println("**********End of test************");

	}

	private static void couponControllerTest() {
		CouponController cc = new CouponController();
		System.out.println("**********Testing Company Controller************");
		

//		createCouponTest(cc);
		getCouponByCouponIdTest();
		getAllCouponsTest();
		removeCouponByIDTest();
		updateCouponTest();
		getCouponByTypeTest();
		getCouponInOrderByPriceTest();
		getCouponsUpToPriceTest();
		getCouponsUpToEndDateTest();
		getCouponsByCompanyIDTest();
		getCouponsByCustomerIDTest();
		buyCouponTest();
		getCouponLeftInStockByCouponIDTest();
		deleteExpiredCouponTest(cc);
		
	}

	private static void createCouponTest(CompanyController cc) {
		
	}

	private static void getCouponByCouponIdTest() {
		
	}

	private static void getAllCouponsTest() {
		
	}

	private static void removeCouponByIDTest() {
		
	}

	private static void updateCouponTest() {
		
	}

	private static void getCouponByTypeTest() {
		
	}

	private static void getCouponInOrderByPriceTest() {
		
	}

	private static void getCouponsUpToPriceTest() {
		
	}

	private static void getCouponsUpToEndDateTest() {
		
	}

	private static void getCouponsByCompanyIDTest() {
		
	}

	private static void getCouponsByCustomerIDTest() {
		
	}

	private static void buyCouponTest() {
		
	}

	private static void getCouponLeftInStockByCouponIDTest() {
		
	}

	private static void deleteExpiredCouponTest(CouponController cc) {
		System.out.println("**********Testing delete Expired Coupon************");
		try {
			cc.deleteExpiredCoupon();
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}

	private static void companyControllerTest() {
		// TODO Auto-generated method stub
		
	}

	private static void customernControllerTest() {
		// TODO Auto-generated method stub
		
	}
	
private static void resetCouponAI() throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "ALTER TABLE coupon AUTO_INCREMENT = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, 1);
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	private static void resetCompanyAI() throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "ALTER TABLE company AUTO_INCREMENT = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, 1);
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	private static void resetCustomerAI() throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "ALTER TABLE customer AUTO_INCREMENT = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, 1);
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	private static void resetCustomerCouponAI() throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "ALTER TABLE customer_coupon AUTO_INCREMENT = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, 1);
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	private static void resetAllAI() throws ApplicationException {
		resetCompanyAI();
		resetCouponAI();
		resetCustomerAI();
		resetCustomerCouponAI();
		System.out.println("reseting all AI");
	}
	private static <T> void printList(List<T> list) {
		for(T c: list) {
			System.out.println(c);
		}
	}
	
	private static void verify(String message) throws IOException {
		System.out.println(message);
		System.in.read();
	}

}
