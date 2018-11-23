package com.idan.coupons.beans;

public class Customer{
	
	private Long customerId;
	private String customerName;
	private String customerPassword;
	private String customerEmail;
	
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
	public Customer() {
	}
	public Customer(String customerName, String customerPassword, String customerEmail) {
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}
	public Customer(Long customerId, String customerName, String customerPassword, String customerEmail) {
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
	
	public String toJson() {
		String JsonStr = "{ \"customerId\": "+customerId+", \"customerName\": \""+customerName+"\", \"customerPassword\":\""+customerPassword+"\", \"customerEmail\":\"" + customerEmail + "\" }";
		return JsonStr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerEmail == null) ? 0 : customerEmail.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((customerPassword == null) ? 0 : customerPassword.hashCode());
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
		Customer other = (Customer) obj;
		if (customerEmail == null) {
			if (other.customerEmail != null)
				return false;
		} else if (!customerEmail.equals(other.customerEmail))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerPassword == null) {
			if (other.customerPassword != null)
				return false;
		} else if (!customerPassword.equals(other.customerPassword))
			return false;
		return true;
	}
	
	
	

}
