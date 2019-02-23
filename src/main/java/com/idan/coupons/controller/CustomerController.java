package com.idan.coupons.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.beans.CustomerEntity;
import com.idan.coupons.dao.CustomerDao;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.ValidationUtils;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerDao customerDao;
	
	
	/**
	 * Creating a customer in the DB.
	 * @param customer - the customer as a Customer object to add to the DB.
	 * @throws ApplicationException
	 */
	public void createCustomer(CustomerEntity customer) throws ApplicationException {
		
		validateCreateCustomer(customer);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
//		Long customerID = this.customerDao.createCustomer(customer);
		this.customerDao.createCustomer(customer);
//		customer.setCustomerId(customerID);
//		return customer;
		
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
	public void updateCustomer(CustomerEntity customer) throws ApplicationException {
		
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
	public CustomerEntity getCustomerByCustomerId(Long customerID) throws ApplicationException {
		if(customerID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		return this.customerDao.getCustomerByCustomerId(customerID);
		
	}
	
	/**
	 * Getting information of a customer by name.
	 * @param customerName a String parameter represent the name of the requested customer.
	 * @return List of customers object of the requested customer name.
	 * @throws ApplicationException
	 */
	public List<CustomerEntity> getCustomersByCustomerName(String customerName) throws ApplicationException {
		if (!ValidationUtils.isValidNameFormat(customerName)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Not valid name format " + customerName + ".");
		}
		
		List<CustomerEntity> customers = customerDao.getCustomersByCustomerName(customerName);
		
		return customers;
	}

	/**
	 * Getting information of a customer by e-mail.
	 * @param customerEmail - a String parameter represent the e-mail of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException
	 */
	public CustomerEntity getCustomerByCustomerEmail(String customerEmail) throws ApplicationException {
		if (!ValidationUtils.isValidEmailFormat(customerEmail)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Not valid email format " + customerEmail + ".");
		}
		
		CustomerEntity customer = customerDao.getCustomerByCustomerEmail(customerEmail);
		
		return customer;
	}

	/**
	 * Getting list of all customers in DB.
	 * @return List collection of all the customers in the customer table.
	 * @throws ApplicationException
	 */
	public List<CustomerEntity> getAllCustomers() throws ApplicationException{
		
		List<CustomerEntity> customers = this.customerDao.getAllCustomers();
		
		return customers;
		
	}

	/**
	 * Logging in to web site.
	 * @param customerEmail - String of the customer email.
	 * @param customerPassword - String of that customer password.
	 * @return The company object that fits the parameters.
	 * @throws ApplicationException
	 */
	public CustomerEntity login (String customerEmail, String customerPassword) throws ApplicationException {
		
		validateCustomer(new CustomerEntity("Valid Name", customerPassword, customerEmail));
		
		return this.customerDao.login(customerEmail, customerPassword);
		
	}

	/**
	 * Validating company object for update. In case of invalid parameter, ApplicationException will be thrown.
	 * @param customer - customer object to validate.
	 * @throws ApplicationException
	 */
	private void validateUpdateCustomer(CustomerEntity customer) throws ApplicationException {

		validateCustomer(customer);
		
		if (this.customerDao.isCustomerEmailExistForUpdate(customer.getCustomerId(), customer.getCustomerEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Update customer has failed."
					+"\nThe user attempted to update a customer using an Email that is already in use."
					+"\nCustomer Email="+customer.getCustomerEmail());
		}
		
	}

	/**
	 * Validating customer object for update. In case of invalid parameter, ApplicationException will be thrown.
	 * @param customer - customer object to validate.
	 * @throws ApplicationException
	 */
	private void validateCreateCustomer(CustomerEntity customer) throws ApplicationException {

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
	private void validateCustomer(CustomerEntity customer) throws ApplicationException {

		List<InputErrorType> errorTypes = new ArrayList<InputErrorType>();
		
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
