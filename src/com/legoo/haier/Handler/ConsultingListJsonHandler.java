package com.legoo.haier.Handler;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.InputFilter.LengthFilter;

import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Handler.Json.ModelListJsonHandler;
import com.legoo.haier.Model.ConsultingModel;
import com.legoo.haier.Model.UserModel;

/**
 * @class User Json Handler 
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-14
 */
public class ConsultingListJsonHandler extends ModelListJsonHandler
{
	private static final String ROOT = "message";
	public static final String USERID = "userid";
	public static final String MESSAGE = "message_info";
	public static final String DATETIEM = "datetime";
	
    public ConsultingListJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			JSONObject jsonObject;
			ConsultingModel current;
			JSONArray jsonArray = new JSONArray();
			initModelList();
			try
			{
				jsonArray = rootObject.getJSONArray(ROOT);
			}catch (JSONException e)
			{
				setError(ERROR_NONE);
	            rootObject = null;
	            return true;
			}
			int lenght = jsonArray.length();
			for(int i=0;i<lenght;i++)
			{
				current = new ConsultingModel();
				jsonObject = jsonArray.getJSONObject(i);
				current.setId(getString(jsonObject, USERID));
				current.setMessage(getString(jsonObject, MESSAGE));
				current.setId(getString(jsonObject, DATETIEM));
				add(current);
			}
			setError(ERROR_NONE);
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
