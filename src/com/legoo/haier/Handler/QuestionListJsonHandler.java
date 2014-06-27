package com.legoo.haier.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelListJsonHandler;
import com.legoo.haier.Model.QuestionListModel;

/**
 * @class Experts Json Handler 
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-10
 */
public class QuestionListJsonHandler extends ModelListJsonHandler
{    
	public final static String ROOT = "obj";
	
	public final static String ITEM_ID = "DZFWMENUID";
	public final static String ITEM_NAME = "MENUNAME";

	private QuestionListModel current;
	
    public QuestionListJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			initModelList(); 
			
			JSONObject rootObject = new JSONObject(content);
			JSONArray jsonArray = rootObject.getJSONArray(ROOT);
			JSONObject jsonObject;
			
			int length = jsonArray.length();  
            for(int i = 0; i < length; i++)
            {
            	current = new QuestionListModel();
            	jsonObject = jsonArray.getJSONObject(i);  
            	
            	current.setID(getString(jsonObject, ITEM_ID));
            	current.setName(getString(jsonObject, ITEM_NAME));
    			add(current);
            }  

            setPageNumber(0);
            setPageCount(0);
            jsonObject = null;
            jsonArray = null;
            rootObject = null;
			current = null;
		} 
		catch (JSONException e)
		{
			setMessage(R.string.json_error_format);
			setError(JsonHandler.ERROR_HANDLER);
		}
		
		return true;
	}
} 
