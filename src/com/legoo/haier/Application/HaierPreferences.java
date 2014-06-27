package com.legoo.haier.Application;

import android.content.SharedPreferences;

/**
 * @class Hospital Preferences
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HaierPreferences
{
	private Haier _application;
	
	private SharedPreferences _preferences;
	private SharedPreferences.Editor _editor;
	
	private final String NAME = "settings";
	
	private final String SETTING_USER_ID = "settingUserID";
	private final String SETTING_USER_ACCOUNT = "settingUserAccount";
	private final String SETTING_USER_PASSWORD = "settingUserPassword";
	
	private final String DEFAULT_STRING = "";
	
	public HaierPreferences(Haier application)
	{
		_application = application;
		initPreferences();
	}
	
	private void initPreferences()
	{
		_preferences = _application.getSharedPreferences(NAME, 0);
	}
	
	public void saveUser(String id, String account, String password)
	{
		_editor = _preferences.edit();
		_editor.putString(SETTING_USER_ID, id);
		_editor.putString(SETTING_USER_ACCOUNT, account);
		_editor.putString(SETTING_USER_PASSWORD, password);
		_editor.commit();
	}
	
	public void eraseUser()
	{
		saveUser(DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING);
	}
	
	public String loadUserID()
	{
		return getString(SETTING_USER_ID, DEFAULT_STRING);
	}
	
	public String loadUserAccount()
	{
		return getString(SETTING_USER_ACCOUNT, DEFAULT_STRING);
	}
	
	public String loadUserPassword()
	{
		return getString(SETTING_USER_PASSWORD, DEFAULT_STRING);
	}
	
	private String getString(String key, String defValue)
	{
		try
		{
			return _preferences.getString(key, defValue);
		} 
		catch (ClassCastException e)
		{
			return DEFAULT_STRING;
		}
	}
}
