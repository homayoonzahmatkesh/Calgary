package com.rest.sensors;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;


// This program is used for Creating and scheduling Two Job USing Quartz to create sesnor position
// Every two second a job i triggered and compute new position for sensors using its last position and direction
// Every 15 Second a job is triggered and compute new position for sensors using its las position and a random direction


public class mainFunction {
	
	static Mobile_Sensor[] new_Pos = null;


	public static void main(String[] args) throws SQLException, ParseException, SchedulerException {
		// TODO Auto-generated method stub
		
		
		
		
		
	      //Every 2 Second change direction Randomly

		    JobDetail job2 = new JobDetail();
	    	job2.setName("nextPos2");
	    	job2.setJobClass(nextPos.class);
	 
	        SimpleTrigger trigger = new SimpleTrigger();
	    	trigger.setName("Trigger_nextPos2");
	    	trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
	    	trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    	trigger.setRepeatInterval(2000);
	    	
	    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
	    	scheduler.start();
	    	scheduler.scheduleJob(job2, trigger);
	    	
//	      //Every 15 Second change direction Randomly
		    JobDetail job15 = new JobDetail();
	    	job15.setName("nextPos15");
	    	job15.setJobClass(nextPosRandomDirection.class);
	 
	        SimpleTrigger trigger15 = new SimpleTrigger();
	    	trigger15.setName("Trigger_nextPos15");
	    	trigger15.setStartTime(new Date(System.currentTimeMillis() + 1000));
	    	trigger15.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    	trigger15.setRepeatInterval(15000);
	    	
	    	Scheduler scheduler15 = new StdSchedulerFactory().getScheduler();
	    	scheduler15.start();
	    	scheduler15.scheduleJob(job15, trigger15);
	    	

	}

}
