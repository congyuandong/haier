package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.RepairTVJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class RepairTVAsyncTask extends NetworkAsyncTask 
{
	private static final String DEVICEID = "mac";
	private static final String USERID  = "userid";
	
	public RepairTVAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().postRepairTV();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
			pairs.add(new BasicNameValuePair(USERID, Haier.getInstance().getUser().getCurrent().getId()));
		    pairs.add(new BasicNameValuePair(DEVICEID, Haier.getInstance().getUser().getCurrent().getDeviceid()));
		    
			RepairTVJsonHandler handler;
			do
			{
				handler = (RepairTVJsonHandler) JsonOperation.post(url, pairs, new RepairTVJsonHandler());
			}
			while (retryTask(handler) == true);
			
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());
			event.setModel(handler.getModel());
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
