package com.example.zupdate_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private TextView lblOutput;
	private Button btnLogin;
	private String restURL;
	private EditText inputUserName;
	private EditText inputPassWord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		restURL = "http://192.168.254.106/zupservice/public/api/v1/user/auth";
		btnLogin = (Button) findViewById(R.id.btnLogin); 
		
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new CallAPI().execute(restURL);
			}
			
		});
		
	}

	private class CallAPI extends AsyncTask<String, String, String> {
		 
	    @Override
	    protected String doInBackground(String... params) {
	    	String jsonData = "";
	    	inputUserName = (EditText) findViewById(R.id.username);
			inputPassWord = (EditText) findViewById(R.id.password);
			String Param = "username="+inputUserName.getText() + "&password=" + inputPassWord.getText();
	    	try{
	    		URL url = new URL(params[0]);
		    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    	con.setRequestMethod("POST");
		    	con.setDoOutput(true);
		    	OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		    	writer.write(Param);
		    	writer.flush();
		    	
		    	//StringBuilder sb = new StringBuilder();
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
	    	// TODO Auto-generated method stub
	    	try {
				
	    		JSONObject obj = new JSONObject(result);
	            String codeStatus = obj.getString("status");
	            String message = obj.getString("message");
	            if( codeStatus == "200"){
	            	//Session
	            	
	            	//Redirect
	            	
	            } else {
	            	//Error message
	            	lblOutput = (TextView) findViewById(R.id.hello_world);
					lblOutput.setText(codeStatus);
			    	lblOutput.getText().toString();
	            }
								
				
		    	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	super.onPostExecute(result);
	    }
	    
	} // end CallAPI
}
