package com.legoo.haier.Adapter;

import java.util.ArrayList;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.BaseListAdapter;
import com.legoo.haier.Adapter.Layout.FeedbackItemLayout;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Model.FeedbackModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @class Contacts Adapter
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-07
 */
public class FeedbackAdapter extends BaseListAdapter
{
	public FeedbackAdapter(Context context, ListView listView)
	{
		super(context, new ArrayList<ModelInterface>(), listView);  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
    {   
		FeedbackItemLayout view = getConvertView(convertView);
		
		FeedbackModel model = (FeedbackModel) getItem(position);
    	
		view.setName(model.getName());
    	view.setDate(model.getDate());
    	String readonly =model.getReadonly(); 
    	if(readonly.equals(FeedbackModel.NOTREADONLY))
    	{
        	view.setReadonly(Haier.getInstance().getString(R.string.user_feedback_notreadonly));    		
    	}else if(readonly.equals(FeedbackModel.ISREADONLY))
    	{
        	view.setReadonly(Haier.getInstance().getString(R.string.user_feedback_isreadonly));    		
    	}else
    	{
        	view.setReadonly(model.getReadonly());    		
    	}
    	model = null;
    	
    	doAnimation(view, position);
        return view;   
    } 
	
	private FeedbackItemLayout getConvertView(View convertView)
	{
		return (convertView != null && convertView instanceof FeedbackItemLayout) ? 
    			(FeedbackItemLayout) convertView : new FeedbackItemLayout(getContext());
	}
}
