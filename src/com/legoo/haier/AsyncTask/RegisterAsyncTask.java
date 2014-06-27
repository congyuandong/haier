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
 * @class Register AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class RegisterAsyncTask extends NetworkAsyncTask 
{
	private static final String ACCOUNT = "LOGID";
	private static final String NAME = "XM";
	private static final String PASSWORD = "PASSWORD";
	private static final String TELEPHONE = "PHONE";
	private static final String EMAIL = "EMAIL";
	private static final String ID_CARD = "SFZH";
	
	private String _account;
	private String _name;
	private String _password;
	private String _telephone;
	private String _email;
	private String _idcard;

	
	public RegisterAsyncTask(String account, String name, String password, String telephone, String email, String idcard)
	{
		super();
		_name = name;
		_account = account;
		_password = password;
		_telephone = telephone;
		_email = email;
		_idcard = idcard;
	}
	
	@Override
	protected JsonEvent doInBackground(Void... params) 
	{   
		JsonEvent event = new JsonEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getRegister();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    pairs.add(new BasicNameValuePair(ACCOUNT, _account));
		    pairs.add(new BasicNameValuePair(NAME, _name));
		    pairs.add(new BasicNameValuePair(PASSWORD, _password));
		    pairs.add(new BasicNameValuePair(TELEPHONE, _telephone));
		    pairs.add(new BasicNameValuePair(EMAIL, _email));
		    pairs.add(new BasicNameValuePair(ID_CARD, _idcard));
		    
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
