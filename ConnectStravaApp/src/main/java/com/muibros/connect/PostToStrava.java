package com.muibros.connect;

import java.io.File;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PostToStrava extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		String action = getIntent().getAction();

		// if this is from the share menu
		if ( Intent.ACTION_SEND.equals(action) ) {
			String targetURL = extractURL();
			File downloadedFile = handleConnectDownload( targetURL );
			if ( downloadedFile != null && downloadedFile.exists() ) {
				sendToStrava ( downloadedFile );
			}
		}
	}

	protected void writeProgress( String message ) {
		TextView log = (TextView ) this.findViewById( R.id.TextMain );
		log.append( message );
		log.append( "\n" );
	}
	
	private void sendToStrava( File downloadedFile ) {
		if ( downloadedFile != null && downloadedFile.exists() ) {
			writeProgress( "Sending upload@strava shared file.." );
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"upload@strava.com"});
			intent.putExtra(Intent.EXTRA_SUBJECT, "Garmin GPX Upload");
			if (!downloadedFile.exists() || !downloadedFile.canRead()) {
			    writeProgress("Attachment Error: file doesnt exist or cant be read " + downloadedFile );
			    return;
			}
			Uri uri = Uri.fromFile( downloadedFile );
			intent.putExtra(Intent.EXTRA_STREAM, uri);
			startActivity(Intent.createChooser(intent, "Send email to strava..."));			
		}
	}

	/**
	 * Get the sd file handle for the fpx file 
	 * @param activityID
	 * @return
	 */
	protected File getExternalSDFile( String activityID ) {
	    String fileName = activityID +".gpx";
		File file = new File( getExternalCacheDir(), fileName );
		if ( file.exists() ) {
			Log.i( getLogContext(), "Deleting existing file: " + fileName );
			file.delete();
		}
	    
	    return file;
	}
	
	private File handleConnectDownload(String targetURL) {
		String activityID = GarminConnectExtract.getActivityID( targetURL );
		File file = null;
		if ( activityID != null ) {
			Log.i( getLogContext(), "Found Garmin Connect URL: " +targetURL );
			file = getExternalSDFile( activityID );
			Callback<File> completeCallback = new Callback<File>() {
				public void execute(File parameter) {
					sendToStrava( parameter );
				}
			};
			
			new DownloadFileTask( GarminConnectExtract.getActivityGPXURL(activityID), file, completeCallback ).execute( "" );
			writeProgress( "Downloaded Garmin Connect activity to: " +file.getAbsolutePath() );
		}

		return file;
	}

	private String getLogContext() {
		return getClass().getSimpleName();
	}

	protected String extractURL() {
		String url = null;
		ClipData clipboard = getIntent().getClipData();
		if ( clipboard.getItemCount() > 0 ) {
			Item item = clipboard.getItemAt( 0 );
			if ( item != null && item.getText() != null ) {
				url = item.getText().toString();	
			}
			
		}
		
		return url;
	}
	
}
