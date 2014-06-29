package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.LinkJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class RecommendAsyncTask extends NetworkAsyncTask 
{

	
	public RecommendAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().getRecommend();
		if (url != null)
		{
			LinkJsonHandler handler;
			do
			{
				handler = (LinkJsonHandler) JsonOperation.get(url, new LinkJsonHandler(),true);
			}
			while (retryTask(handler) == true);
			event.setModel(handler.getModel());
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());

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
