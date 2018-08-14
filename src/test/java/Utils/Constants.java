package Utils;

import org.joda.time.DateTime;

public class Constants {

	public static final String Username = "stefan.bacila@ixxus.com";
	public static final String Password = "rgRV1gxlKrSKFfQeDoQN";
	public static final String BaseURL = "https://stefanbacila29.testrail.io/";
	public static final String ProjectID = "1";
//	public static final String MilestoneID = "1";
	public static final String TPDescription = "Created by automation FW";
	public static final String TPName = currentDate();
//	public static final String Description = "admin";
//	public static final String Description = "admin";
//	public static final String Description = "admin";
//	public static final String Description = "admin";
//	public static final String Description = "admin";
//	public static final String Description = "admin";


	private static String currentDate(){
		DateTime timestamp= new DateTime();
		return timestamp.toDateTimeISO().toString();
	}


}
