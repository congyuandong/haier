package com.legoo.haier.AsyncTask;

import com.legoo.haier.Application.HaierThreadPool;
import com.legoo.haier.AsyncTask.Base.BaseAsyncTask;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import com.legoo.haier.AsyncTask.Callback.SampleEvent;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

/**
 * @class  Sample AsyncTask
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class SampleAsyncTask extends BaseAsyncTask
{
	public SampleAsyncTask() { }
	
	@Override
	protected SampleEvent doInBackground(Void... params) 
	{
		return new SampleEvent(this);
	} 
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public AsyncTask<Void, Void, EventInterface> executeEnhanced(Void... params)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return executeOnExecutor(HaierThreadPool.SAMPLE_EXECUTOR, params);
	    }
		else
		{
	    	return execute(params);
	    }
	}
}
