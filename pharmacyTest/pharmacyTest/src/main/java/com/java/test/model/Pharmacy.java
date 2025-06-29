package com.java.test.model;

import java.sql.Timestamp;

public class Pharmacy {

	private String pharmacyId;
	private Timestamp createdAt;
	private String pharmacyName;
	private String contactNo;
	private String email;
	private String password;
	private String state;
	private String city;
	private String licenseNo;
	private String status;

	private String gstNo;
	private String pinCode;
	private String addressLine1;
	  private String firstName;
	    private String middleName;
	    private String lastName;
	    private String gender;
	    private String ownerMobile;
	    private String ownerEmail;
	    private String aadhar;
	    private String ownerAddress;
		
	    
	    
		@Override
		public String toString() {
			return "Pharmacy [pharmacyId=" + pharmacyId + ", createdAt=" + createdAt + ", pharmacyName=" + pharmacyName
					+ ", contactNo=" + contactNo + ", email=" + email + ", password=" + password + ", state=" + state
					+ ", city=" + city + ", licenseNo=" + licenseNo + ", status=" + status + ", gstNo=" + gstNo
					+ ", pinCode=" + pinCode + ", addressLine1=" + addressLine1 + ", firstName=" + firstName
					+ ", middleName=" + middleName + ", lastName=" + lastName + ", gender=" + gender + ", ownerMobile="
					+ ownerMobile + ", ownerEmail=" + ownerEmail + ", aadhar=" + aadhar + ", ownerAddress="
					+ ownerAddress + "]";
		}
		public String getPharmacyId() {
			return pharmacyId;
		}
		public void setPharmacyId(String pharmacyId) {
			this.pharmacyId = pharmacyId;
		}
		public Timestamp getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
		}
		public String getPharmacyName() {
			return pharmacyName;
		}
		public void setPharmacyName(String pharmacyName) {
			this.pharmacyName = pharmacyName;
		}
		public String getContactNo() {
			return contactNo;
		}
		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
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
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getLicenseNo() {
			return licenseNo;
		}
		public void setLicenseNo(String licenseNo) {
			this.licenseNo = licenseNo;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getGstNo() {
			return gstNo;
		}
		public void setGstNo(String gstNo) {
			this.gstNo = gstNo;
		}
		public String getPinCode() {
			return pinCode;
		}
		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}
		public String getAddressLine1() {
			return addressLine1;
		}
		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getOwnerMobile() {
			return ownerMobile;
		}
		public void setOwnerMobile(String ownerMobile) {
			this.ownerMobile = ownerMobile;
		}
		public String getOwnerEmail() {
			return ownerEmail;
		}
		public void setOwnerEmail(String ownerEmail) {
			this.ownerEmail = ownerEmail;
		}
		public String getAadhar() {
			return aadhar;
		}
		public void setAadhar(String aadhar) {
			this.aadhar = aadhar;
		}
		public String getOwnerAddress() {
			return ownerAddress;
		}
		public void setOwnerAddress(String ownerAddress) {
			this.ownerAddress = ownerAddress;
		}
		public Pharmacy(String pharmacyId, Timestamp createdAt, String pharmacyName, String contactNo, String email,
				String password, String state, String city, String licenseNo, String status,
				String gstNo, String pinCode, String addressLine1, String firstName, String middleName, String lastName,
				String gender, String ownerMobile, String ownerEmail, String aadhar, String ownerAddress) {
			super();
			this.pharmacyId = pharmacyId;
			this.createdAt = createdAt;
			this.pharmacyName = pharmacyName;
			this.contactNo = contactNo;
			this.email = email;
			this.password = password;
			this.state = state;
			this.city = city;
			this.licenseNo = licenseNo;
			this.status = status;
			
			this.gstNo = gstNo;
			this.pinCode = pinCode;
			this.addressLine1 = addressLine1;
			this.firstName = firstName;
			this.middleName = middleName;
			this.lastName = lastName;
			this.gender = gender;
			this.ownerMobile = ownerMobile;
			this.ownerEmail = ownerEmail;
			this.aadhar = aadhar;
			this.ownerAddress = ownerAddress;
		}
		public Pharmacy() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
		
	
		
}
