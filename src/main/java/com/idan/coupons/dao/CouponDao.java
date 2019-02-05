package com.idan.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idan.coupons.beans.Coupon;
import com.idan.coupons.beans.CouponEntity;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.OrderType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.JdbcUtils;
//TODO implement Transactional

@Repository
public class CouponDao{

	@PersistenceContext(unitName="couponSystem")
	private EntityManager entityManager;
	
	/**
	 * Sending a query to the DB to add a new coupon to the coupon table.
	 * @param coupon - the coupon as a Coupon object to add to the DB.
	 * @return Long of the ID of the created coupon.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCoupon(CouponEntity coupon) throws ApplicationException {

		try {
			entityManager.persist(coupon);
		}	catch (Exception e) {
			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, createCoupon(); FAILED");
		} 

	}


	/**
	 * Sending a query to the DB to get information of a coupon.
	 * @param couponId - Long parameter represent the ID of the requested coupon.
	 * @return Coupon Object correspond to the provided ID.
	 * @throws ApplicationException.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CouponEntity getCouponByCouponId(Long couponId) throws ApplicationException{

		try {
			return entityManager.find(CouponEntity.class, couponId);
			
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon with ID: " + couponId + ".");

		} catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponByCouponId(); FAILED");
		}
	}
	
	

	/**
	 * Sending a query to the DB to get all the coupons in coupon table.
	 * @return List collection of all the coupons in the coupon table.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getAllCoupons() throws ApplicationException{
		
		try {
			List<CouponEntity> coupons;
			Query getQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon");
			coupons = getQuery.getResultList();
			return coupons;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon in data base.");

		} catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getAllCoupons(); FAILED");
		}
		
	}

	/**
	 * Sending a query to the DB to remove coupon from the coupon table by a couponID.
	 * @param couponID - the couponID as a long to remove from the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCouponByCouponID(Long couponID) throws ApplicationException {
		
		CouponEntity coupon = getCouponByCouponId(couponID);
		try {
			entityManager.remove(coupon);
		} catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, removeCouponByID(); FAILED");
		}
		
	}
	
	/**
	 * Sending a query to the DB to remove coupon from the customer_coupon table by a couponID.
	 * @param couponID - the couponID as a long to remove from the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
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

	/**
	 * Sending a query to the DB to add a update a coupon in the coupon table. All the fields will be updated according to the ID of the coupon object.
	 * @param coupon - the coupon as a Coupon object to be updated in the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCoupon(CouponEntity coupon) throws ApplicationException {
		
		try {
			entityManager.merge(coupon);
		}

		catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, updateCoupon(); FAILED");
		}
		
	}

	/**
	 * Sending a query to the DB to get all the coupons in coupon table of a specific type.
	 * @param type - type of coupon.
	 * @return List collection of all the coupons in the coupon table of the requested type.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponByType(CouponType couponType) throws ApplicationException{
		
		try {
			List<CouponEntity> coupons;
			Query getQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon WHERE couponType =:customerTypeObj");
			getQuery.setParameter("customerTypeObj", couponType);
			coupons = getQuery.getResultList();
			return coupons;
			
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon with type: " + couponType + ".");

		} catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponByType(); FAILED");
		}
	}


	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table up to requested price.
	 * @param price - Double parameter of the maximum wanted price for a coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested price.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsUpToPrice(double price) throws ApplicationException{

		try {
			List<CouponEntity> coupons;
			Query getQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon WHERE couponPrice <=:couponPriceObj");
			getQuery.setParameter("couponPriceObj", price);
			coupons = getQuery.getResultList();
			return coupons;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon below price: " + price + ".");

		} catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsUpToPrice(); FAILED");
		}
	}
	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table up to requested expiration date.
	 * @param endDate - String parameter of the latest end date of the coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested date.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsUpToEndDate(String endDate) throws ApplicationException{

		try {
			List<CouponEntity> coupons;
			Query getQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon WHERE couponEndDate <=:couponEndDateObj");
			getQuery.setParameter("couponEndDateObj", endDate);
			coupons = getQuery.getResultList();
			return coupons;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon expired before: " + endDate + ".");

		} catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsUpToEndDate(); FAILED");
		}
		
	}
	// CouponID, CouponTitle, CouponStartDate, CouponEndDate, CouponAmount, CouponType, CouponMessage, CouponPrice, CouponImage, companyID
	
	@SuppressWarnings("unchecked")
	/**
	 * Sending a query to the DB to get all the coupons in coupon table issued by the requested company.
	 * @param companyID - Long parameter of the ID of the requested company.
	 * @return List collection of all the coupons in the coupon table issued by the requested company.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByCompanyID(Long companyID) throws ApplicationException{

		try {
			List<CouponEntity> coupons;
			Query getQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon WHERE companyID =:companyIDObj");
			getQuery.setParameter("companyIDObj", companyID);
			coupons = getQuery.getResultList();
			return coupons;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon were made by company with ID: " + companyID + ".");

		} catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsByCompanyID(); FAILED");
		}
	}
	
	/**
	 * Sending a query to the DB to get all the coupons in coupon table bought by the requested customer.
	 * @param customerID - Long parameter of the ID of the requested customer.
	 * @return List collection of all the coupons in the coupon table bought by the requested customer.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Coupon> getCouponsByCustomerID(Long customerID) throws ApplicationException{
		
		List<Coupon> couponsByCustomer = new ArrayList<Coupon>();
		
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
	

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)	
	public List<CouponEntity> getNewestCoupon() throws ApplicationException{

		try {
			List<CouponEntity> coupons;
			Query getQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon  ORDER BY couponID DESC");
			getQuery.setMaxResults(5);
			coupons = getQuery.getResultList();
			return coupons;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon in data base.");

		} catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getCouponsByCustomerID(); FAILED");
		}
	}
	
	/**
	 * Sending a query to the DB to add coupon to a customer in customer_coupon table after the customer has bought a coupon.
	 * @param customerID - Long parameter of the customer ID.
	 * @param couponID - Long parameter of the coupon ID.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
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

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCouponByEndDate(String endDate) throws ApplicationException {
		
		try {
			Query deleteQuery = entityManager.createQuery("DELETE FROM CouponEntity As coupon WHERE couponEndDate <:couponEndDateObj");
			deleteQuery.setParameter("couponEndDateObj", endDate);
			deleteQuery.executeUpdate();
			
		} catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, removeCouponByID(); FAILED");
		}
		
	}

	/**
	 * Sending a query to the DB to get if there is a coupon using that title for creation.
	 * @param couponTitle - String of that coupon tile.
	 * @return true - Title in use by other coupon.
	 * 		   false - Title not in use by other coupon.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCouponExistByTitle(String couponTitle) throws ApplicationException {

		try {
			Query validationQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon WHERE couponTitle =:couponTitleObj");
			validationQuery.setParameter("couponTitleObj", couponTitle);
			validationQuery.getFirstResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponExistByTitle(); FAILED");
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
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCouponTitleExistForUpdate(Long couponID, String couponTitle) throws ApplicationException {
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
		
		try {
			Query validationQuery = entityManager.createQuery("SELECT coupon FROM CouponEntity As coupon WHERE couponTitle =:couponTitleObj AND NOT couponId = :couponIdObj");
			validationQuery.setParameter("couponTitleObj", couponTitle);
			validationQuery.setParameter("couponIdObj", couponID);
			validationQuery.getFirstResult();
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query.
//			String sql = "SELECT * FROM coupon WHERE CouponTitle = ? AND NOT CouponID = ?; ";
//			preparedStatement = connection.prepareStatement(sql);
//			
//			preparedStatement.setString(1, couponTitle);
//			preparedStatement.setLong(2, couponID);
//			
//			resultSet = preparedStatement.executeQuery();
//			
//			// Checking if we got a reply with the requested data. If no data was received, returns true.
//			if (!resultSet.next()) {
//				return false;
//			}
			
			return true;
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponTitleUpdateAvailable(); FAILED");
		}
//		
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
//		}
	}
	
	/**
	 * Sending a query to the DB to get if the customer already purchased the coupon.
	 * @param couponID - a long parameter represent the ID of the requested coupon.
	 * @param customerID - a long parameter represent the ID of the requested customer.
	 * @return true - customer already purchased the coupon.
	 * 		   false - customer didn't purchased the coupon..
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
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
	@Transactional(propagation=Propagation.REQUIRED)
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