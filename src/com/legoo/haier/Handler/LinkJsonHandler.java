package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.LinkModel;

/**
 * @class Value Json Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class LinkJsonHandler extends ModelJsonHandler
{
	private static final String CONTENT = "resp_info";
	public LinkJsonHandler() {}
	
	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			LinkModel model = new LinkModel();
			model.setBody(rootObject.getString(CONTENT));
            rootObject = null;
            setModel(model);
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
