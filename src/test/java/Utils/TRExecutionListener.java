package Utils;

import TestRailAPI.TRClient;
import org.json.simple.JSONArray;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.lang.annotation.Annotation;

public class TRExecutionListener extends RunListener {

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

}