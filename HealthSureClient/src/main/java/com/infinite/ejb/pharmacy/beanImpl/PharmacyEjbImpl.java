package com.infinite.ejb.pharmacy.beanImpl;


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
    

}
