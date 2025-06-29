package com.java.test.dao.impl;

import java.sql.Timestamp;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.java.test.dao.PharmacyDao;
import com.java.test.model.Pharmacy;
import com.java.test.model.PharmacyOTP;
import com.java.test.model.Purpose;
import com.java.test.model.Status;
import com.java.test.util.MailSend;
import com.java.test.util.SessionHelper;

public class PharmacyDaoImpl implements PharmacyDao {

    Session session;

    public int generateOtp() {
        return 100000 + new Random().nextInt(900000);
    }

    public static String getNextPharmacyId(Session session) {
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

    @Override
    public String addPharmacy(Pharmacy pharmacy) {
        session = SessionHelper.getConnection().openSession();
        Transaction trans = session.beginTransaction();

        String nextId = getNextPharmacyId(session);
        pharmacy.setPharmacyId(nextId);
        pharmacy.setStatus("Pending");
        session.save(pharmacy);
        trans.commit();
        int code = generateOtp();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp expiry = new Timestamp(now.getTime() + 10 * 60 * 1000);

        PharmacyOTP otp = new PharmacyOTP();
        otp.setOtpCode(String.valueOf(code));
        otp.setStatus(Status.PENDING);
        otp.setPurpose(Purpose.REGISTER);
        otp.setPharmacyId(pharmacy.getPharmacyId());
        otp.setCreatedAt(now);
        otp.setExpiresAt(expiry);

        trans = session.beginTransaction();
        session.save(otp);
        trans.commit();

        String subject = "Hi " + pharmacy.getPharmacyName() + ", your account is created";
        String body = "Your OTP Code is " + code + ". Please use this to set your password.";
        MailSend.sendInfo(pharmacy.getEmail(), subject, body);

        return "Pharmacy record added and OTP sent via email.";
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
    @Override
    public String generatePassword(String email, int otp) {
        session = SessionHelper.getConnection().openSession();
        Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
        pharmacyQuery.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

        if (pharmacy == null) {
            return "No pharmacy found with the provided email.";
        }

        String hql = "from PharmacyOTP where pharmacyId = :pharmacyId and otpCode = :otp and status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("pharmacyId", pharmacy.getPharmacyId());
        query.setParameter("otp", String.valueOf(otp));
        query.setParameter("status", Status.PENDING);

        PharmacyOTP objOtp = (PharmacyOTP) query.uniqueResult();

        if (objOtp != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.after(objOtp.getExpiresAt())) {
                // Mark OTP as expired
                Transaction tx = session.beginTransaction();
                objOtp.setStatus(Status.EXPIRED);  // Make sure EXPIRED exists in your Status class
                session.update(objOtp);
                tx.commit();

                return "OTP expired. Please resend OTP.";
            }

            // OTP is valid and not expired, proceed
            String pwd = getAlphaNumericString();
            objOtp.setStatus(Status.VERIFIED);
            objOtp.setNewPassword(pwd);

            Transaction trans = session.beginTransaction();
            session.update(objOtp);
            trans.commit();

            String body = "Your One-Time Password for Login is: " + pwd;
            MailSend.sendInfo(email, "One Time Password", body);

            return "OTP verified. New password has been sent to your email.";
        }

        return "Invalid OTP or email.";
    }


    @Override
    public boolean validatePassword(String email, String password) {
        session = SessionHelper.getConnection().openSession();
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) query.uniqueResult();

        return pharmacy != null && pharmacy.getPassword().equals(password);
    }

    public boolean validateTempPassword(String email, String password) {
        session = SessionHelper.getConnection().openSession();

        Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
        pharmacyQuery.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

        if (pharmacy == null) return false;

        Query otpQuery = session.createQuery("from PharmacyOTP where pharmacyId = :pharmacyId and status = :status");
        otpQuery.setParameter("pharmacyId", pharmacy.getPharmacyId());
        otpQuery.setParameter("status", Status.VERIFIED);

        PharmacyOTP otp = (PharmacyOTP) otpQuery.uniqueResult();

        return otp != null && otp.getNewPassword().equals(password);
    }

  
    @Override
    public String updatePassword(String email, String pwd) {
        session = SessionHelper.getConnection().openSession();
        Transaction tx = null;

        // Step 1: Fetch pharmacy by email
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) query.uniqueResult();

        if (pharmacy != null) {
            tx = session.beginTransaction();

            // Step 2: Update the pharmacy's permanent password
            pharmacy.setPassword(pwd);
            session.update(pharmacy);

            // Step 3: Get the latest verified OTP (if any)
            Query otpQuery = session.createQuery(
                "from PharmacyOTP where pharmacyId = :pharmacyId and status = :status order by createdAt desc"
            );
            otpQuery.setParameter("pharmacyId", pharmacy.getPharmacyId());
            otpQuery.setParameter("status", Status.VERIFIED);
            otpQuery.setMaxResults(1);

            PharmacyOTP otp = (PharmacyOTP) otpQuery.uniqueResult();

            if (otp != null) {
                // Step 4: Delete that OTP record (optional, for cleanup)
                session.delete(otp);

                // Optional: You could also delete all expired and pending OTPs
                Query cleanUp = session.createQuery(
                    "delete from PharmacyOTP where pharmacyId = :pharmacyId and status != :status"
                );
                cleanUp.setParameter("pharmacyId", pharmacy.getPharmacyId());
                cleanUp.setParameter("status", Status.VERIFIED);
                cleanUp.executeUpdate();
            }

            tx.commit();
            return "Pharmacy Updated Successfully";
        } else {
            return "No pharmacy found with the given email.";
        }
    }

    

    public Pharmacy getPharmacyByEmail(String email) {
        session = SessionHelper.getConnection().openSession();
        Query query = session.createQuery("from Pharmacy where email = :email");
        query.setParameter("email", email);
        return (Pharmacy) query.uniqueResult();
    }
    
    public String resendOtp(String email) {
        session = SessionHelper.getConnection().openSession();

        Query pharmacyQuery = session.createQuery("from Pharmacy where email = :email");
        pharmacyQuery.setParameter("email", email);
        Pharmacy pharmacy = (Pharmacy) pharmacyQuery.uniqueResult();

        if (pharmacy == null) {
            return "No pharmacy found with this email.";
        }

        int newOtp = generateOtp();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp expiry = new Timestamp(now.getTime() + 10 * 60 * 1000);

        // Invalidate previous OTPs by marking expired
        Transaction tx = session.beginTransaction();
        Query otpUpdate = session.createQuery("update PharmacyOTP set status = :expired where pharmacyId = :pharmacyId and status = :pending");
        otpUpdate.setParameter("expired", Status.EXPIRED);
        otpUpdate.setParameter("pharmacyId", pharmacy.getPharmacyId());
        otpUpdate.setParameter("pending", Status.PENDING);
        otpUpdate.executeUpdate();

        // Save new OTP
        PharmacyOTP newOtpObj = new PharmacyOTP();
        newOtpObj.setOtpCode(String.valueOf(newOtp));
        newOtpObj.setStatus(Status.PENDING);
        newOtpObj.setPurpose(Purpose.REGISTER);
        newOtpObj.setPharmacyId(pharmacy.getPharmacyId());
        newOtpObj.setCreatedAt(now);
        newOtpObj.setExpiresAt(expiry);
        session.save(newOtpObj);
        tx.commit();

        // Send OTP mail
        String subject = "Your new OTP code";
        String body = "Your new OTP is " + newOtp + ". Please use this to verify your account.";
        MailSend.sendInfo(email, subject, body);

        return "New OTP sent to your email.";
    }

}

