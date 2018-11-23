package com.idan.coupons.beans;


import com.idan.coupons.enums.CouponType;


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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result + couponAmount;
		result = prime * result + ((couponEndDate == null) ? 0 : couponEndDate.hashCode());
		result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
		result = prime * result + ((couponImage == null) ? 0 : couponImage.hashCode());
		result = prime * result + ((couponMessage == null) ? 0 : couponMessage.hashCode());
		long temp;
		temp = Double.doubleToLongBits(couponPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((couponStartDate == null) ? 0 : couponStartDate.hashCode());
		result = prime * result + ((couponTitle == null) ? 0 : couponTitle.hashCode());
		result = prime * result + ((couponType == null) ? 0 : couponType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (couponAmount != other.couponAmount)
			return false;
		if (couponEndDate == null) {
			if (other.couponEndDate != null)
				return false;
		} else if (!couponEndDate.equals(other.couponEndDate))
			return false;
		if (couponId == null) {
			if (other.couponId != null)
				return false;
		} else if (!couponId.equals(other.couponId))
			return false;
		if (couponImage == null) {
			if (other.couponImage != null)
				return false;
		} else if (!couponImage.equals(other.couponImage))
			return false;
		if (couponMessage == null) {
			if (other.couponMessage != null)
				return false;
		} else if (!couponMessage.equals(other.couponMessage))
			return false;
		if (Double.doubleToLongBits(couponPrice) != Double.doubleToLongBits(other.couponPrice))
			return false;
		if (couponStartDate == null) {
			if (other.couponStartDate != null)
				return false;
		} else if (!couponStartDate.equals(other.couponStartDate))
			return false;
		if (couponTitle == null) {
			if (other.couponTitle != null)
				return false;
		} else if (!couponTitle.equals(other.couponTitle))
			return false;
		if (couponType != other.couponType)
			return false;
		return true;
	}

	
	
	

}
