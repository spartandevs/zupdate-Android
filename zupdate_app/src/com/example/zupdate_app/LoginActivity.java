package com.example.zupdate_app;

import com.example.zupdate_app.helper.SessionManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btnLogin;
	private String restURL;
	private EditText inputUserName;
	private EditText inputPassWord;
	private SessionManager session;
	
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
	    	
	    	String jsonData  = "";
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
	    	
	    	session = new SessionManager(getApplicationContext());
	    	
	    	// TODO Auto-generated method stub
	    	try {
				
	    		JSONObject obj = new JSONObject(result);
	            int codeStatus = obj.getInt("status");
	            String errMessage = obj.getString("message");
	            
	            
	            if( codeStatus == 200){
	            	//User data
		            JSONObject userObj = obj.getJSONObject("user_data");
		            String userEmail = userObj.getString("email");
		            String userFullName = userObj.getString("name");
		            String userName = userObj.getString("username");
		            String userToken = userObj.getString("remember_token");
		            
	            	//Session
	            	session.setLogin(true);
	            	session.setUserName(userName);
	            	session.setToken(userToken);
	            	session.setUserEmail(userEmail);
	            	session.setFullName(userFullName);
	            	session.commit(); 	            	

	            	//Redirect
	            	Intent intent = new Intent(LoginActivity.this,MainActivity.class);
	            	startActivity(intent);
	            	finish();
	            				    	
	            } else {
	            	//Error message
	            	Context context = getApplicationContext();
	            	CharSequence text = errMessage;
	            	int duration = Toast.LENGTH_SHORT;

	            	Toast toast = Toast.makeText(context, text, duration);
	            	toast.show();
	            }	
				
		    	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	super.onPostExecute(result);
	    }
	    
	} // end CallAPI
}
