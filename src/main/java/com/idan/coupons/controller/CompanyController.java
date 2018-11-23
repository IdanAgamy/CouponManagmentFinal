package com.idan.coupons.controller;

import java.util.ArrayList;
import java.util.List;

import com.idan.coupons.beans.Company;
//import com.idan.coupons.beans.Customer;
import com.idan.coupons.dao.CompanyDao;
//import com.idan.coupons.dao.CouponDao;
//import com.idan.coupons.dao.CustomerDao;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.ValidationUtils;

public class CompanyController {
	
//	private CouponDao couponDao;
	private CompanyDao companyDao;
//	private CustomerDao customerDao;
	
	public CompanyController(){
//		this.couponDao=new CouponDao();
		this.companyDao=new CompanyDao();
//		this.customerDao=new CustomerDao();
	}
	
	/**
	 * Creating a company in the DB.
	 * @param company - the company as a Company object to add to the DB.
	 * @throws ApplicationException
	 */
	public void createCompany(Company company) throws ApplicationException {

		//Validating company parameters for creating company.
		validateCreateCompany(company);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.companyDao.createCompany(company);
		
	}
	
	/**
	 * Removing company from company table.
	 * @param companyID - a long parameter represent the ID of the requested company.
	 * @throws ApplicationException
	 */
	public void removeCompanyByCompanyID(Long companyID) throws ApplicationException {
		if(companyID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
//		this.couponDao.removeCouponByCompanyID(companyID);
		this.companyDao.removeCompanyByCompanyID(companyID);
		
	}
	
	/**
	 * Updating a company in the company table. All the fields will be updated according to the ID of the Company object.
	 * @param company - the company as a Company object to be updated in the DB.
	 * @throws ApplicationException
	 */
	public void updateCompany(Company company) throws ApplicationException {
		
		//Validating company parameters for updating company.
		validateUpdateCompany(company);
		
		this.companyDao.updateCompany(company);
		
	}

	/**
	 *  Getting information of a company.
	 * @param companyId - a long parameter represent the ID of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	public Company getCompanyByComapnyId(Long companyId) throws ApplicationException {
		if(companyId==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		Company company = this.companyDao.getCompanyByComapnyId(companyId);
		
		if(company == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with ID" + companyId + ".");
		}
		
		return company;
		
	}
	
	/**
	 * Getting information of a company by name.
	 * @param companyName - a String parameter represent the name of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	public Company getCompanyByComapnyName(String companyName)  throws ApplicationException{

		if (!ValidationUtils.isValidNameFormat(companyName)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Not valid name format" + companyName + ".");
		}
		
		Company company = companyDao.getCompanyByComapnyName(companyName);
		
		if (company == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with name" + companyName + ".");
		}
		
		return company;
		
	}

	/**
	 * Getting information of a company by Email.
	 * @param companyEmail - a String parameter represent the e-mail of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	public Company getCompanyByComapnyEmail(String companyEmail) throws ApplicationException {
		if (!ValidationUtils.isValidEmailFormat(companyEmail)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Not valid name format " + companyEmail + ".");
		}
		
		Company company = companyDao.getCompanyByComapnyEmail(companyEmail);
		
		if (company == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with name " + companyEmail + ".");
		}
		
		return company;
	}

	/**
	 * Getting list of all companies in DB.
	 * @return List collection of all the companies in the company table
	 * @throws ApplicationException
	 */
	public List<Company> getAllCompanies() throws ApplicationException{
		
		List<Company> companies = this.companyDao.getAllCompanies();
		
		if(companies.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No companies in data base.");
		}
		
		return companies;
		
		
	}
	
	/**
	 * Logging in to web site.
	 * @param companyName - String of the company name.
	 * @param companyPasword - String of that company password.
	 * @return The company object that fits the parameters.
	 * @throws ApplicationException
	 */
	public Company login (String companyName, String companyPasword) throws ApplicationException {
		
		validateCompany(new Company(companyName, companyPasword, "valid@email"));
		
		return this.companyDao.login(companyName, companyPasword);
		
	}
	
	/**
	 * Validating company object for update. In case of invalid parameter, ApplicationException will be thrown.
	 * @param company - company object to validate.
	 * @throws ApplicationException
	 */
	private void validateUpdateCompany(Company company) throws ApplicationException {
		validateCompany(company);
		
		if (this.companyDao.isCompanyNameExistForUpdate(company.getCompanyId(), company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Update company has failed."
					+"\nThe user attempted to update a company using a name that is already in use."
					+"\nCustomer name = "+company.getCompanyEmail());
		}
		
		if (this.companyDao.isCompanyEmailExistForUpdate(company.getCompanyId(), company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.EMAIL_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create company has failed."
					+"\nThe user attempted to update a company using an Email that is already in use."
					+"\nCustomer Email="+company.getCompanyEmail());
		}
	}
	
	/**
	 * Validating company object for creation. In case of invalid parameter, ApplicationException will be thrown
	 * @param company - company object to validate.
	 * @throws ApplicationException
	 */
	private void validateCreateCompany(Company company) throws ApplicationException {

		validateCompany(company);
		
		if (this.companyDao.isCompanyExistByName(company.getCompanyName())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create company has failed."
					+"\nThe user attempted to create a new company using a name that is already in use."
					+"\nCustomer name="+company.getCompanyEmail());
		}
		
		if (this.companyDao.isCompanyExistByEmail(company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create company has failed."
					+"\nThe user attempted to create a new company using an Email that is already in use."
					+"\nCustomer Email="+company.getCompanyEmail());
		}
		
		
	}
	
	/**
	 * Validating parameters of a company. In case of invalid parameter, ApplicationException will be thrown.
	 * @param company - company object to validate.
	 * @throws ApplicationException
	 */
	private void validateCompany(Company company) throws ApplicationException {
		
		
		List<InputErrorType> errorTypes = new ArrayList<>();		
		
		if(!ValidationUtils.isValidNameFormat(company.getCompanyName())) {
			errorTypes.add(InputErrorType.INVALID_NAME);
		}
		
		if(!ValidationUtils.isValidPasswordFormat(company.getCompanyPassword())) {
			errorTypes.add(InputErrorType.INVALID_PASSWORD);
		}
		if(!ValidationUtils.isValidEmailFormat(company.getCompanyEmail())) {
			errorTypes.add(InputErrorType.INVALID_EMAIL);
		}
		
		
		if(!errorTypes.isEmpty()) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Validating company input has failed."
					+"\nOne or more of the fields are incorrect.", errorTypes);
		}
		
	}

}
