package com.legoo.haier.Adapter;

import java.util.ArrayList;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.BaseListAdapter;
import com.legoo.haier.Adapter.Layout.ChannelItemLayout;
import com.legoo.haier.Model.ChannelModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * @class Channel Adapter
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
@SuppressLint("ViewConstructor")
public class ChannelAdapter extends BaseListAdapter
{
	private ChannelModel _all;
	
	public ChannelAdapter(Context context, ListView listView)
	{
		super(context, new ArrayList<ModelInterface>(), listView);  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
    {   
		ChannelItemLayout view = getConvertView(convertView);
    	ChannelModel model = (ChannelModel) getItem(position);
    	
    	view.setTitle(model.getTitle());
    	model = null;
    	
    	doAnimation(view, position);
        return view;   
    } 
	
	private ChannelItemLayout getConvertView(View convertView)
	{
		return (convertView != null && convertView instanceof ChannelItemLayout) ? 
    			(ChannelItemLayout) convertView : new ChannelItemLayout(getContext());
	}
	
	public ChannelModel addAllView()
	{
		_all = new ChannelModel();
		_all.setID("");
		_all.setTitle(getContext().getString(R.string.list_item_channel_all));
		if (containsAllView() == false)
		{
			insert(_all, 0);
		}
		return _all;
	}
	
	private boolean containsAllView()
	{
		return this.getPosition(_all) > 0;
	}
	
	@Override
	public int getModelCount()
	{
		return getCount() - (containsAllView() == true ? 1 : 0);
	}
}
