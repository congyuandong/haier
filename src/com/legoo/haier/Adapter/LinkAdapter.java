package com.legoo.haier.Adapter;

import java.util.ArrayList;
import com.legoo.haier.Adapter.Base.BaseListAdapter;
import com.legoo.haier.Adapter.Layout.LinkItemLayout;
import com.legoo.haier.Model.LinkModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @class Link Adapter
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class LinkAdapter extends BaseListAdapter
{
	public LinkAdapter(Context context, ListView listView)
	{
		super(context, new ArrayList<ModelInterface>(), listView);  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
    {   
		LinkItemLayout view = getConvertView(convertView);
    	LinkModel model = (LinkModel) getItem(position);
    	
    	view.setTitle(model.getTitle());
    	model = null;
    	
    	doAnimation(view, position);
        return view;   
    } 
	
	private LinkItemLayout getConvertView(View convertView)
	{
		return (convertView != null && convertView instanceof LinkItemLayout) ? 
    			(LinkItemLayout) convertView : new LinkItemLayout(getContext());
	}
}
