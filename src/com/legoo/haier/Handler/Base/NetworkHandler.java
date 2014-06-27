package com.legoo.haier.Handler.Base;

import com.legoo.haier.Application.Hospital;


/**
 * @class Network Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class NetworkHandler extends BaseHandler
{
	public static final int ERROR_NONE = 0;
	public static final int ERROR_CONNECTION = 1;
	public static final int ERROR_HANDLER = 2;
	
    private String _message;
    
    private int _error;
    
    public String getMessage()
    {
    	return _message;
    }

    public void setMessage(String message)
    {
    	_message = message;
    }
    
    public void setMessage(int resourceId)
    {
    	_message = Hospital.getInstance().getApplicationContext().getString(resourceId);
    }
    
    public int getError()
    {
    	return _error;
    }
    
    public void setError(int error)
    {
    	_error = error;
    	setHasError(_error != ERROR_NONE);
    }
    
	public boolean isValid()
	{
		return true;
	}
	
	public NetworkHandler()
    {
		_message = "";
		_error= ERROR_NONE;
    }
}
