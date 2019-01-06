package com.idan.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idan.coupons.beans.Company;
import com.idan.coupons.controller.CompanyController;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.CookieUtil;
import com.idan.coupons.utils.ValidationUtils;

@CrossOrigin(origins="http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/companies")
public class CompanyApi {

	@Autowired
	private CompanyController companyController;


	/**
	 * Getting list of all companies from DB.
	 * @return List collection of all the companies in the company table
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/companies/
	public List<Company> getAllCompanies() throws ApplicationException {
		List<Company> companies = companyController.getAllCompanies();
//				System.out.println(companies);
		return companies;
	}

	/**
	 * Getting information of a company.
	 * @param companyId - a long parameter represent the ID of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{companyId}", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/companies/5
	public Company getCompanyByComapnyId(@PathVariable("companyId") long companyId) throws ApplicationException{
		return companyController.getCompanyByComapnyId(companyId);
	}

	/**
	 * Getting information of a company by name.
	 * @param companyName - a String parameter represent the name of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{companyName}/byCompanyName", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/companies/coca cola/byCompanyName
	public Company getCompanyByName(@PathVariable("companyName") String companyName) throws ApplicationException{
		return companyController.getCompanyByComapnyName(companyName);
	}

	/**
	 * Getting information of a company by Email.
	 * @param companyName - a String parameter represent the e-mail of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{companyEmail}/byCompanyEmail", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/companies/HP@gmail.com/byCompanyEmail
	public Company getCompanyByEmail(@PathVariable("companyEmail") String companyEmail) throws ApplicationException{
		return companyController.getCompanyByComapnyEmail(companyEmail);
	}

	/**
	 * Creating a company in the DB.
	 * @param company - the company as a Company object to add to the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Company createCompany(HttpServletRequest request, HttpServletResponse response, @RequestBody Company company) throws ApplicationException{
		if (company != null) {
			company = companyController.createCompany(company);
			
			// If company created, registration is complete and creating cookies.
			request.getSession();					
			List<Cookie> loginCookies = CookieUtil.loginCookies(company);
			response = CookieUtil.addCookies(response, loginCookies);
		}
		return company;
	}

	/**
	 * Updating a company in the company table. All the fields will be updated according to the ID of the Company object.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param company - the company as a Company object to be updated in the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public void updateUser (HttpServletRequest request,@RequestBody  Company company) throws ApplicationException{
		// Will update the company in the DB only if the changes are made by the admin or the same company.
//		ValidationUtils.ValidateUser(request, company.getCompanyId());
		companyController.updateCompany(company);
	}

	/**
	 * Removing company from company table.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param companyId - a long parameter represent the ID of the requested company.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{companyId}", method = RequestMethod.DELETE)
	//http://localhost:8080/CouponManagmentSystemVer3/companies/10
	public void removeUser(HttpServletRequest request, @PathVariable("companyId") long companyId) throws ApplicationException{

		// Will update the company in the DB only if the changes are made by the admin or the same company.
		ValidationUtils.ValidateUser(request, companyId);
		companyController.removeCompanyByCompanyID(companyId);
	}




	/* For debugging use only:
	 * Content-Type      application/json
	 *  Json object
	 {
		"companyName": "abba",
		"companyPassword": "aa555456",
		"companyEmail": "a@b",
		"companyId":?
	}
	 */

}
