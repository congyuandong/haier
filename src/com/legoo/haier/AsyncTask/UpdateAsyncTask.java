package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.UpdateJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Contact AsyncTask
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class UpdateAsyncTask extends NetworkAsyncTask 
{
	
	public UpdateAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getUpdate();
		if (url != null)
		{
			UpdateJsonHandler handler;
			do
			{
				handler = (UpdateJsonHandler) JsonOperation.get(url, new UpdateJsonHandler());
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
