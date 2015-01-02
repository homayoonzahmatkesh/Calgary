package com.sensors.creation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

//This program is for creating random sensors and then save them into pre designed DB

public class initializeSensors {

	/**
	 * @param args
	 */
	
	private static Connection connection;
	static Mobile_Sensor[] new_Pos = null;
	
	
	public static String[] sensor_names(int number_of_sensors) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.

	    String[] names=new String[number_of_sensors];

	    for (int i=1;i<number_of_sensors+1;i++){
	    	
			String b=String.format("%03d\n",i);

	    	names[i-1]="id"+b;
	    }

	    return names;
	}
	public static Mobile_Sensor[] randpos(double minlon, double maxlon, double minlat, double maxlat,int number_of_sensors) {


	    Random rand = new Random();
	    Random rnd=new Random();
	    Random temp=new Random();
	    Random Dir=new Random();

	    double range = 2*Math.PI;
	    double randomlon;
	    double randomlat;
	    double randomTemp;
	    double randomDir;

	    Mobile_Sensor[] sensor=new Mobile_Sensor[number_of_sensors];
	    String[] ID=new String[number_of_sensors];
	    // Create ID Sensors as asked in question 
	    ID=sensor_names(number_of_sensors);


	    for (int i=0;i<number_of_sensors;i++){
	    	
	    	// create random numbers for Temprature, longitude, latitude and direction for sensors 
	      randomlon = (rand.nextDouble() * (maxlon-minlon)) + minlon;
	      
	      randomlat = (rnd.nextDouble() * (maxlon-minlat)) + minlat;
	      
	      randomTemp = (Dir.nextDouble() * (35)) -5;
	      
	      randomDir =  (temp.nextDouble() * (range));
	      /// check if the created point is there in calgary boundary or not
	      boolean res = Point_in_polygon(randomlat,randomlon);
	      while(!res){
	    	  
	    	  randomlon = (rand.nextDouble() * (maxlon-minlon)) + minlon;
		      randomlat = (rnd.nextDouble() * (maxlat-minlat)) + minlat;
		      res = Point_in_polygon(randomlon,randomlat);

	      }    
	      
	      sensor[i]=new Mobile_Sensor(ID[i],randomlat,randomlon,randomTemp,randomDir);
	  
	    }

	    return sensor;
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
	
	
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		final Mobile_Sensor[] pos= randpos(-114.256525,-113.925075,50.88296111,51.14156667,100);
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/calgary", "postgres",
					"123456789");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 100; i++) {

			System.out.println(pos[i].getID() + "    " + pos[i].getLat()
					+ "    " + pos[i].getLon() + "      "
					+ pos[i].getTemprature() + "  " + pos[i].getDir());


			Statement stmt = null;
			stmt = connection.createStatement();

			String sql = "insert into sensor(sensor_id,actual_position,Direction,observed_value)"
					+ "values ("
					+ "'"
					+ pos[i].getID()
					+ "'"
					+ ",ST_GeometryFromText('POINT("
					+ pos[i].getLat()
					+ " "
					+ pos[i].getLon()
					+ ")', 4326),"
					+ pos[i].getDir()
					+ ","
					+ pos[i].getTemprature() + ");";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("success");
		}

	}

}
