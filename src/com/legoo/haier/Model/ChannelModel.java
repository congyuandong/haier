package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Channel Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class ChannelModel extends BaseModel implements ModelInterface
{
	private String _id;
	private String _title;
	
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public ChannelModel()
	{
		
	}
	
	public ChannelModel(String id, String title)
	{
		_id = id;
		_title = title;
	}
	
	@Override
	public void dispose()
	{
		_title = null;
	}
}
