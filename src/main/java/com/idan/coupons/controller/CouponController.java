package com.idan.coupons.controller;

import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.idan.coupons.beans.Coupon;
//import com.idan.coupons.dao.CompanyDao;
import com.idan.coupons.dao.CouponDao;
//import com.idan.coupons.dao.CustomerDao;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputErrorType;
//import com.idan.coupons.enums.OrderType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.ValidationUtils;

public class CouponController {
	
	//Assigning a local variable for each one of the 'dao' objects,
	//in order to gain access to the methods communicating with the DB.
	private CouponDao couponDao;
//	private CompanyDao companyDao;
//	private CustomerDao customerDao;
	
	public CouponController(){
		this.couponDao=new CouponDao();
//		this.companyDao=new CompanyDao();
//		this.customerDao=new CustomerDao();
	}
	
	/**
	 * Creating a coupon in the DB.
	 * @param coupon - the coupon as a Coupon object to add to the DB.
	 * @throws ApplicationException
	 */
	public void createCoupon(Coupon coupon)throws ApplicationException{
		//We validate the creation of a new coupon
		validateCreateCoupon(coupon);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.couponDao.createCoupon(coupon);
	}
	
	/**
	 * Getting information of a coupon.
	 * @param couponId - Long parameter represent the ID of the requested coupon.
	 * @return Coupon Object correspond to the provided ID.
	 * @throws ApplicationException
	 */
	public Coupon getCouponByCouponId(Long couponId) throws ApplicationException{
		if(couponId==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		Coupon coupon = this.couponDao.getCouponByCouponId(couponId);
		if (coupon == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon with ID " + couponId + ".");
		}
		
		return coupon;
	}
	
	/**
	 * Getting list of all coupons from DB.
	 * @return List collection of all the coupons in the coupon table.
	 * @throws ApplicationException
	 */
	public List<Coupon> getAllCoupons() throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getAllCoupons();
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons in data base.");
		}
		
		return coupons;
		
	}
	
	/**
	 * Removing coupon from coupon table.
	 * @param couponID - the couponID as a long to remove from the DB.
	 * @throws ApplicationException
	 */
	public void removeCouponByID(Long couponID) throws ApplicationException {
		
		if(couponID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		couponDao.removeCouponByID(couponID);
		
	}
	
	/**
	 * Removing a coupon-customer relation after customer cancelled a purchase.
	 * @param couponID - Long parameter of the coupon ID.
	 * @param customerID - Long parameter of the customer ID.
	 * @throws ApplicationException
	 */
	public void removeBoughtCouponByCouponIDandCustomerID(Long couponID, Long customerID) throws ApplicationException {
		if(couponID ==null || customerID == null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		couponDao.removeBoughtCouponByCouponIDandCustomerID(couponID, customerID);
		
		// Updating the amount of the coupon
		Coupon coupon = couponDao.getCouponByCouponId(couponID);
		int amount = coupon.getCouponAmount() + 1;
		coupon.setCouponAmount(amount);
		couponDao.updateCoupon(coupon);
	}
	
//	/**
//	 * 
//	 * @param customerID
//	 * @throws ApplicationException
//	 */
//	public void removeCustomerPurchasesByCustomerID(Long customerID) throws ApplicationException {
//		couponDao.removeCustomerPurchasesByCustomerID(customerID);
//	}
	
	/**
	 * Updating a coupon in the coupon table. All the fields will be updated according to the ID of the coupon object.
	 * @param coupon- the coupon as a Coupon object to be updated in the DB.
	 * @throws ApplicationException
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		
		//We validate the creation of a new coupon
		validateUpdateCoupon(coupon);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.couponDao.updateCoupon(coupon);
		
	}
	
	/**
	 * Getting a list of coupon from the DB of a certain type.
	 * @param type - type of coupon.
	 * @returnList collection of all the coupons in the coupon table of the requested type.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponByType(CouponType type) throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getCouponByType(type);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons with type " + type.toString() + ".");
		}
		
		return coupons;
		
	}
	
//	/**
//	 * 
//	 * @param orderType
//	 * @return
//	 * @throws ApplicationException
//	 */
//	public List<Coupon> getCouponInOrderByPrice(OrderType orderType) throws ApplicationException{
//		
//		List<Coupon> coupons = couponDao.getCouponInOrderByPrice(orderType);
//		
//		if(coupons.isEmpty()) {
//			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
//					+ " No coupons in data base.");
//		}
//		
//		return coupons;
//		
//	}
	
	/**
	 * Getting a list of coupon from the DB up to a certain price.
	 * @param price - Double parameter of the maximum wanted price for a coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested price.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsUpToPrice(Double price) throws ApplicationException{
		if(price==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		if (!ValidationUtils.isValidPrice(price)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Invalid price entered: " + price + ".");
		}
		
		List<Coupon> coupons = couponDao.getCouponsUpToPrice(price);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons below price of " + price + ".");
		}
		
		return coupons;
		
	}
	
	/**
	 * Getting a list of coupon from the DB up to a certain expiration date.
	 * @param endDate - String parameter of the latest end date of the coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested date.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsUpToEndDate(String endDate) throws ApplicationException{
		
		if(!ValidationUtils.isValidDateFormat(endDate)) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Invalid date format entered: " + endDate + ".");
		}
		
		List<Coupon> coupons = couponDao.getCouponsUpToEndDate(endDate);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons that end before " + DateUtils.ConvertForDisplay(endDate) + ".");
		}
		
		return coupons;
		
	}
	
	/**
	 * Getting a list of coupon from the DB from a certain company.
	 * @param companyID - Long parameter of the ID of the requested company.
	 * @return List collection of all the coupons in the coupon table issued by the requested company.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsByCompanyID(Long companyID) throws ApplicationException{
		if(companyID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		
		List<Coupon> coupons = couponDao.getCouponsByCompanyID(companyID);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons of asked company in database in data base.");
		}
		
		return coupons;
		
	}
	
	/**
	 * Getting a list of coupon from the DB purchased from a certain customer.
	 * @param customerID - Long parameter of the ID of the requested customer.
	 * @return List collection of all the coupons in the coupon table bought by the requested customer.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsByCustomerID(Long customerID) throws ApplicationException{
		if(customerID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value.");
		}
		List<Coupon> coupons = couponDao.getCouponsByCustomerID(customerID);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons bought by customer.");
		}
		
		return coupons;
		
	}
	
	/**
	 * Adding a coupon-customer relation after customer bought a coupon.
	 * @param customerID - Long parameter of the customer ID.
	 * @param couponID - Long parameter of the coupon ID.
	 * @throws ApplicationException
	 */
	public void buyCoupon(Long customerID, Long couponID) throws ApplicationException {
		if(couponID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value of couponID.");
		}
		
		if(customerID==null) {
			throw new ApplicationException(ErrorType.BAD_INPUT, DateUtils.getCurrentDateAndTime()
					+"  Bad input inserted, null value of customerID.");
		}
		Coupon couponToBuy = this.couponDao.getCouponByCouponId(couponID);
		
		if(couponToBuy==null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon exist.");
		}
		
		int couponAmount = couponToBuy.getCouponAmount();
		
		// Checking if there are remaining amount for coupon.
		if(couponAmount <= 0) {
			
			throw new ApplicationException(ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" Coupon out of stock.");
		}
		
		if(this.couponDao.isCouponAlreadyPurchasedByCustomerID(couponID,customerID)) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" Coupon already purchesed.");
		}
		
		this.couponDao.buyCoupon(customerID, couponID);
		
		// After purchase the amount of the coupon is updated in the DB
		couponToBuy.setCouponAmount(--couponAmount);
		this.couponDao.updateCoupon(couponToBuy);
		
		
	}
	
//	public int getCouponLeftInStockByCouponID(long couponID) throws ApplicationException {
//		
//		return this.couponDao.getCouponAmountByCouponID(couponID) - this.couponDao.getBoughtCouponCountByCouponID(couponID);
//	}
		
	/**
	 * Deleting expired coupon from DB.
	 * @throws ApplicationException
	 */
	public void deleteExpiredCoupon() throws ApplicationException {

		// Getting today's date.
		GregorianCalendar today = new GregorianCalendar();

		String todayStr = DateUtils.dateToStrConverter(today);
		
		couponDao.removeCouponByEndDate(todayStr);
		
	}

	/**
	 * Validating creation of coupon. If invalid ApplicationException will be thrown.
	 * @param coupon - Coupon object to be validated.
	 * @throws ApplicationException
	 */
	private void validateCreateCoupon(Coupon coupon) throws ApplicationException{
		
		validateCoupon(coupon);
		
		//We check if the coupon's name is already exist in the DB
		if (this.couponDao.isCouponExistByTitle(coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create coupon has failed."
					+"\nThe user attempted to create a new coupon using a name that is already exists."
					+"\nCoupon Name = "+coupon.getCouponTitle());
		}
		
	}

	/**
	 * Validating update of coupon. If invalid ApplicationException will be thrown.
	 * @param coupon - Coupon object to be validated.
	 * @throws ApplicationException
	 */
	private void validateUpdateCoupon(Coupon coupon) throws ApplicationException {
		validateCoupon(coupon);
		
		//We check if the change to coupon's name is already exist in the DB
		if (this.couponDao.isCouponTitleExistForUpdate(coupon.getCouponId() ,coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Update coupon has failed."
					+"\nThe user attempted to update coupon using a name that is already exists."
					+"\nCoupon Name = "+coupon.getCouponTitle());
		}
		
	}

	/**
	 * Validating parameters of a coupon. In case of invalid parameter, ApplicationException will be thrown.
	 * @param company - company object to validate.
	 * @throws ApplicationException
	 */
	private void validateCoupon(Coupon coupon) throws ApplicationException {
		
		List<InputErrorType> errorTypes = new ArrayList<>();
		boolean isDateValide = true;
		
		if(!ValidationUtils.isValidNameFormat(coupon.getCouponTitle())) {
			
			errorTypes.add(InputErrorType.INVALID_NAME);
		}
		
		if(!ValidationUtils.isValidDateFormat(coupon.getCouponStartDate())) {
			
			errorTypes.add(InputErrorType.INVALID_START_DATE);
			isDateValide = false;
		}
		
		if(!ValidationUtils.isValidDateFormat(coupon.getCouponEndDate())) {
			
			errorTypes.add(InputErrorType.INVALID_END_DATE);
			isDateValide = false;
		}
		
		if(!ValidationUtils.isValidAmount(coupon.getCouponAmount())){
			errorTypes.add(InputErrorType.INVALID_AMOUNT);
		}
		
		if(!ValidationUtils.isValidMessageFormat(coupon.getCouponMessage())){
			errorTypes.add(InputErrorType.INVALID_MESSAGE);
		}
		
		if(!ValidationUtils.isValidPrice(coupon.getCouponPrice())) {
			errorTypes.add(InputErrorType.INVALID_PRICE);
		}
		
		if(isDateValide && ValidationUtils.isStartEndDateMiss(coupon.getCouponStartDate(), coupon.getCouponEndDate())) {
			errorTypes.add(InputErrorType.INVALID_END_BEFORE_START);
		}
		
		if(isDateValide && ValidationUtils.isStartDateAlreadyPassed(coupon.getCouponStartDate())) {
			errorTypes.add(InputErrorType.INVALID_START_ALREADY_PASSED);
		}
		
		if(!errorTypes.isEmpty()) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, DateUtils.getCurrentDateAndTime()
					+" Create coupon has failed."
					+"\nOne or more of the fields are incorrect.", errorTypes);
		}
	}
		
}
