package com.idan.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
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

import com.idan.coupons.beans.CompanyEntity;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.beans.CustomerEntity;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.JdbcUtils;
//TODO implement Transactional

@Repository
public class CustomerDao{

	@PersistenceContext(unitName="couponSystem")
	private EntityManager entityManager;
	
	/**
	 * Sending a query to the DB to add a new customer to the customer table.
	 * @param customer - the customer as a Customer object to add to the DB
	 * @return Long of the ID of the created customer.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCustomer(CustomerEntity customer) throws ApplicationException {

		try {
			entityManager.persist(customer);
		} 
		catch (Exception e) {

//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, createCustomer(); FAILED");
		} 

	}


	/**
	 * Sending a query to the DB to remove customer from the customer table by a Customer object.
	 * @param Customer - the customer as a Customer object to remove from the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCustomerByCustomerID(Long customerID) throws ApplicationException {
		
		CustomerEntity customer = getCustomerByCustomerId(customerID);
		try {
			entityManager.remove(customer);	
		}
		catch (Exception e) {
//				In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, removeCustomer(); FAILED");
		}
		
	}
	
	


	/**
	 * Sending a query to the DB to add a update a customer in the customer table. All the fields will be updated according to the ID of the Customer object.
	 * @param company - the company as a Customer object to be updated in the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCustomer(CustomerEntity customer) throws ApplicationException {

			try {
				entityManager.merge(customer);
			}
	
			catch (Exception e) {
//				In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
				throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, creatCompany(); FAILED");
				}
		}


	/**
	 * Sending a query to the DB to get information of a customer.
	 * @param customerId - a long parameter represent the ID of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerEntity getCustomerByCustomerId(Long customerId) throws ApplicationException {

		try {
			return entityManager.find(CustomerEntity.class, customerId);
			
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customer with ID: " + customerId + ".");
		} catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, getCustomerByCustomerId(); FAILED");
			}
	}
	
	
	/**
	 * Sending a query to the DB to get information of customers by name.
	 * @param customerName - a String parameter represent the name of the requested customers.
	 * @return List of customer objects of the requested company.
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CustomerEntity> getCustomersByCustomerName(String customerName) throws ApplicationException {

		try {
			List<CustomerEntity> customers;
			Query getQuery = entityManager.createQuery("SELECT customer FROM CustomerEntity As customer WHERE customerName = :customerNameObj ");
			getQuery.setParameter("customerNameObj", customerName);
			customers = getQuery.getResultList();
			return customers;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customer with name: " + customerName + ".");
		}
		catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomersByCustomerName(); FAILED");
			}
		
	}

	/**
	 * Sending a query to the DB to get information of a customer by Email.
	 * @param customerEmail - a String parameter represent the e-mail of the requested customer.
	 * @return Company object of the requested customer.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerEntity getCustomerByCustomerEmail(String customerEmail) throws ApplicationException {
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Customer customer = null;

		try {
			Query getQuery = entityManager.createQuery("SELECT customer FROM CustomerEntity As customer WHERE customerEmail = :customerEmailObj ");
			getQuery.setParameter("customerEmailObj", customerEmail);
			CustomerEntity customer = (CustomerEntity) getQuery.getSingleResult();
//			// Getting a connection to the DB
//			connection = JdbcUtils.getConnection();
//			
//			// Creating a string which will contain the query
//			String sql = "SELECT * FROM customer WHERE customerEmail = ? ";
//			preparedStatement = connection.prepareStatement(sql);
//			
//			preparedStatement.setString(1, customerEmail);
//			
//		
//			
//			resultSet = preparedStatement.executeQuery();
//
//			// Checking if we got a reply with the requested data. If no data was received, returns null.
//			if (!resultSet.next()) {
//				return null;
//			}
//			customer = extractCustomerFromResultSet(resultSet);
//
//			
			return customer;
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customer with email: " + customerEmail + ".");
		} catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerEmail(); FAILED");
			}
//
//		finally {
//			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
//		}
	}


	/**
	 * Sending a query to the DB to get all the customers in customer table.
	 * @return List collection of all the customers in the customer table.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CustomerEntity> getAllCustomers() throws ApplicationException{
		

		try {
			List<CustomerEntity> customers;
			Query getQuery = entityManager.createQuery("SELECT customer FROM CustomerEntity As customer");
			customers = getQuery.getResultList();
			return customers;
		}catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+"  No companies in data base.");
		} 

		catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, getAllCustomers(); FAILED");
			}
		
		
	}
	
	/**
	 * Sending a query to the DB to get if there is a customer with that password to approve login.
	 * @param customerName - String parameter of the customer name.
	 * @param customerPasword - String parameter of that customer password
	 * @return The customer object that fits the parameters.
	 * @throws ApplicationException 
	 */
	public CustomerEntity login (String customerEmail, String customerPassword) throws ApplicationException {
	
		try {
			Query loginQuery = entityManager.createQuery("SELECT customer FROM CustomerEntity as customer WHERE customerEmail =:customerEmailObj AND customerPassword =:customerPasswordObj");
			loginQuery.setParameter("customerEmailObj", customerEmail);
			loginQuery.setParameter("customerPasswordObj", customerPassword);
			CustomerEntity customer = (CustomerEntity) loginQuery.getSingleResult();
			return customer;	
		} catch (NoResultException e) {
			return null;
		}

		catch (Exception e) {
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CustomerDao, login(); FAILED");
			}		
	}
	
	/**
	 * Sending a query to the DB to get if there is a customer using that email for creation.
	 * @param customerEmail - String of that customer email.
	 * @return true - Email in use by other customer.
	 * 		   false - Email not in use by other customer.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCustomerExistByEmail(String customerEmail) throws ApplicationException {

		try {
			Query verifyQUey = entityManager.createQuery("SELECT customer FROM CustomerEntity As customer WHERE customerEmail = :customerEmailObj");
			verifyQUey.setParameter("customerEmailObj", customerEmail);			
			verifyQUey.getSingleResult();
			
			// If a company with email exist in DB, return true.
			return true;
		} catch (NoResultException e) {
		//	In case of no result exception, no company with this email exist, return false.
			return false;
		}		
		catch (Exception e) {
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCustomerExistByEmail(); FAILED");
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
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCustomerEmailExistForUpdate(Long customerId, String customerEmail) throws ApplicationException {

		try {
			Query verifyQUey = entityManager.createQuery("SELECT customer FROM CustomerEntity As customer WHERE customerEmail = :customerEmailObj AND NOT customerId = :customerIDObj");
			verifyQUey.setParameter("customerEmailObj", customerEmail);		
			verifyQUey.setParameter("customerIDObj", customerId);	
			verifyQUey.getSingleResult();
			
			return true;
		} catch (NoResultException e) {
		//	In case of no result exception, no company with this email exist, return false.
			return false;
		}		
		catch (Exception e) {
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCustomerExistByEmail(); FAILED");
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