package com.example.zupdate_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.zupdate_app.helper.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView welcomeMessage;
	private SessionManager session;
	private Button btnSend;
	private String restURL;
	private EditText receiver;
	private EditText messageBody;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		session = new SessionManager(getApplicationContext());
		
		welcomeMessage = (TextView) findViewById(R.id.textView1);
		welcomeMessage.setText("Welcome : " + session.getFullName());
		
		
		restURL = "http://192.168.254.106/zupservice/public/api/v1/message/send";
		btnSend = (Button) findViewById(R.id.button1); 
		
		btnSend.setOnClickListener(new View.OnClickListener() {

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
	    	messageBody = (EditText) findViewById(R.id.editText2);
	    	receiver = (EditText) findViewById(R.id.editText1);
			String Param = "sender="+session.getUserName() + "&receiver=" + receiver.getText().toString() + "&message=" + messageBody.getText().toString();
	    	
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
	            String message = obj.getString("message");
	            
	            
	            if( codeStatus == 200){
	            	            

	            	
	            				    	
	            } else {
	            	
	            }	
	            Context context = getApplicationContext();
            	CharSequence text = message;
            	int duration = Toast.LENGTH_SHORT;

            	Toast toast = Toast.makeText(context, text, duration);
            	toast.show();
		    	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	super.onPostExecute(result);
	    }
	    
	} // end CallAPI


}
