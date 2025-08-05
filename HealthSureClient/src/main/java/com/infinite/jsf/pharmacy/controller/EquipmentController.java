package com.infinite.jsf.pharmacy.controller;

import java.util.List;
import java.util.Comparator;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.ejb.pharmacy.beanImpl.PharmacyEjbImpl;
import com.infinite.jsf.pharmacy.daoImpl.EquipmentDaoImpl;
import com.infinite.jsf.pharmacy.model.Equipment;
import com.infinite.jsf.pharmacy.model.Medicines;

public class EquipmentController {

    private EquipmentDaoImpl equipmentDao;
    private List<Equipment> equipmentList;
    private boolean searchPerformed;
    public boolean isSearchPerformed(){
    	return searchPerformed;
    }

    public void setEquipmentDao(EquipmentDaoImpl equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    public List<Equipment> getEquipmentList() {
//        if (equipmentList == null) {
//            String pharmacyId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pharmacy_id");
//            equipmentList = equipmentDao.getEquipmentByPharmacyId(pharmacyId);
//        }
        return equipmentList;
    }

    // Search
    private String searchText;
    private String searchMode = null;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String searchEquipment() {
        String pharmacyId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pharmacy_id");

        if (searchText != null) {
            searchText = searchText.trim(); 
        }
        if (searchText == null || searchText.trim().isEmpty()) {
            equipmentList = equipmentDao.getEquipmentByPharmacyId(pharmacyId);
        } else if ("starts".equals(searchMode)) {
            equipmentList = equipmentDao.searchEquipmentStartingWith(searchText, pharmacyId);
        } else if ("contains".equals(searchMode)) {
            equipmentList = equipmentDao.searchEquipmentContaining(searchText, pharmacyId);
        }else {
        	equipmentList = equipmentDao.searchEquipmentExactMatch(searchText, pharmacyId);
        }

         currentPage = 1;
         sortCurrentList(); // Re-sort after search
         searchPerformed = true;
         return null;
    }

    // Pagination
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

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        currentPage = 1;
    }

    public int getTotalPages() {
        int total = (equipmentList != null) ? equipmentList.size() : 0;
        return (int) Math.ceil((double) total / pageSize);
    }

    public List<Equipment> getPaginatedEquipment() {
        if (equipmentList == null) {
            return new java.util.ArrayList<>();
        }
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, equipmentList.size());
        return equipmentList.subList(fromIndex, toIndex);
    }

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
    
    public int getShowingFrom() {
	    return (equipmentList == null || equipmentList.isEmpty()) ? 0 : ((currentPage - 1) * pageSize) + 1;
	}

	public int getShowingTo() {
	    if (equipmentList == null || equipmentList.isEmpty())
	        return 0;
	        
	    int toIndex = currentPage * pageSize;
	    return Math.min(toIndex, equipmentList.size());
	}

	public int getTotalRecords() {
	    return equipmentList == null ? 0 : equipmentList.size();
	}

    // Sorting
	private String sortField = "equipmentNam"; // default
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
	    if (equipmentList != null && !equipmentList.isEmpty()) {
	        Comparator<Equipment> comparator = getComparatorForField(sortField);
	        if (comparator != null) {
	            if (!sortAscending) {
	                comparator = comparator.reversed();
	            }
	            equipmentList.sort(comparator);
	        }
	    }
	}


	private Comparator<Equipment> getComparatorForField(String field) {
	    switch (field) {
	        case "equipmentId":
	            return Comparator.comparing(Equipment::getEquipmentId);
	        case "equipmentName":
	            return Comparator.comparing(Equipment::getEquipmentName, Comparator.nullsLast(String::compareToIgnoreCase));
	        case "description":
	        	return Comparator.comparing(Equipment:: getDescription,Comparator.nullsLast(String::compareToIgnoreCase));
	        case "quantityInStock":
	            return Comparator.comparing(Equipment::getQuantity);
	        case "unitPrice":
	            return Comparator.comparing(Equipment::getUnitPrice);
	        case "purchaseDate":
	            return Comparator.comparing(Equipment::getPurchaseDate, Comparator.nullsLast(Date::compareTo));
	        case "status":
	        	return Comparator.comparing(Equipment::getStatus, Comparator.nullsLast(String::compareToIgnoreCase));
	        
	        default:
	            return null;
	    }
	}
	

//    public String ViewPharmacyStocks() {
//        return "ViewEquipments.jsf?faces-redirect=true";
//    }
    
    public String resetSearch() {
    	
    	return "ViewEquipments.jsf?faces-redirect=true";
    }
    
    /** server side ....*/
    private com.infinite.ejb.pharmacy.model.Equipment ejbEquipmentController;
	private PharmacyEjbImpl pharmacyEjbImpl;
    private com.infinite.ejb.pharmacy.model.Equipment ejbEquipment;
    private int originalQuantityInStock;
    
	public int getOriginalQuantityInStock() {
		Object qtyObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("originalQty");
	    if (qtyObj != null) {
	        return (Integer) qtyObj;
	    }
	    return 0;
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
	    if(ejbEquipment.getQuantity() <getOriginalQuantityInStock()) {
	    	context.addMessage("err:quantity", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Quantity can only be increased (Current: " + getOriginalQuantityInStock() +")", null));
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
	

	
	public String prepareUpdate(com.infinite.jsf.pharmacy.model.Equipment med) {
	    try {
	        // Fetch full medicine details from DB using EJB
	        com.infinite.ejb.pharmacy.model.Equipment fetched =
	                pharmacyEjbImpl.getEquipmentById(med.getEquipmentId());

	        if (fetched != null) {
	            // Store EJB medicine object in session
	            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	                .put("ejbEquipment", fetched);

	            // Also storing original quantity for validation
	            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	                .put("originalQty", fetched.getQuantity());
	        }

	        return "UpdateEquipments.jsf?faces-redirect=true"; // redirect to update page

	    } catch (Exception e) {
	        e.printStackTrace();
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error preparing update.", null));
	        return null;
	    }
	}	
}