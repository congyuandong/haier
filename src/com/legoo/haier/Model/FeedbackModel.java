package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Contacts Model
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-07
 */
public class FeedbackModel extends BaseModel implements ModelInterface, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4455896913495809124L;
	
	public static final String NOTREADONLY = "0";
	public static final String ISREADONLY = "1";
	private String _id;
	private String _idcard;
	private String _name;
	private String _readonly;
	private String _date;
	private String _opinion;
	private String _suggestions;
	
	
	public String getIDCard()
	{
		return _idcard;
	}
	
	public void setIDCard(String idcard)
	{
		_idcard = idcard;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getReadonly()
	{
		return _readonly;
	}
	
	public void setReadonly(String allow)
	{
		_readonly=allow;
	}
	
	
	public FeedbackModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_idcard = null;
		_name = null;
		_readonly = null;
	}

	public String getDate() {
		return _date;
	}

	public void setDate(String date) {
		this._date = date;
	}

	public String getOpinion() {
		return _opinion;
	}

	public void setOpinion(String _opinion) {
		this._opinion = _opinion;
	}

	public String getSuggestions() {
		return _suggestions;
	}

	public void setSuggestions(String suggestions) {
		this._suggestions = suggestions;
	}

	public String getID() {
		return _id;
	}

	public void setID(String id) {
		this._id = id;
	}
}
