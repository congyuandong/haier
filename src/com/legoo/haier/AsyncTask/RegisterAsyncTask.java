package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Handler.UserJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;
import com.legoo.haier.Model.UserModel;


/**
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class RegisterAsyncTask extends NetworkAsyncTask 
{
	private static final String NAME = "name";
	private static final String PASSWORD = "password";
	
	private String _name;
	private String _password;

	
	public RegisterAsyncTask(String name, String password)
	{
		super();
		_name = name;
		_password = password;
	}
	
	@Override
	protected ModelEvent doInBackground(Void... params) 
	{   
		ModelEvent event = new ModelEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().postRegister();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    pairs.add(new BasicNameValuePair(NAME, _name));
		    pairs.add(new BasicNameValuePair(PASSWORD, _password));
		    
		    UserJsonHandler handler;
			do
			{
				handler = (UserJsonHandler) JsonOperation.post(url, pairs, new UserJsonHandler());
			}
			while (retryTask(handler) == true);
			
			event.setError(handler.getError());
			event.setMessage(handler.getMessage());
			UserModel model =(UserModel)handler.getModel();
			model.setName(_name);
			model.setPassword(_password);
			event.setModel(model);
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
