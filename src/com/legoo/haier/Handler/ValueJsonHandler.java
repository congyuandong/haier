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
public class ValueJsonHandler extends JsonHandler
{
	private static final String VALUE = "value";
	
	private String _value;

	public String getValue()
	{
		return _value;
	}

	public ValueJsonHandler() {}
	
	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			
			_value = getString(rootObject, VALUE);
			
            rootObject = null;
		} 
		catch (JSONException e)
		{
			setMessage(R.string.json_error_format);
			setError(JsonHandler.ERROR_HANDLER);
		}
		
		return true;
	}
}
