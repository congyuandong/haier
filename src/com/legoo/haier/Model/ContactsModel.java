package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Contacts Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-04-01
 */
public class ContactsModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = 3826601102836966984L;
	private String _id;
	private String _idcard;
	private String _telephone;
	private String _name;
	private String _allowdelete;
	
	
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	
	public String getIDCard()
	{
		return _idcard;
	}
	
	public void setIDCard(String idcard)
	{
		_idcard = idcard;
	}
	
	public String getTelephone()
	{
		return _telephone;
	}
	
	public void setTelephone(String telephone)
	{
		_telephone = telephone;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getAllowDelete()
	{
		return _allowdelete;
	}
	
	public void setAllowDelete(String allow)
	{
		_allowdelete=allow;
	}
	public ContactsModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_id = null;
		_idcard = null;
		_telephone = null;
		_name = null;
		_allowdelete = null;
	}
}
