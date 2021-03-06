package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class UseDBTest {
	
	/*
	 * The methods getFreeID() and connectDB() are being used (and tested) by other methods. 
	 */
	
	int id = UseDB.getFreeID("training"); //used as id if getIDByName is not working

	@Test
	public void DBTest() {
		
		UseDB.addRow("training", id, "TestTrack", "12:00", "2019-03-12", 0, "", "yes");
		
		ArrayList<ArrayList<String>> result1 = UseDB.getTable("SELECT * FROM training WHERE trainingid="+id);
		String track = result1.get(result1.size()-1).get(1);
		String geojson = result1.get(result1.size()-1).get(5);
			
		Assert.assertEquals("TestTrack", track);
		Assert.assertEquals("", geojson);
		
		ArrayList<ArrayList<String>> addedTracks = UseDB.getIDByName("training", "place=TestTrack");
		
		for (int i = 0; i < addedTracks.size(); i++) {
			UseDB.deleteRow("training", Integer.parseInt(addedTracks.get(i).get(0)));	
		}		
		Assert.assertTrue(UseDB.getIDByName("training", "place=TestTrack").size() == 0);	
	}


	@Test
	public void getRunnerByIDTest() {
		int id = 1;
		String runner = UseDB.getTable("SELECT firstname, lastname FROM runner WHERE runner.runnerid = " + id).get(0).get(0);
		String result2 = UseDB.getRunnerByID(id).get(0);
		Assert.assertEquals(runner, result2);
	}
	@Test
	public void illegalQueryTest() {
		ArrayList<ArrayList<String>> runner = UseDB.getTable("Illegal query to database");
		Assert.assertEquals(null, runner);
	}
	@Test
	public void addCommentToResultTest() {
		UseDB.addRow("runner", 99, "Andreas", "Haukeland", "18-05-1995", "andreas@haukeland.no", "90361850", "");
		UseDB.updateCommentToTraining(1, 99, "10:00:00", "");
		UseDB.updateCommentToTraining(1, 99, "05:00:00", "TestComment");
		String comment = UseDB.getTable("select comment from result where trainingid=1 and runnerid=99").get(0).get(0);
		Assert.assertEquals("TestComment", comment);
		
		SessionInformation.userId = 99;
		UseDB.addComment(1, "TestComment2");
		comment = UseDB.getTable("select comment from result where trainingid=1 and runnerid=99").get(0).get(0);
		Assert.assertEquals("TestComment2", comment);
		UseDB.deleteRow("result", 99);
		UseDB.deleteRow("runner", 99);

	}
	
	@Test
	public void submitWeeklyRunTest() {
		UseDB.submitWeeklyRun("TestTrack2", "2019-03-12", "12:00", 0, "", "yes");
		ArrayList<ArrayList<String>> result2 = UseDB.getTable("SELECT trainingid, place FROM training WHERE place='TestTrack2'");
		int id2 = Integer.parseInt(result2.get(0).get(0));
		
		Assert.assertEquals("TestTrack2", result2.get(0).get(1));
		UseDB.deleteRow("training", id2);
	}
	
	@Test
	public void submitTimeToTrainingTest() {
		int runner_id = 999;
		UseDB.addRow("runner", runner_id, "Andreas", "Haukeland", "18.05.95", "andhauk@stud.no", "90909090", "");
		UseDB.submitTimeToTraining(runner_id, "10:10:10", "test comment", "test comment", "test comment");
		ArrayList<ArrayList<String>> result3 = UseDB.getTable("SELECT comment FROM result WHERE comment='test comment'");
		Assert.assertEquals("test comment", result3.get(result3.size()-1).get(0));
		UseDB.deleteRow("result", runner_id);
		UseDB.deleteRow("runner", 999);

	}
	
	@Test
	public void testUpdateTrainingRow() {
		int currentTrainingId = Integer.parseInt(UseDB.getLastRun().get(0));
		UseDB.submitTimeToTraining(999, "10:00", "hei", "", "");
		UseDB.updateTrainingRow(currentTrainingId, 999, "09:00", "slay");
		UseDB.deleteRow("result", 999);
	}

	
}

