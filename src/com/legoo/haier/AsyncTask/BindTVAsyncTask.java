package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.Handler.RespJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class BindTVAsyncTask extends NetworkAsyncTask 
{
	private static final String USERID = "userid";
	private static final String DEVICEID = "mac";
	
	private String deviceid;
	public BindTVAsyncTask(String deviceid)
	{
		super();
		this.deviceid = deviceid;
	}
	
	@Override
	protected JsonEvent doInBackground(Void... params) 
	{   
		JsonEvent event = new JsonEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().postBindTV();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
			pairs.add(new BasicNameValuePair(USERID, Haier.getInstance().getUser().getCurrent().getId()));
			pairs.add(new BasicNameValuePair(DEVICEID, deviceid));
			RespJsonHandler handler;
			do
			{
				handler = (RespJsonHandler) JsonOperation.post(url, pairs, new RespJsonHandler());
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
