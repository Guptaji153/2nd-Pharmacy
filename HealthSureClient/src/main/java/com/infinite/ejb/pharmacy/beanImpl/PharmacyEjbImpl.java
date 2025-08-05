package com.infinite.ejb.pharmacy.beanImpl;


import java.sql.SQLException;

import javax.naming.NamingException;

import com.infinite.ejb.pharmacy.bean.PharmacyBeanRemote;
import com.infinite.ejb.pharmacy.model.Equipment;
import com.infinite.ejb.pharmacy.model.Medicines;
import com.infinite.ejb.remoteHelper.RemoteHelper;

public class PharmacyEjbImpl {

    static PharmacyBeanRemote remote;
    

    static {
        try {
            remote = RemoteHelper.lookupRemotePharmacyBean(); // JNDI lookup
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    
    public boolean updateMedicine(Medicines med) {
    	return remote.updateMedicineDetails(med);
    }
    
    public boolean updateEquipment(Equipment equipment) {
    	return remote.updateEquipmentDetails(equipment);
    }
    
    public Medicines getMedicineById(String medicineId) throws ClassNotFoundException {
    	return remote.getMedicineById(medicineId);
    }
    
    public Equipment getEquipmentById(String equipmentId) throws ClassNotFoundException, SQLException{
    	return remote.getEquipmentById(equipmentId);
    }

}
