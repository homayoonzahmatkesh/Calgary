package com.rest.sensors;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement

public class Mobile_Sensor {

	/**
	 * @param args
	 */
	String ID;
	double lon;
	double lat;
	double Temprature;
	double dir;
	
	public Mobile_Sensor(){

	}
	public Mobile_Sensor(String ID,double lon,double lat,double Temprature,double dir) {
		this.Temprature=Temprature;
		this.ID=ID;
		this.lat=lat;
		this.lon=lon;
		this.dir=dir;
	}

	
	public double getDir() {
		return dir;
	}


	public void setDir(double dir) {
		this.dir = dir;
	}


	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getTemprature() {
		return Temprature;
	}
	public void setTemprature(double temprature) {
		Temprature = temprature;
	}
	
}
