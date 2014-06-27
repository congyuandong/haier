package com.legoo.haier.AsyncTask.Base;

import java.util.EventObject;

/**
 * @class Json Event
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class JsonEvent extends EventObject implements EventInterface
{
	private static final long serialVersionUID = -5219245307097539149L;

	private String _message;
	
	private int _error;
	
	private int _mark;

	public JsonEvent(Object source)
    {
        super(source);
        source = null;
    }
	
	public int getError()
	{
		return _error;
	}
	
	public void setError(int error)
	{
		_error = error;
	}
	
	public void setMessage(String message)
	{
		_message = message;
	}
	
	public String getMessage()
	{
		return _message;
	}
	
	public int getMark()
	{
		return _mark;
	}
	
	public void setMark(int mark)
	{
		_mark = mark;
	}

	@Override
	public void dispose() 
	{
		_message = null;
	}
}
