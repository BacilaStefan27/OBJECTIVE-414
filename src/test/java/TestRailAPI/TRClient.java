package TestRailAPI;


import Utils.Constants;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.util.NameConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class TRClient {

	public static APIClient TrClient23 = new APIClient(Constants.BaseURL);

	private static String PlanID= "";

	public static String getRunID() {
		return RunID;
	}

	private static void setRunID(String runID) {
		RunID = runID;
	}

	private static String RunID="";

	public static String getPlanID() {
		return PlanID;
	}

	private static void setPlanID(String planID) {
		PlanID = planID;
	}

	private static void AuthClient(){
		TrClient23.setPassword(Constants.Password);
		TrClient23.setUser(Constants.Username);
	}

	public static void CreateTestPlan(){
		JSONObject data = new JSONObject();
		data.put("name",Constants.TPName);
		data.put("milestone_id",getActiveMilestone());
		data.put("description",Constants.TPDescription);

		JSONObject PlanID;
		try {
			AuthClient();
			PlanID = ((JSONObject) TrClient23.sendPost("add_plan/"+Constants.ProjectID,data));
			setPlanID(PlanID.get("id").toString());
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
	}

	public static void CloseTestPlan(){
		JSONObject data = new JSONObject();
		data.put("project_id",Constants.ProjectID);

		try {
			AuthClient();
			TrClient23.sendPost("close_plan/"+PlanID,data);
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
	}

	private static String getActiveMilestone(){
		JSONArray Milestone = new JSONArray();
		try {
			AuthClient();
			Milestone = ((JSONArray) TrClient23.sendGet("get_milestones/"+Constants.ProjectID+"&is_started=1"));
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
		return ((JSONObject) Milestone.get(0)).get("id").toString();

	}

	public static void AddSuitetoPlan(JSONArray cases, String name) {
		JSONObject data = new JSONObject();
		data.put("include_all",false);
		data.put("name",name);
		data.put("description","CreatedByAutomationRun");
		data.put("case_ids",cases);
		data.put("suite_id",593);

		JSONObject answ;
		try {
			answ = (JSONObject) TrClient23.sendPost("add_plan_entry/"+PlanID,data);
			String rund = answ.get("runs").toString().substring(answ.get("runs").toString().lastIndexOf("\"id\":")+5);
			rund = rund.substring(0,rund.indexOf(","));
			setRunID(rund);
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
	}

	public static void AddTestRun(JSONArray Cases){
		JSONObject data = new JSONObject();
		data.put("suite_id",1);
		data.put("name",Constants.TPName);
		data.put("milestone_id",getActiveMilestone());
		data.put("description",Constants.TPDescription);
		data.put("include_all",false);
		data.put("case_ids",Cases);

		try {
			TrClient23.sendPost("add_run/"+Constants.ProjectID,data);
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
	}

	public static JSONArray GetStepsForCase(String CaseID){
		JSONObject resp;
		JSONArray Steps = new JSONArray();

		try {
			resp = (JSONObject) TrClient23.sendGet("get_case/"+CaseID);
			Steps = (JSONArray) resp.get("custom_step_separated");
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
		return Steps;
	}

	public static void AddResultsForCases(JSONArray data){
		JSONObject result = new JSONObject();
		result.put("results",data);
		try {
			TrClient23.sendPost("add_results_for_cases/"+RunID,result);
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
	}

	public static void ParseSerenityResults(List<TestOutcome> testOutcomeResults){
		JSONArray results = new JSONArray();
		testOutcomeResults.forEach(testCase -> {
					JSONObject tCase = new JSONObject();
					tCase.put("case_id", testCase.getTitle().substring(1));
					tCase.put("status_id",parseSerenityResult(testCase.getResult().name()));
					tCase.put("elapsed",String.format("%ss",Math.round(testCase.getDurationInSeconds())));
					if(testCase.countTestSteps()>0){
						JSONArray tSteps = GetStepsForCase(testCase.getTitle().substring(testCase.getTitle().lastIndexOf("C")+1));
						testCase.getTestSteps().forEach(testStep -> {
							if(testStep.hasChildren())
								testStep.getChildren().forEach(testStepChild -> parseStepsResult(testStepChild,tSteps));
							else parseStepsResult(testStep,tSteps);
						});
						tCase.put("custom_step_results",tSteps);
					}
					results.add(tCase);
				}
		);
		AddResultsForCases(results);
	}

//	==============================PRIVATE METHODS==========================

	private static Integer parseSerenityResult(String result){
		switch (result){
			case "SUCCESS" : return 1;
			case "FAILURE" : return 5;
		}
		return null;
	}

	private static void parseStepsResult(TestStep step, JSONArray caseSteps){
		ListIterator<JSONObject> stepIterTR = caseSteps.listIterator();
		while(stepIterTR.hasNext()){
			JSONObject customStep = stepIterTR.next();
			if(NameConverter.humanize(customStep.get("content").toString()).equals(step.getDescription()))
				customStep.put("status_id",parseSerenityResult(step.getResult().name()));
		}
//		return caseSteps;
	}
}

