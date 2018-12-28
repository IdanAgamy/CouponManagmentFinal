package com.idan.coupons.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idan.coupons.beans.Customer;
import com.idan.coupons.controller.CustomerController;
//import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
//import com.idan.coupons.utils.CookieUtil;
//import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.ValidationUtils;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/customers")
public class CustomerApi {

	@Autowired
	private CustomerController customerController;


	/**
	 * Getting list of all customers in DB.
	 * @return List collection of all the customers in the customer table.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/customers
	public List<Customer> getAllCustomers() throws ApplicationException {
		return customerController.getAllCustomers();
	}

	/**
	 * Getting information of a customer.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param customerId - a long parameter represent the ID of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{customerId}", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/customers/2
	public Customer getUser(HttpServletRequest request, @PathVariable("customerId") long customerId) throws ApplicationException{
		// Will update the company in the DB only if the changes are made by the admin or the same customer.
		ValidationUtils.ValidateUser(request, customerId);
		return customerController.getCustomerByCustomerId(customerId);
	}

	/**
	 * Getting information of a customer by name.
	 * @param customerName - a String parameter represent the name of the requested customer.
	 * @return List of customers object of the requested customer name.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/byCustomerName", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/customers/byCustomerName?customerName=patrick
	public List<Customer> getCustomerByName(@RequestParam("customerName") String customerName) throws ApplicationException{

		return customerController.getCustomersByCustomerName(customerName);
	}

	/**
	 * Getting information of a customer by e-mail.
	 * @param customerEmail - a String parameter represent the e-mail of the requested customer.
	 * @return Customer object of the requested customer.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{customerEmail}/byCustomerEmail", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/customers/Picard@EnterpriseD/byCustomerEmail
	public Customer getCustomerByEmail(@PathVariable("customerEmail") String customerEmail) throws ApplicationException{
		return customerController.getCustomerByCustomerEmail(customerEmail);
	}

	/**
	 * Creating a customer in the DB.
	 * @param customer - the customer as a Customer object to add to the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.POST)
	//http://localhost:8080/CouponManagmentSystemVer3/customers/
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException{
		customerController.createCustomer(customer);
	}

	/**
	 * Updating a customer in the customer table. All the fields will be updated according to the ID of the customer object.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param customer - the customer as a Customer object to be updated in the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.PUT)
	//http://localhost:8080/CouponManagmentSystemVer3/customers/
	public void updateUser (HttpServletRequest request,@RequestBody  Customer customer) throws ApplicationException{
		// Will update the customer in the DB only if the changes are made by the admin or the same customer.
		Long customerID = customer.getCustomerId();
		ValidationUtils.ValidateUser(request, customerID);
		customerController.updateCustomer(customer);
	}

	/**
	 * Removing customer from customer table.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param customerId - a long parameter represent the ID of the requested customer.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{customerId}", method = RequestMethod.DELETE)
	//http://localhost:8080/CouponManagmentSystemVer3/customers/2
	public void removeUser(HttpServletRequest request, @PathVariable("customerId") long customerId) throws ApplicationException{
		// Will update the customer in the DB only if the changes are made by the admin or the same customer.
		ValidationUtils.ValidateUser(request, customerId);
		customerController.removeCustomerByCustomerID(customerId);

	}

	/* Json object
	 * DON'T FORGET: add header for JSon!!!!!!!!!!!!!!!!!one!!!!1!
	 {
		"customerId": 1,
			"customerName": "lord ainz",
			"customerPassword":"asdf1234",
			"customerEmail":"a@b"
	}
	 */


}
