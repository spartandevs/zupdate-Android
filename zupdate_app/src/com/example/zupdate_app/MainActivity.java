package com.example.zupdate_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView lblOutput;
	private Button button1;
	private String restURL;
	private EditText inputUserName;
	private EditText inputPassWord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		restURL = "http://192.168.254.106/zupservice/public/api/v1/user/auth";
		button1 = (Button) findViewById(R.id.button1); 
		
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new CallAPI().execute(restURL);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class CallAPI extends AsyncTask<String, String, String> {
		 
	    @Override
	    protected String doInBackground(String... params) {
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
		    	
		    	StringBuilder sb = new StringBuilder();
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

		    	String line;
		    	while((line = reader.readLine()) != null){
		    		sb.append(line + "\n");
		    	}
		    	
		    	return sb.toString();
	    	}catch(Exception e){
	    		
	    	}
			return null;
	    }
	 
	    @Override
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    	lblOutput = (TextView) findViewById(R.id.hello_world);
	    	lblOutput.setText(result);
	    	lblOutput.getText().toString();
	    	super.onPostExecute(result);
	    }
	    
	} // end CallAPI
}
