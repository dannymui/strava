package com.muibros.connect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadFileTask extends AsyncTask< String, String, File >{
	private String sourceURL;
	private File target;
	private Callback<File> completeCallback;
	
	public DownloadFileTask( String url, File target, Callback<File> completeCallback ) {
		this.sourceURL = url;
		this.target = target;
		this.completeCallback = completeCallback;
	}
	
	
	@Override
	protected File doInBackground(String... params) {
        int count;
        try {
            URL url = new URL( this.sourceURL );
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
//            int lenghtOfFile = conection.getContentLength();
 
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
            // Output stream to write file
            OutputStream output = new FileOutputStream( target );
 
            byte data[] = new byte[1024];
 
//            long total = 0;
 
            while ((count = input.read(data)) != -1) {
//                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
//                publishProgress(""+(int)((total*100)/lenghtOfFile));
 
                // writing data to file
                output.write(data, 0, count);
            }
 
            // flushing output
            output.flush();
 
            // closing streams
            output.close();
            input.close();
 
        } catch (Exception e) {
            Log.e("Error: ", e.toString() );
        }
 
        return this.target;
	}


	@Override
	protected void onPostExecute( File result ) {
		this.completeCallback.execute( result );
	}

}
