package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.BaseActivity;
import com.legoo.haier.Application.HaierSettings;
import com.legoo.haier.Extension.ApplicationUtils;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;


/**
 * @class Load Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2013-8-27
 */
public class LoadActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_load);
		initTask();
	}

	private void initTask() 
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				openMainActivity();
			}
		}, HaierSettings.LOAD_TIME);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{     
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        else
        {        
            return super.onKeyDown(keyCode, event);
        }
    }
	
	private void openMainActivity()
	{
		ApplicationUtils.restoreApplication(this);
		LoadActivity.this.finish();
	}
}
