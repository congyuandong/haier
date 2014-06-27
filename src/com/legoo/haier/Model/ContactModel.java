package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Contact Model
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class ContactModel extends BaseModel implements ModelInterface, Serializable
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5445292301519958568L;
	private String _id;
	private String _content;
	public ContactModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_id=null;
		_content=null;
	}

	public String getID() {
		return _id;
	}

	public void setID(String _id) {
		this._id = _id;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String _content) {
		this._content = _content;
	}
}
