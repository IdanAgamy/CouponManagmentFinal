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
	
	public void createCompany(Company company) throws ApplicationException {

		validateCreateCompany(company);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.companyDao.createCompany(company);
		
	}
	
	public void removeCompanyByCompanyID(Long companyID) throws ApplicationException {
		
//		this.couponDao.removeCouponByCompanyID(companyID);
		this.companyDao.removeCompanyByCompanyID(companyID);
		
	}
	
	public void updateCompany(Company company) throws ApplicationException {
		
		validateUpdateCompany(company);
		
		this.companyDao.updateCompany(company);
		
	}

	public Company getCompanyByComapnyId(Long companyId) throws ApplicationException {
		
		Company company = this.companyDao.getCompanyByComapnyId(companyId);
		
		if(company == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with ID" + companyId + ".");
		}
		
		return company;
		
	}
	
	public List<Company> getAllCompanies() throws ApplicationException{
		
		List<Company> companies = this.companyDao.getAllCompanies();
		
		if(companies.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No companies in data base.");
		}
		
		return companies;
		
		
	}
	
	public boolean login (String companyName, String companyPasword) throws ApplicationException {
		
		validateCompany(new Company(companyName, companyPasword, "valid@email"));
		
		return this.companyDao.login(companyName, companyPasword);
		
	}
	

	private void validateUpdateCompany(Company company) throws ApplicationException {
		validateCompany(company);
		
		if (this.companyDao.isCompanyNameExistForUpdate(company.getCompanyId(), company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Update company has failed."
					+"\nThe user attempted to update a company using a name that is already in use."
					+"\nCustomer name="+company.getCompanyEmail());
		}
		
		if (this.companyDao.isCompanyEmailExistForUpdate(company.getCompanyId(), company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create company has failed."
					+"\nThe user attempted to update a company using an Email that is already in use."
					+"\nCustomer Email="+company.getCompanyEmail());
		}
	}

	private void validateCreateCompany(Company company) throws ApplicationException {

		validateCompany(company);
		
		if (this.companyDao.isCompanyExistByName(company.getCompanyEmail())) {
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

	private void validateCompany(Company company) throws ApplicationException {
		
		List<InputErrorType> errorTypes = new ArrayList<InputErrorType>();

		
		
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
