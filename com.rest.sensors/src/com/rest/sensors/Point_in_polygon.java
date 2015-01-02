package com.rest.sensors;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class Point_in_polygon {

	/**
	 * @param args
	 */
	public static boolean pt_in_polygon(double lon, double lat){
		
		
		
		    final GeometryFactory gf = new GeometryFactory();
		    
		    System.out.println("started");
		    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
		    points.add(new Coordinate(51.14156667, -114.256525));
		    points.add(new Coordinate(51.10081389, -113.925075));
		    points.add(new Coordinate(50.88296111, -113.9556861));
		    points.add(new Coordinate(50.89308056, -114.1173611));
		    points.add(new Coordinate(50.99975278, -114.1341361));
		    points.add(new Coordinate(51.01778889, -114.2133694));
		    final Polygon polygon2 = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
		        .toArray(new Coordinate[points.size()])), gf), null);

		    final Coordinate coord = new Coordinate(lon, lat);
		    final Point point2 = gf.createPoint(coord);
		    boolean res = point2.within(polygon2);
		    System.out.println(point2.within(polygon2));
		return res;
	}
	
	
	
	
	
	
	
	
	
//	public static void main(String[] args) {
//		
//		
//
//	}

}
