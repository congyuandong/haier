package com.legoo.haier.Activity.Base;

import com.legoo.haier.Extension.ApplicationUtils;
import android.app.ActivityGroup;
import android.os.Bundle;

/**
 * @class Base Activity Group
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-26
 */
@SuppressWarnings("deprecation")
public class BaseActivityGroup extends ActivityGroup
{
	public static final String EXTRA_SELF = "EXTRA_SELF";
	
	public void onCreate(Bundle savedInstanceState, int view) 
    {
        super.onCreate(savedInstanceState);
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
