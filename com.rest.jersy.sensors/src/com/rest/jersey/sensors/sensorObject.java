package com.rest.jersey.sensors;

import com.vividsolutions.jts.geom.Point;
public class sensorObject {
    private String id;
    private double Direction;
    private double  observed_value;
    private Point actual_position;
    
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getDirection() {
		return Direction;
	}
	public void setDirection(double direction) {
		Direction = direction;
	}
	public double getObserved_value() {
		return observed_value;
	}
	public void setObserved_value(double observed_value) {
		this.observed_value = observed_value;
	}
	public Point get_position() {
		return actual_position;
	}
	public void set_position(Point actual_position) {
		this.actual_position = actual_position;
	}

}
