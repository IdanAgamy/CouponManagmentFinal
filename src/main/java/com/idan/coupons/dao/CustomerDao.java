package com.idan.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;


import com.idan.coupons.beans.Customer;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.JdbcUtils;

public class CustomerDao{

	// TODO delete printStackTrace in phase 2
	
	
	/**
	 * Sending a query to the DB to add a new customer to the customer table.
	 * @param customer - the customer as a Customer object to add to the DB
	 * @return Long of the ID of the created customer.
	 * @throws ApplicationException
	 */
	public Long createCustomer(Customer customer) throws ApplicationException {

		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "INSERT INTO customer (CustomerName, CustomerPassword, CustomerEmail) values (?, ?, ?)";

			preparedStatement= connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getCustomerPassword());
			preparedStatement.setString(3, customer.getCustomerEmail());

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

//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, createCustomer(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}


	/**
	 * Sending a query to the DB to remove customer from the customer table by a Customer object.
	 * @param Customer - the customer as a Customer object to remove from the DB.
	 * @throws ApplicationException 
	 */
	public void removeCustomerByCustomerID(Long customerID) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "DELETE FROM customer WHERE CustomerID = ?;";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, customerID);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			preparedStatement.executeUpdate();
			
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
//				In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, removeCustomer(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	


	/**
	 * Sending a query to the DB to add a update a customer in the customer table. All the fields will be updated according to the ID of the Customer object.
	 * @param company - the company as a Customer object to be updated in the DB.
	 * @throws ApplicationException 
	 */
	public void updateCustomer(Customer customer) throws ApplicationException {
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
	
			try {
				// Getting a connection to the DB
				connection = JdbcUtils.getConnection();
				
				// Creating a string which will contain the query
				String sql = "UPDATE customer SET CustomerName = ?, CustomerPassword = ?, CustomerEmail = ? WHERE CustomerID = ?";
				preparedStatement = connection.prepareStatement(sql);
				
				preparedStatement.setString(1, customer.getCustomerName());
				preparedStatement.setString(2, customer.getCustomerPassword());
				preparedStatement.setString(3, customer.getCustomerEmail());
				preparedStatement.setLong(4, customer.getCustomerId());
				
				// TODO delete print
				System.out.println(preparedStatement); // Checking the query sent to the server
				
				preparedStatement.executeUpdate();
				
			}
	
			catch (SQLException e) {
				e.printStackTrace();
//				In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
				throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, creatCompany(); FAILED");
				}
	
			finally {
				JdbcUtils.closeResources(connection, preparedStatement);
			}
			
		}


	/**
	 * Sending a query to the DB to get information of a customer.
	 * @param customerId - a long parameter represent the ID of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException 
	 */
	public Customer getCustomerByCustomerId(Long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "SELECT * FROM customer WHERE CustomerID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, customerId);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Checking if we got a reply with the requested data. If no data was received, returns null.
			if (!resultSet.next()) {
				return null;
			}
			customer = extractCustomerFromResultSet(resultSet);

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, getCustomerByCustomerId(); FAILED");
			}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return customer;
	}
	
	
	/**
	 * Sending a query to the DB to get information of customers by name.
	 * @param customerName - a String parameter represent the name of the requested customers.
	 * @return List of customer objects of the requested company.
	 * @throws ApplicationException
	 */
	public List<Customer> getCustomersByCustomerName(String customerName) throws ApplicationException {
		ArrayList<Customer> customers = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "SELECT * FROM Customer WHERE customerName = ?";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, customerName);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Customer objects.
			while (resultSet.next()) {
				customers.add(extractCustomerFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomersByCustomerName(); FAILED");
			}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return customers;
	}

	/**
	 * Sending a query to the DB to get information of a customer by Email.
	 * @param customerEmail - a String parameter represent the e-mail of the requested customer.
	 * @return Company object of the requested customer.
	 * @throws ApplicationException
	 */
	public Customer getCustomerByCustomerEmail(String customerEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "SELECT * FROM customer WHERE customerEmail = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, customerEmail);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Checking if we got a reply with the requested data. If no data was received, returns null.
			if (!resultSet.next()) {
				return null;
			}
			customer = extractCustomerFromResultSet(resultSet);

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerEmail(); FAILED");
			}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return customer;
	}


	/**
	 * Sending a query to the DB to get all the customers in customer table.
	 * @return List collection of all the customers in the customer table.
	 * @throws ApplicationException 
	 */
	public ArrayList<Customer> getAllCustomers() throws ApplicationException{
		
		ArrayList<Customer> customers = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "SELECT * FROM Customer";
			preparedStatement = connection.prepareStatement(sql);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();

			// Looping on the received result to add to a list of Customer objects.
			while (resultSet.next()) {
				customers.add(extractCustomerFromResultSet(resultSet));
			}

			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, getAllCustomers(); FAILED");
			}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return customers;
		
	}
	
	/**
	 * Sending a query to the DB to get if there is a customer with that password to approve login.
	 * @param customerName - String parameter of the customer name.
	 * @param customerPasword - String parameter of that customer password
	 * @return The customer object that fits the parameters.
	 * @throws ApplicationException 
	 */
	public Customer login (String customerEmail, String customerPassword) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "SELECT * FROM customer WHERE customerEmail = ? AND BINARY CustomerPassword = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, customerEmail);
			preparedStatement.setString(2, customerPassword);
			
			// TODO delete print
			System.out.println(preparedStatement); // Checking the query sent to the server
			
			resultSet = preparedStatement.executeQuery();
			
			// If no result was received, return false because there is no matching customer name and customer password
			if (!resultSet.next()) {
				return null;
			}
			
			 Customer customer = extractCustomerFromResultSet(resultSet);
			
			return customer;
			
		}

		catch (SQLException e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, login(); FAILED");
			}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
	}
	
	/**
	 * Sending a query to the DB to get if there is a customer using that email for creation.
	 * @param customerEmail - String of that customer email.
	 * @return true - Email in use by other customer.
	 * 		   false - Email not in use by other customer.
	 * @throws ApplicationException
	 */
	public boolean isCustomerExistByEmail(String customerEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM customer WHERE customerEmail = ? ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, customerEmail);
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
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCustomerExistByEmail(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * Sending a query to the DB to get if there is a customer using that email for update.
	 * @param customerID - a long parameter represent the ID of the requested customer.
	 * @param customerEmail - String of that customer email.
	 * @return true - Email in use by other customer.
	 * 		   false - Email not in use by other customer.
	 * @throws ApplicationException
	 */
	public boolean isCustomerEmailExistForUpdate(Long customerID, String customerEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB.
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query.
			String sql = "SELECT * FROM customer WHERE customerEmail = ? AND NOT CustomerID = ?; ";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, customerEmail);
			preparedStatement.setLong(2, customerID);
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
	 * Extract customer's data by parameters from the database
	 * @param resultSet - Data received from DB
	 * @return Company object made from the resultSet
	 * @throws SQLException
	 */
	private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(resultSet.getLong("CustomerID"));
		customer.setCustomerName(resultSet.getString("CustomerName"));
		customer.setCustomerPassword(resultSet.getString("CustomerPassword"));
		customer.setCustomerEmail(resultSet.getString("customerEmail"));
	
		return customer;
	}

	
}