package com.idan.coupons.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idan.coupons.beans.CustomerEntity;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;

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
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
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

		try {
			Query getQuery = entityManager.createQuery("SELECT customer FROM CustomerEntity As customer WHERE customerEmail = :customerEmailObj ");
			getQuery.setParameter("customerEmailObj", customerEmail);
			CustomerEntity customer = (CustomerEntity) getQuery.getSingleResult();
			return customer;
		} catch (NoResultException e) {
			// getSingleResult throws a NoResultException in case of no results, so it will be replaced simple null.
			return null;
		} catch (Exception e) {
			e.printStackTrace();
//			In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerEmail(); FAILED");
			}

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
		} 	catch (Exception e) {
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
	@Transactional(propagation=Propagation.REQUIRED)
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
	
}