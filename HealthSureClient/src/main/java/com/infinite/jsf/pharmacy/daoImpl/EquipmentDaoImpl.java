package com.infinite.jsf.pharmacy.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import com.infinite.jsf.pharmacy.dao.EquipmentDao;
import com.infinite.jsf.pharmacy.model.Equipment;
import com.infinite.jsf.util.SessionHelper;

public class EquipmentDaoImpl implements EquipmentDao {
	private static final Logger log = Logger.getLogger("com.infinite.jsf.pharmacy.daoImpl.PharmacyDaoImpl");

	/**
	 * Fetch all equipment of a specific pharmacy by using their id
	 *  */
	@Override
	public List<Equipment> getEquipmentByPharmacyId(String pharmacyId) {
		log.info("get equipment by equipmentId");
        Session session = SessionHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from Equipment where pharmacy.pharmacyId = :pharmacyId");
            query.setParameter("pharmacyId", pharmacyId);
            return query.list();
        } finally {
            session.close();
        }
	}

	/** 
     * Search equipment based on start with for a particular pharmacy 
     *  */
	@Override
	public List<Equipment> searchEquipmentStartingWith(String name, String pharmacyId) {
		log.info("searching equipment start with");
		 Session session = SessionHelper.getSessionFactory().openSession();
	        try {
	            Query query = session.createQuery("from Equipment where lower(equipmentName) like :name and pharmacy.pharmacyId = :pharmacyId");
	            query.setParameter("name", name.toLowerCase() + "%");
	            query.setParameter("pharmacyId", pharmacyId);
	            return query.list();
	        } finally {
	            session.close();
	        }
	}

	/** 
     * Search equipment based on contain with for a particular pharmacy 
     * */
	@Override
	public List<Equipment> searchEquipmentContaining(String name, String pharmacyId) {
		log.info("searching equipment contains with");
		 Session session = SessionHelper.getSessionFactory().openSession();
	        try {
	            Query query = session.createQuery("from Equipment where lower(equipmentName) like :name and pharmacy.pharmacyId = :pharmacyId");
	            query.setParameter("name", "%" + name.toLowerCase() + "%");
	            query.setParameter("pharmacyId", pharmacyId);
	            return query.list();
	        } finally {
	            session.close();
	        }
	}

	@Override
	public List<Equipment> searchEquipmentExactMatch(String name, String pharmacyId) {
		// TODO Auto-generated method stub
		log.info("searching equipment with exact same equipment name");
		Session session = SessionHelper.getSessionFactory().openSession();
		try {
			Query query = session.createQuery("from Equipment where lower(equipmentName) = :name and pharmacy.pharmacyId = :pharmacyId");
			query.setParameter("name", name.toLowerCase());
            query.setParameter("pharmacyId", pharmacyId);
            return query.list();
		}finally {
			session.close();
		}
		
	}

}
