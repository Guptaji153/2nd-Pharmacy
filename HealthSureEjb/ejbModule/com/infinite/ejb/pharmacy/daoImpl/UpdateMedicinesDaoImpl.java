package com.infinite.ejb.pharmacy.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.infinite.ejb.pharmacy.dao.UpdateMedicinesDao;
import com.infinite.ejb.pharmacy.model.Medicines;
import com.infinite.ejb.util.ConnectionHelper;

public class UpdateMedicinesDaoImpl implements UpdateMedicinesDao {

	Connection connection;
	PreparedStatement pst;
	@Override
	public boolean updateMedicineDetails(Medicines med) {
		try {
            connection = ConnectionHelper.getConnection(); 
            String sql = "UPDATE Medicines SET medicine_name = ?, description = ?, quantity_in_stock = ?, unit_price = ?, purpose = ? WHERE medicine_id = ?";
            pst = connection.prepareStatement(sql);

            pst.setString(1, med.getMedicineName());
            pst.setString(2, med.getDescription());
            pst.setInt(3, med.getQuantityInStock());
            pst.setDouble(4, med.getUnitPrice());
            pst.setString(5, med.getPurpose());
            pst.setString(6, med.getMedicineId());

            System.out.println("update"+med.getMedicineId());

            int rows = pst.executeUpdate();
            return rows > 0;

            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
	
	@Override
	public Medicines getMedicineById(String medicineId) throws ClassNotFoundException {
	    Medicines med = null;

	    try {
	        connection = ConnectionHelper.getConnection();
	        String sql = "SELECT * FROM Medicines WHERE medicine_id = ?";
	        pst = connection.prepareStatement(sql);
	        pst.setString(1, medicineId);

	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            med = new Medicines();
	            med.setMedicineId(rs.getString("medicine_id"));
	            med.setMedicineName(rs.getString("medicine_name"));
	            med.setDescription(rs.getString("description"));
	            med.setQuantityInStock(rs.getInt("quantity_in_stock"));
	            med.setUnitPrice(rs.getDouble("unit_price"));
	            med.setPurpose(rs.getString("purpose"));
	            med.setBatchNo(rs.getString("batch_no"));
	            med.setExpiryDate(rs.getDate("expiry_date"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return med;
	}
	
	
}
