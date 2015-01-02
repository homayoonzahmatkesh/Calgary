package com.rest.jersey.sensors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// this is the implementation of the interface which is responsible for retriving data from DB
public class TransactionBoImpl implements TransactionBo {
	

	public List<sensors> req() {
		
		  List<sensors> sensorList=new ArrayList<sensors>();
		  try {
			Class.forName("org.postgresql.Driver");
		 
		   Connection restConnection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/calgary", "postgres",
					"123456789");
		   
			
			String selectSQL = "select sensor_id,ST_X(actual_position) AS lon ,ST_Y(actual_position) AS lat,observed_value from sensor ORDER BY sensor_id asc";
			PreparedStatement preparedStatement = restConnection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				sensors sens = new sensors();
				 sens.ID=rs.getString("sensor_id");
				 sens.Lat=Double.parseDouble(rs.getString("lat"));
				 sens.Lon=Double.parseDouble(rs.getString("lon"));
				 sens.Temprature=Double.parseDouble(rs.getString("observed_value"));
				 sensorList.add(sens);
			}
			restConnection.close();
		  }catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
		return sensorList;
	}
 
}