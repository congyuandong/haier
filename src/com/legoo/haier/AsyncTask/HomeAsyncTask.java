package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.HomeJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Home AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-20
 */
public class HomeAsyncTask extends NetworkAsyncTask 
{
	public HomeAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getHome();
		if (url != null)
		{
			HomeJsonHandler handler;
			do
			{
				handler = (HomeJsonHandler) JsonOperation.get(url, new HomeJsonHandler());
			}
			while (retryTask(handler) == true);
			
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());
			event.setModel(handler.getModel());  
			handler = null;
		}
		else 
		{
			event.setError(JsonHandler.ERROR_CONNECTION);
		}
		return event;
	}
}
