package com.legoo.haier.AsyncTask.Base;

import java.util.Vector;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

/**
 * @class Base Async Task
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-10
 */
public class BaseAsyncTask extends AsyncTask<Void, Void, EventInterface>
{
	private Vector<BaseListener> vectorListeners = new Vector<BaseListener>();

	public synchronized void addOnFinishListener(BaseListener listener)
	{
		vectorListeners.addElement(listener);
		listener = null;
	}

	public synchronized void removeOnFinishListener(BaseListener listener)
	{
		vectorListeners.removeElement(listener);
		listener = null;
	}
	
	@SuppressWarnings("unchecked")
	protected void activateOnFinishEvent(EventInterface event)
    {
        Vector<BaseListener> tempVector = null;

        synchronized(this)
        {
            tempVector = (Vector<BaseListener>) vectorListeners.clone();
            for(int i=0; i < tempVector.size(); i++)
            {
            	BaseListener listener = (BaseListener)tempVector.elementAt(i);
            	listener.EventActivated(event);
            	listener = null;
            }
        }

        tempVector = null;
        event = null;
    }

	@Override
	protected EventInterface doInBackground(Void... params) {return null;} 
	
	@Override 
	protected void onPostExecute(EventInterface event) 
	{    
		activateOnFinishEvent(event);
	}
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public AsyncTask<Void, Void, EventInterface> executeEnhanced(Void... params)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			return executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
	    }
		else
		{
	    	return execute(params);
	    }
	}
}