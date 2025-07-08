package com.infinite.ejb.pharmacy.beanImpl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    public String updateMedicine(Medicines med) {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean isValid = true;

        // Validate Medicine Name
        if (med.getMedicineName() == null || med.getMedicineName().trim().length() < 3) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Medicine name must be at least 3 characters long.", ""));
            isValid = false;
        }

        // Validate Description
        if (med.getDescription() == null || med.getDescription().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Description cannot be empty.", ""));
            isValid = false;
        }

        // Validate Quantity
        if (med.getQuantityInStock() < 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Quantity cannot be negative.", ""));
            isValid = false;
        }

        // Validate Unit Price
        if (med.getUnitPrice() < 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unit Price must be a positive number.", ""));
            isValid = false;
        }

        // Validate Purpose
        if (med.getPurpose() == null || med.getPurpose().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Purpose cannot be empty.", ""));
            isValid = false;
        }

        if (!isValid) {
            context.validationFailed();
            return null; // Stay on same page
        }

        // If validations pass, call EJB to update
        boolean success = remote.updateMedicineDetails(med);

        if (success) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Medicine updated successfully.", ""));
            return "viewMedicines.jsf?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to update medicine. Please try again.", ""));
            return null;
        }
    }
    
    
    public String updateEquipment(Equipment equipment) {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean isValid = true;

        // Validate Medicine Name
        if (equipment.getEquipmentName() == null || equipment.getEquipmentName().trim().length() < 3) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "equipment name must be at least 3 characters long.", ""));
            isValid = false;
        }

        // Validate Description
        if (equipment.getDescription() == null || equipment.getDescription().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Description cannot be empty.", ""));
            isValid = false;
        }

        // Validate Quantity
        if (equipment.getQuantity() < 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Quantity cannot be negative.", ""));
            isValid = false;
        }

        // Validate Unit Price
        if (equipment.getUnitPrice() < 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unit Price must be a positive number.", ""));
            isValid = false;
        }

        // Validate Purpose
        if (equipment.getStatus() == null || equipment.getPurpose().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Status cannot be empty.", ""));
            isValid = false;
        }

        if (!isValid) {
            context.validationFailed();
            return null; // Stay on same page
        }

        // If validations pass, call EJB to update
        boolean success = remote.updateEquipmentDetails(equipment);

        if (success) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Equipment updated successfully.", ""));
            return "viewEquipments.jsf?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Failed to update equipment. Please try again.", ""));
            return null;
        }
    }
}
