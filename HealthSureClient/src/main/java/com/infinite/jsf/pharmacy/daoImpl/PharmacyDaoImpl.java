package com.infinite.jsf.pharmacy.daoImpl;

import java.sql.Timestamp;
import java.util.Random;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import com.infinite.jsf.pharmacy.dao.PharmacyDao;
import com.infinite.jsf.pharmacy.model.Passwords;
import com.infinite.jsf.pharmacy.model.Pharmacy;
import com.infinite.jsf.pharmacy.model.PharmacyOtp;
import com.infinite.jsf.pharmacy.model.Purpose;
import com.infinite.jsf.util.EncryptPassword;
import com.infinite.jsf.pharmacy.model.Status;
import com.infinite.jsf.util.MailSend;
import com.infinite.jsf.util.SessionHelper;
import org.apache.log4j.Logger;

public class PharmacyDaoImpl implements PharmacyDao {
	private static final Logger log = Logger.getLogger("com.infinite.jsf.pharmacy.daoImpl.PharmacyDaoImpl");
	Session session;

	/**
	 * generating six digit otp code
	 */
	public int generateOtp() {
		return 100000 + new Random().nextInt(900000);
	}

	public String getAlphaNumericString() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * generating next pharmacy Id
	 */
	public static String getNextPharmacyId(Session session) {
		
		log.info("Pharmacy id generated");
		String prefix = "PHM";
		String hql = "select max(pharmacyId) from Pharmacy";
		String maxId = (String) session.createQuery(hql).uniqueResult();
		if (maxId == null) {
			return prefix + "001";
		}
		int id = Integer.parseInt(maxId.substring(prefix.length()));
		id++;
		return String.format("%s%03d", prefix, id);
	}
	
	/**
	 * storing temporary password to TempPwd variable 
	 *  */
	public String TempPwd;
	/**
	 * Add/ Register new pharmacy save otp sending otp vie email
	 */
	@Override
	public String addPharmacy(Pharmacy pharmacy) {
		Session session = SessionHelper.getSessionFactory().openSession();
		Transaction trans = null;
		log.info("going to add/register new pharmacy");
		try {
			trans = session.beginTransaction();
			String nextId = getNextPharmacyId(session);
			System.out.println("pharmacy id" + nextId);
			pharmacy.setPharmacyId(nextId);
			pharmacy.setStatus("Pending");
			

			session.save(pharmacy);

			Timestamp now = new Timestamp(System.currentTimeMillis());

			PharmacyOtp otp = new PharmacyOtp();
			TempPwd = getAlphaNumericString();
			otp.setPharmacyId(pharmacy.getPharmacyId());
			otp.setPurpose(Purpose.REGISTER);
			otp.setNewPassword(TempPwd);
			otp.setStatus(Status.PENDING);
			otp.setCreatedAt(now);

			session.save(otp);

			trans.commit();
			// sending email....
			String subject = "Hi " + pharmacy.getPharmacyName() + ", your account is created";
			String body = "Your Temperary Password to reset your password is " + TempPwd
					+ ". Please use this to set your password.";
			MailSend.sendInfo(pharmacy.getEmail(), subject, body);

			return "Pharmacy record added and OTP sent via email.";
			//return TempPwd;

		} catch (Exception e) {
			// log.error("some error while adding new pharmacy"+e);
			if (trans != null)
				trans.rollback();

			e.printStackTrace();
			return "Error occurred while saving pharmacy.";
		} finally {
			session.close();
		}

	}

	/**
	 * validate login using permanent password
	 * if email and password valid/correct redirect to pharmacyHome page
	 */

	@Override
	public boolean validatePassword(String email, String password) {
		log.info("user is trying to login using their credential");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where email = :email");
		query.setParameter("email", email);
		Pharmacy pharmacy = (Pharmacy) query.uniqueResult();
		String encryptedPwd = EncryptPassword.getCode(password);
		return pharmacy != null && pharmacy.getPassword().equals(encryptedPwd);
	}

	/** get pharmacy by their email id (used for login, otp verification etc) */
	public Pharmacy getPharmacyByEmail(String email) {
		log.info("getting pharmacy by their emailId");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where email = :email");
		query.setParameter("email", email);
		return (Pharmacy) query.uniqueResult();
	}



	/**
	 * login vie OTP 
	 * this method send OTP to entered email id
	 */
	@Override
	public String sendLoginOtp(String email) {
		log.info("Generating login OTP for email: " + email);

		// Check if pharmacy exists
		Pharmacy pharmacy = getPharmacyByEmail(email);
		if (pharmacy == null) {
			log.warn("Email not found: " + email);
			return "Email not registered.";
		}

		// Generate OTP
		int otpCode = generateOtp();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000); 

		// Insert new OTP record
		session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		PharmacyOtp otp = new PharmacyOtp();
		otp.setPharmacyId(pharmacy.getPharmacyId());
		otp.setOtpCode(String.valueOf(otpCode));
		otp.setPurpose(Purpose.OTP_LOGIN);
		otp.setStatus(Status.PENDING);
		otp.setCreatedAt(now);
		otp.setExpiresAt(expiry);

		session.save(otp);
		tx.commit();
		session.close();

		// Send OTP via email
		String subject = "Your Login OTP";
		String body = "Hello " + pharmacy.getPharmacyName() + ",\n\nYour OTP for login is: " + otpCode
				+ "\nThis OTP is valid for 2 minutes.\n\n- HealthSure Team";
		MailSend.sendInfo(email, subject, body);

		log.info("Login OTP sent to: " + email);
		return "OTP sent to your email.";
	}

	/**
	 * Verify the user entered OTP with actual OTP code
	 */
	@Override
	public String verifyLoginOtp(String email, String otpCode) {
		log.info("Verifying login OTP for email: " + email);

		// Lookup pharmacy by emailId
		Pharmacy pharmacy = getPharmacyByEmail(email);
		if (pharmacy == null) {
			log.warn("No pharmacy found with email: " + email);
			return "Invalid email.";
		}

		session = SessionHelper.getSessionFactory().openSession();

		// Find matching pending OTP
		Query query = session.createQuery(
				"from PharmacyOtp where pharmacyId = :pharmacyId and otpCode = :otp and purpose = :purpose and status = :status");
		query.setParameter("pharmacyId", pharmacy.getPharmacyId());
		query.setParameter("otp", String.valueOf(otpCode));
		query.setParameter("purpose", Purpose.OTP_LOGIN);
		query.setParameter("status", Status.PENDING);

		PharmacyOtp pendingOtp = (PharmacyOtp) query.uniqueResult();

		if (pendingOtp == null) {
			session.close();
			log.info("Invalid or already used OTP for email: " + email);
			return "Invalid or expired OTP.";
		}

		// Check expiration
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (now.after(pendingOtp.getExpiresAt())) {
			session.close();
			log.info("OTP expired for pharmacy ID: " + pharmacy.getPharmacyId());
			return "OTP expired. Please request a new one.";
		}

		// OTP is valid → insert VERIFIED status record
		Transaction tx = session.beginTransaction();

		PharmacyOtp verifiedOtp = new PharmacyOtp();
		verifiedOtp.setPharmacyId(pharmacy.getPharmacyId());
		verifiedOtp.setOtpCode(pendingOtp.getOtpCode());
		verifiedOtp.setPurpose(Purpose.OTP_LOGIN);
		verifiedOtp.setStatus(Status.VERIFIED);
		verifiedOtp.setCreatedAt(now);
		verifiedOtp.setExpiresAt(pendingOtp.getExpiresAt());

		session.save(verifiedOtp);
		tx.commit();
		session.close();

		log.info("OTP verified and inserted for email: " + email);
		return "OTP verified successfully.";
	}

	/**
	 * Re-send OTP while login vie OTP
	 */
	public String resendLoginOtp(String email) {
		log.info("Resending OTP for login");

		session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
		pharmacyQuery.setParameter("email", email);
		Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

		if (pharmacy == null) {
			return "No pharmacy found with this email.";
		}

		int newOtp = generateOtp();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000); 

		// Expire any previous OTPs of type LOGIN
		Query updateOldOtps = session.createQuery(
				"update PharmacyOtp set status = :expired where pharmacyId = :pharmacyId and status = :pending and purpose = :loginPurpose");
		updateOldOtps.setParameter("expired", Status.EXPIRED);
		updateOldOtps.setParameter("pending", Status.PENDING);
		updateOldOtps.setParameter("pharmacyId", pharmacy.getPharmacyId());
		updateOldOtps.setParameter("loginPurpose", Purpose.OTP_LOGIN);
		updateOldOtps.executeUpdate();

		// Save new OTP
		PharmacyOtp otp = new PharmacyOtp();
		otp.setOtpCode(String.valueOf(newOtp));
		otp.setStatus(Status.PENDING);
		otp.setPurpose(Purpose.OTP_LOGIN);
		otp.setPharmacyId(pharmacy.getPharmacyId());
		otp.setCreatedAt(now);
		otp.setExpiresAt(expiry);
		session.save(otp);

		tx.commit();

		// Send email
		String subject = "Login OTP";
		String body = "Your OTP for login is: " + newOtp + ". It is valid for 2 minutes.";
		MailSend.sendInfo(email, subject, body);

		return "OTP resent successfully to your registered email.";
	}

	/**
	 * get OTP table data by pharmacyId
	 */
	public PharmacyOtp getPharmacyOtpByPharmacyId(String pharmacyId) {
		SessionFactory session = SessionHelper.getSessionFactory();
		Query query = ((SharedSessionContract) session)
				.createQuery("from PharmacyOtp where pharmacy.pharmacyId = :pharmacyId");
		query.setParameter("pharmacyId", pharmacyId);
		PharmacyOtp otp = (PharmacyOtp) query.uniqueResult();
		session.close();
		return otp;
	}

	/**
	 * update password while OTP login
	 * 
	 */
	@Override
	public String updatePasswordByOtpLogin(String email, String pwd, String tempPassword) {
		log.info("Resetting password for OTP login: " + email);
		session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = null;
		String encryptedPwd = EncryptPassword.getCode(pwd);
		// Check if pharmacy exists
		Query query = session.createQuery("from Pharmacy where email = :email");
		query.setParameter("email", email);
		Pharmacy pharmacy = (Pharmacy) query.uniqueResult();

		if (pharmacy == null) {
			log.error("No pharmacy found with the given email: " + email);
			return "No pharmacy found with the given email.";
		}

		// Check if temporary password is valid for this pharmacy
		String hql = "FROM PharmacyOtp WHERE pharmacyId = :pharmacyId AND newPassword = :tempPassword AND purpose = :purpose AND status = :status";
		Query tempPwdQuery = session.createQuery(hql);
		tempPwdQuery.setParameter("pharmacyId", pharmacy.getPharmacyId());
		tempPwdQuery.setParameter("tempPassword", tempPassword);
		tempPwdQuery.setParameter("purpose", Purpose.REGISTER);
		tempPwdQuery.setParameter("status", Status.PENDING);

		PharmacyOtp otpEntry = (PharmacyOtp) tempPwdQuery.uniqueResult();
		if (otpEntry == null) {
			return "Invalid temporary password.";
		}

		// Check if password already exists in Passwords table
		Query pwdQuery = session.createQuery("from Passwords where pharmacy.pharmacyId = :id and password = :pwd");
		pwdQuery.setParameter("id", pharmacy.getPharmacyId());
		pwdQuery.setParameter("pwd", encryptedPwd);

		Passwords existing = (Passwords) pwdQuery.uniqueResult();

		if (existing != null) {
			log.warn("Password already used previously by pharmacy ID: " + pharmacy.getPharmacyId());
			return "This password has already been used. Please choose a new one.";
		}

		// Begin transaction
		tx = session.beginTransaction();

		// Update Pharmacy password

		pharmacy.setPassword(encryptedPwd);
		session.update(pharmacy);
		log.info("Password updated in Pharmacy table");

		// Save in Passwords history
		Passwords passHistory = new Passwords();
		passHistory.setPharmacy(pharmacy);
		passHistory.setPassword(encryptedPwd);
		session.save(passHistory);
		log.info("Password recorded in Passwords history table");

		// Delete the latest verified OTP
		Query otpQuery = session
				.createQuery("from PharmacyOtp where pharmacyId = :pid and status = :status order by createdAt desc");
		otpQuery.setParameter("pid", pharmacy.getPharmacyId());
		otpQuery.setParameter("status", Status.VERIFIED);
		otpQuery.setMaxResults(1);
		PharmacyOtp otp = (PharmacyOtp) otpQuery.uniqueResult();

		if (otp != null) {
			session.delete(otp);
			log.info("Latest verified OTP deleted");
		}

		// Clean up expired and pending OTPs
		Query cleanUp = session.createQuery("delete from PharmacyOtp where pharmacyId = :pid and status != :verified");
		cleanUp.setParameter("pid", pharmacy.getPharmacyId());
		cleanUp.setParameter("verified", Status.VERIFIED);
		cleanUp.executeUpdate();
		log.info("Cleaned up expired/pending OTPs");

		tx.commit();
		return "Password reset successfully.";
	}

	/**
	 * checking for duplicate entity for aadhar no for GST no for owner email for
	 * pharmacy email for contact no for license no
	 */

	public boolean isAadharExists(String aadhar) {
		log.info("checking for unique aadhar no.");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where aadhar = :aadhar");
		query.setParameter("aadhar", aadhar);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

	public boolean isGstExists(String gstNo) {
		log.info("checking for unique GST no");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where gstNo = :gst");
		query.setParameter("gst", gstNo);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

	public boolean isEmailExist(String email) {
		log.info("checking for unique pharmacy email");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where email = :email");
		query.setParameter("email", email);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

	public boolean isOwnerEmailExist(String ownerEmail) {
		log.info("checking for unique Owner email");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where ownerEmail = :ownerEmail");
		query.setParameter("ownerEmail", ownerEmail);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

	public boolean isOwnerMobileExist(String ownerMobile) {
		log.info("checking for unique owner contact no");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where ownerMobile = :ownerMobile");
		query.setParameter("ownerMobile", ownerMobile);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

	public boolean isPharmacyMobileExist(String PharmacyMobile) {
		log.info("checking for unique pharmacy contact no");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where contactNo = :contactNo");
		query.setParameter("contactNo", PharmacyMobile);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

	public boolean isPharmacyLicenceExist(String PharmacyLicence) {
		log.info("checking for unique pharmacy licence no");
		session = SessionHelper.getSessionFactory().openSession();
		Query query = session.createQuery("from Pharmacy where licenseNo = :licenseNo");
		query.setParameter("licenseNo", PharmacyLicence);
		query.setMaxResults(1);
		return query.uniqueResult() != null;
	}

}
