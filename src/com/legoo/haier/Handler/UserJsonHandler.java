package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.UserModel;

/**
 * @class User Json Handler 
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-14
 */
public class UserJsonHandler extends ModelJsonHandler
{      	
//	public final static String ROOT = "user";
	public final static String RESP = "resp_code";
	public final static String ID = "userid";
	public final static String DEVICE_ID = "mac";

	public final static String CODE_SUCCESS = "0";
	public final static String CODE_PASSWORD = "1";
	public final static String CODE_USERNAME = "2";
	public final static String CODE_UNKNOWN = "3";
	
    public UserJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			UserModel model = new UserModel();
			if(!getString(rootObject, RESP).equals(CODE_SUCCESS))
			{
				setError(ERROR_HANDLER);
				setMessage(getString(rootObject, RESP));
			}else
			{
				model.setId(getString(rootObject, ID));
				model.setDeviceid(getString(rootObject,DEVICE_ID));
				setError(ERROR_NONE);
			}			
			setModel(model);
			
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
