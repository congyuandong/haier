package com.legoo.haier.Activity.Base;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @class Tab View Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-5
 */
public abstract class TabViewActivity extends BaseActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState, int view)  
	{
		super.onCreate(savedInstanceState, view);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{     
        return false;
	}
}
