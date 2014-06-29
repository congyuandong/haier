package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;

/**
 * @class Value Json Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class RespJsonHandler extends JsonHandler
{
	public static final String SUCCESS = "0";
	private static final String VALUE = "resp_code";
	
	private String _value;

	public String getValue()
	{
		return _value;
	}
	public boolean isSuccess()
	{
		return _value !=null &&_value.equals(SUCCESS);
	}

	public RespJsonHandler() {}
	
	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			
			_value = getString(rootObject, VALUE);
			if(!isSuccess())
			{
				setError(ERROR_HANDLER);
			}
            rootObject = null;
            setMessage(_value);
            setError(ERROR_NONE);
		} 
		catch (JSONException e)
		{
			setMessage(R.string.json_error_format);
			setError(JsonHandler.ERROR_HANDLER);
		}
		
		return true;
	}
}
