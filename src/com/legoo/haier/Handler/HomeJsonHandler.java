package com.legoo.haier.Handler;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.legoo.haier.R;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.ModelJsonHandler;
import com.legoo.haier.Model.HomeModel;
import com.legoo.haier.Model.LinkModel;
import com.legoo.haier.Model.PagerModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Home Json Handler 
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-20
 */
public class HomeJsonHandler extends ModelJsonHandler
{    
	public final static String TOPIC_ROOT = "list_banner";
	public final static String TOPIC_ID = "ADBANNERID";
	public final static String TOPIC_URL = "PHONEURL";
	public final static String TOPIC_IMAGE = "TPMC";
	
	public final static String NOTICE_ROOT = "list_zxgg";
	public final static String NOTICE_ID = "ZXGGID";
	public final static String NOTICE_TIME = "FBRQ";
	public final static String NOTICE_TITLE = "TITLE";
	public final static String NOTICE_BODY = "CONTENT";

    public HomeJsonHandler() {}

	@Override
	protected boolean process(String content) 
	{
		try
		{
			JSONObject rootObject = new JSONObject(content);
			JSONObject jsonObject;
			
			PagerModel pager;
			HomeModel model = new HomeModel();
			List<ModelInterface> list = new ArrayList<ModelInterface>();
			
			JSONArray jsonArray = rootObject.getJSONArray(TOPIC_ROOT);
			int length = jsonArray.length();  
            for(int i = 0; i < length; i++)
            {
            	pager = new PagerModel();
            	jsonObject = jsonArray.getJSONObject(i);  
            	
            	pager.setUrl(getString(jsonObject, TOPIC_URL));
            	pager.setImage(getString(jsonObject, TOPIC_IMAGE));
            	
            	list.add(pager);
            }  
            model.setTopics(list);
            
            LinkModel link;
            list = new ArrayList<ModelInterface>();
            jsonArray = rootObject.getJSONArray(NOTICE_ROOT);
            length = jsonArray.length();  
            for(int i = 0; i < length; i++)
            {
            	link = new LinkModel();
            	jsonObject = jsonArray.getJSONObject(i);  
            	
            	link.setID(getString(jsonObject, NOTICE_ID));
            	link.setTime(getString(jsonObject, NOTICE_TIME));
            	link.setTitle(getString(jsonObject, NOTICE_TITLE));
            	link.setBody(getString(jsonObject, NOTICE_BODY));
            	
            	list.add(link);
            }  
            model.setNotices(list);
            
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
