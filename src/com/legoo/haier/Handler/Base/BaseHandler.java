package com.legoo.haier.Handler.Base;

import org.xml.sax.helpers.DefaultHandler;

/**
 * @class Base Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class BaseHandler extends DefaultHandler
{
	private boolean _hasError;
	
	public boolean getHasError()
	{
		return _hasError;
	}
	
	public void setHasError(boolean error)
	{
		_hasError = error;
	}
    
	public BaseHandler() {}
}
