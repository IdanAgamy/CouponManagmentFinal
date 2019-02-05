package com.idan.coupons.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class CompanyEntity {

	@GeneratedValue
	@Id
	@Column(name="CompanyID", nullable=false)
	private Long companyId;
	
	@Column(name="CompanyName", nullable=false)
	private String companyName;

	@Column(name="CompanyPassword", nullable=false)
	private String companyPassword ;

	@Column(name="CompanyEmail", nullable=false)
	private String companyEmail;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyPassword() {
		return companyPassword;
	}
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public CompanyEntity() {
		super();
	}
	public CompanyEntity(String companyName, String companyPassword, String companyEmail) {
		super();
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
	}
	public CompanyEntity(Long companyId, String companyName, String companyPassword, String companyEmail) {
		this( companyName,  companyPassword,  companyEmail);
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", companyPassword="
				+ companyPassword + ", companyEmail=" + companyEmail + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
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
		CompanyEntity other = (CompanyEntity) obj;
		if (companyId != other.companyId)
			return false;
		return true;
	}
	
	



}
