package com.rest.sensors;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class sensors {
	
	String ID;
	double Lon,Lat,Temprature,dir;
	
	
	public sensors(){

	}
	
	public sensors(String id,double Lon,double Lat,double Temprature,double dir){
		this.ID=id;
		this.Lat=Lat;
		this.Lon=Lon;
		this.Temprature=Temprature;
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
		return Lon;
	}
	public void setLon(double lon) {
		Lon = lon;
	}
	public double getLat() {
		return Lat;
	}
	public void setLat(double lat) {
		Lat = lat;
	}
	public double getTemprature() {
		return Temprature;
	}
	public void setTemprature(double temprature) {
		Temprature = temprature;
	}

}
