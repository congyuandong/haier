package com.legoo.haier.Adapter.Base;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @class Base Layout
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-5
 */
public abstract class BaseRelativeLayout extends RelativeLayout
{
	public BaseRelativeLayout(Context context, int resource) 
	{
		super(context);
		View.inflate(context, resource, this);
	}
}
