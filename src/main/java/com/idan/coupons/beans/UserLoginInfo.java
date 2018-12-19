package com.idan.coupons.beans;



import com.idan.coupons.enums.UserType;

public class UserLoginInfo {
	
	private Long userID;
	private String name;
	private String email;
	private String password;
	private UserType userType;
	
	public UserLoginInfo() {
		super();
	}
	public UserLoginInfo(String name, String email, String password, UserType userType) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	@Override
	public String toString() {
		return "UserLoginInfo [email=" + email + ", password=" + password + ", userType=" + userType + "]";
	}
	
	

}
