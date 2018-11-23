package com.idan.coupons.beans;


import javax.xml.bind.annotation.XmlRootElement;

import com.idan.coupons.enums.CouponType;

@XmlRootElement
public class Coupon{
	
	private Long couponId;
	private String couponTitle;
	private String couponStartDate; //yyyy-mm-dd
	private String couponEndDate;	//yyyy-mm-dd
	private int couponAmount;
	private CouponType couponType;
	private String couponMessage;
	private double couponPrice;
	private String couponImage;
	private Long companyID;
	
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public String getCouponTitle() {
		return couponTitle;
	}
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}
	public String getCouponStartDate() {
		return couponStartDate;
	}
	public void setCouponStartDate(String couponStartDate) {
		this.couponStartDate = couponStartDate;

	}
	public String getCouponEndDate() {
		return couponEndDate;
	}
	public void setCouponEndDate(String couponEndDate) {
		
		this.couponEndDate = couponEndDate;
		
	}
	public int getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(int couponAmount) {
		this.couponAmount = couponAmount;
	}
	public CouponType getCouponType() {
		return couponType;
	}
	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}
	public String getCouponMessage() {
		return couponMessage;
	}
	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}
	public double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getCouponImage() {
		return couponImage;
	}
	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}
	
	public Long getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}
	
	public Coupon() {
		super();
	}
	
	
	
	public Coupon(String couponTitle, String couponStartDate, String couponEndDate, int couponAmount,
			CouponType couponType, String couponMessage, double couponPrice, String couponImage, Long companyID) {
		super();
		this.couponTitle = couponTitle;
		this.couponStartDate = couponStartDate;
		this.couponEndDate = couponEndDate;
		this.couponAmount = couponAmount;
		this.couponType = couponType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
		this.companyID = companyID;
	}
	
	
	public Coupon(Long couponId, String couponTitle, String couponStartDate, String couponEndDate, int couponAmount,
			CouponType couponType, String couponMessage, double couponPrice, String couponImage, Long companyID) {
		super();
		this.couponId = couponId;
		this.couponTitle = couponTitle;
		this.couponStartDate = couponStartDate;
		this.couponEndDate = couponEndDate;
		this.couponAmount = couponAmount;
		this.couponType = couponType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
		this.companyID = companyID;
	}
	
	
	
	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", couponTitle=" + couponTitle + ", couponStartDate=" + couponStartDate
				+ ", couponEndDate=" + couponEndDate + ", couponAmount=" + couponAmount + ", couponType=" + couponType
				+ ", couponMessage=" + couponMessage + ", couponPrice=" + couponPrice + ", couponImage=" + couponImage
				+ ", companyID=" + companyID + "]";
	}
	/*
	 *  This method checks if the date is in the correct date format "YYYY-MM-DD"
	 */
	
	

}
