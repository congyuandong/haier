package com.legoo.haier.Archon;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.BaseActivityGroup;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @class Tab Archon
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-11
 */
public class TabArchon
{
	public static final String EXTRA_TAB_ID = "EXTRA_TAB_ID"; 
	
	private BaseActivityGroup _activity;
	private TabHost _tabHost;
	private int _layoutButton;
	
	public TabArchon(BaseActivityGroup activity, int layoutButton)
	{
		_activity = activity;
		_layoutButton = layoutButton;
		initArchon();
	}
	
	@SuppressWarnings("deprecation")
	private void initArchon()
	{
		_tabHost = (TabHost) _activity.findViewById(android.R.id.tabhost);
		_tabHost.setup(_activity.getLocalActivityManager());
	}
	
	public void setDividerDrawable(int dividerDrawable)
	{
		_tabHost.getTabWidget().setDividerDrawable(dividerDrawable);
	}
	
	public void addText(String tag, Intent content, String text)
	{
		addView(tag, content, createTabText(text));
	}
	
	private void addView(String tag, Intent content, View view)
	{
		_tabHost.addTab(_tabHost.newTabSpec(tag).setIndicator(view).setContent(content));
	}
	
	private View createTabText(String text) 
	{
		View view = LayoutInflater.from(_tabHost.getContext()).inflate(_layoutButton, null);
		TextView textView = (TextView) view.findViewById(R.id.tabsText);
		textView.setText(text);
		text = null;
		return view;
	}
	
	public void setTabTitle(int index, String text)
	{
		View view = _tabHost.getTabWidget().getChildTabViewAt(index);
		TextView textView = (TextView) view.findViewById(R.id.tabsText);
		if (textView != null)
		{
			textView.setText(text);
		}
	}
	
	public void requestFocus()
	{
		_tabHost.requestFocus();
	}
}
