package com.rest.sensors;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

// creating Get function for RESTfull web service to generate JSON from Sensors of DB
import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;

	@Path("/sensors")
	public class SensorInstance {
	  @GET
	  @Path("/getJson")
	  @Consumes("Application/json")
	  
	  public String getJson() throws TransformationException, IOException, SQLException, ClassNotFoundException {
		  
		  List<sensors> sensorList=new ArrayList<sensors>();
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
			
			//Using Genson Library for creatin JSON from String
			
	    Genson genson = new Genson();
	    String json = genson.serialize(sensorList); 
	    genson.serialize(true);
	    
	    return json;
	    
	  }
	  
	} 
