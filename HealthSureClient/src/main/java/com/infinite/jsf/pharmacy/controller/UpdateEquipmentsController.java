package com.infinite.jsf.pharmacy.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.ejb.pharmacy.beanImpl.PharmacyEjbImpl;

public class UpdateEquipmentsController {

	private com.infinite.ejb.pharmacy.model.Equipment ejbEquipmentController;
	private PharmacyEjbImpl pharmacyEjbImpl;
    private com.infinite.ejb.pharmacy.model.Equipment ejbEquipment;
    private int originalQuantityInStock;
	public int getOriginalQuantityInStock() {
		return originalQuantityInStock;
	}
	public void setOriginalQuantityInStock(int originalQuantityInStock) {
		this.originalQuantityInStock = originalQuantityInStock;
	}
    
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
    
	
//	public String updateEquipmentDetails() {
//		return pharmacyEjbImpl.updateEquipment(ejbEquipment);
//	}
	
	public String updateEquipmentDetails() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    boolean isValid = true;

	    // Validate Equipment Name
	    if (ejbEquipment.getEquipmentName() == null || ejbEquipment.getEquipmentName().trim().length() < 3) {
	        context.addMessage("err:equipmentName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Equipment name must be at least 3 characters long.", null));
	        isValid = false;
	    }

	    // Validate Description
	    if (ejbEquipment.getDescription() == null || ejbEquipment.getDescription().trim().isEmpty()) {
	        context.addMessage("err:description", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Description cannot be empty.", null));
	        isValid = false;
	    }

	    // Validate Quantity
	    if (ejbEquipment.getQuantity() < 0) {
	        context.addMessage("err:quantity", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Quantity cannot be negative.", null));
	        isValid = false;
	    }
	    if(ejbEquipment.getQuantity() <originalQuantityInStock) {
	    	context.addMessage("err:quantity", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Quantity can only be increased (Current: " + originalQuantityInStock +")", null));
	        isValid = false;
	    }

	    // Validate Unit Price
	    if (ejbEquipment.getUnitPrice() < 0) {
	        context.addMessage("err:unitPrice", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Unit Price must be a positive number.", null));
	        isValid = false;
	    }

	    // Validate Status
	    if (ejbEquipment.getStatus() == null || ejbEquipment.getStatus().trim().isEmpty()) {
	        context.addMessage("err:status", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Status cannot be empty.", null));
	        isValid = false;
	    }

	    if (!isValid) {
	        context.validationFailed();
	        return null;
	    }

	    boolean success = pharmacyEjbImpl.updateEquipment(ejbEquipment);

	    if (success) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Equipment updated successfully.", null));
	        return "viewEquipments.jsf?faces-redirect=true";
	    } else {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to update equipment.", null));
	        return null;
	    }
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
	   

	    this.ejbEquipment = equ;
	    this.originalQuantityInStock=equ.getQuantity();
	    System.out.println("Converted and setting Equipment for update: " + equ.getEquipmentId());
	    return "UpdateEquipments.jsf?faces-redirect=true";
	}


}
