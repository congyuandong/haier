package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.QuestionDetailJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Question AsyncTask
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class QuestionDetailAsyncTask extends NetworkAsyncTask 
{
	private String _id;
	public QuestionDetailAsyncTask(String id)
	{
		super();
		_id = id;
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().getQuestionDetail(_id);
		if (url != null)
		{
			QuestionDetailJsonHandler handler;
			do
			{
				handler = (QuestionDetailJsonHandler) JsonOperation.get(url, new QuestionDetailJsonHandler());
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
