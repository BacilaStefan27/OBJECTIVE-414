package TestRailAPI;


import Utils.Constants;
import Utils.JUnitExecutionListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Iterator;

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

	public static void AddResultForCase(String RunID, String CaseID, Integer Result, String Elapsed,Integer Stepscount){
		JSONObject data = new JSONObject();
		data.put("run_id",RunID);
		data.put("case_id",CaseID);
		data.put("elapsed",Elapsed);
		data.put("status_id",Result);

		if(JUnitExecutionListener.getCounter()!=0){
			JSONArray CustomSteps = GetStepsForCase(CaseID);
			Iterator<JSONObject> it1 = CustomSteps.iterator();
			Integer nr = 0;
			while(it1.hasNext()){
				if(nr<Stepscount){
					it1.next().put("status_id",1);
					nr++;
				}
				else it1.next().put("status_id",5);
			}
			data.put("custom_step_results",CustomSteps);
		}

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

	public static void AddSuitetoPlan(JSONArray cases, String name) {
		JSONObject data = new JSONObject();
		data.put("include_all",false);
		data.put("name",name);
		data.put("description","CreatedByAutomationRun");
		data.put("case_ids",cases);
		data.put("suite_id",1);

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
		JSONObject resp = new JSONObject();
		JSONArray Steps = new JSONArray();

		try {
			resp = (JSONObject) TrClient23.sendGet("get_case/"+CaseID);
			Steps = (JSONArray) resp.get("custom_steps_separated");
		} catch (IOException | APIException e) {
			e.printStackTrace();
		}
		return Steps;
	}
}

