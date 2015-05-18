package com.example.zupdate_app.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	// Shared Preferences
	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "ZupdataLogin";
	
	private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	public void setUserID(String value){
		editor.putString("userID", value);
	}
	
	public void setUserName(String value){
		editor.putString("username",value);
	}
	
	public void setToken(String value){
		editor.putString("token", value);
	}
	
	public void setUserEmail(String value){
		editor.putString("email", value);
	}
	
	public void setFullName(String value){
		editor.putString("fullname", value);
	}
	
	public void setLogin(boolean isLoggedIn) {
		editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
	}
	
	public String getUserID(){
		String userID;
		userID = pref.getString("userID",null);
		return userID;		
	}
	
	public String getUserName(){
		String userName;
		userName = pref.getString("username",null);
		return userName;		
	}
	
	public String getFullName(){
		String fullName;
		fullName = pref.getString("fullname",null);
		return fullName;
	}
	
	public String getUserEmail(){
		String userEmail;
		userEmail = pref.getString("email",null);
		return userEmail;
	}
	
	public String getToken(){
		String rememberToken;
		rememberToken = pref.getString("token",null);
		return rememberToken;
	}

	public void commit(){
		editor.commit();
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGEDIN, false);
	}
	
	public Editor editorObj(){
		return editor;
	}
}

