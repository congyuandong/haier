package com.legoo.haier.Model;

import java.util.List;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Channel Model
 * @author Mlzx
 * @version 1.0
 * @date 2014-04-17
 */
public class ContactsListModel extends BaseModel implements ModelInterface
{
	private List<ContactsModel> _contacts;
	
	public List<ContactsModel> getContacts()
	{
		return _contacts;
	}
	
	public void setContacts(List<ContactsModel> contacts)
	{
		_contacts = contacts;
	}
	
	public ContactsListModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_contacts = null;
	}
}
