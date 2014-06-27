package com.legoo.haier.Archon;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @class  ViewPager Archon
 * @author LeonNoW
 * @version 1.0
 * @date 2013-11-3
 */
public class ViewPagerArchon 
{
	private Context _context;
	
	private ViewPager _pager;
	private LinearLayout _layoutPoint;
	private int _pointView;
	private List<View> _points;
	private boolean _notPoint = false;
	
	private OnPageChangedListener _pageChangedListener;
	
	public void setAdapter(PagerAdapter adapter)
	{
		_pager.setAdapter(adapter);
		initPoints(adapter.getCount());
		setCurrentPoint(0);
	}
	
	public void setOnPageChangedListener(OnPageChangedListener listener)
	{
		_pageChangedListener = listener;
	}
	
	public void setCurrentPoint(int index)
	{
		if (_points != null)
		{
			for (int i = 0; i < _points.size(); i++)
			{
				_points.get(i).setEnabled(i == index);
			}
		}
	}
	
	public int getPosition()
	{
		return _pager.getCurrentItem();
	}
	
	public ViewPagerArchon(Activity activity)
	{
		_context = activity;
	}
	
	public ViewPagerArchon(View view)
	{
		_context = view.getContext();
	}
	
	public void setPager(ViewPager pager)
	{
		_pager = pager;
		_pager.setOnPageChangeListener(new OnPageChangeListener() 
		{
			@Override
			public void onPageSelected(int position) 
			{
				setCurrentPoint(position);
				if (_pageChangedListener != null)
				{
					_pageChangedListener.OnPageChanged(position);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
	}
	
	public void setPoints(LinearLayout layout, int pointView)
	{
		_layoutPoint = layout;
		_layoutPoint.removeAllViews();
		_pointView = pointView;
	}
	
	private void initPoints(int count)
	{
		_points = new ArrayList<View>();
		View view;
		for (int i = 0; i < count; i++)
		{
			view = View.inflate(_context, _pointView, null);
			_layoutPoint.addView(view);
			_points.add(view);
		}
		_layoutPoint.setVisibility(_notPoint && count <= 1 ? View.INVISIBLE : View.VISIBLE);
	}
	
	public void setNoPointWhenSinglePager(boolean enable)
	{
		_notPoint = enable;
	}
	
	public int getCount()
	{
		return _points == null ? 0 : _points.size();
	}
	
	public void clear()
	{
		_layoutPoint.removeAllViews();
	}
	
	public void dispose() 
	{
		_points.clear();
		_points = null;
	}
	
	public static interface OnPageChangedListener
	{
		public void OnPageChanged(int position);
	}
}
