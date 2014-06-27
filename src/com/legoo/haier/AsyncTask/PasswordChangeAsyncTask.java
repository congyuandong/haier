package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.Handler.ValueJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class Password Change AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class PasswordChangeAsyncTask extends NetworkAsyncTask 
{
	private static final String ACCOUNT = "LOGID";
	private static final String PASSWORD_OLD = "PASSWORD";
	private static final String PASSWORD_NEW = "NEWPASSWORD";
	
	private String _passwordOld;
	private String _passwordNew;
	
	public PasswordChangeAsyncTask(String passwordOld, String passwordNew)
	{
		super();
		_passwordOld = passwordOld;
		_passwordNew = passwordNew;
	}
	
	@Override
	protected JsonEvent doInBackground(Void... params) 
	{   
		JsonEvent event = new JsonEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getPasswordChange();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    pairs.add(new BasicNameValuePair(ACCOUNT, Hospital.getInstance().getUser().getCurrent().getAccount())); 
		    pairs.add(new BasicNameValuePair(PASSWORD_OLD, _passwordOld));
		    pairs.add(new BasicNameValuePair(PASSWORD_NEW, _passwordNew));
		    
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
