package com.example.zupdate_app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zupdate_app.helper.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Interpolator.Result;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private SessionManager session;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		session = new SessionManager(getApplicationContext());
		String restURL = "http://192.168.254.108/zupservice/public/api/v1/message/getAll?user_id="+session.getUserID();
		new getData().execute(restURL);
	
	}
	
	
	 public class getData extends AsyncTask<String, String, String> {

	        HttpURLConnection urlConnection;

	        @Override
	        protected String doInBackground(String... args) {

	            String jsonData = "";

	            try {
	                URL url = new URL(args[0]);
	                urlConnection = (HttpURLConnection) url.openConnection();
	                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

	                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

	                String line;
	                while ((line = reader.readLine()) != null) {
	                    jsonData += line+"\n";
	                }
	                
	            }catch( Exception e) {
	                e.printStackTrace();
	            }
	            finally {
	                urlConnection.disconnect();
	            }
				return jsonData;


	            
	        }

	        @Override
	        protected void onPostExecute(String result) {
	        	try{
	        		JSONObject obj = new JSONObject(result);
		            int codeStatus = obj.getInt("status");
		            
		            //Do something with the JSON string
		        	TextView tv = (TextView) findViewById(R.id.textView1);        	
		        	tv.setText(codeStatus);
		            tv.getText().toString();
	        	}catch(Exception e){
	        		
	        	}
	        	
	            
	            

	        }

	    }
	
	

}
