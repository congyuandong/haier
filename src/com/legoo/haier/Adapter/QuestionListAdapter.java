package com.legoo.haier.Adapter;

import java.util.ArrayList;
import com.legoo.haier.Adapter.Base.BaseListAdapter;
import com.legoo.haier.Adapter.Layout.QuestionItemLayout;
import com.legoo.haier.Model.QuestionListModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @class Expert Adapter
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class QuestionListAdapter extends BaseListAdapter
{
	public QuestionListAdapter(Context context, ListView listView)
	{
		super(context, new ArrayList<ModelInterface>(), listView);  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
    {   
		QuestionItemLayout view = getConvertView(convertView);
		QuestionListModel model = (QuestionListModel) getItem(position);
    	
    	view.setName(model.getName());
    	
    	model = null;
    	
    	doAnimation(view, position);
        return view;   
    } 
	
	private QuestionItemLayout getConvertView(View convertView)
	{
		return (convertView != null && convertView instanceof QuestionItemLayout) ? 
    			(QuestionItemLayout) convertView : new QuestionItemLayout(getContext());
	}
}
