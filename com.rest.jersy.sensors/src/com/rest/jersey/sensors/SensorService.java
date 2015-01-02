package com.rest.jersey.sensors;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;
 
@Component
@Path("/sensors")

public class SensorService {
 
	@Autowired
	TransactionBo transactionBo;
	@GET
	 @Consumes("Application/json")
	@Path("/getJson")
	public String getJson() throws TransformationException, IOException {
 
		
		List<sensors> sensorList=transactionBo.req();
		
	    Genson genson = new Genson();
	    
	    String json = genson.serialize(sensorList);
	    genson.serialize(true);
		return json;
 
	}
 
}