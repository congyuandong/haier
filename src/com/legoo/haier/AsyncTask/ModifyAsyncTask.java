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
 * @class Modify AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class ModifyAsyncTask extends NetworkAsyncTask 
{
	private static final String ACCOUNT = "LOGID";
	private static final String PASSWORD = "PASSWORD";
	private static final String NAME = "XM";
	private static final String TELEPHONE = "PHONE";
	private static final String ID_CARD = "SFZH";
	
	private String _name;
	private String _telephone;
	private String _idcard;

	
	public ModifyAsyncTask(String name, String telephone, String idcard)
	{
		super();
		_name = name;
		_telephone = telephone;
		_idcard = idcard;
	}
	
	@Override
	protected JsonEvent doInBackground(Void... params) 
	{   
		JsonEvent event = new JsonEvent(this);
		event.setMark(super.getMark());
		String url = Hospital.getInstance().getDataService().getModify();
		if (url != null)
		{
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		    pairs.add(new BasicNameValuePair(ACCOUNT, Hospital.getInstance().getUser().getCurrent().getAccount()));
		    pairs.add(new BasicNameValuePair(PASSWORD, Hospital.getInstance().getUser().getCurrent().getPasswordMD5())); 
		    pairs.add(new BasicNameValuePair(NAME, _name));
		    pairs.add(new BasicNameValuePair(TELEPHONE, _telephone));
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
