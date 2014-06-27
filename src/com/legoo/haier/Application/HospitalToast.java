package com.legoo.haier.Application;

import com.legoo.haier.R;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/**
 * @class Hospital Toast
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HospitalToast
{
	private Hospital _application;
	
	private final int DURATION = 2500;
	
	private Toast _toast;
	
	public HospitalToast(Hospital application)
	{
		_application = application;
	}
	
	public synchronized void show(final String content, int delay)
	{
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				show(content);
			}
		}, delay);
	}
	
	public synchronized void show(String content)
	{
		String text = String.format(" %1$s ", content);
		if (_toast == null)
    	{
			_toast = Toast.makeText(_application.getApplicationContext(), text, DURATION);
    		View view = _toast.getView();
    		view.setBackgroundResource(R.drawable.public_toast_black);
    		_toast.setView(view);
    		view = null;
    	}
    	else
    	{
    		_toast.setText(text);
    		_toast.setDuration(DURATION);
    	}
		_toast.show();
	}
}
