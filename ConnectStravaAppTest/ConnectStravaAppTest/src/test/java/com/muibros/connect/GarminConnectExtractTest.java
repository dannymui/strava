package com.muibros.connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class GarminConnectExtractTest {

	@Test
	public void test_activity_parse() {
		assertNull( GarminConnectExtract.getActivityID( null  ) );
		assertNull( GarminConnectExtract.getActivityID( ""  ) );
		assertNull( GarminConnectExtract.getActivityID( " "  ) );
		assertNull( GarminConnectExtract.getActivityID( "/"  ) );
		assertEquals( "360555", GarminConnectExtract.getActivityID( "http://connect.garmin.com/activity/360555" ) );
		assertEquals( "360555", GarminConnectExtract.getActivityID( "http://connect.garmin.com/activity/360555 " ) );
	}
}
