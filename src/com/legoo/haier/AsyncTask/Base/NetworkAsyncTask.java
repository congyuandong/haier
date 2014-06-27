package com.legoo.haier.AsyncTask.Base;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import com.legoo.haier.Application.HospitalSettings;
import com.legoo.haier.Application.HospitalThreadPool;
import com.legoo.haier.Handler.Base.NetworkHandler;

/**
 * @class NetworkAsyncTask.java
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public abstract class NetworkAsyncTask extends BaseAsyncTask
{
	private NetworkRetryTask retryTask;
	
	private int mark;
	
	public NetworkAsyncTask()
	{
		initRetryTask();
	}
	
	private void initRetryTask()
	{
		retryTask = new NetworkRetryTask();
	}
	
	protected boolean retryTask(NetworkHandler hanlder)
	{
		return retryTask.retry(hanlder);
	}
	
	public int getMark()
	{
		return mark;
	}
	
	public void setMark(int mark)
	{
		this.mark = mark;
	}
	
	public class NetworkRetryTask 
	{
		private final int RETRY_MAX = HospitalSettings.NETWORK_TASK_RETRY_COUNT;
		private int retry = 0;
		
		public NetworkRetryTask() { }
		
		public boolean retry(NetworkHandler hanlder)
		{
			boolean flag = false;
			if (hanlder.isValid() == false)
			{
				hanlder.setError(NetworkHandler.ERROR_HANDLER);
			}
			while (hanlder.getError() != NetworkHandler.ERROR_NONE && retry < RETRY_MAX - 1)
			{
				retry += 1;
				flag = true;
				break;
			}
			return flag;
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public AsyncTask<Void, Void, EventInterface> executeEnhanced(Void... params)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return executeOnExecutor(HospitalThreadPool.NETWORK_EXECUTOR, params);
	    }
		else
		{
	    	return execute(params);
	    }
	}
}
