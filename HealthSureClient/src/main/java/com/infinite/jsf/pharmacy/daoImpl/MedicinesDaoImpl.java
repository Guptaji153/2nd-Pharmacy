package com.infinite.jsf.pharmacy.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import com.infinite.jsf.pharmacy.dao.MedicinesDao;
import com.infinite.jsf.pharmacy.model.Medicines;
import com.infinite.jsf.util.SessionHelper;

public class MedicinesDaoImpl implements MedicinesDao {
	private static final Logger log = Logger.getLogger("com.infinite.jsf.pharmacy.daoImpl.PharmacyDaoImpl");

	/**
	 * Fetch all medicines of a specific pharmacy by using their id
	 *  */
//    @Override
//    public List<Medicines> getMedicinesByPharmacyId(String pharmacyId) {
//        Session session = SessionHelper.getSessionFactory().openSession();
//        try {
//            Query query = session.createQuery("from Medicines where pharmacy.pharmacyId = :pharmacyId");
//            query.setParameter("pharmacyId", pharmacyId);
//            return query.list();
//        } finally {
//            session.close();
//        }
//    }
   
	
	@Override
	public List<Medicines> getMedicinesByPharmacyId(String pharmacyId) {
		log.info("get medicine by medicineId");
		Session session = SessionHelper.getSessionFactory().openSession();
		try {
		    session.clear(); 
		    session.flush(); 
		    Query query = session.createQuery("from Medicines where pharmacy.pharmacyId = :pharmacyId");
		    query.setParameter("pharmacyId", pharmacyId);
		    List<Medicines> list = query.list();		    
		    for (Medicines med : list) {
		        session.evict(med);  
		    }

		    return list;
		} finally {
		    session.close();
		}
	}
    /** 
     * Search medicines based on start with for a particular pharmacy 
     *  */
    @Override
    public List<Medicines> searchMedicinesStartingWith(String name, String pharmacyId) {
    	log.info("searching medicines start with");
        Session session = SessionHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from Medicines where lower(medicineName) like :name and pharmacy.pharmacyId = :pharmacyId");
            query.setParameter("name", name.toLowerCase() + "%");
            query.setParameter("pharmacyId", pharmacyId);
            return query.list();
        } finally {
            session.close();
        }
    }

    /** 
     * Search medicines based on contain with for a particular pharmacy 
     * */
    @Override
    public List<Medicines> searchMedicinesContaining(String name, String pharmacyId) {
    	log.info("searching medicines contains with");
        Session session = SessionHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from Medicines where lower(medicineName) like :name and pharmacy.pharmacyId = :pharmacyId");
            query.setParameter("name", "%" + name.toLowerCase() + "%");
            query.setParameter("pharmacyId", pharmacyId);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Medicines> searchMedicinesExactMatch(String name, String pharmacyId) {
    	log.info("searching medicine with exact same medicine name");
        Session session = SessionHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from Medicines where lower(medicineName) = :name and pharmacy.pharmacyId = :pharmacyId");
            query.setParameter("name", name.toLowerCase());
            query.setParameter("pharmacyId", pharmacyId);
            return query.list();
        } finally {
            session.close();
        }
    }
    

}
