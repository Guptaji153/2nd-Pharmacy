package com.infinite.jsf.pharmacy.controller;

import com.infinite.ejb.pharmacy.beanImpl.PharmacyEjbImpl;

public class UpdateEquipmentsController {

	private com.infinite.ejb.pharmacy.model.Equipment ejbEquipmentController;
	private PharmacyEjbImpl pharmacyEjbImpl;
    private com.infinite.ejb.pharmacy.model.Equipment ejbEquipment;
	public com.infinite.ejb.pharmacy.model.Equipment getEjbEquipmentController() {
		return ejbEquipmentController;
	}
	public void setEjbEquipmentController(com.infinite.ejb.pharmacy.model.Equipment ejbEquipmentController) {
		this.ejbEquipmentController = ejbEquipmentController;
	}
	public PharmacyEjbImpl getPharmacyEjbImpl() {
		return pharmacyEjbImpl;
	}
	public void setPharmacyEjbImpl(PharmacyEjbImpl pharmacyEjbImpl) {
		this.pharmacyEjbImpl = pharmacyEjbImpl;
	}
	public com.infinite.ejb.pharmacy.model.Equipment getEjbEquipment() {
		return ejbEquipment;
	}
	public void setEjbEquipment(com.infinite.ejb.pharmacy.model.Equipment ejbEquipment) {
		this.ejbEquipment = ejbEquipment;
	}
    
	public String updateEquipmentDetails() {
		return pharmacyEjbImpl.updateEquipment(ejbEquipment);
	}
	
//	public String prepareUpdate(com.infinite.ejb.pharmacy.model.Equipment equ) {
//	System.out.println("preparing update" + equ.getEquipmentId());
//	this.ejbEquipment = equ;
//	return "UpdateEquipments.jsf?faces-redirect=true";
//}
	
	public String prepareUpdate(com.infinite.jsf.pharmacy.model.Equipment jsfEqu) {
	    com.infinite.ejb.pharmacy.model.Equipment equ = new com.infinite.ejb.pharmacy.model.Equipment();
	    equ.setEquipmentId(jsfEqu.getEquipmentId());
	    equ.setEquipmentName(jsfEqu.getEquipmentName());
	    equ.setDescription(jsfEqu.getDescription());
	    equ.setQuantity(jsfEqu.getQuantity());
	    equ.setUnitPrice(jsfEqu.getUnitPrice());
	    equ.setStatus(jsfEqu.getStatus());
	    equ.setPurpose(jsfEqu.getPurpose());
	    equ.setPurchaseDate(jsfEqu.getPurchaseDate());
	    // convert Pharmacy if needed

	    this.ejbEquipment = equ;
	    System.out.println("Converted and setting Equipment for update: " + equ.getEquipmentId());
	    return "UpdateEquipments.jsf?faces-redirect=true";
	}

}
