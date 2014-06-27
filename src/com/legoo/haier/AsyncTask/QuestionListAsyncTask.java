package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Handler.QuestionListJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Question AsyncTask
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class QuestionListAsyncTask extends NetworkAsyncTask 
{
	
	public QuestionListAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelListEvent doInBackground(Void... params) 
	{   
		ModelListEvent event = new ModelListEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().getQuestionList();
		if (url != null)
		{
			QuestionListJsonHandler handler;
			do
			{
				handler = (QuestionListJsonHandler) JsonOperation.get(url, new QuestionListJsonHandler());
			}
			while (retryTask(handler) == true);
			
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());
			event.setModelList(handler.getModelList());  
			event.setPageCount(handler.getPageCount());
			event.setPageNumber(handler.getPageNumber());
			handler = null;
		}
		else 
		{
			event.setError(JsonHandler.ERROR_CONNECTION);
		}
		return event;
	}
}
