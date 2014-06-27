package com.legoo.haier.Adapter.Base;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @class Base Layout
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-5
 */
public abstract class BaseLinearLayout extends LinearLayout
{
	public BaseLinearLayout(Context context, int resource) 
	{
		super(context);
		View.inflate(context, resource, this);
	}
}
