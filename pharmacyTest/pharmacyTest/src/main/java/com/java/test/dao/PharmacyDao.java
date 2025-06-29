package com.java.test.dao;

import com.java.test.model.Pharmacy;

public interface PharmacyDao {

	String addPharmacy(Pharmacy pharmacy);
	
	String generatePassword(String email , int otp);
	
	boolean validatePassword(String email, String password);
	
	String updatePassword(String email, String pwd);
	public boolean validateTempPassword(String email, String password);
}

