package com.legoo.haier.Application;

import com.legoo.haier.Model.UserModel;


/**
 * @class Hospital User
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HospitalUser
{
	private Hospital _application;
	
	private UserModel _current;
	private UserModel _remember;
	
	public UserModel getCurrent()
	{
		return _current;
	}
	
	public UserModel getRemember()
	{
		return _remember;
	}
	
	public boolean hasLogin()
	{
		return (_current.getPasswordMD5() != null);
	}
	
	public boolean hasPrepared()
	{
		return (_remember.getAccount() != null && _remember.getAccount().length() > 0 
				&& _remember.getPassword() != null && _remember.getPassword().length() > 0);
	}
	
	public HospitalUser(Hospital application)
	{
		_application = application;
		initCurrent();
		initRemember();
	}
	
	public boolean login(UserModel user)
	{
		if (user != null)
		{
			_current = user;
			save();
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public void logout()
	{
		_current = new UserModel();
		_remember = new UserModel();
		erase();
	}
	
	private void initCurrent() 
	{
		_current = new UserModel();
	}
	
	private void initRemember()
	{
		_remember = new UserModel();
		_remember.setID(_application.getPreferences().loadUserID());
		_remember.setAccount(_application.getPreferences().loadUserAccount());
		_remember.setPassword(_application.getPreferences().loadUserPassword());
	}
	
	private void save()
	{
		_application.getPreferences().saveUser(_current.getID(), _current.getAccount(), _current.getPassword());
	}
	
	public void erase()
	{
		_application.getPreferences().eraseUser();
	}
}
