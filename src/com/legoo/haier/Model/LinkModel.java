package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Link Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class LinkModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = -779571728492804598L;
	
	private String _id;
	private String _title;
	private String _time;
	private String _image;
	private String _url;
	private boolean _isexternal;
	private String _body;
	
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	public String getImage()
	{
		return _image;
	}
	
	public void setImage(String image)
	{
		_image = image;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public String getTime()
	{
		return _time;
	}
	
	public void setTime(String time)
	{
		_time = time;
	}
	
	public String getUrl()
	{
		return _url;
	}
	
	public void setUrl(String url)
	{
		_url = url;
	}
	
	public boolean getIsExternal()
	{
		return _isexternal;
	}
	
	public void setIsExternal(boolean isexternal)
	{
		_isexternal = isexternal;
	}
	
	public String getBody()
	{
		return _body;
	}
	
	public void setBody(String body)
	{
		_body = body;
	}
	
	public LinkModel()
	{
		
	}
	
	public LinkModel(String id, String title, String url)
	{
		_id = id;
		_title = title;
		_url = url;
	}
	
	@Override
	public void dispose()
	{
		_title = null;
		_url = null;
	}
}
