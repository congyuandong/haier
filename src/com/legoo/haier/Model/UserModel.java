package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class User Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class UserModel extends BaseModel implements ModelInterface
{
	private String _id;
	private String _account;
	private String _name;
	private String _password;
	private String _passwordMD5;
	private String _telephone;
	private String _idCard;
	private String _email;
	private boolean _sex;
	
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	public String getAccount()
	{
		return _account;
	}
	
	public void setAccount(String account)
	{
		_account = account;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getPassword()
	{
		return _password;
	}
	
	public void setPassword(String password)
	{
		_password = password;
	}
	
	public String getPasswordMD5()
	{
		return _passwordMD5;
	}
	
	public void setPasswordMD5(String passwordMD5)
	{
		_passwordMD5 = passwordMD5;
	}
	
	public String getTelephone()
	{
		return _telephone;
	}
	
	public void setTelephone(String telephone)
	{
		_telephone = telephone;
	}
	
	public String getIDCard()
	{
		return _idCard;
	}
	
	public void setIDCard(String idCard)
	{
		_idCard = idCard;
	}
	
	public String getEmail()
	{
		return _email;
	}
	
	public void setEmail(String email)
	{
		_email = email;
	}
	
	public boolean getSex()
	{
		return _sex;
	}
	
	public void setSex(boolean sex)
	{
		_sex = sex;
	}
	
	public UserModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_id = null;
		_account = null;
		_name = null;
		_password = null;
		_telephone = null;
		_idCard = null;
		_email = null;
	}
}
