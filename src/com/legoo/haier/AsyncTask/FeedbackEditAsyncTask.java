package com.legoo.haier.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Handler.ValueJsonHandler;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Handler.Json.JsonOperation;


/**
 * @class ContactsAsyncTask AsyncTask
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-07
 */
public class FeedbackEditAsyncTask extends NetworkAsyncTask 
{
	private static final String ACCOUNT = "LOGID";
	private static final String PASSWORD = "PASSWORD";
	private static final String ID = "ID";
	private static final String NAME = "XM";
	private static final String OPINION = "YJ";
	private static final String SUGGESTIONS = "JY";
	private static final String IDCARD = "SFZH";

	private String _name;
	private String _opinion;
	private String _suggestions;
	private String _idCard;
	private String _ID;
	public FeedbackEditAsyncTask(String name,String opinion,String suggestions,String idcard,String id)
	{
		super();
		_name = name;
		_opinion = opinion;
		_suggestions = suggestions;
		_idCard = idcard;
		_ID = id;
	}
	
	@Override
	protected ModelListEvent doInBackground(Void... params) 
	{   
		ModelListEvent event = new ModelListEvent(this);
		event.setMark(super.getMark());
		String url = Haier.getInstance().getDataService().getFeedbackEdit();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
			pairs.add(new BasicNameValuePair(ACCOUNT, Haier.getInstance().getUser().getCurrent().getAccount()));
			pairs.add(new BasicNameValuePair(PASSWORD, Haier.getInstance().getUser().getCurrent().getPasswordMD5()));
			pairs.add(new BasicNameValuePair(ID, _ID));
			pairs.add(new BasicNameValuePair(NAME, _name));
			pairs.add(new BasicNameValuePair(OPINION, _opinion));
			pairs.add(new BasicNameValuePair(SUGGESTIONS,_suggestions ));
			pairs.add(new BasicNameValuePair(IDCARD, _idCard));
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
