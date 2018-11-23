package com.idan.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.idan.coupons.beans.Company;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.JdbcUtils;

public class CompanyDao{

	// TODO delete printStackTrace in phase 2
	
	
	/**
	 * Sending a query to the DB to add a new company to the company table.
	 * @param company - the company as a Company object to add to the DB.
	 * @return Long of the ID of the created company.
	 * @throws ApplicationException
	 */
	public Long createCompany(Company company) throws ApplicationException {

		// Preparing the JDBC resources for sending the query.
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();	
			
			// Creating a string which will contain the query.
			String sql = "insert into company (CompanyName, CompanyPassword, CompanyEMail) values (?,?,?)";
			preparedStatement= connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			// Switching the question marks with the input from the user.
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			// Executing the query.
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
			
			return null;
		} 

		catch (SQLException e) {
			
			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, creatCompany(); FAILED");
		
		} 

		finally {
			
			// Closing the resources.
			JdbcUtils.closeResources(connection, preparedStatement);
		
		}

	}

	/**
	 * Sending a query to the DB to remove company from the company table by a Company object.
	 * @param company - the company as a Company object to remove from the DB.
	 * @throws ApplicationException 
	 */
	public void removeCompanyByCompanyID(Long companyID) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "DELETE FROM company WHERE CompanyID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyID);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			preparedStatement.executeUpdate();
						
		}
		
		catch (SQLException e) {
			
			e.printStackTrace();
			
			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, removeCompany(); FAILED");
		
		}
		
		finally {
			
			JdbcUtils.closeResources(connection, preparedStatement);
		
		}
		
	}
			

	/**
	 * Sending a query to the DB to add a update a company in the company table. All the fields will be updated according to the ID of the Company object.
	 * @param company - the company as a Company object to be updated in the DB.
	 * @throws ApplicationException. 
	 */
	public void updateCompany(Company company) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {

			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "UPDATE company SET CompanyName = ?, CompanyPassword = ?, CompanyEMail = ? WHERE CompanyID = ?";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString	(1, company.getCompanyName());
			preparedStatement.setString	(2, company.getCompanyPassword());
			preparedStatement.setString	(3, company.getCompanyEmail());
			preparedStatement.setLong	(4, company.getCompanyId());
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e) {
			
			e.printStackTrace();
			
			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, updateCompany(); FAILED");
			
		}
		
		finally {

			JdbcUtils.closeResources(connection, preparedStatement);
			
		}
		
	}

	
	/**
	 * Sending a query to the DB to get information of a company.
	 * @param companyId - a long parameter represent the ID of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException. 
	 */
	public Company getCompanyByComapnyId(Long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {

			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE CompanyID = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setLong(1, companyId);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			resultSet = preparedStatement.executeQuery();
			
			// Checking if we got a reply with the requested data. If no data was received, returns null.
			if (!resultSet.next()) {
				return null;
			}
			
			company = extractCompanyFromResultSet(resultSet);

		}

		catch (SQLException e) {
			
			e.printStackTrace();

			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getCompanyByComapnyId(); FAILED");
		
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		
		}
		
		return company;
		
	}
	
	/**
	 * Sending a query to the DB to get information of a company by name.
	 * @param companyName - a String parameter represent the name of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	public Company getCompanyByComapnyName(String companyName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {

			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE companyName = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, companyName);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			resultSet = preparedStatement.executeQuery();
			
			// Checking if we got a reply with the requested data. If no data was received, returns null.
			if (!resultSet.next()) {
				return null;
			}
			
			company = extractCompanyFromResultSet(resultSet);

		}

		catch (SQLException e) {
			
			e.printStackTrace();

			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getCompanyByComapnyName(); FAILED");
		
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		
		}
		
		return company;
	}

	/**
	 * Sending a query to the DB to get information of a company by Email.
	 * @param companyEmail - a String parameter represent the e-mail of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	public Company getCompanyByComapnyEmail(String companyEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {

			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE companyEmail = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, companyEmail);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			resultSet = preparedStatement.executeQuery();
			
			// Checking if we got a reply with the requested data. If no data was received, returns null.
			if (!resultSet.next()) {
				return null;
			}
			
			company = extractCompanyFromResultSet(resultSet);

		}

		catch (SQLException e) {
			
			e.printStackTrace();

			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in getCompanyByComapnyEmail, getCompanyByComapnyName(); FAILED");
		
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		
		}
		
		return company;
	}

	/**
	 * Sending a query to the DB to get all the companies in company table.
	 * @return List collection of all the companies in the company table.
	 * @throws ApplicationException.
	 */
	public List<Company> getAllCompanies() throws ApplicationException{

		List<Company> companies = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company";
			preparedStatement = connection.prepareStatement(sql);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			resultSet = preparedStatement.executeQuery();
			
			// Looping on the received result to add to a list of Company objects.
			while (resultSet.next()) {
				companies.add(extractCompanyFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			
			e.printStackTrace();

			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getAllCompanies(); FAILED");
		
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		
		}
		
		return companies;
		
	}
	
	/**
	 * Sending a query to the DB to get if there is a company with that password to approve login.
	 * @param companyName - String of the company name.
	 * @param companyPasword - String of that company password
	 * @return The company object that fits the parameters.
	 * @throws ApplicationException. 
	 */
	public Company login (String companyName, String companyPasword) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query.
			String sql = "SELECT * FROM company WHERE CompanyName = ? AND BINARY CompanyPassword = ?; ";
			preparedStatement = connection.prepareStatement(sql);
		
			preparedStatement.setString(1, companyName);
			preparedStatement.setString(2, companyPasword);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server

			resultSet = preparedStatement.executeQuery();
			
			// If no result was received, return false because there is no matching company name and company password
			if (!resultSet.next()) {
				return null;
			}
			
			Company company = extractCompanyFromResultSet(resultSet);
			
			return company;
			
		}

		catch (SQLException e) {
			e.printStackTrace();
			// In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, login(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		
	}
	
	/**
	 * Sending a query to the DB to get if there is a company using that email for creation.
	 * @param companyEmail - String of that company email.
	 * @return true - Email in use by other company.
	 * 		   false - Email not in use by other company.
	 * @throws ApplicationException
	 */
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
	 * Sending a query to the DB to get if there is a company using that name for creation.
	 * @param companyName - String of that company name.
	 * @return true - Name in use by other company.
	 * 		   false - Name not in use by other company.
	 * @throws ApplicationException
	 */
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