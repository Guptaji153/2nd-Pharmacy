package com.infinite.ejb.pharmacy.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.infinite.ejb.pharmacy.dao.UpdateEquipmentsDao;
import com.infinite.ejb.pharmacy.model.Equipment;
import com.infinite.ejb.util.ConnectionHelper;

public class UpdateEquipmentsDaoImpl implements UpdateEquipmentsDao {

	Connection connection;
	PreparedStatement pst;
	@Override
	public boolean updateEquipmentDetails(Equipment equipment) {
		try {
			connection = ConnectionHelper.getConnection();
            String sql = "UPDATE equipment SET equipment_name = ?, description = ?, quantity = ?, unit_price = ?, status = ? WHERE equipment_id = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, equipment.getEquipmentName());
            pst.setString(2, equipment.getDescription() );
            pst.setInt(3, equipment.getQuantity());
            pst.setDouble(4, equipment.getUnitPrice());
            pst.setString(5, equipment.getStatus());
            pst.setString(6, equipment.getEquipmentId());
            System.out.println("update"+equipment.getEquipmentId());
            int rows = pst.executeUpdate();
            return rows > 0;
            
		} catch (Exception e) {
			e.printStackTrace();
            return false;
		}
	}
	@Override
	public Equipment getEquipmentById(String equipmentId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Equipment eq= null;
		connection = ConnectionHelper.getConnection();
		 try {
		        connection = ConnectionHelper.getConnection();
		        String sql = "SELECT * FROM Equipment WHERE equipment_id = ?";
		        pst = connection.prepareStatement(sql);
		        pst.setString(1, equipmentId);

		        ResultSet rs = pst.executeQuery();
		        if (rs.next()) {
		        	eq = new Equipment();
		        	eq.setDescription(rs.getString("description"));
		        	eq.setEquipmentId(rs.getString("equipment_id"));
		        	eq.setEquipmentName(rs.getString("equipment_name"));
		        	eq.setPurchaseDate(rs.getDate("purchase_date"));
		        	eq.setQuantity(rs.getInt("quantity"));
		        	eq.setUnitPrice(rs.getDouble("unit_price"));
		        	eq.setPurpose(rs.getString("purpose"));
		        	eq.setStatus(rs.getString("status"));
		        }
		 } catch (SQLException e) {
		        e.printStackTrace();
		    }
		  
		 return eq;
	}

}
