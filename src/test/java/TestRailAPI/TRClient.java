package TestRailAPI;


import Utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

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

	public static void AddResultForCase(String RunID, String CaseID, Integer Result, String Elapsed){
		JSONObject data = new JSONObject();
		data.put("run_id",RunID);
		data.put("case_id",CaseID);
		data.put("elapsed",Elapsed);
		data.put("status_id",Result);

		try {
			AuthClient();
			TrClient23.sendPost("add_result_for_case/"+RunID+"/"+CaseID,data);
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

	public static void AddSuitetoPlan(JSONArray cases) {
		JSONObject data = new JSONObject();
		data.put("include_all",false);
		data.put("name","TestSuitForClassX");
		data.put("description","CreatedByAutomationRun");
		data.put("case_ids",cases);
		data.put("suite_id",1);

		JSONObject answ = new JSONObject();
		try {
			answ= (JSONObject) TrClient23.sendPost("add_plan_entry/"+PlanID,data);
			String rund = answ.get("runs").toString().substring(answ.get("runs").toString().lastIndexOf("\"id\":")+5);
			rund = rund.substring(0,rund.indexOf(","));
			setRunID(rund);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (APIException e) {
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		}
	}
}

