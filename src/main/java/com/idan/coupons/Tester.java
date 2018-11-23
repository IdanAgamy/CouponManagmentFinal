package com.idan.coupons;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Coupon;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.controller.CouponController;
import com.idan.coupons.dao.CompanyDao;
import com.idan.coupons.dao.CouponDao;
import com.idan.coupons.dao.CustomerDao;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.OrderType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.JdbcUtils;
import com.idan.coupons.utils.ValidationUtils;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class Tester {
	
//	private static final Logger logger = LogManager.getLogger("HelloWorld");
	
	public static void main(String[] args) throws SQLException, InterruptedException, ApplicationException, IOException {
//		JdbcUtils.getConnection();
//		Thread.sleep(3000);
		
////		couponControllerCreatTest();
//		
////		removeExpiredCouponTest(new CouponDao());
//		
////		getCouponAmountByCouponIDTest();
////		getBoughtCouponCountByCouponIDTest();
//		
////		isCustomerExistByEmailTest();
//		isCustomerEmailUpdateAvailableTest();
//		
////		resetAllAI();
//		System.out.println("End of test");
		
	    
//	        logger.info("Hello, World!");
//	    }
	}
	
//	private static void isCustomerEmailUpdateAvailableTest() throws ApplicationException {
//		CustomerDao cd = new CustomerDao();
//		System.out.println("Testing is Customer Exist By Email ");
//		System.out.println(cd.isCustomerEmailExistForUpdate(2l, "Kirk@Enterprise"));
//		
//	}
//	
//	private static void isCustomerExistByEmailTest() throws ApplicationException {
//		CustomerDao cd = new CustomerDao();
//		System.out.println("Testing is Customer Exist By Email ");
//		System.out.println(cd.isCustomerExistByEmail("Kirk@Enterprise"));
//		
//	}
//
////	private static void getBoughtCouponCountByCouponIDTest() throws ApplicationException  {
////		CouponDao cd = new CouponDao();
////		System.out.println("Testing get Bought Coupon Count By CouponID ");
////		System.out.println(cd.getBoughtCouponCountByCouponID(3L));
////		
////	}
////
////	private static void getCouponAmountByCouponIDTest() throws ApplicationException {
////
////		CouponDao cd = new CouponDao();
////		
////		System.out.println("Testing get Coupon Amount By CouponID");
////		
////		System.out.println(cd.getCouponAmountByCouponID(1L));
////		
////	}
//
////	private static void couponControllerCreatTest() {
////
////		CouponController cc = new CouponController();
////		
////		List<Coupon> coupons = new ArrayList<>();
////		
////		String msg = "fhghgchgkhgfhgfkhtfkhgvjhvhglghlhjgjhvkjgfhfchgv,jgvhjhvjv,bv,jvhgchcmhgcmhvcjfcmhgcv,gjvkgvchgcmhgvcmhcgfxngx";
////		
////		coupons.add(new Coupon("h==j","kjh","sdf",-2,CouponType.Food,"s",-3.3,"ss",4));
////		coupons.add(new Coupon("hjh","2000-10-10","2000-10-11",2,CouponType.Food,msg,3.3,"ss",4));
////		coupons.add(new Coupon("james cook","2020-10-20","2020-11-20",5,CouponType.Food,msg,3.3,"ss",4));
////		coupons.add(new Coupon("james cook","2020-10-20","2020-11-20",5,CouponType.Food,msg,3.3,"ss",4));
////		coupons.add(new Coupon("james cook","2020-10-20","2020-09-20",5,CouponType.Food,msg,3.3,"ss",4));
////
////		
////		for (Coupon c:coupons) {
////			try {
////				cc.createCoupon(c);
////			} catch (ApplicationException e) {
////				System.out.println(e.getMessage() + e.getInputErrorCouse());
////			} 
////		}
////		
////	}
//
//	private static void removeExpiredCouponTest(CouponDao couponDao)  throws ApplicationException, IOException {
//		
//		couponDao.createCoupon(new Coupon("just meat","2000-10-10","2018-09-02",2,CouponType.Restaurants,"just eat meat",20.20,":D",2l));
//		couponDao.createCoupon(new Coupon("just meat","2000-10-10","2018-09-02",2,CouponType.Restaurants,"just eat meat",20.20,":D",2l));
//		couponDao.createCoupon(new Coupon("just meat","2000-10-10","2018-09-02",2,CouponType.Restaurants,"just eat meat",20.20,":D",2l));
//		couponDao.createCoupon(new Coupon("just meat","2000-10-10","2018-09-02",2,CouponType.Restaurants,"just eat meat",20.20,":D",2l));
//		couponDao.createCoupon(new Coupon("just meat","2000-10-10","2018-09-02",2,CouponType.Restaurants,"just eat meat",20.20,":D",2l));
//		couponDao.createCoupon(new Coupon("just meat","2000-10-10","2018-09-02",2,CouponType.Restaurants,"just eat meat",20.20,":D",2l));
//		
//		
//		verify("Verify the added coupons, then press enter");
//		
//		CouponController cc = new CouponController();
//		
//		cc.deleteExpiredCoupon();
//		
//	}
//
//	private static void verify(String message) throws IOException {
//		System.out.println(message);
//		System.in.read();
//	}
//	
//	private static List<Company> printAllCompanies(CompanyDao companyDao) throws ApplicationException {
//		
//		List<Company> companies = companyDao.getAllCompanies();
//		
//		for(Company c:companies) {
//			System.out.println(c);
//		}
//		
//		return companies;
//	}
//	
//	
//	private static void addCompany(CompanyDao companyDao) throws ApplicationException {
//		
//		ArrayList<Company> companies = new ArrayList<>();
//		companies.add( new Company("HP", "1234", "HP@gmail.com"));
//		companies.add( new Company("Coca Cola", "1234", "cocacola@gmail.com"));
//		companies.add( new Company("Pepsi", "1234", "Pepsi@gmail.com"));
//		companies.add( new Company("Dell", "1234", "Dell@gmail.com"));
//		companies.add( new Company("Samsung", "1234", "Samsung@gmail.com"));
//		companies.add( new Company("BBB", "1234", "BBB@gmail.com"));
//		companies.add( new Company("ANA", "1234", "ANA@gmail.com"));
//		companies.add( new Company("APA", "1234", "APA@gmail.com"));
//		
//		
//		for(Company c:companies) {
//			companyDao.createCompany(c);
//		}
//		
//	}
//	
//	private static void resetCouponAI() throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query.
//			String sql = "ALTER TABLE coupon AUTO_INCREMENT = ?;";
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, 1);
//			
//			preparedStatement.executeUpdate();
//			
//		}
//		
//		catch (SQLException e) {
//			e.printStackTrace();
////			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
//		}
//		
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement);
//		}
//		
//	}
//	
//	private static void resetCompanyAI() throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query.
//			String sql = "ALTER TABLE company AUTO_INCREMENT = ?;";
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, 1);
//		
//			preparedStatement.executeUpdate();
//			
//		}
//		
//		catch (SQLException e) {
//			e.printStackTrace();
////			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
//		}
//		
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement);
//		}
//		
//	}
//	
//	private static void resetCustomerAI() throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query.
//			String sql = "ALTER TABLE customer AUTO_INCREMENT = ?;";
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, 1);
//		
//			preparedStatement.executeUpdate();
//			
//		}
//		
//		catch (SQLException e) {
//			e.printStackTrace();
////		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
//		}
//		
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement);
//		}
//		
//	}
//	
//	private static void resetCustomerCouponAI() throws ApplicationException {
//		
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		
//		try {
//			// Getting a connection to the DB.
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query.
//			String sql = "ALTER TABLE customer_coupon AUTO_INCREMENT = ?;";
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, 1);
//			preparedStatement.executeUpdate();
//			
//		}
//		
//		catch (SQLException e) {
//			e.printStackTrace();
////		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
//			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CouponDao, removeBoughtCouponByID(); FAILED");
//		}
//		
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement);
//		}
//		
//	}
//	
//	private static void resetAllAI() throws ApplicationException {
//		resetCompanyAI();
//		resetCouponAI();
//		resetCustomerAI();
//		resetCustomerCouponAI();
//	}
	
}
