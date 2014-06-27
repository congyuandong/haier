package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.UserJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;
import com.legoo.haier.Model.UserModel;


/**
 * @class Authenticate AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class AuthenticateAsyncTask extends NetworkAsyncTask 
{
	private static final String ACCOUNT = "LOGID";
	private static final String PASSWORD = "PASSWORD";
	
	private String _account;
	private String _password;
	
	public AuthenticateAsyncTask(String account, String password)
	{
		super();
		_account = account;
		_password = password;
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getAuthenticate();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    pairs.add(new BasicNameValuePair(ACCOUNT, _account));
		    pairs.add(new BasicNameValuePair(PASSWORD, _password));
		    
			UserJsonHandler handler;
			do
			{
				handler = (UserJsonHandler) JsonOperation.post(url, pairs, new UserJsonHandler());
			}
			while (retryTask(handler) == true);
			
			if (handler.getError() == JsonHandler.ERROR_NONE)
			{
				UserModel model = (UserModel) handler.getModel();
				model.setPassword(_password);
			}
			
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
