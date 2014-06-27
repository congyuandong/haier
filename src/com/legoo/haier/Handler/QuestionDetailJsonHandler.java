package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.QuestionDetailModel;

/**
 * @class Question Json Handler 
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class QuestionDetailJsonHandler extends ModelJsonHandler
{   
	
	public final static String ID = "DZFWMENUID";
	public final static String CONTENT = "CONTENT";

    public QuestionDetailJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			
			QuestionDetailModel model = new QuestionDetailModel();
			model.setID(getString(rootObject, ID));
			model.setContent(getString(rootObject, CONTENT));
			
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
