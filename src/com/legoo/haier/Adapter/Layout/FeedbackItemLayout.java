package com.legoo.haier.Adapter.Layout;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.BaseRelativeLayout;
import com.legoo.haier.Adapter.Base.LayoutInterface;
import android.content.Context;
import android.widget.TextView;

/**
 * @class Reservation Item Layout
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-17
 */
public class FeedbackItemLayout extends BaseRelativeLayout implements LayoutInterface 
{
	private TextView _name;
	private TextView _date;
	private TextView _readonly;
	
	
	public void setName(String name)
	{
		_name.setText(name);
	}
	
	public void setDate(String idcard)
	{
		_date.setText(idcard);
	}
	
	public void setReadonly(String readonly)
	{
		_readonly.setText(readonly);
	}
	
	public FeedbackItemLayout(Context context)
	{
		super(context, R.layout.list_item_feedback);
		initView();
	}

	private void initView() 
	{
		_name = (TextView) findViewById(R.id.textListItemFeedbackName);
		_date = (TextView) findViewById(R.id.textListItemFeedbackDate);
		_readonly = (TextView) findViewById(R.id.textListItemFeedbackReadonly);
	}
	
	@Override
	public void dispose() 
	{
		_name = null;
		_date = null;
		_readonly = null;
	}
}
