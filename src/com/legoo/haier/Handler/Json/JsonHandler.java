package com.legoo.haier.Handler.Json;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.Handler.Base.NetworkHandler;

/**
 * @class Json Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public abstract class JsonHandler extends NetworkHandler
{
	private final boolean AUTO_CORRECT_VALUE = true;
	
	private final String BASE_ERROR_MARK = "success";
	private final String BASE_ERROR_MESSAGR = "error";
	
	private boolean baseErrorCheck = true;
	
    private String _original;
    
    public JsonHandler setBaseErrorCheck(boolean check)
	{
		baseErrorCheck = check;
		return this;
	}
    
	public String getOriginal()
	{
		return _original;
	}

	public void setOriginal(String json)
	{
		_original = json;
	}
	
	public JsonHandler()
	{
		super();
	}
	
	protected void parse(String content)
	{
		if (baseErrorCheck == false || detectBaseError(content) == false)
		{
			process(content);
		}
	}
	
	protected abstract boolean process(String content);
	
	protected int getInt(JSONObject jsonObject, String name)
	{
		int defaultValue = 0;
		try 
		{
			if (jsonObject.has(name) && jsonObject.get(name) != JSONObject.NULL)
			{
				return jsonObject.getInt(name);
			}
			else 
			{
				return defaultValue;
			}
		} 
		catch (JSONException e) 
		{
			return defaultValue;
		}
	}
	
	protected boolean getBoolean(JSONObject jsonObject, String name)
	{
		boolean defaultValue = false;
		try 
		{
			if (jsonObject.has(name) && jsonObject.get(name) != JSONObject.NULL)
			{
				return jsonObject.getBoolean(name);
			}
			else 
			{
				return defaultValue;
			}
		} 
		catch (JSONException e) 
		{
			return defaultValue;
		}
	}
	
	protected double getDouble(JSONObject jsonObject, String name)
	{
		double defaultValue = 0.0d;
		try 
		{
			if (jsonObject.has(name) && jsonObject.get(name) != JSONObject.NULL)
			{
				return jsonObject.getDouble(name);
			}
			else 
			{
				return defaultValue;
			}
		} 
		catch (JSONException e) 
		{
			return defaultValue;
		}
	}
	
	protected String getString(JSONObject jsonObject, String name)
	{
		String defaultValue = AUTO_CORRECT_VALUE ? "" : null;
		try 
		{
			if (jsonObject.has(name) && jsonObject.get(name) != JSONObject.NULL)
			{
				return jsonObject.getString(name);
			}
			else 
			{
				return defaultValue;
			}
		} 
		catch (JSONException e) 
		{
			return defaultValue;
		}
	}
	
	protected boolean detectBaseError(String content) 
	{
		if (getError() == ERROR_NONE 
				&& content != null && content.trim().length() > 0 
				&& content.trim().substring(0, 1).equals("{") == true)
    	{
			JSONObject jsonObject;
			try
			{
				jsonObject = new JSONObject(content);
				if (jsonObject.has(BASE_ERROR_MARK) && getBoolean(jsonObject, BASE_ERROR_MARK) == false)
				{
					if (jsonObject.get(BASE_ERROR_MESSAGR) != JSONObject.NULL)
					{
						setMessage(jsonObject.getString(BASE_ERROR_MESSAGR));
					}
	    			setError(ERROR_HANDLER);
				}
			} 
			catch (JSONException e)
			{
				setMessage("");
				setError(ERROR_HANDLER);
			}
    	}
		return getError() != ERROR_NONE;
	}
}
