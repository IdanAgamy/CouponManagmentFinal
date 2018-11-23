package com.idan.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;

import com.idan.coupons.beans.Coupon;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.OrderType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.JdbcUtils;

public class CouponDao{

	// TODO delete printStackTrace in phase 2
	
	
	/**
	 * Sending a query to the DB to add a new coupon to the coupon table.
	 * @param coupon - the coupon as a Coupon object to add to the DB.
	 * @return Long of the ID of the created coupon.
	 * @throws ApplicationException
	 */
	public Long createCoupon(Coupon coupon) throws ApplicationException {

		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();			
			
			// Creating a string which will contain the query.
			String sql = "insert into coupon_system.coupon (CouponTitle, CouponStartDate, CouponEndDate, CouponAmount, CouponType, CouponMessage, CouponPrice, CouponImage, companyID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement= connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			preparedStatement.setString	(1, coupon.getCouponTitle()			);
			preparedStatement.setString	(2, coupon.getCouponStartDate()		);
			preparedStatement.setString	(3, coupon.getCouponEndDate()		);
			preparedStatement.setLong	(4, coupon.getCouponAmount()		);
			preparedStatement.setString	(5, coupon.getCouponType().name()	);
			preparedStatement.setString	(6, coupon.getCouponMessage()		);
			preparedStatement.setDouble	(7, coupon.getCouponPrice()			);
			preparedStatement.setString	(8, coupon.getCouponImage()			);
			preparedStatement.setLong	(9, coupon.getCompanyID()			);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		} 

		catch (SQLException e) {
			
			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, createCoupon(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}


	/**
	 * Sending a query to the DB to get information of a coupon.
	 * @param couponId - Long parameter represent the ID of the requested coupon.
	 * @return Coupon Object correspond to the provided ID.
	 * @throws ApplicationException.
	 */
	public Coupon getCouponByCouponId(Long couponId) throws ApplicationException{

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE CouponID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, couponId);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			resultSet = preparedStatement.executeQuery();

			// Checking if we got a reply with the requested data. If no data was received, returns null.
			if (!resultSet.next()) {
				return null;
			}
			coupon = extractCouponFromResultSet(resultSet);

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponByCouponId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupon;
	}
	
	

	/**
	 * Sending a query to the DB to get all the coupons in coupon table.
	 * @return List collection of all the coupons in the coupon table.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getAllCoupons() throws ApplicationException{
		
		List<Coupon> coupons = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon";
			preparedStatement = connection.prepareStatement(sql);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();
			
			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				coupons.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getAllCoupons(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return coupons;
		
	}

	/**
	 * Sending a query to the DB to remove coupon from the coupon table by a couponID.
	 * @param couponID - the couponID as a long to remove from the DB.
	 * @throws ApplicationException 
	 */
	public void removeCouponByID(Long couponID) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "DELETE FROM coupon WHERE CouponID = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponID);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			preparedStatement.executeUpdate();
			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, removeCouponByID(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	/**
	 * Sending a query to the DB to remove coupon from the customer_coupon table by a couponID.
	 * @param couponID - the couponID as a long to remove from the DB.
	 * @throws ApplicationException 
	 */
	public void removeBoughtCouponByCouponIDandCustomerID(Long couponID, Long customerID) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "DELETE FROM customer_coupon WHERE CouponID = ? and CustomerID =?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponID);
			preparedStatement.setLong(2, customerID);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, removeBoughtCouponByID(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
//	public void removeCustomerPurchasesByCustomerID(Long customerID) throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			// Getting a connection to the DB
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query
//			String sql = "DELETE FROM customer_coupon WHERE CustomerID = ?;";
//			preparedStatement = connection.prepareStatement(sql);
//			
//			preparedStatement.setLong(1, customerID);
//			
//			// TODO delete print
//			System.out.println(preparedStatement); // Checking the query sent to the server
//			
//			preparedStatement.executeUpdate();
//			
//			
//			
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
////				In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, removeCustomer(); FAILED");
//		}
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement);
//		}
//		
//	}
	
//	public void removeCouponByCompanyID(Long companyID) throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//
//			// Creating a string which will contain the query.
//			String sql = "DELETE FROM coupon WHERE CompanyID = ?; ";
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, companyID);
//			// TODO delete print
//			System.out.println(preparedStatement); // Checking the query sent to the server
//			preparedStatement.executeUpdate();
//			
//		}
//
//		catch (SQLException e) {
//			e.printStackTrace();
////			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, removeCouponByID(); FAILED");
//		}
//
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement);
//		}
//		
//	}

	/**
	 * Sending a query to the DB to add a update a coupon in the coupon table. All the fields will be updated according to the ID of the coupon object.
	 * @param coupon - the coupon as a Coupon object to be updated in the DB.
	 * @throws ApplicationException 
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "UPDATE coupon_system.coupon SET CouponTitle = ?, CouponStartDate = ?, CouponEndDate = ?, CouponAmount = ?, CouponType = ?, CouponMessage = ?, CouponPrice = ?, CouponImage = ?, companyID = ? WHERE CouponID = ?";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, coupon.getCouponTitle());
			preparedStatement.setString(2, coupon.getCouponStartDate());
			preparedStatement.setString(3, coupon.getCouponEndDate());
			preparedStatement.setInt(4, coupon.getCouponAmount());
			preparedStatement.setString(5, coupon.getCouponType().name());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setDouble(7, coupon.getCouponPrice());
			preparedStatement.setString(8, coupon.getCouponImage());
			preparedStatement.setLong(9, coupon.getCompanyID());
			preparedStatement.setLong(10, coupon.getCouponId());
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			preparedStatement.executeUpdate();
			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, updateCoupon(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}

	/**
	 * Sending a query to the DB to get all the coupons in coupon table of a specific type.
	 * @param type - type of coupon.
	 * @return List collection of all the coupons in the coupon table of the requested type.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getCouponByType(CouponType type) throws ApplicationException{
		
		List<Coupon> couponsByType = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE CouponType=?";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, type.name());
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				couponsByType.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponByType(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}		
		
		return couponsByType;
	}

	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table by order of the price.
	 * @param orderType - Descending or ascending order. 
	 * @return List collection of all the coupons in the coupon table in descending or ascending order by price.
	 * @throws ApplicationException.
	 */
	public List<Coupon> getCouponInOrderByPrice(OrderType orderType) throws ApplicationException{
		
		List<Coupon> couponsInOrderByPrice = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = null;
			
			// Sending a query to the DB 
			switch (orderType) {
			case ASCENDING:sql = "SELECT * FROM coupon order by CouponPrice ASC";
				break;
			case DESCENDING: sql = "SELECT * FROM coupon order by CouponPrice DESC";
				break;
			}			
			preparedStatement = connection.prepareStatement(sql);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				couponsInOrderByPrice.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponInOrderByPrice(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return couponsInOrderByPrice;
	}

	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table up to requested price.
	 * @param price - Double parameter of the maximum wanted price for a coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested price.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getCouponsUpToPrice(double price) throws ApplicationException{
		
		List<Coupon> couponsUpToPrice = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE CouponPrice <= ?;";	
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setDouble(1, price);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				couponsUpToPrice.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsUpToPrice(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		
		return couponsUpToPrice;
		
	}
	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table up to requested expiration date.
	 * @param endDate - String parameter of the latest end date of the coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested date.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getCouponsUpToEndDate(String endDate) throws ApplicationException{
		
		List<Coupon> couponsUpToPrice = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE CouponEndDate <= ?;";	
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, endDate);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				couponsUpToPrice.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsUpToEndDate(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return couponsUpToPrice;
		
	}
	// CouponID, CouponTitle, CouponStartDate, CouponEndDate, CouponAmount, CouponType, CouponMessage, CouponPrice, CouponImage, companyID
	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table issued by the requested company.
	 * @param companyID - Long parameter of the ID of the requested company.
	 * @return List collection of all the coupons in the coupon table issued by the requested company.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getCouponsByCompanyID(Long companyID) throws ApplicationException{
		
		List<Coupon> couponsByCompany = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE companyID = ?;";	
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, companyID);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				couponsByCompany.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsByCompanyID(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return couponsByCompany;
	}
	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table bought by the requested customer.
	 * @param customerID - Long parameter of the ID of the requested customer.
	 * @return List collection of all the coupons in the coupon table bought by the requested customer.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getCouponsByCustomerID(Long customerID) throws ApplicationException{
		
		List<Coupon> couponsByCustomer = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon c INNER JOIN customer_coupon cc ON c.CouponID = cc.CouponID WHERE cc.CustomerID = ?;";	
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, customerID);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Coupon objects.
			while (resultSet.next()) {
				couponsByCustomer.add(extractCouponFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsByCustomerID(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return couponsByCustomer;
	}
	
	/**
	 * Sending a query to the DB to add coupon to a customer in customer_coupon table after the customer has bought a coupon.
	 * @param customerID - Long parameter of the customer ID.
	 * @param couponID - Long parameter of the coupon ID.
	 * @throws ApplicationException 
	 */
	public void buyCoupon(Long customerID, Long couponID) throws ApplicationException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "INSERT INTO customer_coupon (CustomerID, CouponID) VALUES (?, ?);";

			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setLong(1, customerID);
			preparedStatement.setLong(2, couponID);

			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			preparedStatement.executeUpdate();
		} 

		catch (SQLException e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, buyCoupon(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
//	public Integer getCouponAmountByCouponID(Long couponID) throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		int couponCount = 0;
//
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//
//			// Creating a string which will contain the query.
//			String sql = "SELECT CouponAmount FROM coupon where CouponID=?; ";
//			preparedStatement = connection.prepareStatement(sql);
//			
//			preparedStatement.setLong(1, couponID);
//			// TODO delete print
//			System.out.println(preparedStatement); // Checking the query sent to the server
//			resultSet = preparedStatement.executeQuery();
//
//			// Checking if we got a reply with the requested data. If no data was received, returns null.
//			if (!resultSet.next()) {
//				return null;
//			}
//			couponCount = resultSet.getInt("CouponAmount");
//
//			
//		}
//
//		catch (SQLException e) {
//			e.printStackTrace();
////			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponAmountByCouponID(); FAILED");
//		}
//
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
//		}
//		return couponCount;
//		
//	}
//	
//	/**
//	 * Sending a query to the DB to get count of how many of a certain coupon where bought.
//	 * @param couponID - Long parameter of the coupon ID.
//	 * @throws ApplicationException 
//	 */
//	public Integer getBoughtCouponCountByCouponID(Long couponID) throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		
//		int couponCount = 0;
//
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//
//			// Creating a string which will contain the query.
//			String sql = "SELECT COUNT(*) as Counting FROM customer_coupon where CouponID=?; ";
//			preparedStatement = connection.prepareStatement(sql);
//			
//			preparedStatement.setLong(1, couponID);
//			// TODO delete print
//			System.out.println(preparedStatement); // Checking the query sent to the server
//			resultSet = preparedStatement.executeQuery();
//
//			// Checking if we got a reply with the requested data. If no data was received, returns null.
//			if (!resultSet.next()) {
//				return null;
//			}
//			couponCount = resultSet.getInt("Counting");
//
//			
//		}
//
//		catch (SQLException e) {
//			e.printStackTrace();
////			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getBoughtCouponCountByCouponID(); FAILED");
//		}
//
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
//		}
//		return couponCount;
//		
//	}
	
	public void removeCouponByEndDate(String endDate) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "DELETE FROM coupon WHERE CouponEndDate < ?; ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, endDate);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			preparedStatement.executeUpdate();
			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, removeCouponByID(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
		
	}

	/**
	 * Sending a query to the DB to get if there is a coupon using that title for creation.
	 * @param couponTitle - String of that coupon tile.
	 * @return true - Title in use by other coupon.
	 * 		   false - Title not in use by other coupon.
	 * @throws ApplicationException
	 */
	public boolean isCouponExistByTitle(String couponTitle) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE CouponTitle = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, couponTitle);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			resultSet = preparedStatement.executeQuery();
			
			// Checking if we got a reply with the requested data. If no data was received, returns true.
			if (!resultSet.next()) {
				return false;
			}
			
			return true;
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponExistByTitle(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * Sending a query to the DB to get if there is a coupon using that title for update.
	 * @param couponID - a long parameter represent the ID of the requested company.
	 * @param couponTitle - String of that coupon tile.
	 * @return true - Title in use by other coupon.
	 * 		   false - Title not in use by other coupon.
	 * @throws ApplicationException
	 */
	public boolean isCouponTitleExistForUpdate(Long couponID, String couponTitle) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM coupon WHERE CouponTitle = ? AND NOT CouponID = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, couponTitle);
			preparedStatement.setLong(2, couponID);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			resultSet = preparedStatement.executeQuery();
			
			// Checking if we got a reply with the requested data. If no data was received, returns true.
			if (!resultSet.next()) {
				return false;
			}
			
			return true;
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponTitleUpdateAvailable(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * Sending a query to the DB to get if the customer already purchased the coupon.
	 * @param couponID - a long parameter represent the ID of the requested coupon.
	 * @param customerID - a long parameter represent the ID of the requested customer.
	 * @return true - customer already purchased the coupon.
	 * 		   false - customer didn't purchased the coupon..
	 * @throws ApplicationException
	 */
	public boolean isCouponAlreadyPurchasedByCustomerID(Long couponID, Long customerID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM customer_coupon WHERE customerID = ? AND CouponID = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, customerID);
			preparedStatement.setLong(2, couponID);
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			resultSet = preparedStatement.executeQuery();
			
			// Checking if we got a reply with the requested data. If no data was received, returns true.
			if (!resultSet.next()) {
				return false;
			}
			
			return true;
		}
		
		catch (SQLException e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponTitleUpdateAvailable(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}


	/**
	 * Extract coupon's data by parameters from the database.
	 * @param resultSet -  Data received from DB.
	 * @return Coupon object made from the resultSet.
	 * @throws SQLException.
	 */
	private Coupon extractCouponFromResultSet(ResultSet resultSet) throws SQLException {
		
		Coupon coupon = new Coupon();
		coupon.setCouponId(resultSet.getLong("CouponID"));
		coupon.setCouponTitle(resultSet.getString("CouponTitle"));
		coupon.setCouponStartDate(resultSet.getString("CouponStartDate"));
		coupon.setCouponEndDate(resultSet.getString("CouponEndDate"));
		coupon.setCouponAmount(resultSet.getInt("CouponAmount"));
		coupon.setCouponType(CouponType.valueOf(resultSet.getString("CouponType")));
		coupon.setCouponMessage(resultSet.getString("CouponMessage"));
		coupon.setCouponPrice(resultSet.getDouble("CouponPrice"));
		coupon.setCouponImage(resultSet.getString("CouponImage"));
		coupon.setCompanyID(resultSet.getLong("companyID"));
		
		return coupon;
	}


	
}