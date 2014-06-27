package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.AgreementJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Agreement Detail AsyncTask
 * @author Mlzx
 * @version 1.0
 * @date 2014-04-09
 */
public class AgreementAsyncTask extends NetworkAsyncTask 
{
	
	public AgreementAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getAgreement();
		if (url != null)
		{
			AgreementJsonHandler handler;
			do
			{
				handler = (AgreementJsonHandler) JsonOperation.get(url, new AgreementJsonHandler());
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
