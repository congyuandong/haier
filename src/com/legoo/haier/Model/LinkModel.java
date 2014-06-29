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
	
	private String _title;
	private String _url;
	private String _body;
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public String getUrl()
	{
		return _url;
	}
	
	public void setUrl(String url)
	{
		_url = url;
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
	
	public LinkModel(String title, String url)
	{
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
