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
public class ConsultingItemLayout extends BaseRelativeLayout implements LayoutInterface 
{
	private TextView message;
	
	public void setMessage(String message)
	{
		this.message.setText(message);
	}
	
	public ConsultingItemLayout(Context context)
	{
		super(context, R.layout.list_item_comment_total);
		initView();
	}

	private void initView() 
	{
		message = (TextView) findViewById(R.id.textListItemConsulting);
	}
	
	@Override
	public void dispose() 
	{
		message = null;
	}
}
