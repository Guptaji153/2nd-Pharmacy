package com.infinite.jsf.pharmacy.daoImpl;

import java.sql.Timestamp;
import java.util.Random;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.infinite.jsf.pharmacy.dao.PharmacyDao;
import com.infinite.jsf.pharmacy.model.Passwords;
import com.infinite.jsf.pharmacy.model.Pharmacy;
import com.infinite.jsf.pharmacy.model.PharmacyOtp;
import com.infinite.jsf.pharmacy.model.Purpose;
import com.infinite.jsf.pharmacy.model.Status;
import com.infinite.jsf.util.MailSend;
import com.infinite.jsf.util.SessionHelper;
import org.apache.log4j.Logger;

public class PharmacyDaoImpl implements PharmacyDao {
	private static final Logger log = Logger.getLogger("com.infinite.jsf.pharmacy.daoImpl.PharmacyDaoImpl");
    Session session;
    /**
     *  generating six digit otp code
     *  */   
    public int generateOtp() {
        return 100000 + new Random().nextInt(900000);
    }
    
    /**
     *  generating next pharmacy Id
     *  */
    public static String getNextPharmacyId(Session session) {
    	
    	log.info("going to generate pharmacy id ");
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
     * Add/ Register new pharmacy 
     * save otp
     * sending otp vie email
     *  */
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
                log.info("new pharmacy saved to db");
    	        int code = generateOtp();
    	        Timestamp now = new Timestamp(System.currentTimeMillis());
    	        Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000);
    	        //same time inserting data into PharmacyOtp table
    	        PharmacyOtp otp = new PharmacyOtp();
    	        otp.setOtpCode(String.valueOf(code));
    	        otp.setStatus(Status.PENDING);
    	        otp.setPurpose(Purpose.REGISTER);
    	        otp.setPharmacyId(pharmacy.getPharmacyId());
    	        otp.setCreatedAt(now);
    	        otp.setExpiresAt(expiry);
    	        session.save(otp);
    	        log.info("otp generated and saved sucessfully");
    	        trans.commit();
    	        //sending email....
    	        String subject = "Hi " + pharmacy.getPharmacyName() + ", your account is created";
    	        String body = "Your OTP Code is " + code + ". Please use this to set your password.";
    	        MailSend.sendInfo(pharmacy.getEmail(), subject, body);
    	        log.info("otv send vie email for the registration of :"+pharmacy.getPharmacyName());

    	        return "Pharmacy record added and OTP sent via email.";

    	    } catch (Exception e) {
    	    	log.error("some error while adding new pharmacy"+e);
    	        if (trans != null) trans.rollback();
//
//    	        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
//    	            return "Aadhar or GST already exists";
//    	        }
    	        e.printStackTrace();
    	        return "Error occurred while saving pharmacy.";
    	    } finally {
    	        session.close();
    	    }
        
	}

/**
    public String getAlphaNumericString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
    */
    
    /**
     *  validating otp if valid and not expired
     *  mark as VERIFIED and redirect to reset password
     *  */
   
    @Override
	public String generatePassword(String email, int otp) {
    	log.info("going to validate otp entered by user");
	       session = SessionHelper.getSessionFactory().openSession();
	        Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
	        pharmacyQuery.setParameter("email", email);
	        Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

	        if (pharmacy == null) {
	        	log.info("email entered by user while otp varification is not registerd");
	            return "No pharmacy found with the provided email.";
	        }

	        //testing....
	        System.out.println("Validating OTP:");
	        System.out.println("Email: " + email);
	        System.out.println("Pharmacy ID: " + pharmacy.getPharmacyId());
	        System.out.println("OTP: " + otp);
	        System.out.println("Expected status: PENDING");

	        
	        String hql = "from PharmacyOtp where pharmacyId = :pharmacyId and otpCode = :otp and status = :status";
	        Query query = session.createQuery(hql);
	        query.setParameter("pharmacyId", pharmacy.getPharmacyId());
	        query.setParameter("otp", String.valueOf(otp));
	        query.setParameter("status", Status.PENDING);

	        PharmacyOtp objOtp = (PharmacyOtp) query.uniqueResult();

	        if (objOtp != null) {
	            Timestamp now = new Timestamp(System.currentTimeMillis());
	            if (now.after(objOtp.getExpiresAt())) {
	                // Mark OTP as expired
	            	log.info("otp entered by user was expierd and time is :"+now);
	                Transaction tx = session.beginTransaction();
	                objOtp.setStatus(Status.EXPIRED);  
	                session.update(objOtp);
	                tx.commit();
	                return "Otp expired. Please resend OTP.";
	            }

	            // OTP is valid and not expired, proceed
	           // String pwd = getAlphaNumericString();
	            objOtp.setStatus(Status.VERIFIED);
	            log.info("otp verified");
	           // objOtp.setNewPassword(pwd);

	            Transaction trans = session.beginTransaction();
	            session.update(objOtp);
	            trans.commit();

	           // String body = "Your One-Time Password for Login is: " + pwd;
	           // MailSend.sendInfo(email, "One Time Password", body);

	            return "Otp verified New password has been sent to your email.";
	        }

	        return "Invalid Otp or email.";
	}

/**
 * validate login using permanent password
 *  */
    
	@Override
	public boolean validatePassword(String email, String password) {
		log.info("user is trying to login using their credential");
		session = SessionHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) query.uniqueResult();
        
        return pharmacy != null && pharmacy.getPassword().equals(password);
	}
     
	/**
    public boolean validateTempPassword(String email, String password) {
        session = SessionHelper.getSessionFactory().openSession();

        Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
        pharmacyQuery.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

        if (pharmacy == null) return false;

        Query otpQuery = session.createQuery("from PharmacyOtp where pharmacyId = :pharmacyId and status = :status");
        otpQuery.setParameter("pharmacyId", pharmacy.getPharmacyId());
        otpQuery.setParameter("status", Status.VERIFIED);

        PharmacyOtp otp = (PharmacyOtp) otpQuery.uniqueResult();

        return otp != null && otp.getNewPassword().equals(password);
    }
*/
	
	/**
	 * update pharmacy password (means) create custom password 
	 * [first time during registration]
	 * also saving in another password table so that to be used by others
	 * deleting used OTP(for safety purpose)
	 *  */
  	@Override
	public String updatePassword(String email, String pwd) {
  		log.info("updating password by user");
		session = SessionHelper.getSessionFactory().openSession();
        Transaction tx = null;

        //Fetch pharmacy by email
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) query.uniqueResult();

        if (pharmacy != null) {
            tx = session.beginTransaction();

            //Update the pharmacy's permanent password
            pharmacy.setPassword(pwd);
            session.update(pharmacy);
            log.info("password saved to pharmacy table");
            //saving in passwords table also
            Passwords passRecords = new Passwords();
            passRecords.setPharmacy(pharmacy);
            passRecords.setPassword(pwd);
            session.save(passRecords);
            log.info("password saved to passwordHistory table sucessfully");
            

            //Get the latest verified OTP 
            Query otpQuery = session.createQuery(
                "from PharmacyOtp where pharmacyId = :pharmacyId and status = :status order by createdAt desc"
            );
            otpQuery.setParameter("pharmacyId", pharmacy.getPharmacyId());
            otpQuery.setParameter("status", Status.VERIFIED);
            otpQuery.setMaxResults(1);

            PharmacyOtp otp = (PharmacyOtp) otpQuery.uniqueResult();

            if (otp != null) {
                // Delete that OTP record 
                session.delete(otp);
                log.info("old otp deleated for cleaning purpose");

                //   deleting all expired and pending OTPs
                Query cleanUp = session.createQuery(
                    "delete from PharmacyOtp where pharmacyId = :pharmacyId and status != :status"
                );
                cleanUp.setParameter("pharmacyId", pharmacy.getPharmacyId());
                cleanUp.setParameter("status", Status.VERIFIED);
                cleanUp.executeUpdate();
                log.info("old otp with verified & expierd are deleated");
            }

            tx.commit();
            return "Pharmacy Updated Successfully";
        } else {
        	log.error("email entered by user while updating password was invalid or did not found in db");
            return "No pharmacy found with the given email.";
        }
	}
    
  	/** get pharmacy by their email id (used for login, otp verification etc) */
  	    public Pharmacy getPharmacyByEmail(String email) {
  	    log.info("getting pharmacy by their emailId");
        session = SessionHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        return (Pharmacy) query.uniqueResult();
    }
    
  	    /** Resend OTP 
  	     * invalidate previous one
  	     * send new OTP
  	     * 
  	     *  */
    //resend otp..
    public String resendOtp(String email) {
    	log.info("reSending otp as per request by user");
        session = SessionHelper.getSessionFactory().openSession();

        Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
        pharmacyQuery.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

        if (pharmacy == null) {
            return "No pharmacy found with this email.";
        }

        int newOtp = generateOtp();
        log.info("new otp generated");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000);

        // Invalidate previous OTPs by marking expired
        log.info("marking previous otp as expierd");
        Transaction tx = session.beginTransaction();
        Query otpUpdate = session.createQuery("update PharmacyOtp set status = :expired where pharmacyId = :pharmacyId and status = :pending");
        otpUpdate.setParameter("expired", Status.EXPIRED);
        otpUpdate.setParameter("pharmacyId", pharmacy.getPharmacyId());
        otpUpdate.setParameter("pending", Status.PENDING);
        otpUpdate.executeUpdate();
        log.info("expierd marking sucess");

        // Save new OTP
        log.info("saving new otp after resend");
        PharmacyOtp newOtpObj = new PharmacyOtp();
        newOtpObj.setOtpCode(String.valueOf(newOtp));
        newOtpObj.setStatus(Status.PENDING);
        newOtpObj.setPurpose(Purpose.REGISTER);
        newOtpObj.setPharmacyId(pharmacy.getPharmacyId());
        newOtpObj.setCreatedAt(now);
        newOtpObj.setExpiresAt(expiry);
        session.save(newOtpObj);
        tx.commit();
        log.info("new otp saved");

        // Send OTP mail
        String subject = "Your new OTP code";
        String body = "Your new OTP is " + newOtp + ". Please use this to verify your account.";
        MailSend.sendInfo(email, subject, body);
        log.info("new otp send on email");

        return "New OTP sent to your email.";
    }
    
    /**
     * login vie OTP
     * this method send OTP to entered email id 
     *  */
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
        Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000); // 2 minutes validity

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
        String body = "Hello " + pharmacy.getPharmacyName() + ",\n\nYour OTP for login is: " + otpCode +
                      "\nThis OTP is valid for 2 minutes.\n\n- HealthSure Team";
        MailSend.sendInfo(email, subject, body);

        log.info("Login OTP sent to: " + email);
        return "OTP sent to your email.";
    }

    /**
     * Verify the user entered OTP with actual OTP code
     *  */
    @Override
    public String verifyLoginOtp(String email, String otpCode) {
        log.info("Verifying login OTP for email: " + email);

        //  Lookup pharmacy by emailId
        Pharmacy pharmacy = getPharmacyByEmail(email);
        if (pharmacy == null) {
            log.warn("No pharmacy found with email: " + email);
            return "Invalid email.";
        }

        session = SessionHelper.getSessionFactory().openSession();

        //Find matching pending OTP
        Query query = session.createQuery(
            "from PharmacyOtp where pharmacyId = :pharmacyId and otpCode = :otp and purpose = :purpose and status = :status"
        );
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

        //Check expiration
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
     *  */
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
        Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000); // 2 minutes validity

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
 * update password while OTP login
 * 
 *  */
    @Override
    public String updatePasswordByOtpLogin(String email, String pwd) {
        log.info("Resetting password for OTP login: " + email);
        session = SessionHelper.getSessionFactory().openSession();
        Transaction tx = null;

        // Check if pharmacy exists
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) query.uniqueResult();

        if (pharmacy == null) {
            log.error("No pharmacy found with the given email: " + email);
            return "No pharmacy found with the given email.";
        }

        // Check if password already exists in Passwords table
        Query pwdQuery = session.createQuery("from Passwords where pharmacy.pharmacyId = :id and password = :pwd");
        pwdQuery.setParameter("id", pharmacy.getPharmacyId());
        pwdQuery.setParameter("pwd", pwd);
        Passwords existing = (Passwords) pwdQuery.uniqueResult();

        if (existing != null) {
            log.warn("Password already used previously by pharmacy ID: " + pharmacy.getPharmacyId());
            return "This password has already been used. Please choose a new one.";
        }

        // Begin transaction
        tx = session.beginTransaction();

        // Update Pharmacy password
        pharmacy.setPassword(pwd);
        session.update(pharmacy);
        log.info("Password updated in Pharmacy table");

        // Save in Passwords history
        Passwords passHistory = new Passwords();
        passHistory.setPharmacy(pharmacy);
        passHistory.setPassword(pwd);
        session.save(passHistory);
        log.info("Password recorded in Passwords history table");

        // Delete the latest verified OTP
        Query otpQuery = session.createQuery("from PharmacyOtp where pharmacyId = :pid and status = :status order by createdAt desc");
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
    
    /** checking for duplicate entity
     * for aadhar no
     * for GST no
     * for owner email
     * for pharmacy email
     * for contact no
     * for license no
     *  */

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
