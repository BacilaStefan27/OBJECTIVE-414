package Utils;

import TestRailAPI.TRClient;
import org.json.simple.JSONArray;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.ListIterator;

public class JUnitExecutionListener extends RunListener {

	long Timer = 0;

	private static final Description FAILED = Description.createTestDescription("failed", "failed");

	public void testRunStarted(Description description) throws Exception {
		if(TRClient.getPlanID().isEmpty()){
			TRClient.CreateTestPlan();
		}
		String TPName = description.getDisplayName().substring(description.getDisplayName().indexOf(".")+1);
		TRClient.AddSuitetoPlan(getTestIDs(description),TPName);

	}

	public void testRunFinished(Result result) throws Exception {
		TRClient.CloseTestPlan();
	}

	public void testStarted(Description description) throws Exception {
		System.out.println("Starting: " + description.getMethodName());
		Timer=System.currentTimeMillis();
	}

	public void testFinished(Description description) throws Exception {
		long duration = (System.currentTimeMillis()-Timer)/1000;
		if (description.getChildren().contains(FAILED)){
			TRClient.AddResultForCase(TRClient.getRunID(),getTestID(description),5,String.format("%ss",duration));
		}
		else TRClient.AddResultForCase(TRClient.getRunID(),getTestID(description),1,String.format("%ss",duration));
		Timer=0;
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		failure.getDescription().addChild(FAILED);
	}

	public void testAssumptionFailure(Failure failure) {
	}

	public void testIgnored(Description description) throws Exception {
	}

	private JSONArray getTestIDs(Description tests){
		JSONArray testIDs = new JSONArray();
		ListIterator<Description> iter1 = tests.getChildren().listIterator();
		while(iter1.hasNext()){
			Iterator<Annotation> iter2 = iter1.next().getAnnotations().iterator();
			while (iter2.hasNext()){
				Annotation obj1 = iter2.next();
				if(obj1.toString().contains("Title(value="))
				{
					Integer csID = Integer.parseInt(obj1.toString().substring(obj1.toString().lastIndexOf("C") + 1, obj1.toString().lastIndexOf(")")));
					testIDs.add(csID);
				}
			}
		}

		return testIDs;
	}

	private String getTestID(Description test){
		JSONArray testIDs = new JSONArray();
		String csID="";
		Iterator<Annotation> iter1 = test.getAnnotations().iterator();
		while(iter1.hasNext()){
			while (iter1.hasNext()){
				Annotation obj1 = iter1.next();
				if(obj1.toString().contains("Title(value="))
				{
					csID = obj1.toString().substring(obj1.toString().lastIndexOf("C") + 1, obj1.toString().lastIndexOf(")"));
				}
			}
		}
		return csID;
	}
}