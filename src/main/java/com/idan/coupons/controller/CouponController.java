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
import com.idan.coupons.enums.OrderType;
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
	
	public void createCoupon(Coupon coupon)throws ApplicationException{
		//We validate the creation of a new coupon
		validateCreateCoupon(coupon);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.couponDao.createCoupon(coupon);
	}
	
	public Coupon getCouponByCouponId(Long couponId) throws ApplicationException{
		Coupon coupon = this.couponDao.getCouponByCouponId(couponId);
		if (coupon == null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon with ID" + couponId + ".");
		}
		
		return coupon;
	}
	
	public List<Coupon> getAllCoupons() throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getAllCoupons();
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons in data base.");
		}
		
		return coupons;
		
	}
	
	public void removeCouponByID(Long couponID) throws ApplicationException {
		
//		couponDao.removeBoughtCouponByID(couponID);
		couponDao.removeCouponByID(couponID);
		
	}
	
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		
		//We validate the creation of a new coupon
		validateUpdateCoupon(coupon);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		this.couponDao.updateCoupon(coupon);
		
	}
	
	public List<Coupon> getCouponByType(CouponType type) throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getCouponByType(type);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons with type " + type.toString() + ".");
		}
		
		return coupons;
		
	}
	
	public List<Coupon> getCouponInOrderByPrice(OrderType orderType) throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getCouponInOrderByPrice(orderType);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+ " No coupons in data base.");
		}
		
		return coupons;
		
	}
	
	public List<Coupon> getCouponsUpToPrice(double price) throws ApplicationException{
		
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
	
	public List<Coupon> getCouponsByCompanyID(Long companyID) throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getCouponsByCompanyID(companyID);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons of asked company in database in data base.");
		}
		
		return coupons;
		
	}
	
	public List<Coupon> getCouponsByCustomerID(Long customerID) throws ApplicationException{
		
		List<Coupon> coupons = couponDao.getCouponsByCustomerID(customerID);
		
		if(coupons.isEmpty()) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupons bought by customer.");
		}
		
		return coupons;
		
	}
	
	public void buyCoupon(Long customerID, Long couponID) throws ApplicationException {
		
		Coupon couponToBuy = this.couponDao.getCouponByCouponId(couponID);
		
		if(couponToBuy==null) {
			throw new ApplicationException(ErrorType.NO_RETURN_OBJECT, DateUtils.getCurrentDateAndTime()
					+" No coupon exist.");
		}
		
		int couponAmount = couponToBuy.getCouponAmount();
		
		if(couponAmount <= 0) {
			
			throw new ApplicationException(ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" Coupon out of stock.");
		}
		
		if(this.couponDao.isCouponAlreadyPurchasedByCustomerID(couponID,customerID)) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" Coupon already purchesed.");
		}
		
		this.couponDao.buyCoupon(customerID, couponID);
		
		couponToBuy.setCouponAmount(--couponAmount);
		this.couponDao.updateCoupon(couponToBuy);
		
		
	}
	
//	public int getCouponLeftInStockByCouponID(long couponID) throws ApplicationException {
//		
//		return this.couponDao.getCouponAmountByCouponID(couponID) - this.couponDao.getBoughtCouponCountByCouponID(couponID);
//	}
		
	public void deleteExpiredCoupon() throws ApplicationException {

		GregorianCalendar today = new GregorianCalendar();

		String todayStr = DateUtils.dateToStrConverter(today);
		
		couponDao.removeCouponByEndDate(todayStr);
		
	}


	private void validateCreateCoupon(Coupon coupon) throws ApplicationException{
		
		validateCoupon(coupon);
		
		//We check if the coupon's name is already exist in the DB
		if (this.couponDao.isCouponExistByTitle(coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Create coupon has failed."
					+"\nThe user attempted to create a new coupon using a name that is already exists."
					+"\nCoupon Name="+coupon.getCouponTitle());
		}
		
	}

	private void validateUpdateCoupon(Coupon coupon) throws ApplicationException {
		validateCoupon(coupon);
		
		//We check if the change to coupon's name is already exist in the DB
		if (this.couponDao.isCouponTitleExistForUpdate(coupon.getCouponId() ,coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+" Update coupon has failed."
					+"\nThe user attempted to update coupon using a name that is already exists."
					+"\nCoupon Name="+coupon.getCouponTitle());
		}
		
	}

	private void validateCoupon(Coupon coupon) throws ApplicationException {
		
		List<InputErrorType> errorTypes = new ArrayList<InputErrorType>();
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
