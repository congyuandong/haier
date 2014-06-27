package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.Handler.ValueJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class HelpAsyncTask extends NetworkAsyncTask 
{
	private static final String INFO_ID = "info_id";
	
	private String info_id;

	
	public HelpAsyncTask()
	{
		super();
		info_id = "1";
	}
	
	@Override
	protected JsonEvent doInBackground(Void... params) 
	{   
		JsonEvent event = new JsonEvent(this);
		event.setMark(super.getMark());
//		String url = Haier.getInstance().getDataService().getRegister();
		String url = "";
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    pairs.add(new BasicNameValuePair(INFO_ID, info_id));
		    
			ValueJsonHandler handler;
			do
			{
				handler = (ValueJsonHandler) JsonOperation.post(url, pairs, new ValueJsonHandler());
			}
			while (retryTask(handler) == true);
			
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());

			pairs.clear();
			pairs = null;
			url = null;
			handler = null;
		}
		else 
		{
			event.setError(JsonHandler.ERROR_CONNECTION);
		}
		return event;
	}
}
