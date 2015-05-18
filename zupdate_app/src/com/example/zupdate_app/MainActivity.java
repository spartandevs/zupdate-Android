package com.example.zupdate_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zupdate_app.helper.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
		
		String restURL = "http://192.168.254.110/zupservice/public/api/v1/message/getAll?user_id="+ session.getUserID();
		
		new CallAPI().execute(restURL);
		
	}
	
	private class CallAPI extends AsyncTask<String, String, String> {
		 
		@Override
	    protected String doInBackground(String... params) {
			
			session = new SessionManager(getApplicationContext());
					
			
			String jsonData  = "";
	    			    	
			try{
				URL url = new URL(params[0]);
		    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    	con.setRequestMethod("GET");
		    	con.setDoOutput(true);
		    	con.connect();	    	
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

		    	String line;
		    	while((line = reader.readLine()) != null){
		    		jsonData += line + "\n";
		    	}
		    	
		    	return jsonData;
	    	}catch(Exception e){
	    		
	    	}
			
			return null;
	    }
	 
	    @Override
	    protected void onPostExecute(String result) {
	    	
	    	TextView listMessages = (TextView) findViewById(R.id.textView1);
	    	
	    	try{
	    		JSONObject obj = new JSONObject(result);
	    		JSONObject messages= obj.getJSONObject("message");
	    		JSONArray msgs = messages.getJSONArray("message");
	    		    		
	    		listMessages.setText("hello world");
	    		listMessages.getText().toString();
	    	}catch(Exception e){
	    		
	    	}
	    	
	    	super.onPostExecute(result);
	    }
	    
	} // end CallAPI

}
