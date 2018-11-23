package com.idan.coupons.controller;

import java.util.ArrayList;
import java.util.List;

//import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Customer;
//import com.idan.coupons.dao.CompanyDao;
//import com.idan.coupons.dao.CouponDao;
import com.idan.coupons.dao.CustomerDao;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.ValidationUtils;

public class CustomerController {
	
//	private CouponDao couponDao;
//	private CompanyDao companyDao;
	private CustomerDao customerDao;
	
	public CustomerController(){
//		this.couponDao=new CouponDao();
//		this.companyDao=new CompanyDao();
		this.customerDao=new CustomerDao();
	}
	
	/**
	 * Creating a customer in the DB.
	 * @param customer - the customer as a Customer object to add to the DB.
	 * @throws ApplicationException
	 */
	public void createCustomer(Customer customer) throws ApplicationException {
		
		validateCreateCustomer(customer);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.customerDao.createCustomer(customer);
	}
	
	/**
	 * Removing customer from customer table.
	 * @param customerID - a long parameter represent the ID of the requested customer.
	 * @throws ApplicationException
	 */
	public void removeCustomerByCustomerID(Long customerID) throws ApplicationException {
		if(customerID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
//		this.couponDao.removeCustomerPurchasesByCustomerID(customerID);
		this.customerDao.removeCustomerByCustomerID(customerID);
		
	}
	
	/**
	 * Updating a customer in the customer table. All the fields will be updated according to the ID of the customer object.
	 * @param customer
	 * @throws ApplicationException
	 */
	public void updateCustomer(Customer customer) throws ApplicationException {
		
		validateUpdateCustomer(customer);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.customerDao.updateCustomer(customer);
		
	}
	
	/**
	 * Getting information of a customer.
	 * @param customerID - a long parameter represent the ID of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException
	 */
	public Customer getCustomerByCustomerId(Long customerID) throws ApplicationException {
		if(customerID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		Customer customer = this.customerDao.getCustomerByCustomerId(customerID);
		if (customer == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customer with ID" + customerID + ".");
		}
		
		return customer;
	}
	
	/**
	 * Getting information of a customer by name.
	 * @param customerName a String parameter represent the name of the requested customer.
	 * @return List of customers object of the requested customer name.
	 * @throws ApplicationException
	 */
	public List<Customer> getCustomersByCustomerName(String customerName) throws ApplicationException {
		if (!ValidationUtils.isValidNameFormat(customerName)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Not valid name format" + customerName + ".");
		}
		
		List<Customer> customers = customerDao.getCustomersByCustomerName(customerName);
		
		if (customers.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customer with name" + customerName + ".");
		}
		
		return customers;
	}

	/**
	 * Getting information of a customer by e-mail.
	 * @param customerEmail - a String parameter represent the e-mail of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException
	 */
	public Customer getCustomerByCustomerEmail(String customerEmail) throws ApplicationException {
		if (!ValidationUtils.isValidEmailFormat(customerEmail)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Not valid name format" + customerEmail + ".");
		}
		
		Customer customer = customerDao.getCustomerByCustomerEmail(customerEmail);
		
		if (customer == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customer with name" + customerEmail + ".");
		}
		
		return customer;
	}

	/**
	 * Getting list of all customers in DB.
	 * @return List collection of all the customers in the customer table.
	 * @throws ApplicationException
	 */
	public List<Customer> getAllCustomers() throws ApplicationException{
		
		List<Customer> customers = this.customerDao.getAllCustomers();
		
		if(customers.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No customers in data base.");
		}
		
		return customers;
		
	}

	/**
	 * Logging in to web site.
	 * @param customerEmail - String of the customer email.
	 * @param customerPassword - String of that customer password.
	 * @return The company object that fits the parameters.
	 * @throws ApplicationException
	 */
	public Customer login (String customerEmail, String customerPassword) throws ApplicationException {
		
		validateCustomer(new Customer("Valid Name", customerPassword, customerEmail));
		
		return this.customerDao.login(customerEmail, customerPassword);
	}

	/**
	 * Validating company object for update. In case of invalid parameter, ApplicationException will be thrown.
	 * @param customer - customer object to validate.
	 * @throws ApplicationException
	 */
	private void validateUpdateCustomer(Customer customer) throws ApplicationException {

		validateCustomer(customer);
		
		if (this.customerDao.isCustomerEmailExistForUpdate(customer.getCustomerId(), customer.getCustomerEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Update customer has failed."
					+"\nThe user attempted to update a customer using an Email that is already in use."
					+"\nCustomer Name="+customer.getCustomerName());
		}
		
	}

	/**
	 * Validating customer object for update. In case of invalid parameter, ApplicationException will be thrown.
	 * @param customer - customer object to validate.
	 * @throws ApplicationException
	 */
	private void validateCreateCustomer(Customer customer) throws ApplicationException {

		validateCustomer(customer);
		
		if (this.customerDao.isCustomerExistByEmail(customer.getCustomerEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create customer has failed."
					+"\nThe user attempted to create a new customer using an Email that is already in use."
					+"\nCustomer Email="+customer.getCustomerEmail());
		}
		
	}

	/**
	 * Validating customer object for creation. In case of invalid parameter, ApplicationException will be thrown
	 * @param customer - customer object to validate.
	 * @throws ApplicationException
	 */
	private void validateCustomer(Customer customer) throws ApplicationException {

		List<InputErrorType> errorTypes = new ArrayList<>();
		
		if(!ValidationUtils.isValidNameFormat(customer.getCustomerName())) {
			errorTypes.add(InputErrorType.INVALID_NAME);
		}
		
		if(!ValidationUtils.isValidPasswordFormat(customer.getCustomerPassword())) {
			errorTypes.add(InputErrorType.INVALID_PASSWORD);
		}
		if(!ValidationUtils.isValidEmailFormat(customer.getCustomerEmail())) {
			errorTypes.add(InputErrorType.INVALID_EMAIL);
		}
		
		if(!errorTypes.isEmpty()) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Validating customer input has failed."
					+"\nOne or more of the fields are incorrect.", errorTypes);
		}
		
	}

}
