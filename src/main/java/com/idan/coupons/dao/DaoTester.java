
package com.idan.coupons.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Coupon;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.OrderType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.JdbcUtils;

public class DaoTester {
	
	public static void main(String[] args) throws SQLException, InterruptedException, ApplicationException, IOException {
		Connection conection = JdbcUtils.getConnection();
		Thread.sleep(3000);
		
		
		companyTest();
		couponTest();
		customerTest();
		
		
		resetAllAI();
		conection.close();
		System.out.println("**********End of test************");
	}
	
	private static void couponTest() throws IOException {
		CouponDao cd = new CouponDao();
		System.out.println("**********Testing couponDao************");
		
		createCouponTest(cd);
		updateCouponTest(cd);
		removeCouponByIDTest(cd);
		buyCouponTest(cd);
		getCouponByCouponIdTest(cd);
		getAllCouponsTest(cd);
		removeBoughtCouponByIDTest(cd);
		removeCustomerPurchasesByCustomerIDTest(cd);
//		removeCouponByCompanyIDTest(cd);
		getCouponByTypeTest(cd);
		getCouponInOrderByPriceTest(cd);
		getCouponsUpToPriceTest(cd);
		getCouponsUpToEndDateTest(cd);
		getCouponsByCompanyIDTest(cd);
		getCouponsByCustomerIDTest(cd);
//		getCouponAmountByCouponIDTest(cd);
//		getBoughtCouponCountByCouponIDTest(cd);
//		getCouponIDsByEndDateTest(cd);
		isCouponExistByTitleTest(cd);
		isCouponTitleExistForUpdateTest(cd);
		isCouponAlreadyPurchasedByCustomerIDTest(cd);
		
	}
	
	private static void companyTest() {
		CompanyDao cd = new CompanyDao();
		System.out.println("**********Testing CompanyDao************");
		
		createCompanyTest(cd);
		removeCompanyByCompanyIDTest(cd);
		updateCompanyTest(cd);
		getCompanyByComapnyIdTest(cd);
		getAllCompaniesTest(cd);
		loginTest(cd);
		isCompanyExistByNameTest(cd);
		isCompanyNameUpdateAvailableTest(cd);
		isCompanyExistByEmailTest(cd);
		isCompanyEmailUpdateAvailableTest(cd);
		
	}
	
	private static void customerTest() {
		CustomerDao cd = new CustomerDao();
		System.out.println("Testing CustomerDao");
		
		createCustomerTest(cd);
		removeCustomerByCustomerIDTest(cd);
		updateCustomerTest(cd);
		getCustomerByCustomerIdTest(cd);
		getAllCustomersTest(cd);
		loginTest(cd);
		isCustomerExistByEmailTest(cd);
		isCustomerEmailUpdateAvailableTest(cd);
		
	}
	
	private static void createCouponTest(CouponDao cd) throws IOException {
		
		System.out.println("**********Testing create Coupon************");
		try {
			cd.createCoupon(new Coupon("a title", "2012-09-05", "2017-09-05", 10, CouponType.Food, "bird is the word", 30.3, "dicpic", 2));
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		verify("press enter after checking mysql");
		
	}
	
	private static void getCouponByCouponIdTest(CouponDao cd) {
		
		System.out.println("**********Testing get Coupon By Coupon Id************");		
		try {
			Coupon c = cd.getCouponByCouponId(1L);
			System.out.println(c);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		try {
			verify("press enter after checking mysql");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
		
	}
	
	private static void getAllCouponsTest(CouponDao cd) {
		System.out.println("**********Testing get All Coupons ************");
		try {
			List<Coupon> coupons = cd.getAllCoupons();
			
			printList(coupons);
			
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
	}
	
	private static void removeCouponByIDTest(CouponDao cd) {
		System.out.println("**********Testing remove Coupon by ID************");		
		try {
			cd.removeCouponByID(9l);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		try {
			verify("press enter after checking mysql");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void removeBoughtCouponByIDTest(CouponDao cd) {
		System.out.println("**********Testing remove Bought Coupon By ID************");
		
		try {
			cd.removeBoughtCouponByID(3l);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		try {
			verify("press enter after checking mysql");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void removeCustomerPurchasesByCustomerIDTest(CouponDao cd) {
		System.out.println("**********Testing remove Customer Purchases By Customer ID************");
		buyCouponTest(cd);
		
		try {
			cd.removeCustomerPurchasesByCustomerID(3l);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		try {
			verify("press enter after checking mysql");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
//	private static void removeCouponByCompanyIDTest(CouponDao cd) {
//		System.out.println("**********Testing remove Coupon By Company ID************");
//		
//		try {
//			// 5 is a not used company ID in current DB
//			long companyID = 5;
//			
//			cd.createCoupon(new Coupon("another tite", "2018-09-05", "2018-09-06", 11, CouponType.Holiday, "bird is the word", 31.3, "dicpic", companyID));
//			verify("press enter after checking mysql");
//			cd.removeCouponByCompanyID(companyID);
//			
//			
//		} catch (ApplicationException e) {
//			// 
//			e.printStackTrace();
//		} catch (IOException e) {
//			// 
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
	
	private static void updateCouponTest(CouponDao cd) {
		System.out.println("**********Testing update Coupon************");
		try {
			cd.updateCoupon(new Coupon(9l, "another tite", "2018-09-05", "2018-09-06", 11, CouponType.Holiday, "bird is the word", 31.3, "dicpic", 1));
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		try {
			verify("press enter after checking mysql");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getCouponByTypeTest(CouponDao cd) {
		System.out.println("**********Testing get Coupon By Type************");
		
		try {
			List<Coupon> coupons = cd.getCouponByType(CouponType.Food);
			printList(coupons);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getCouponInOrderByPriceTest(CouponDao cd) {
		System.out.println("**********Testing get Coupon In Order By Price************");
		
		try {
			List<Coupon> coupons = cd.getCouponInOrderByPrice(OrderType.ASCENDING);
			printList(coupons);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getCouponsUpToPriceTest(CouponDao cd) {
		System.out.println("**********Testing get Coupons Up To Price************");
		
		try {
			List<Coupon> coupons = cd.getCouponsUpToPrice(90.0);
			printList(coupons);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getCouponsUpToEndDateTest(CouponDao cd) {
		System.out.println("**********Testing get Coupons Up To End Date************");
		
		try {
			List<Coupon> coupons = cd.getCouponsUpToEndDate("2001-10-10");
			printList(coupons);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getCouponsByCompanyIDTest(CouponDao cd) {
		System.out.println("**********Testing get Coupons By Company ID************");
		
		try {
			List<Coupon> coupons = cd.getCouponsByCompanyID(4l);
			printList(coupons);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getCouponsByCustomerIDTest(CouponDao cd) {
		System.out.println("**********Testing get Coupons By Customer ID************");
		
		try {
			List<Coupon> coupons = cd.getCouponsByCustomerID(2l);
			printList(coupons);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void buyCouponTest(CouponDao cd) {
		
		// not used couponID: 3; not used customerID: 3
		System.out.println("**********Testing buy Coupon Coupon************");
		
		try {
			cd.buyCoupon(3l,3l);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
		try {
			verify("press enter after checking mysql");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
		
	}
	
//	private static void getCouponAmountByCouponIDTest(CouponDao cd) {
//		System.out.println("**********Testing get Coupon Amount By Coupon ID************");
//		
//		try {
//			System.out.println(cd.getCouponAmountByCouponID(8l));
//		} catch (ApplicationException e) {
//			// 
//			e.printStackTrace();
//		} 
//		
//	}
//	
//	private static void getBoughtCouponCountByCouponIDTest(CouponDao cd) {
//		System.out.println("**********Testing create Coupon************");
//		
//		try {
//			System.out.println(cd.getBoughtCouponCountByCouponID(2l));
//		} catch (ApplicationException e) {
//			// 
//			e.printStackTrace();
//		}
//		
//	}
	
//	private static void getCouponIDsByEndDateTest(CouponDao cd) {
//		System.out.println("**********Testing get Coupon ID By End Date************");
//		
//		try {
//			List<Long> ids = cd.getCouponIDsByEndDate("2018-10-10");
//			
//			printList(ids);
//		} catch (ApplicationException e) {
//			// 
//			e.printStackTrace();
//		}
//		
//	}
	
	private static void isCouponExistByTitleTest(CouponDao cd) {
		System.out.println("**********Testing is Coupon Exist By Title************");
		
		try {
			System.out.println("should be true" + cd.isCouponExistByTitle("best song f all time"));
			System.out.println("should be false" + cd.isCouponExistByTitle("best song of all time"));
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void isCouponTitleExistForUpdateTest(CouponDao cd) {
		System.out.println("**********Testing is Coupon Title Exist For Update************");
		
		try {
			System.out.println("should be false " + cd.isCouponTitleExistForUpdate(8l, "james cook"));
			System.out.println("should be true " + cd.isCouponTitleExistForUpdate(7l, "best song f all time"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void isCouponAlreadyPurchasedByCustomerIDTest(CouponDao cd) {
		System.out.println("**********Testing is Coupon Already Purchased By Customer ID************");
		
		try {
			System.out.println("should be false " + cd.isCouponAlreadyPurchasedByCustomerID(3l, 1l));
			System.out.println("should be true " + cd.isCouponAlreadyPurchasedByCustomerID(5l, 1l));
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private static void createCompanyTest(CompanyDao cd) {
		System.out.println("**********Testing create Company************");
		
		try {
			cd.createCompany(new Company("Maister Inc.", "6Shipupi", "couponManagment@Maister.com"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void removeCompanyByCompanyIDTest(CompanyDao cd) {
		System.out.println("**********Testing remove Company By Company ID************");
		
		try {
			cd.removeCompanyByCompanyID(9L);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void updateCompanyTest(CompanyDao cd) {
		System.out.println("**********Testing update Company************");
		
//		createCompanyTest(cd);
		
		try {
//			verify("check for new coupon");
			cd.updateCompany(new Company(9, "Meister Inc", "654shlomp", "something@meister.com"));
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private static void getCompanyByComapnyIdTest(CompanyDao cd) {
		System.out.println("**********Testing get Company By Comapny Id************");
		
		try {
			System.out.println(cd.getCompanyByComapnyId(6l));
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void getAllCompaniesTest(CompanyDao cd) {
		System.out.println("**********Testing get All Companies************");
		
		try {
			List<Company> cs = cd.getAllCompanies();
			printList(cs);
		} catch (ApplicationException e) {
			// 
			e.printStackTrace();
		}
		
	}
	
	private static void loginTest(CompanyDao cd) {
		System.out.println("**********Testing login************");
		
		try {
			System.out.println("should be true " + cd.login("bbb", "1234"));
			System.out.println("should be false " + cd.login("bbb", "12c34"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void isCompanyExistByNameTest(CompanyDao cd) {
		System.out.println("**********Testing is Company Exist By Name************");
		
		try {
			System.out.println("should be true " + cd.isCompanyExistByName("bbb"));
			System.out.println("should be false " + cd.isCompanyExistByName("bbb12c34"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void isCompanyNameUpdateAvailableTest(CompanyDao cd) {
		System.out.println("**********Testing is Company Name Update Available************");
		
		try {
			System.out.println("should be false " + cd.isCompanyNameExistForUpdate(6, "bbb"));
			System.out.println("should be true " + cd.isCompanyNameExistForUpdate(5, "bbb"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	private static void isCompanyExistByEmailTest(CompanyDao cd) {
		System.out.println("**********Testing is Company Exist By Email************");
		
		try {
			System.out.println("should be true " + cd.isCompanyExistByEmail("BBB@gmail.com"));
			System.out.println("should be false " + cd.isCompanyExistByEmail("BB@gmail.com"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void isCompanyEmailUpdateAvailableTest(CompanyDao cd) {
		System.out.println("**********Testing is Company Name Update Available************");
		
		try {
			System.out.println("should be false " + cd.isCompanyEmailExistForUpdate(6l, "BBB@gmail.com"));
			System.out.println("should be true " + cd.isCompanyEmailExistForUpdate(5l, "BBB@gmail.com"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void createCustomerTest(CustomerDao cd) {
		System.out.println("**********Testing create Customer************");
		
		try {
			cd.createCustomer(new Customer("billy", "ght", "mm@mm"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void removeCustomerByCustomerIDTest(CustomerDao cd) {
		System.out.println("**********Testing remove Customer By Customer ID************");
		
		try {
			cd.removeCustomerByCustomerID(4l);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void updateCustomerTest(CustomerDao cd) {
		System.out.println("**********Testing update Customer************");
		
		createCustomerTest(cd);
		
		try {
//			verify("Check db");
			cd.updateCustomer(new Customer(4, "Karl", "sdsdsdsd", "asd@asd"));
		} catch ( ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void getCustomerByCustomerIdTest(CustomerDao cd) {
		System.out.println("**********Testing get Customer By CustomerId************");
		
		try {
			System.out.println(cd.getCustomerByCustomerId(2l));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void getAllCustomersTest(CustomerDao cd) {
		System.out.println("**********Testing get All Customers************");
		
		try {
			List<Customer> list = cd.getAllCustomers();
			printList(list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void loginTest(CustomerDao cd) {
		System.out.println("**********Testing login************");
		
		try {
			System.out.println("should be true " + cd.login("Picard@EnterpriseD", "1701D"));
			System.out.println("should be false " + cd.login("Picard@EnterpriseD", "1701d"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private static void isCustomerExistByEmailTest(CustomerDao cd) {
		System.out.println("**********Testing is Customer Exist By Email************");
		
		try {
			System.out.println("should be true " + cd.isCustomerExistByEmail("Picard@EnterpriseD"));
			System.out.println("should be false " + cd.isCustomerExistByEmail("Picard@Enterprise"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void isCustomerEmailUpdateAvailableTest(CustomerDao cd) {
		System.out.println("**********Testing is Customer Email Update Available************");
		
		try {
			System.out.println("should be false " + cd.isCustomerEmailExistForUpdate(2l,"Picard@EnterpriseD"));
			System.out.println("should be true " + cd.isCustomerEmailExistForUpdate(1l,"Picard@EnterpriseD"));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
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