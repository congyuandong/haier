package com.legoo.haier.AsyncTask.Base;

import java.util.EventObject;

/**
 * 
 * @class Base Event
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public abstract class BaseEvent extends EventObject implements EventInterface
{
	private static final long serialVersionUID = 7441197675113605847L;
	
	private boolean _hasError;
	
	public boolean getHasError()
	{
		return _hasError;
	}
	
	public void setHasError(boolean error)
	{
		_hasError = error;
	}
	
	public BaseEvent(Object source)
    {
        super(source);
        source = null;
    }
}
