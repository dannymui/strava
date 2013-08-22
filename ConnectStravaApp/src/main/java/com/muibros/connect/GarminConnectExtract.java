package com.muibros.connect;



/**
 * Given a Garmin Connect Activity this class will be responsible for downloading and exporting the GPX file for a *PUBLIC* activity
 * <br/>
 * This is the format of the HTTP request:
 * http://connect.garmin.com/proxy/activity-service-1.1/gpx/activity/360555286?full=true
 * 
 * @author danny
 *
 */
public class GarminConnectExtract {
	public static final String CONNECT_URL_PREFIX = "http://connect.garmin.com/proxy/activity-service-1.1/gpx/activity/";
	public static final String CONNECT_URL_SUFFIX = "?full=true";
	
	public static String getActivityID( String url ) {
		String activityID = null;
		if ( url != null && url.length() > 0 ) {
			url = url.trim();
			int lastSlash = url.lastIndexOf( "/");
			if ( lastSlash >= 0 && lastSlash < url.length() - 1 ) {
				activityID = url.substring( lastSlash + 1 ).trim();
			}
		}
		
		return activityID;
	}
	
	public static String getActivityGPXURL ( String activityID ) {
		return CONNECT_URL_PREFIX + activityID + CONNECT_URL_SUFFIX;
	}

	
}
