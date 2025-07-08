package com.infinite.ejb.pharmacy.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

}
