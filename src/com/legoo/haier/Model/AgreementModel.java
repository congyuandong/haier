package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Agreement Model
 * @author MLZX
 * @version 1.0
 * @date 2014-04-10
 */
public class AgreementModel extends BaseModel implements ModelInterface, Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6489870244878360919L;
	
	private String _id;
	private String _content;
	public AgreementModel()
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
