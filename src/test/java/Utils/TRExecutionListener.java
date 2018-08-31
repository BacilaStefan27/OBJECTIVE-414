package Utils;

import TestRailAPI.TRClient;
import org.json.simple.JSONArray;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.lang.annotation.Annotation;

public class TRExecutionListener extends RunListener {

	private long Timer = 0;

	public static int getCounter() {
		return Conunter;
	}

	public static void setConunterto0() {
		Conunter=0;
	}

	public static void ConunterIncrement() {
		Conunter++;
	}

	private static int Conunter;

	private static final Description FAILED = Description.createTestDescription("failed", "failed");

	public void testRunStarted(Description description) {
		if(TRClient.getPlanID().isEmpty()){
			TRClient.CreateTestPlan();
		}
		String TPName = description.getDisplayName().substring(description.getDisplayName().indexOf(".")+1);
		TRClient.AddSuitetoPlan(getTestIDs(description),TPName);
	}

	public void testRunFinished(Result result) {
		TRClient.CloseTestPlan();
	}

	public void testStarted(Description description) {
		System.out.println("Starting: " + description.getMethodName());
		Timer=System.currentTimeMillis();
	}

	public void testFinished(Description description) {
		long duration = (System.currentTimeMillis()-Timer)/1000;
		if (description.getChildren().contains(FAILED)){
			TRClient.AddResultForCase(TRClient.getRunID(),getTestID(description),5,String.format("%ss",duration),Conunter);

		}
		else TRClient.AddResultForCase(TRClient.getRunID(),getTestID(description),1,String.format("%ss",duration),Conunter);
		Timer=0;
	}

	@Override
	public void testFailure(Failure failure) {
		failure.getDescription().addChild(FAILED);
	}

	public void testAssumptionFailure(Failure failure) {
	}

	public void testIgnored(Description description) {
	}

	//PRIVATE METHODS TO MANIPULATE DATA

	private JSONArray getTestIDs(Description tests){
		JSONArray testIDs = new JSONArray();
		for (Description description : tests.getChildren()) {
			for (Annotation obj1 : description.getAnnotations()) {
				if (obj1.toString().contains("Title(value=")) {
					Integer csID = Integer.parseInt(obj1.toString().substring(obj1.toString().lastIndexOf("C") + 1, obj1.toString().lastIndexOf(")")));
					testIDs.add(csID);
				}
			}
		}
		return testIDs;
	}

	private String getTestID(Description test){
		String csID="";
		for (Annotation obj1 : test.getAnnotations())
			if (obj1.toString().contains("Title(value=")) {
				csID = obj1.toString().substring(obj1.toString().lastIndexOf("C") + 1, obj1.toString().lastIndexOf(")"));
			}
		return csID;
	}

}