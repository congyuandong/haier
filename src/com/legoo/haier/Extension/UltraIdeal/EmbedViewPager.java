package com.legoo.haier.Extension.UltraIdeal;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @class Embed ViewPager
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class EmbedViewPager extends ViewPager
{
	private float _lastX;
	private float _lastY;

	public EmbedViewPager(Context context)
	{
		super(context);
	}

	public EmbedViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override  
    public boolean dispatchTouchEvent(MotionEvent ev) 
	{  
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			_lastX = ev.getX();
			_lastY = ev.getY();
		}
		else if (ev.getAction() == MotionEvent.ACTION_UP)
		{
			_lastX = 0;
			_lastY = 0;
		}
		else if (ev.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (Math.abs(ev.getY() - _lastY) < Math.abs(ev.getX() - _lastX))
			{
				getParent().requestDisallowInterceptTouchEvent(true);
			}
		}
        return super.dispatchTouchEvent(ev);  
    } 
}
