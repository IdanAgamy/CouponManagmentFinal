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

import com.idan.coupons.beans.Coupon;
import com.idan.coupons.controller.CouponController;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.enums.ErrorType;
//import com.idan.coupons.enums.OrderType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.DateUtils;
import com.idan.coupons.utils.ValidationUtils;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/coupons")
public class CouponApi {

	@Autowired
	CouponController couponController;

	/**
	 * Getting list of all coupons from DB.
	 * @return List collection of all the coupons in the coupon table.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons
	public List<Coupon> getAllCoupons() throws ApplicationException {
		List<Coupon> coupons = couponController.getAllCoupons();
		//System.out.println(coupons);
		return coupons;
	}

	/**
	 * Getting information of a coupon.
	 * @param couponId - Long parameter represent the ID of the requested coupon.
	 * @return Coupon Object correspond to the provided ID.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{couponId}", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/5
	public Coupon getCouponByCouponId(@PathVariable("couponId") long couponId) throws ApplicationException {
		//System.out.println(couponId);
		//System.out.println(couponController.getCouponByCouponId(couponId));
		return couponController.getCouponByCouponId(couponId);
	}

	/**
	 * Creating a coupon in the DB.
	 * @param coupon - the coupon as a Coupon object to add to the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.POST)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		//		System.out.println(coupon);
		couponController.createCoupon(coupon);
	}

	/**
	 * Updating a coupon in the coupon table. All the fields will be updated according to the ID of the coupon object.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param coupon - the coupon as a Coupon object to be updated in the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.PUT)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons
	public void updateCoupon(HttpServletRequest request,@RequestBody Coupon coupon) throws ApplicationException {
		//		System.out.println(coupon);
		// Will update the company in the DB only if the changes are made by the admin or the same company.
		Long companyID = coupon.getCompanyID();
		ValidationUtils.ValidateUser(request, companyID);
		couponController.updateCoupon(coupon);
	}

	/**
	 * Removing coupon from coupon table.
	 * @param request - an HttpServletRequest object, for validating use.
	 * @param couponId - the couponID as a long to remove from the DB.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{couponId}", method = RequestMethod.DELETE)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/?
	public void removeCouponByID(HttpServletRequest request, @PathVariable("couponId") long couponId) throws ApplicationException {
		// Will update the company in the DB only if the changes are made by the admin or the same company.
		Coupon coupon = couponController.getCouponByCouponId(couponId);
		ValidationUtils.ValidateUser(request, coupon.getCompanyID());
		
		couponController.removeCouponByID(couponId);
	}

	/**
	 * Adding a coupon-customer relation after customer bought a coupon.
	 * @param request - an HttpServletRequest object, for retrieving the customer data.
	 * @param couponID - Long parameter of the coupon ID.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{couponId}/buyCoupon", method = RequestMethod.POST)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/3/buyCoupon
	public void buyCoupon(HttpServletRequest request, @PathVariable("couponId") Long couponID) throws ApplicationException{

		String customerIDStr = (String) request.getAttribute("userID");
		if(customerIDStr != null) {
			Long customerID = Long.valueOf(customerIDStr);
			couponController.buyCoupon(customerID, couponID);
		}
		else {
			throw new ApplicationException(ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime()
					+" System error, problem with cookies.");
		}
	}

	/**
	 * Removing a coupon-customer relation after customer cancelled a purchase.
	 * @param request - an HttpServletRequest object, for retrieving the customer data.
	 * @param couponID - Long parameter of the coupon ID.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{couponId}/removeBoughtCoupon", method = RequestMethod.DELETE)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/3/removeBoughtCoupon
	public void removeBoughtCoupon(HttpServletRequest request, @PathVariable("couponId") Long couponID) throws ApplicationException {

//		String customerIDStr = (String) request.getAttribute("userID");
//		if(customerIDStr != null) {
//			Long customerID = Long.valueOf(customerIDStr);
//			couponController.removeBoughtCouponByCouponIDandCustomerID(couponID, customerID);
//		}
//		else {
//			throw new ApplicationException(ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime()
//					+" System error, problem with cookies.");
//		}
		
		couponController.removeBoughtCouponByCouponIDandCustomerID(couponID, 2L);
	}

	/**
	 * Getting a list of coupon from the DB of a certain type.
	 * @param couponType - type of coupon.
	 * @return List collection of all the coupons in the coupon table of the requested type.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/byCouponType", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/byCouponType?couponType=Food
	public List<Coupon> getCouponByType(@RequestParam("couponType") CouponType couponType) throws ApplicationException{
		//System.out.println(couponController.getCouponByType(couponType));
		return couponController.getCouponByType(couponType);
	}

	//	@GET
	//	@Path("/priceByOrderType")
	//	//http://localhost:8080/CouponManagmentSystemVer3/coupons/priceByOrderType?orderType=ASCENDING
	//	public List<Coupon> getCouponInOrderByPrice(@QueryParam("orderType") OrderType orderType) throws ApplicationException{
	//		//		System.out.println(couponController.getCouponInOrderByPrice(orderType));
	//		return couponController.getCouponInOrderByPrice(orderType);
	//	}
	/**
	 * Getting a list of coupon from the DB up to a certain price.
	 * @param price - Double parameter of the maximum wanted price for a coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested price.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/upToPrice", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/upToPrice?price=200
	public List<Coupon> getCouponsUpToPrice(@RequestParam("price") double price) throws ApplicationException{
		//		System.out.println(couponController.getCouponsUpToPrice(price));
		return couponController.getCouponsUpToPrice(price);
	}

	/**
	 * Getting a list of coupon from the DB up to a certain expiration date.
	 * @param endDate - String parameter of the latest end date of the coupon.
	 * @return List collection of all the coupons in the coupon table up to the requested date.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/upToEndDate", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/upToEndDate?endDate=2019-12-12
	public List<Coupon> getCouponsUpToEndDate(@RequestParam("endDate") String endDate) throws ApplicationException{
		//		System.out.println(couponController.getCouponsUpToEndDate(endDate));
		return couponController.getCouponsUpToEndDate(endDate);
	}

	/**
	 * Getting a list of coupon from the DB from a certain company.
	 * @param companyID - Long parameter of the ID of the requested company.
	 * @return List collection of all the coupons in the coupon table issued by the requested company.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/byCompanyID", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/byCompanyID?companyID=4
	public List<Coupon> getCouponsByCompanyID(@RequestParam("companyID") Long companyID) throws ApplicationException{
		//		System.out.println(couponController.getCouponsByCompanyID(companyID));
		return couponController.getCouponsByCompanyID(companyID);
	}

	/**
	 * Getting a list of coupon from the DB purchased from a certain customer.
	 * @param request - an HttpServletRequest object, for retrieving the customer data.
	 * @return List collection of all the coupons in the coupon table bought by the requested customer.
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/purchasedCoupons", method = RequestMethod.GET)
	//http://localhost:8080/CouponManagmentSystemVer3/coupons/purchasedCoupons
	public List<Coupon> getCouponsByCustomerID(HttpServletRequest request) throws ApplicationException{
		
		String customerIDStr = (String) request.getAttribute("userID");
		if(customerIDStr != null) {
			Long customerID = Long.valueOf(customerIDStr);
			return couponController.getCouponsByCustomerID(customerID);
		}
		else {
			throw new ApplicationException(ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime()
					+" System error, problem with cookies.");
		}
	}



	/*
	 * Content-Type      application/json
	 *  Json object
	 {
		"couponId": ?,
		"couponTitle": "a",
		"couponStartDate": "2000-10-10",
		"couponEndDate": "2000-10-11",
		"couponAmount": 2,
		"couponType": "Food",
		"couponMessage": "a",
		"couponPrice": 2.2,
		"couponImage": "a",
		"companyID": 4
	}
	 */


}
