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
	public final static String ROOT = "user";
	
	public final static String ID = "YHID";
	public final static String ACCOUNT = "LOGID";
	public final static String NAME = "XM";
	public final static String PASSWORD = "PASSWORD";
	public final static String TELEPHONE = "PHONE";
	public final static String ID_CARD = "SFZH";
	public final static String EMAIL = "EMAIL";
	
    public UserJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content).getJSONObject(ROOT);
			UserModel model = new UserModel();
			model.setID(getString(rootObject, ID));
			model.setAccount(getString(rootObject, ACCOUNT));
			model.setName(getString(rootObject, NAME));
			model.setPasswordMD5(getString(rootObject, PASSWORD));
			model.setTelephone(getString(rootObject, TELEPHONE));
			model.setIDCard(getString(rootObject, ID_CARD));
			model.setEmail(getString(rootObject, EMAIL));
			
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
