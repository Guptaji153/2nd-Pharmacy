package com.infinite.ejb.pharmacy.bean;

import java.sql.SQLException;

import javax.ejb.Remote;

import com.infinite.ejb.pharmacy.model.Equipment;
import com.infinite.ejb.pharmacy.model.Medicines;


@Remote
public interface PharmacyBeanRemote {

	public boolean updateMedicineDetails(com.infinite.ejb.pharmacy.model.Medicines med);
	
	public boolean updateEquipmentDetails(com.infinite.ejb.pharmacy.model.Equipment equipment);
	
	public Medicines getMedicineById(String medicineId) throws ClassNotFoundException;
	
	public Equipment getEquipmentById(String equipmentId) throws ClassNotFoundException, SQLException;
}
