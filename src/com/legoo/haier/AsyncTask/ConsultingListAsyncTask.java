package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Handler.ValueJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class ConsultingListAsyncTask extends NetworkAsyncTask 
{

	
	public ConsultingListAsyncTask()
	{
		super();
	}
	
	@Override
	protected ModelListEvent doInBackground(Void... params) 
	{   
		ModelListEvent event = new ModelListEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().postConsultingList();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    
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
