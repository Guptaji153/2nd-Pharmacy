package com.infinite.jsf.pharmacy.dao;

import com.infinite.jsf.pharmacy.model.Pharmacy;

public interface PharmacyDao {

	/**
     * Add/ Register new pharmacy 
     * save otp
     * sending otp vie email
     *  */
	String addPharmacy(Pharmacy pharmacy);
	
	 /**
     *  validating otp if valid and not expired
     *  mark as VERIFIED and redirect to reset password
     *  */
	//String generatePassword(String email , int otp);
	//public boolean validateTempPassword(String email, String password);
	
	/**
	 * validate login using permanent password
	 *  */
	boolean validatePassword(String email, String password);
	
	/**
	 * update pharmacy password (means) create custom password 
	 * [first time during registration]
	 * also saving in another password table so that to be used by others
	 * deleting used OTP(for safety purpose)
	 *  */
	//String updatePassword(String email, String pwd);
	
	 /**
     * login vie OTP
     * this method send OTP to entered email id 
     *  */
	public String sendLoginOtp(String email);
	/**
     * Verify the user entered OTP with actual OTP code
     *  */
	
	public String verifyLoginOtp(String email, String otpCode);
	/**
	 * update password while OTP login
	 * 
	 *  */
	public String updatePasswordByOtpLogin(String email, String pwd, String tempPassword);
	
}


