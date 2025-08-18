package com.infinite.ejb.pharmacy.dao;

import java.sql.SQLException;

import com.infinite.ejb.pharmacy.model.Medicines;

public interface UpdateMedicinesDao {

	public boolean updateMedicineDetails(Medicines med);
	public Medicines getMedicineById(String medicineId) throws ClassNotFoundException;
}
