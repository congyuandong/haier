package com.legoo.haier.AsyncTask.Callback;

import com.legoo.haier.AsyncTask.Base.BaseEvent;

/**
 * @class Sample Event
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-10
 */
@SuppressWarnings("serial")
public class SampleEvent extends BaseEvent
{
	private Object _content;
	
	public SampleEvent(Object source)
    {
        super(source);
        source = null;
    }
	
	public void setContent(Object content)
	{
		_content = content;
	}
	
	public Object getContent()
	{
		return _content;
	}
	
	@Override
	public void dispose()
	{
		_content = null;
	}
}
