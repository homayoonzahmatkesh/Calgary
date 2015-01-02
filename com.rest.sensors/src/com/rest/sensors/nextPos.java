package com.rest.sensors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class nextPos implements Job{



	public static Mobile_Sensor[] nextPos(Mobile_Sensor[] sensor ,int number_of_sensors, double Time_interval) {
		   
		double distance = Time_interval*41.667;
	    double lon2, lat2,temp;
	    Random RndTemp=new Random();
	    double range = 2*Math.PI;
	    double randomDir;
	    Mobile_Sensor[] new_sensor=new Mobile_Sensor[number_of_sensors];
	    
	    for (int i=0;i<number_of_sensors;i++){
	     
	    		double lat=sensor[i].getLat();
	    		double lon=sensor[i].getLon();
	    		double bearing=sensor[i].getDir();
	    		double a=6377563.396;
	    		
	    		double e=0.08181919092890624;
	    		double ep=0.08209443803685366;
	    		
	    		double N1=(a)/Math.sqrt((1-(Math.pow(e,2))*Math.pow((Math.sin(lon)),2)));
	    		double V=(distance/N1)*Math.sin(bearing);
	    		double U=(distance/N1)*Math.cos(bearing);
	    		double eta2=(Math.pow(ep, 2))*Math.cos(lon);
	    		double t=Math.tan(lat);
	    		
	    		lat2=lat+(1+eta2)*U+(1+eta2)*((-(Math.pow(V, 2)*t)/2)-((3/2)*(Math.pow(U, 2))*(eta2)*(t)));
	    		lon2=lon+(V/Math.cos(lat))+(U*V*t)/(Math.cos(lat));

	      boolean res = Point_in_polygon(lat2,lon2);



      while(!res){
    	  
    	  
    	  randomDir =  (RndTemp.nextDouble() * (range));
    	  
    	  V=(distance/N1)*Math.sin(randomDir);
  		  U=(distance/N1)*Math.cos(randomDir);

    	  lat2=lat+(1+eta2)*U+(1+eta2)*((-(Math.pow(V, 2)*t)/2)-((3/2)*(Math.pow(U, 2))*(eta2)*(t)));
  		  lon2=lon+(V/Math.cos(lat))+(U*V*t)/(Math.cos(lat));

	      res = Point_in_polygon(lat2,lon2);

	      System.out.println(res);
	      
	      }    
      

	      
	      temp = (RndTemp.nextDouble() * (35)) -5;

		new_sensor[i]=new Mobile_Sensor(sensor[i].getID(),lon2,lat2,temp,sensor[i].getDir());
			
	    }


	    return new_sensor;
	}

	
	public static boolean Point_in_polygon(double lon, double lat) {
		
		final GeometryFactory gf = new GeometryFactory();
	    
	    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
	    points.add(new Coordinate(-114.256525,51.14156667));
	    points.add(new Coordinate(-113.925075,51.10081389));
	    points.add(new Coordinate(-113.9556861,50.88296111));
	    points.add(new Coordinate(-114.1173611,50.89308056));
	    points.add(new Coordinate(-114.1341361,50.99975278));
	    points.add(new Coordinate(-114.2133694,51.01778889));
	    points.add(new Coordinate(-114.256525,51.14156667));
	    final Polygon polygon2 = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
	        .toArray(new Coordinate[points.size()])), gf), null);

	    final Coordinate coord = new Coordinate(lon, lat);
	    final Point point2 = gf.createPoint(coord);
	    boolean res = point2.within(polygon2);
	return res;
		
	}


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException{
		
		int Number=0;

		System.out.println("Every2 second job triggered");

		 Mobile_Sensor[] sensor = new Mobile_Sensor[100];
		 
		try {
			Class.forName("org.postgresql.Driver");
			
			Connection connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/calgary", "postgres",
					"123456789");

			String selectSQL = "select sensor_id,ST_X(actual_position) AS lon ,ST_Y(actual_position) AS lat,observed_value, Direction from sensor ORDER BY sensor_id asc";
			PreparedStatement preparedStatement = connection
					.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				sensor[Number]  = new Mobile_Sensor(rs.getString("sensor_id"), Double.parseDouble(rs.getString("lat")),
						Double.parseDouble(rs.getString("lon")), Double.parseDouble(rs
						.getString("observed_value")),Double.parseDouble(rs.getString("Direction")));
				Number=Number+1;
			}
			
			
			connection.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   

		Mobile_Sensor[] newPos=nextPos(sensor ,Number,2);
	
		Connection connection2 = null;
		for (int i = 0; i < 100; i++) {
//			System.out.println(newPos[i].getID() + "    " + newPos[i].getLat()
//					+ "    " + newPos[i].getLon() + "      "
//					+ newPos[i].getTemprature() + "  " + newPos[i].getDir());
			
			Statement stmt;
			try {
				
				Class.forName("org.postgresql.Driver");
				
				 connection2 = DriverManager.getConnection(
						"jdbc:postgresql://localhost:5432/calgary", "postgres",
						"123456789");
				stmt = connection2.createStatement();
			
				String sql = "update sensor  set actual_position=ST_GeometryFromText('POINT("
						+ newPos[i].getLat()
						+ " "
						+ newPos[i].getLon()
						+ ")', 4326) , observed_value="
						+ newPos[i].getTemprature()
						+ " ,Direction="
						+ newPos[i].getDir()
						+ "  where sensor_id like '%"
						+ newPos[i].getID() + "%'";
			
				stmt.executeUpdate(sql);
				connection2.close();
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 


		}
					

	}
}
