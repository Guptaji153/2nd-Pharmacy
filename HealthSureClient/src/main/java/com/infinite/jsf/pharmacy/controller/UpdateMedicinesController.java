package com.infinite.jsf.pharmacy.controller;

import com.infinite.ejb.pharmacy.beanImpl.PharmacyEjbImpl;
import com.infinite.ejb.pharmacy.model.Medicines;

public class UpdateMedicinesController {

	private com.infinite.ejb.pharmacy.model.Medicines ejbMedicine;
	private PharmacyEjbImpl pharmacyEjbImpl;
    private com.infinite.ejb.pharmacy.model.Equipment ejbEquipment;
    
    
	public com.infinite.ejb.pharmacy.model.Equipment getEjbEquipment() {
		return ejbEquipment;
	}
	public void setEjbEquipment(com.infinite.ejb.pharmacy.model.Equipment ejbEquipment) {
		this.ejbEquipment = ejbEquipment;
	}
	public com.infinite.ejb.pharmacy.model.Medicines getEjbMedicine() {
		return ejbMedicine;
	}
	public void setEjbMedicine(com.infinite.ejb.pharmacy.model.Medicines ejbMedicine) {
		this.ejbMedicine = ejbMedicine;
	}
	public PharmacyEjbImpl getPharmacyEjbImpl() {
		return pharmacyEjbImpl;
	}
	public void setPharmacyEjbImpl(PharmacyEjbImpl pharmacyEjbImpl) {
		this.pharmacyEjbImpl = pharmacyEjbImpl;
	}
	
//	public String updateMedicineDetails(com.infinite.ejb.pharmacy.model.Medicines med) {
//		return pharmacyEjbImpl.updateMedicine(med);
//	}
	public String updateMedicineDetails() {
		return pharmacyEjbImpl.updateMedicine(ejbMedicine);
		//return "ViewMedicines.jsf?faces-redirect=true";
	}
	
//	public String prepareUpdate(com.infinite.ejb.pharmacy.model.Medicines meds) {
//		System.out.println("preparing update" + meds.getMedicineName());
//		this.ejbMedicine = meds;
//		return "UpdateMedicine.jsf?faces-redirect=true";
//	}
	
	public String prepareUpdate(com.infinite.jsf.pharmacy.model.Medicines med) {
	    // Manual conversion (if needed)
	    com.infinite.ejb.pharmacy.model.Medicines converted = new com.infinite.ejb.pharmacy.model.Medicines();
	    converted.setMedicineId(med.getMedicineId());
	    converted.setMedicineName(med.getMedicineName());
	    converted.setDescription(med.getDescription());
	    converted.setQuantityInStock(med.getQuantityInStock());
	    converted.setExpiryDate(med.getExpiryDate());
	    converted.setUnitPrice(med.getUnitPrice());
	    converted.setPurpose(med.getPurpose());
	    converted.setBatchNo(med.getBatchNo());
	    // Optionally: converted.setPharmacy(…);
	    
	    System.out.println("update"+med.getMedicineId());
	    
	    this.ejbMedicine = converted;

	    return "UpdateMedicine.jsf?faces-redirect=true";
	}

}
