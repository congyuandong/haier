package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Question Model
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class QuestionListModel extends BaseModel implements ModelInterface, Serializable
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4618046938430392489L;
	private String _id;
	private String _name;
	public QuestionListModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_id=null;
		_name=null;
	}

	public String getID() {
		return _id;
	}

	public void setID(String _id) {
		this._id = _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
}
