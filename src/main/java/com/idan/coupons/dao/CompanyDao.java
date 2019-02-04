package com.idan.coupons.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.idan.coupons.beans.CompanyEntity;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;

@Repository
public class CompanyDao{

	@PersistenceContext(unitName="couponSystem")
	private EntityManager entityManager;
	
	
	/**
	 * Sending a query to the DB to add a new company to the company table.
	 * @param company - the company as a Company object to add to the DB.
	 * @return Long of the ID of the created company.
	 * @throws ApplicationException
	 */	
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCompany(CompanyEntity company) throws ApplicationException {
		try {
			entityManager.persist(company);
		} catch (Exception e) {
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, creatCompany(); FAILED");

		}
	}

	/**
	 * Sending a query to the DB to remove company from the company table by a Company object.
	 * @param company - the company as a Company object to remove from the DB.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCompanyByCompanyID(Long companyID) throws ApplicationException {
		CompanyEntity company = getCompanyByComapnyId(companyID);
		try {
			entityManager.remove(company);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, removeCompany(); FAILED");
		}
		
	}
			

	/**
	 * Sending a query to the DB to add a update a company in the company table. All the fields will be updated according to the ID of the Company object.
	 * @param company - the company as a Company object to be updated in the DB.
	 * @throws ApplicationException. 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) throws ApplicationException {
		try {
			entityManager.merge(company);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, updateCompany(); FAILED");
		}
		
	}

	
	/**
	 * Sending a query to the DB to get information of a company.
	 * @param companyId - a long parameter represent the ID of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException. 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByComapnyId(Long companyId) throws ApplicationException {
		
		try {
			return entityManager.find(CompanyEntity.class, companyId);
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with ID: " + companyId + ".");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getCompanyByComapnyId(); FAILED");
		}
		
	}
	
	/**
	 * Sending a query to the DB to get information of a company by name.
	 * @param companyName - a String parameter represent the name of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByComapnyName(String companyName) throws ApplicationException {
		
		CompanyEntity company;
		try {
			Query getQuery = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyName = :CompanyNameObj ");
			getQuery.setParameter("CompanyNameObj", companyName);
			company = (CompanyEntity) getQuery.getSingleResult();
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with name: " + companyName + ".");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getCompanyByComapnyName(); FAILED");
		}
		return company;
	}

	/**
	 * Sending a query to the DB to get information of a company by Email.
	 * @param companyEmail - a String parameter represent the e-mail of the requested company.
	 * @return Company object of the requested company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByComapnyEmail(String companyEmail) throws ApplicationException {
		CompanyEntity company;
		try {
			Query getQuery = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyEmail = :companyEmailObj ");
			getQuery.setParameter("companyEmailObj", companyEmail);
			company = (CompanyEntity) getQuery.getSingleResult();
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No company with email: " + companyEmail + ".");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in getCompanyByComapnyEmail, getCompanyByComapnyName(); FAILED");
		}
		return company;
	}

	/**
	 * Sending a query to the DB to get all the companies in company table.
	 * @return List collection of all the companies in the company table.
	 * @throws ApplicationException.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CompanyEntity> getAllCompanies() throws ApplicationException{
		List<CompanyEntity> companies;
		try {
			Query getQuery = entityManager.createQuery("SELECT company FROM CompanyEntity As company");
			companies = getQuery.getResultList();
		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+"  No companies in data base.");
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getAllCompanies(); FAILED");
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
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity login (String companyName, String companyPasword) throws ApplicationException {
		
		try {
			Query loginQuery = entityManager.createQuery("SELECT company FROM CompanyEntity as company WHERE companyName =:companyNameObj AND companyPassword =:companyPasswordObj");
			loginQuery.setParameter("companyNameObj", companyName);
			loginQuery.setParameter("companyPasswordObj", companyPasword);
			CompanyEntity company = (CompanyEntity) loginQuery.getSingleResult();
			return company;		
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, login(); FAILED");
		}
	}
	
	/**
	 * Sending a query to the DB to get if there is a company using that email for creation.
	 * @param companyEmail - String of that company email.
	 * @return true - Email in use by other company.
	 * 		   false - Email not in use by other company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyExistByEmail(String companyEmail) throws ApplicationException {
		
		try {
			Query verifyQUey = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyEmail = :companyEmailObj");
			verifyQUey.setParameter("companyEmailObj", companyEmail);			
			verifyQUey.getSingleResult();
			
			// If a company with email exist in DB, return true.
			return true;
		} catch (NoResultException e) {
		//	In case of no result exception, no company with this email exist, return false.
			return false;
		}
		catch (Exception e) {
		//	In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCompanyExistByEmail(); FAILED");
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
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyEmailExistForUpdate(Long companyID, String companyEmail) throws ApplicationException {

		try {
			Query verifyQUey = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyEmail = :companyEmailObj AND NOT companyId = :companuIdObj");
			verifyQUey.setParameter("companyEmailObj", companyEmail);		
			verifyQUey.setParameter("companuIdObj", companyID);	
			verifyQUey.getSingleResult();
			
			return true;
		} catch (NoResultException e) {
		//	In case of no result exception, no other company with this email exist, return false.
			return false;
		}	
		catch (Exception e) {
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponTitleUpdateAvailable(); FAILED");
		}
	}
	
	/**
	 * Sending a query to the DB to get if there is a company using that name for creation.
	 * @param companyName - String of that company name.
	 * @return true - Name in use by other company.
	 * 		   false - Name not in use by other company.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyExistByName(String companyName) throws ApplicationException {
		
		try {
			Query verifyQUey = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyName = :companyNameObj");
			verifyQUey.setParameter("companyNameObj", companyName);			
			verifyQUey.getSingleResult();
			
			// If a company with name exist in DB, return true.
			return true;
			
		}
		catch (NoResultException e) {
			//	In case of no result exception, no other company with this name exist, return false.
			return false;
		}	
		catch (Exception e) {
			e.printStackTrace();
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCompanyExistByName(); FAILED");
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
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean isCompanyNameExistForUpdate(long companyID, String companyName) throws ApplicationException {

		try {
			Query verifyQUey = entityManager.createQuery("SELECT company FROM CompanyEntity As company WHERE companyName = :companyNameObj AND NOT companyId = :companuIdObj");
			verifyQUey.setParameter("companyNameObj", companyName);		
			verifyQUey.setParameter("companuIdObj", companyID);	
			verifyQUey.getSingleResult();
			
			return true;
		}
		catch (NoResultException e) {
			//	In case of no result exception, no other company with this name exist, return false.
			return false;
		}
		
		catch (Exception e) {
//		In case of SQL exception it will be sent as a cause of an application exception to the exception handler.
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, isCouponTitleUpdateAvailable(); FAILED");
		}
	}
	

	
}