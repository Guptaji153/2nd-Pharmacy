package com.infinite.ejb.pharmacy.dao;

import java.sql.SQLException;

import com.infinite.ejb.pharmacy.model.Equipment;


public interface UpdateEquipmentsDao {

	public boolean updateEquipmentDetails(Equipment equipment);
	public Equipment getEquipmentById(String equipmentId) throws ClassNotFoundException, SQLException;
}
