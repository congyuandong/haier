package com.legoo.haier.Activity.Base;

import com.legoo.haier.Extension.ApplicationUtils;
import android.app.Activity;
import android.os.Bundle;

/**
 * @class Base Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public abstract class BaseActivity extends Activity
{
	public static final String EXTRA_SELF = "EXTRA_SELF";
	
	public void onCreate(Bundle savedInstanceState, int view) 
	{
		this.onCreate(savedInstanceState, view, false);
	}
	
	public void onCreate(Bundle savedInstanceState, int view, boolean fullscreen)
	{
		super.onCreate(savedInstanceState);
		if (fullscreen == true)
		{
			ApplicationUtils.setFullScreen(this);
		}
		setContentView(view);
	}

	protected void finishForRestore()
	{
		if (ApplicationUtils.hasMainReady(this) == true
				|| getIntent().getBooleanExtra(EXTRA_SELF, false) == false) 
        {  
            super.finish();
        } 
        else 
        {  
        	ApplicationUtils.restoreApplication(this);
        	super.finish();  
        } 
	}
}
