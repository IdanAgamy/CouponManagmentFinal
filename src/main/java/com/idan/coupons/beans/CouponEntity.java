package com.idan.coupons.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.idan.coupons.enums.CouponType;

@Entity
@Table(name="Coupon")
public class CouponEntity {

	@GeneratedValue
	@Id
	@Column(name="CouponID", nullable=false)
	private Long couponId;
	
	@Column(name="CouponTitle", nullable=false)
	private String couponTitle;
	
	@Column(name="CouponStartDate", nullable=false)
	private String couponStartDate; //yyyy-mm-dd
	
	@Column(name="CouponEndDate", nullable=false)
	private String couponEndDate;	//yyyy-mm-dd
	
	@Column(name="CouponAmount", nullable=false)
	private int couponAmount;
	
	@Column(name="CouponType", nullable=false)
	private CouponType couponType;
	
	@Column(name="CouponMessage", nullable=false)
	private String couponMessage;
	
	@Column(name="CouponPrice", nullable=false)
	private double couponPrice;
	
	@Column(name="CouponImage", nullable=true)
	private String couponImage;
	
	@Column(name="CompanyID", nullable=false)
	private Long companyID;
	
//	@ManyToMany(cascade=CascadeType.MERGE, mappedBy="purchase", fetch = FetchType.LAZY)
//	private List<CustomerEntity> purchasers;
	
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
	

//	public List<CustomerEntity> getPurchasers() {
//		return purchasers;
//	}
//	public void setPurchasers(List<CustomerEntity> purchasers) {
//		this.purchasers = purchasers;
//	}
	
	public CouponEntity() {
		super();
	}
	
	public CouponEntity(String couponTitle, String couponStartDate, String couponEndDate, int couponAmount,
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
	
	
	public CouponEntity(Long couponId, String couponTitle, String couponStartDate, String couponEndDate, int couponAmount,
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
		CouponEntity other = (CouponEntity) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		return true;
	}
	
//	public void addKids(CustomerEntity purcheser) {
//		this.purchasers.add(purcheser);
//	}

}
