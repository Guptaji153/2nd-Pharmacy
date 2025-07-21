package com.infinite.jsf.pharmacy.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.ejb.pharmacy.beanImpl.PharmacyEjbImpl;

public class UpdateMedicinesController {

	private com.infinite.ejb.pharmacy.model.Medicines ejbMedicine;
	private PharmacyEjbImpl pharmacyEjbImpl;
    private com.infinite.ejb.pharmacy.model.Equipment ejbEquipment;
    private int originalQuantityInStock;
    
	public int getOriginalQuantityInStock() {
		return originalQuantityInStock;
	}
	public void setOriginalQuantityInStock(int originalQuantityInStock) {
		this.originalQuantityInStock = originalQuantityInStock;
	}
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
	    FacesContext context = FacesContext.getCurrentInstance();
	    boolean isValid = true;

	    // Validation
	    if (ejbMedicine.getMedicineName() == null || ejbMedicine.getMedicineName().trim().length() < 3) {
	        context.addMessage("updateForm:medicineName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Medicine name must be at least 3 characters long.", null));
	        isValid = false;
	    }

	    if (ejbMedicine.getDescription() == null || ejbMedicine.getDescription().trim().isEmpty()) {
	        context.addMessage("updateForm:description", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Description cannot be empty.", null));
	        isValid = false;
	    }

	    if (ejbMedicine.getQuantityInStock() < 0) {
	        context.addMessage("updateForm:quantity", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Quantity cannot be negative.", null));
	        isValid = false;
	    }
	    if(ejbMedicine.getQuantityInStock() <originalQuantityInStock) {
	    	context.addMessage("updateForm:quantity", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Quantity can only be increased (Current: " + originalQuantityInStock +")", null));
	        isValid = false;
	    }

	    if (ejbMedicine.getUnitPrice() < 0) {
	        context.addMessage("updateForm:unitPrice", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Unit Price must be a positive number.", null));
	        isValid = false;
	    }

	    if (ejbMedicine.getPurpose() == null || ejbMedicine.getPurpose().trim().isEmpty()) {
	        context.addMessage("updateForm:purpose", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Purpose cannot be empty.", "Purpose"));
	        isValid = false;
	    }

	    if (!isValid) {
	        context.validationFailed();
	        return null;
	    }

	    // Call EJB logic
	    boolean success = pharmacyEjbImpl.updateMedicine(ejbMedicine);

	    if (success) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                "Medicine updated successfully.", null));
	        return "viewMedicines.jsf?faces-redirect=true";
	    } else {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Failed to update medicine. Please try again.", null));
	        return null;
	    }
	}
	
//	public String prepareUpdate(com.infinite.ejb.pharmacy.model.Medicines meds) {
//		System.out.println("preparing update" + meds.getMedicineName());
//		this.ejbMedicine = meds;
//		return "UpdateMedicine.jsf?faces-redirect=true";
//	}
	
	public String prepareUpdate(com.infinite.jsf.pharmacy.model.Medicines med) {
	    // Manual conversion
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
	    this.originalQuantityInStock=med.getQuantityInStock();

	    return "UpdateMedicine.jsf?faces-redirect=true";
	}

}
