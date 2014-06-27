package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.AgreementModel;

/**
 * @class Agreement Json Handler 
 * @author Mlzx
 * @version 1.0
 * @date 2014-04-09
 */
public class AgreementJsonHandler extends ModelJsonHandler
{   
	public final static String ROOT = "web_fwxy";
	
	public final static String ID = "FWXYID";
	public final static String CONTENT = "CONTENT";

    public AgreementJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			JSONObject jsonObject =rootObject.getJSONObject(ROOT);
			
			AgreementModel model = new AgreementModel();
			model.setID(getString(jsonObject, ID));
			model.setContent(getString(jsonObject, CONTENT));
			
			setModel(model);
			
            rootObject = null;
            jsonObject = null;
		} 
		catch (JSONException e)
		{
			setMessage(R.string.json_error_format);
			setError(JsonHandler.ERROR_HANDLER);
		}
		
		return true;
	}
} 
