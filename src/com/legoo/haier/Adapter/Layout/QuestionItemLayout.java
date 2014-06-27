package com.legoo.haier.Adapter.Layout;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.BaseRelativeLayout;
import com.legoo.haier.Adapter.Base.LayoutInterface;
import android.content.Context;
import android.widget.TextView;

/**
 * @class Department Item Layout
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class QuestionItemLayout extends BaseRelativeLayout implements LayoutInterface 
{
	private TextView _name;
	
	public void setName(String name)
	{
		_name.setText(name);
	}
	
	public QuestionItemLayout(Context context)
	{
		super(context, R.layout.list_item_question);
		initView();
	}

	private void initView() 
	{
		_name = (TextView) findViewById(R.id.textListItemQuestionName);
	}
	
	@Override
	public void dispose() 
	{
		_name = null;
	}
}
