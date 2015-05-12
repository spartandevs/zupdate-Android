package com.example.zupdate;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;





import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static String URL_LOGIN = "http://localhost/zupservice/public/api/v1/user/auth";
	Button loginBtn;
    EditText username;
    EditText password;
    
	@Override	    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        loginBtn = (Button) this.findViewById(R.id.loginBtn);
                
        loginBtn.setOnClickListener(new doLogin());
	}
	
	class doLogin implements Button.OnClickListener
	{
		URL url;
		HttpURLConnection conn;
		
		public void onClick(View v){
			
			try {
				String param="username=" + URLEncoder.encode(username.getText().toString(),"UTF-8")+
						"&password="+URLEncoder.encode(password.getText().toString(),"UTF-8");
				HttpURLConnection con = (HttpURLConnection) ( new URL(URL_LOGIN)).openConnection();
			    con.setRequestMethod("POST");
			    con.setDoInput(true);
			    con.setDoOutput(true);
			    con.setRequestProperty("Connection", "Keep-Alive");
			    con.setRequestProperty("Content-Type", "multipart/form-data");
			    con.connect();			    
				//send the POST out
				PrintWriter out = new PrintWriter(con.getOutputStream());
				out.print(param);
				out.close();			
				
				
				
				
			}catch(MalformedURLException ex){
				System.out.println();
			}catch(IOException ex){
				System.out.println();
			}
			
		}					
		
	}
	
}