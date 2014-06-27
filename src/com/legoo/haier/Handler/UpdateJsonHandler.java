package com.legoo.haier.Handler;

import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.UpdateModel;

/**
 * @class Contact Json Handler 
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class UpdateJsonHandler extends ModelJsonHandler
{
	
	public final static String ID = "ID";
	public final static String URL = "URL";
	public final static String VERSIONNAME = "VERSIONNAME";
	public final static String VERSIONCODE = "VERSIONCODE";
	

    public UpdateJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			UpdateModel model = new UpdateModel();
			model.setID(getString(rootObject, ID));
			model.setURL(getString(rootObject, URL));
			model.setVersionCode(getString(rootObject, VERSIONCODE));
			model.setVersionName(getString(rootObject, VERSIONNAME));
			
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
