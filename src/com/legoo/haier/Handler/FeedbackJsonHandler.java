package com.legoo.haier.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelListJsonHandler;
import com.legoo.haier.Model.FeedbackModel;

/**
 * @class Reservations Json Handler 
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-07
 */
public class FeedbackJsonHandler extends ModelListJsonHandler
{      	
	public final static String ROOT = "list_ydyf";

	public final static String ID = "ID";
	public final static String Name = "XM";
	public final static String ID_CARD = "SFZH";
	public final static String READONLY = "ZT";
	public final static String DATE = "RQ";
	public final static String OPINION = "YJ";
	public final static String SUGGESTIONS = "JY";
	
	private FeedbackModel current;
	
    public FeedbackJsonHandler() {}

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
            	current = new FeedbackModel();
            	jsonObject = jsonArray.getJSONObject(i);  
            	
            	current.setID(getString(jsonObject, ID));
            	current.setName(getString(jsonObject,Name));
            	current.setIDCard(getString(jsonObject, ID_CARD));
            	current.setReadonly(getString(jsonObject, READONLY));
            	current.setDate(getString(jsonObject,DATE));
            	current.setOpinion(getString(jsonObject,OPINION));
            	current.setSuggestions(getString(jsonObject,SUGGESTIONS));
            	
    			add(current);
            }  

            setPageNumber(1);
            setPageCount(1);
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
