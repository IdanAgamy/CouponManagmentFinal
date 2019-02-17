package com.idan.coupons.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="customer")
public class CustomerEntity {

	@GeneratedValue
	@Id
	@Column(name="CustomerID", nullable=false)
	private Long customerId;
	
	@Column(name="CustomerName", nullable=false)
	private String customerName;

	@Column(name="CustomerPassword", nullable=false)
	private String customerPassword;

	@Column(name="CustomerEmail", nullable=false)
	private String customerEmail;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "purchasers")
	@JsonIgnore
	private List<CouponEntity> purchases;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long id) {
		this.customerId = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String password) {
		this.customerPassword = password;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public List<CouponEntity> getPurchases() {
		return purchases;
	}
	public void setPurchases(List<CouponEntity> purchases) {
		this.purchases = purchases;
	}
	public CustomerEntity() {
	}
	public CustomerEntity(String customerName, String customerPassword, String customerEmail) {
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}
	public CustomerEntity(Long customerId, String customerName, String customerPassword, String customerEmail) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerPassword="
				+ customerPassword + ", customerEmail=" + customerEmail + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
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
		CustomerEntity other = (CustomerEntity) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}

	

}
