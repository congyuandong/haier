package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.RepairTVModel;

/**
 * @class Value Json Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class RepairTVJsonHandler extends ModelJsonHandler
{
	private static final String CODE = "resp_code";
	
	public RepairTVJsonHandler() {}
	
	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			RepairTVModel model = new RepairTVModel();
			model.setCode(getString(rootObject, CODE));
			
			setError(ERROR_NONE);
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
