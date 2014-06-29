package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Handler.ConsultingListJsonHandler;
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
	public static final String ID = "userid";
	public static final String DATETIME = "datetime";

	private String datetime;
	
	public ConsultingListAsyncTask(String datetime)
	{
		super();
		this.datetime = datetime;
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
		    pairs.add(new BasicNameValuePair(ID, Haier.getInstance().getUser().getCurrent().getId()));
		    pairs.add(new BasicNameValuePair(DATETIME, datetime));
		    
			ConsultingListJsonHandler handler;
			do
			{
				handler = (ConsultingListJsonHandler) JsonOperation.post(url, pairs, new ConsultingListJsonHandler());
			}
			while (retryTask(handler) == true);
			
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());
			event.setModelList(handler.getModelList());

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
