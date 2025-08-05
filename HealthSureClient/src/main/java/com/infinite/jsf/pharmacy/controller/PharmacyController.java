package com.infinite.jsf.pharmacy.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.infinite.jsf.pharmacy.daoImpl.PharmacyDaoImpl;
import com.infinite.jsf.pharmacy.model.Pharmacy;
import com.infinite.jsf.pharmacy.model.PharmacyOtp;

import java.sql.Timestamp;
import java.util.regex.Pattern;

public class PharmacyController {

	private PharmacyDaoImpl pharmacyDao;
	private Pharmacy pharmacy;
	private String confirmPassword;
	private PharmacyOtp pharmacyOtp;
	public PharmacyDaoImpl getPharmacyDao() {
		return pharmacyDao;
	}

	public PharmacyOtp getPharmacyOtp() {
		return pharmacyOtp;
	}

	public void setPharmacyOtp(PharmacyOtp pharmacyOtp) {
		this.pharmacyOtp = pharmacyOtp;
	}

	public void setPharmacyDao(PharmacyDaoImpl pharmacyDao) {
		this.pharmacyDao = pharmacyDao;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * pharmacy owner registration
	 */
	public String proceedToPharmacyDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		if (pharmacy.getFirstName() == null || pharmacy.getFirstName().trim().isEmpty()) {
			context.addMessage("form:firstName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "First name is required", null));
			isValid = false;
		}
		if (!isAlphabetic(pharmacy.getFirstName().trim())) {
			context.addMessage("form:firstName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Special symbol is not allowed", null));
			isValid = false;
		}

		if (pharmacy.getLastName() == null || pharmacy.getLastName().trim().isEmpty()) {
			context.addMessage("form:lastName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Last name is required", null));
			isValid = false;
		}
		if (!isAlphabetic(pharmacy.getLastName().trim())) {
			context.addMessage("form:lastName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Special symbol is not allowed", null));
			isValid = false;
		}

		if (pharmacy.getGender() == null || pharmacy.getGender().trim().isEmpty()) {
			context.addMessage("form:gender",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gender is required.", null));
			isValid = false;
		}

		if (pharmacyDao.isOwnerMobileExist(pharmacy.getOwnerMobile())) {
			context.addMessage("form:ownerMobile",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Owner Mobile already registered.", null));
			isValid = false;
		}
		if (pharmacy.getOwnerMobile() == null || pharmacy.getOwnerMobile().trim().isEmpty()) {
			context.addMessage("form:ownerMobile",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mobile number is required", null));
			isValid = false;
		}
		if (!pharmacy.getOwnerMobile().matches("[1-9]\\d{9}")) {
			context.addMessage("form:ownerMobile",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mobile number must be 10 digits.", null));
			isValid = false;
		}

		if (pharmacy.getOwnerAddress() == null || pharmacy.getOwnerAddress().trim().isEmpty()) {
			context.addMessage("form:ownerAddress",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Owner address is required.", null));
			isValid = false;
		}
		if (pharmacy.getAadhar() == null || pharmacy.getAadhar().trim().isEmpty()) {
			context.addMessage("form:aadharNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aadhar is required.", null));
			isValid = false;
		}

		if (!pharmacy.getAadhar().matches("[2-9]\\d{3}-\\d{4}-\\d{4}")) {
			context.addMessage("form:aadharNo", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Aadhar must be in the format XXXX-XXXX-XXXX. & can't start with 0 or 1", null));
			isValid = false;
		}

		if (pharmacyDao.isAadharExists(pharmacy.getAadhar())) {
			context.addMessage("form:aadharNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aadhar already registered.", null));
			isValid = false;
		}
		if (pharmacy.getOwnerEmail() == null || pharmacy.getOwnerEmail().trim().isEmpty()) {
			context.addMessage("form:ownerEmail",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Owner email is required.", null));
			isValid = false;
		}
		if (!isValidEmail(pharmacy.getOwnerEmail())) {
			context.addMessage("form:ownerEmail",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid owner email.", null));
			isValid = false;
		}

		if (pharmacyDao.isOwnerEmailExist(pharmacy.getOwnerEmail())) {
			context.addMessage("form:ownerEmail",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Owner email allready register registered.", null));
			isValid = false;
		}

		if (!isValid) {
			context.validationFailed();
			return null;
		}

		return "AddPharmacy.jsf?faces-redirect=true";
	}

	/**
	 * pharmacy registration...
	 */
	public String registerPharmacy() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		if (pharmacy.getPharmacyName() == null || pharmacy.getPharmacyName().trim().isEmpty()) {
			context.addMessage("form:pharmacyName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pharmacy name is required.", null));
			isValid = false;
		}
		if (pharmacy.getPharmacyName().length() < 2) {
			context.addMessage("form:pharmacyName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pharmacy Name must be greater than 2 characters.", null));
			isValid = false;
		}
		if (pharmacy.getLicenseNo() == null || pharmacy.getLicenseNo().trim().isEmpty()) {
			context.addMessage("form:licenseNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "License Number is required.", null));
			isValid = false;
		}
		if ((!pharmacy.getLicenseNo().equals(pharmacy.getLicenseNo().toUpperCase()))) {
			context.addMessage("form:licenseNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "License number must be in upper case ", null));
			isValid = false;
		}
		if (!pharmacy.getLicenseNo().matches("[A-Z]{2}/[0-9]{4}/[0-9]{5}/[A-Z]{1}")) {
			context.addMessage("form:licenseNo", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"License Number must be of 12 alphanumeric format [AA/0000/00000/A.]", null));
			isValid = false;
		}
		
		if (pharmacy.getGstNo() == null || pharmacy.getGstNo().trim().isEmpty()) {
			context.addMessage("form:gstNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "GST no. is required", null));
			isValid = false;
		}
		if ((!pharmacy.getGstNo().equals(pharmacy.getGstNo().toUpperCase()))) {
			context.addMessage("form:gstNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "GST number must be in upper case ", null));
			isValid = false;
		}

		if (!pharmacy.getGstNo().matches("[0-9]{2}[0-9A-Z]{10}[0-9]{1}[Z]{1}[0-9]{1}")) {
			context.addMessage("form:gstNo", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"GST Number must be 15 alphanumeric characters formate [0-9]-2d,[A-Z:0-9]-10d,[0-9]-1d,[Z]-1d,[0-9]-1d.",
					null));
			isValid = false;
		}

		if (pharmacy.getEmail() == null || pharmacy.getEmail().trim().isEmpty()) {
			context.addMessage("form:email",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pharmacy email is required.", null));
			isValid = false;
		}
		if (!isValidEmail(pharmacy.getEmail())) {
			context.addMessage("form:email",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format.", null));
			isValid = false;
		}
		if (pharmacyDao.isEmailExist(pharmacy.getEmail())) {
			context.addMessage("form:email",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " email allready registered.", null));
			isValid = false;
		}
		if (pharmacy.getContactNo() == null || pharmacy.getContactNo().trim().isEmpty()) {
			context.addMessage("form:contactNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contact Number is required.", null));
			isValid = false;
		}
		if (!pharmacy.getContactNo().matches("[1-9]\\d{9}")) {
			context.addMessage("form:contactNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contact Number must be 10 digits.", null));
			isValid = false;
		}
		if (pharmacyDao.isPharmacyMobileExist(pharmacy.getContactNo())) {
			context.addMessage("form:contactNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pharmacy Mobile already registered.", null));
			isValid = false;
		}

		if (pharmacy.getAddressLine1() == null || pharmacy.getAddressLine1().trim().isEmpty()) {
			context.addMessage("form:addressLine1",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Address Line 1 is required.", null));
			isValid = false;
		}

		if (pharmacy.getCity() == null || pharmacy.getCity().trim().isEmpty()) {
			context.addMessage("form:city", new FacesMessage(FacesMessage.SEVERITY_ERROR, "City is required.", null));
			isValid = false;
		}
		if (!isAlphabetic(pharmacy.getCity().trim())) {
			context.addMessage("form:city",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Special symbol is not allowed", null));
			isValid = false;
		}

		if (pharmacy.getState() == null || pharmacy.getState().trim().isEmpty()) {
			context.addMessage("form:state", new FacesMessage(FacesMessage.SEVERITY_ERROR, "State is required.", null));
			isValid = false;
		}
		if (!isAlphabetic(pharmacy.getState().trim())) {
			context.addMessage("form:state",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Special symbol is not allowed", null));
			isValid = false;
		}

		if (pharmacy.getPinCode() == null || pharmacy.getPinCode().trim().isEmpty()) {
			context.addMessage("form:pinCode",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "PIN Code is required.", null));
			isValid = false;
		}
		if (!pharmacy.getPinCode().matches("[1-9][0-9]{5}")) {
			context.addMessage("form:pinCode",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "PIN Code must be 6 digits.", null));
			isValid = false;
		}
		if (pharmacy.getPinCode().endsWith("000")) {
			context.addMessage("form:pinCode",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "PIN Code cannot end in 000.", null));
			isValid = false;
		}
		if (pharmacyDao.isGstExists(pharmacy.getGstNo())) {
			context.addMessage("form:gstNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "GST number already registered.", null));
			isValid = false;
		}
		if (pharmacyDao.isPharmacyLicenceExist(pharmacy.getLicenseNo())) {
			context.addMessage("form:licenseNo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Licence number already registered.", null));
			isValid = false;
		}

		if (!isValid) {
			context.validationFailed();
			return null;
		}

		pharmacy.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		String result = pharmacyDao.addPharmacy(pharmacy);
		// context.getExternalContext().getSessionMap().put("otpemail",
		// pharmacy.getEmail());
		// context.getExternalContext().getSessionMap().put("pharmacyObj", pharmacy);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result, null));
		context.getExternalContext().getSessionMap().put("otpemail", pharmacy.getEmail());

		return "Otp.jsf?faces-redirect=true";

	}

	/**
	 * validation for input fields like state,name(these fields only contain string
	 * A to Z)
	 */
	private boolean isAlphabetic(String input) {
		return input != null && input.matches("^[a-zA-Z]+( [a-zA-Z]+)*$");
	}

	/**
	 * validation for email i.e. email format is standard or not
	 */
	private boolean isValidEmail(String email) {
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return Pattern.matches(regex, email);
	}

	/**
	 * Validates login - if active,-> go to home
	 */
	public String validateLogin() {
		FacesContext cont = FacesContext.getCurrentInstance();

		String email = pharmacy.getEmail();
		String password = pharmacy.getPassword();
		boolean isValid = true;
		// validate email
		if (email == null || email.trim().isEmpty()) {
			cont.addMessage("form:email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "email is required", null));
			isValid = false;
		}

		if (!isValidEmail(email)) {
			cont.addMessage("form:email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format.", null));
			isValid = false;
		}
		// password....
		if (password == null || password.trim().isEmpty()) {
			cont.addMessage("form:password",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "password is required", null));
			isValid = false;
		}
		if (!isStrongPassword(password)) {
			cont.addMessage("form:password", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Password must be at least 8 characters long and include uppercase, lowercase, digit, and special character.",
					null));
			isValid = false;
		}

		// stop if validation faild....
		if (!isValid) {
			cont.validationFailed();
			return null;
		}
		// if valid, proceed.....
		if (pharmacyDao.validatePassword(email, password)) {
			Pharmacy found = pharmacyDao.getPharmacyByEmail(email);

			if (!"Active".equalsIgnoreCase(found.getStatus())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Account not activated. Please wait for admin approval.", null));
				return null;
			}

			// setting session attributes to use further
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("pharmacy_id", found.getPharmacyId());
			context.getExternalContext().getSessionMap().put("pharmacy_name", found.getPharmacyName());
			context.getExternalContext().getSessionMap().put("owner_name",
					found.getFirstName() + " " + found.getLastName());
			context.getExternalContext().getSessionMap().put("email", found.getEmail());
			context.getExternalContext().getSessionMap().put("address", found.getAddressLine1());
			context.getExternalContext().getSessionMap().put("city", found.getCity());
			context.getExternalContext().getSessionMap().put("state", found.getState());
			context.getExternalContext().getSessionMap().put("zip_code", found.getPinCode());

			pharmacy.setEmail(null);
			pharmacy.setPassword(null);
			return "Pharmacy.jsf?faces-redirect=true";
		}

//        if (pharmacyDao.validateTempPassword(email, password)) {
//            return "Login.jsf?faces-redirect=true";
//        }

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
		return null;
	}

	/**
	 * currently not using this
	 * 
	 * Checks temporary password and redirects to reset
	 * 
	 * public String validateTempPassword() {
	 * 
	 * String email = pharmacy.getEmail(); String password = pharmacy.getPassword();
	 * 
	 * //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email",
	 * email);
	 * 
	 * boolean isValid = pharmacyDao.validateTempPassword(email, password);
	 * 
	 * if (isValid) { return "ResetPasword.jsf?faces-redirect=true"; } else {
	 * FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid password. Please check
	 * your email.", null)); return null; } }
	 * 
	 */

	/**
	 * OTP verification (not using-> generating temp password)
	 */

	public String generatePassword(String email, int otp) {
		String result = pharmacyDao.generatePassword(email, otp);
		if (result.contains("Otp verified")) {
			//pharmacyOtp.setOtpCode(null);
			return "ResetPasword.jsf?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
			return null;
		}
	}

	/** checking password strength/ how strong password is ? */
	private boolean isStrongPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
		return Pattern.matches(regex, password);
	}

	/**
	 * Resetting password after OTP saving the password for first time users....
	 */
	public String updatePassword(String email, String pwd) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (pwd == null || pwd.trim().isEmpty()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords can't be empty", null));
			return null;
		}
		if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirm passwords is required.", null));
			return null;
		}
		if (!pwd.equals(confirmPassword)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null));
			return null;
		}

		if (!isStrongPassword(pwd)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Password must be at least 8 characters long and include uppercase, lowercase, digit, and special character.",
					null));
			return null;
		}
		if (pharmacy.getPassword() == null) {
			context.addMessage("form:pwd",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords Value is required.", null));
		}

		String result = pharmacyDao.updatePassword(email, pwd);
		if ("Pharmacy Updated Successfully".equals(result)) {
			context.getExternalContext().getFlash().put("message", "Password updated successfully.");
			return "Login.jsf?faces-redirect=true";
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
			return null;
		}
	}

	/**
	 * Resends OTP
	 */
	public String resendOtp() {
		String email = pharmacy.getEmail();
		String result = pharmacyDao.resendOtp(email);
		String temp =pharmacyOtp.getOtpCode();
		System.out.println("temp otp is ............."+ temp);
		pharmacyOtp.setOtpCode(null);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result, null));
		return null;
	}

	/** logout method */
	public String logout() {

		try {
			// invalidating session by using facescontext
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "/home/Home.jsp?faces-redirect=true";

		} catch (Exception e) {

			e.printStackTrace();

			FacesContext.getCurrentInstance().addMessage(null,

					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logout failed", null));

			return null;

		}

	}

	/** back button function */
	public String returnToBack() {
		return "AddOwner.jsf?faces-redirect=true";
	}

	/** back to pharmacy registration */
	public String returnToPharmacyk() {
		return "AddPharmacy.jsf?faces-redirect=true";
	}

	/** Refresh button */

	public String refreshButton() {
		pharmacy.setFirstName(null);
		pharmacy.setMiddleName(null);
		pharmacy.setLastName(null);
		pharmacy.setGender(null);
		pharmacy.setOwnerEmail(null);
		pharmacy.setOwnerMobile(null);
		pharmacy.setAadhar(null);
		pharmacy.setOwnerAddress(null);
		return null;
	}
	/** login by OTP 
	 * 
	 */
	private String loginEmail;
	private String loginOtp;

	public String getLoginEmail() {
	    return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
	    this.loginEmail = loginEmail;
	}

	public String getLoginOtp() {
	    return loginOtp;
	}
	public void setLoginOtp(String loginOtp) {
	    this.loginOtp = loginOtp;
	}

	/** 
	 * send OTP during login by sendLoginOtp(from daoimpl)
	 */
	public String sendLoginOtp() {
	    FacesContext ctx = FacesContext.getCurrentInstance();

	    if (loginEmail == null || loginEmail.trim().isEmpty()) {
	        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email is required.", null));
	        return null;
	    }

	    if (!isValidEmail(loginEmail)) {
	        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format.", null));
	        return null;
	    }

	    String result = pharmacyDao.sendLoginOtp(loginEmail);
	    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result, null));
	    return null;
	}

	/** 
	 * verify OTP entered by user with actual OTP
	 */
	public String verifyLoginOtp() {
	    FacesContext ctx = FacesContext.getCurrentInstance();
	    String result = pharmacyDao.verifyLoginOtp(loginEmail, loginOtp);
	    if (loginOtp == null || loginOtp.trim().isEmpty()) {
	        ctx.addMessage("otpForm:otp", new FacesMessage(FacesMessage.SEVERITY_ERROR, "OTP is required.", null));
	        return null;
	    }

	    if ("OTP verified successfully.".equals(result)) {
	       // this.loginEmail = loginEmail;
	        ctx.getExternalContext().getSessionMap().put("otpemail", loginEmail);
	        return "CreatePassword.jsf?faces-redirect=true";
	    } else {
	        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
	        return null;
	    }
	}
	
	public String resendLoginOtp() {
	    FacesContext context = FacesContext.getCurrentInstance();

	    if (loginEmail == null || loginEmail.trim().isEmpty()) {
	        context.addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter your registered email first.", null));
	        return null;
	    }

	    String result = pharmacyDao.resendLoginOtp(loginEmail);
	    if (result.contains("successfully")) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, result, null));
	    } else {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
	    }

	    return null;
	}


	public String updatePasswordByOtp() {
	    FacesContext context = FacesContext.getCurrentInstance();
	   // String email = (String) context.getExternalContext().getSessionMap().get("otpEmail");

	    String email = loginEmail;

	    if (pharmacy.getPassword() == null || pharmacy.getPassword().trim().isEmpty()) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password is required.", null));
	        return null;
	    }

	    if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirm Password is required.", null));
	        return null;
	    }

	    if (!pharmacy.getPassword().equals(confirmPassword)) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null));
	        return null;
	    }

	    if (!isStrongPassword(pharmacy.getPassword())) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Password must be at least 8 characters and include uppercase, lowercase, digit, and special character.",
	                null));
	        return null;
	    }

	    String result = pharmacyDao.updatePasswordByOtpLogin(email, pharmacy.getPassword());

	    System.out.println("email is ,............."+email);
	    if ("Password reset successfully.".equals(result)) {
	        context.getExternalContext().getFlash().put("message", "Password updated successfully. Please login.");
	        return "Login.jsf?faces-redirect=true";
	    } else {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
	        return null;
	    }
	}

}
