package Utils;

import org.joda.time.DateTime;

public class Constants {

	public static final String Username = "ci.auto@ixxus.com";
	public static final String Password = "?u2-Brodu";
	public static final String BaseURL = "https://ixxus.testrail.com/";
	public static final String ProjectID = "115";
	public static final String TPDescription = "Created by automation FW";
	public static final String TPName = currentDate();

	private static String currentDate(){
		DateTime timestamp= new DateTime();
		return timestamp.toDateTimeISO().toString();
	}


}
