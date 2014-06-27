package com.legoo.haier.Model;

import java.util.List;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Home Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-20
 */
public class HomeModel extends BaseModel implements ModelInterface
{
	private List<ModelInterface> _topics;
	private List<ModelInterface> _notices;
	
	public List<ModelInterface> getTopics()
	{
		return _topics;
	}
	
	public void setTopics(List<ModelInterface> topics)
	{
		_topics = topics;
	}
	
	public List<ModelInterface> getNotices()
	{
		return _notices;
	}
	
	public void setNotices(List<ModelInterface> notices)
	{
		_notices = notices;
	}
	
	public HomeModel()
	{
		
	}

	@Override
	public void dispose()
	{
		_topics = null;
		_notices = null;
	}
}
