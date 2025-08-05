package com.infinite.jsf.pharmacy.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remove;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.ejb.pharmacy.beanImpl.PharmacyEjbImpl;
import com.infinite.jsf.pharmacy.daoImpl.MedicinesDaoImpl;
import com.infinite.jsf.pharmacy.model.Medicines;

public class MedicinesController {

	private MedicinesDaoImpl medicinesDao;
	private List<Medicines> medicinesList;
	private boolean searchPerformed;

	public boolean isSearchPerformed() {
		return searchPerformed;
	}

	public void setMedicinesDao(MedicinesDaoImpl medicinesDao) {
		this.medicinesDao = medicinesDao;
	}

	/**
	 * list to store medicines
	 */
	public List<Medicines> getMedicinesList() {
//        if (medicinesList == null) {
//           // medicinesList = medicinesDao.getAllMedicines();
//        	String pharmacyId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pharmacy_id");
//        	medicinesList = medicinesDao.getMedicinesByPharmacyId(pharmacyId);
//        }
		return medicinesList;
	}

	// Search related fields
	private String searchText;
	private String searchMode = null; // default

	// Getter and Setter for searchText
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	// Getter and Setter for searchMode
	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * search medicines based on start with and contains
	 */

	/**  */
	public String searchMedicines() {
		String pharmacyId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("pharmacy_id");

		// Trim spaces around searchText
		if (searchText != null) {
			searchText = searchText.trim();
		}

		if (searchText == null || searchText.isEmpty()) {
			medicinesList = medicinesDao.getMedicinesByPharmacyId(pharmacyId);
		}
//       else if("null".equals(searchMode)) {
//        	medicinesList = medicinesDao.getMedicinesByPharmacyId(pharmacyId);
//        }
		else if ("starts".equals(searchMode)) {
			medicinesList = medicinesDao.searchMedicinesStartingWith(searchText, pharmacyId);
		} else if ("contains".equals(searchMode)) {
			medicinesList = medicinesDao.searchMedicinesContaining(searchText, pharmacyId);
		} else {
			medicinesList = medicinesDao.searchMedicinesExactMatch(searchText, pharmacyId);
		}

		sortCurrentList();
		currentPage = 1;
		searchPerformed = true;
		return null;
	}

	/**
	 * Pagination Support
	 */

	private int currentPage = 1;
	private int pageSize = 5;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	// reset to first page when page size changes
	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
		currentPage = 1;
	}

	public int getTotalPages() {
		if (medicinesList == null || medicinesList.isEmpty())
			return 1;
		return (int) Math.ceil((double) medicinesList.size() / pageSize);
	}

	public List<Medicines> getPaginatedMedicines() {
		if (medicinesList == null) {
			String pharmacyId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("pharmacy_id");
			
			medicinesList = medicinesDao.getMedicinesByPharmacyId(pharmacyId);
			Object savedPage = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentPage");
			if(savedPage != null) {
				currentPage = (Integer)savedPage;
			}else {
				currentPage = 1; // reset to first page
			}
			
			searchPerformed = true; 
		}

		int fromIndex = (currentPage - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, medicinesList.size());
		return medicinesList.subList(fromIndex, toIndex);
	}

	// Navigation
	public void nextPage() {
		if (currentPage < getTotalPages()) {
			currentPage++;
		}
	}

	public void previousPage() {
		if (currentPage > 1) {
			currentPage--;
		}
	}

	// ............
	public int getShowingFrom() {
		return (medicinesList == null || medicinesList.isEmpty()) ? 0 : ((currentPage - 1) * pageSize) + 1;
	}

	public int getShowingTo() {
		if (medicinesList == null || medicinesList.isEmpty())
			return 0;

		int toIndex = currentPage * pageSize;
		return Math.min(toIndex, medicinesList.size());
	}

	public int getTotalRecords() {
		return medicinesList == null ? 0 : medicinesList.size();
	}

	/**
	 * sorting..............
	 */

	private String sortField = ""; // default
	private boolean sortAscending = true;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isSortAscending() {
		return sortAscending;
	}

	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}

	public void sortByAsc(String field) {
		this.sortField = field;
		this.sortAscending = true;
		sortCurrentList();
	}

	public void sortByDesc(String field) {
		this.sortField = field;
		this.sortAscending = false;
		sortCurrentList();
	}

	private void sortCurrentList() {
		if (medicinesList != null && !medicinesList.isEmpty()) {
			Comparator<Medicines> comparator = getComparatorForField(sortField);
			if (comparator != null) {
				if (!sortAscending) {
					comparator = comparator.reversed();
				}
				medicinesList.sort(comparator);
			}
		}
	}

	private Comparator<Medicines> getComparatorForField(String field) {
		switch (field) {
		case "medicineId":
			return Comparator.comparing(Medicines::getMedicineId);
		case "medicineName":
			return Comparator.comparing(Medicines::getMedicineName, Comparator.nullsLast(String::compareToIgnoreCase));
		case "unitPrice":
			return Comparator.comparing(Medicines::getUnitPrice);
		case "expiryDate":
			return Comparator.comparing(Medicines::getExpiryDate, Comparator.nullsLast(Date::compareTo));
		case "quantityInStock":
			return Comparator.comparing(Medicines::getQuantityInStock);
		case "description":
			return Comparator.comparing(Medicines::getDescription);
		default:
			return null;
		}
	}

	public Date getCurrentDate() {
		return new Date();
	}

//to call in home pharmacy page
//	public String ViewMedicineStocks() {
//		return "ViewMedicines.jsf?faces-redirect=true";
//	}

	/** refresh */
	public String resetSearch() {
//		this.searchText = null;
//		this.searchMode = null;
//		this.medicinesList = null;
//		this.currentPage = 1;
//		this.searchPerformed = false;
//		this.setSortField(null);
		return "ViewMedicines.jsp?faces-redirect=true";
	}

	/** EJB side ... */
	private com.infinite.ejb.pharmacy.model.Medicines ejbMedicine;
	/** object from faces config(bin) */
	private PharmacyEjbImpl pharmacyEjbImpl;
	private com.infinite.ejb.pharmacy.model.Equipment ejbEquipment;
	private int originalQuantityInStock;
	private String medicineName;
	private String description;
	private double unitPrice;
	private String purpose;

	/**
	 * getters and setters
	 * 
	 */

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
		if (ejbMedicine == null) {
			ejbMedicine = (com.infinite.ejb.pharmacy.model.Medicines) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("ejbMedicine");
		}
		return ejbMedicine;
	}

	public int getOriginalQuantityInStock() {
		Object qtyObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("originalQty");
		if (qtyObj != null) {
			return (Integer) qtyObj;
		}
		return 0;
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

	/** end of getters and setters */
//	public String updateMedicineDetails(com.infinite.ejb.pharmacy.model.Medicines med) {
//		return pharmacyEjbImpl.updateMedicine(med);
//	}

	/**
	 * updating medicines details adding validations while updating
	 */

	public String updateMedicineDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isValid = true;

		// --- validations for update ---
		if (ejbMedicine.getMedicineName() == null || ejbMedicine.getMedicineName().trim().length() < 3) {
			context.addMessage("updateForm:medicineName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Medicine name must be at least 3 characters long.", null));
			isValid = false;
		}
		if (ejbMedicine.getExpiryDate() != null && ejbMedicine.getExpiryDate().before(new Date())) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot update. Medicine is already expired.", null));
			isValid = false;
		}

		if (ejbMedicine.getDescription() == null || ejbMedicine.getDescription().trim().isEmpty()) {
			context.addMessage("updateForm:description",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Description cannot be empty.", null));
			isValid = false;
		}

		if (ejbMedicine.getQuantityInStock() < 0) {
			context.addMessage("updateForm:quantity",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity cannot be negative.", null));
			isValid = false;
		}

		if (ejbMedicine.getQuantityInStock() < getOriginalQuantityInStock()) {
			context.addMessage("updateForm:quantity", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Quantity can only be increased (Current: " + getOriginalQuantityInStock() + ")", null));
			isValid = false;
		}

		if (ejbMedicine.getUnitPrice() < 0) {
			context.addMessage("updateForm:unitPrice",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unit Price must be a positive number.", null));
			isValid = false;
		}

		if (ejbMedicine.getPurpose() == null || ejbMedicine.getPurpose().trim().isEmpty()) {
			context.addMessage("updateForm:purpose",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Purpose cannot be empty.", null));
			isValid = false;
		}

		if (!isValid) {
			context.validationFailed();
			return null;
		}

		// --- EJB method call ---
		boolean success = pharmacyEjbImpl.updateMedicine(ejbMedicine);

		if (success) {
			// --- clear session for better memory ---
			context.getExternalContext().getSessionMap().remove("ejbMedicine");
			context.getExternalContext().getSessionMap().remove("originalQty");

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicine updated successfully.", null));

			// Redirecting back to view page
			return null;
		} else {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update failed. Please try again.", null));
			return null;
		}
	}

//	public String prepareUpdate(com.infinite.ejb.pharmacy.model.Medicines meds) {
//		System.out.println("preparing update" + meds.getMedicineName());
//		this.ejbMedicine = meds;
//		return "UpdateMedicine.jsf?faces-redirect=true";
//	}

	/**
	 * manually converting to ejb side model class
	 * 
	 */

	public String prepareUpdate(com.infinite.jsf.pharmacy.model.Medicines meds) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentPage", this.currentPage);
			// Fetch full medicine details from DB using EJB
			com.infinite.ejb.pharmacy.model.Medicines fetched = pharmacyEjbImpl.getMedicineById(meds.getMedicineId());

			if (fetched != null) {
				// Store EJB medicine object in session
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ejbMedicine", fetched);

				// Also storing original quantity for validation
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("originalQty",
						fetched.getQuantityInStock());
			}

			return "UpdateMedicine.jsf?faces-redirect=true"; // redirect to update page

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error preparing update.", null));
			return null;
		}
	}

	/**
	 * Redirect view medicines
	 */

	
	public String redirectToView() {
	    
	    return "ViewMedicines.jsf"; // no redirect
	}
		
}
