package com.idan.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.CompanyEntity;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.JdbcUtils;
//import com.mysql.cj.Query;

//TODO implement Transactional

@Repository
public class CompanyDao{

	@PersistenceContext(unitName="couponSystem")
	private EntityManager entityManager;
	
	
	/**
	 * Sending a query to the DB to add a new company to the company table.
	 * @param company - the company as a Company object to add to the DB.
	 * @return Long of the ID of the created company.
	 * @throws ApplicationException
	 */	
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCompany(CompanyEntity company) throws ApplicationException {
		entityManager.persist(company);
	}

	/**
	 * Sending a query to the DB to remove company from the company table by a Company object.
	 * @param company - the company as a Company object to remove from the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCompanyByCompanyID(Long companyID) throws ApplicationException {
		CompanyEntity company = getCompanyByComapnyId(companyID);
		entityManager.remove(company);
		
	}
			

	/**
	 * Sending a query to the DB to add a update a company in the company table. All the fields will be updated according to the ID of the Company object.
	 * @param company - the company as a Company object to be updated in the DB.
	 * @throws ApplicationException. 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) throws ApplicationException {
		entityManager.merge(company);
		
	}

	
	/**
	 * Sending a query to the DB to get information of a company.
	 * @param companyId - a long parameter represent the ID of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException. 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByComapnyId(Long companyId) throws ApplicationException {
		
		return entityManager.find(CompanyEntity.class, companyId);
		
	}
	
	/**
	 * Sending a query to the DB to get information of a company by name.
	 * @param companyName - a String parameter represent the name of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByComapnyName(String companyName) throws ApplicationException {
		
		Query getQuery = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyName = :CompanyNameObj ");
		getQuery.setParameter("CompanyNameObj", companyName);
		CompanyEntity company = (CompanyEntity) getQuery.getSingleResult();
		return company;
	}

	/**
	 * Sending a query to the DB to get information of a company by Email.
	 * @param companyEmail - a String parameter represent the e-mail of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByComapnyEmail(String companyEmail) throws ApplicationException {
		Query getQuery = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyEmail = :companyEmailObj ");
		getQuery.setParameter("companyEmailObj", companyEmail);
		CompanyEntity company = (CompanyEntity) getQuery.getSingleResult();
		return company;
	}

	/**
	 * Sending a query to the DB to get all the companies in company table.
	 * @return List collection of all the companies in the company table.
	 * @throws ApplicationException.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CompanyEntity> getAllCompanies() throws ApplicationException{
		Query getQuery = entityManager.createQuery("SELECT company FROM companyEntity As company");
		List<CompanyEntity> companies = getQuery.getResultList();
		return companies;
	}
	
	/**
	 * Sending a query to the DB to get if there is a company with that password to approve login.
	 * @param companyName - String of the company name.
	 * @param companyPasword - String of that company password
	 * @return The company object that fits the parameters.
	 * @throws ApplicationException. 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity login (String companyName, String companyPasword) throws ApplicationException {
		Query loginQuery = entityManager.createQuery("SELECT company FROM CompanyEntity as company WHERE companyName =:companyNameObj AND companyPassword =:companyPasswordObj");
		loginQuery.setParameter("companyNameObj", companyName);
		loginQuery.setParameter("companyPasswordObj", companyPasword);
		CompanyEntity company = (CompanyEntity) loginQuery.getSingleResult();
		return company;		
	}
	
	/**
	 * Sending a query to the DB to get if there is a company using that email for creation.
	 * @param companyEmail - String of that company email.
	 * @return true - Email in use by other company.
	 * 		   false - Email not in use by other company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyExistByEmail(String companyEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE companyEmail = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, companyEmail);
			
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
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCompanyExistByEmail(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * Sending a query to the DB to get if there is a company using that email for update.
	 * @param companyID - a long parameter represent the ID of the requested company.
	 * @param companyEmail - String of that company email.
	 * @return true - Email in use by other company.
	 * 		   false - Email not in use by other company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyEmailExistForUpdate(Long companyID, String companyEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE companyEmail = ? AND NOT CompanyID = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, companyEmail);
			preparedStatement.setLong(2, companyID);
	
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
	 * Sending a query to the DB to get if there is a company using that name for creation.
	 * @param companyName - String of that company name.
	 * @return true - Name in use by other company.
	 * 		   false - Name not in use by other company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyExistByName(String companyName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE companyName = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, companyName);

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
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCompanyExistByName(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * Sending a query to the DB to get if there is a company using that name for creation.
	 * @param companyID - a long parameter represent the ID of the requested company.
	 * @param companyName - String of that company name.
	 * @return true - Name in use by other company.
	 * 		   false - Name not in use by other company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyNameExistForUpdate(long companyID, String companyName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE companyName = ? AND NOT CompanyID = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, companyName);
			preparedStatement.setLong(2, companyID);

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
	 * Extract company's data by parameters from the database.
	 * @param resultSet - Data received from DB.
	 * @return Company object made from the resultSet.
	 * @throws SQLException.
	 */
	private Company extractCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		
		Company company = new Company();
		
		company.setCompanyId		(resultSet.getLong	("CompanyID"));
		company.setCompanyName		(resultSet.getString("CompanyName"));
		company.setCompanyPassword	(resultSet.getString("CompanyPassword"));
		company.setCompanyEmail		(resultSet.getString("CompanyEMail"));

		return company;
	}

	
}