package com.legoo.haier.Adapter.Layout;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.BaseRelativeLayout;
import com.legoo.haier.Adapter.Base.LayoutInterface;
import android.content.Context;
import android.widget.TextView;

/**
 * @class Link Item Layout
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class LinkItemLayout extends BaseRelativeLayout implements LayoutInterface 
{
	private TextView _title;
	
	public void setTitle(String title)
	{
		_title.setText(title);
	}
	
	public LinkItemLayout(Context context)
	{
		super(context, R.layout.list_item_link);
		initView();
	}

	private void initView() 
	{
		_title = (TextView) findViewById(R.id.textListItemLinkTitle);
	}
	
	@Override
	public void dispose() 
	{
		_title = null;
	}
}
