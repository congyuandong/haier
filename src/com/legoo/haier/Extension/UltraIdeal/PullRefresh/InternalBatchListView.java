package com.legoo.haier.Extension.UltraIdeal.PullRefresh;

import com.legoo.haier.Extension.UltraIdeal.Base.ProtoListView;
import android.content.Context;
import android.util.AttributeSet;

/**
 * @class Internal Batch ListView
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-26
 */
public class InternalBatchListView extends ProtoListView 
{

	public InternalBatchListView(Context context) 
	{
		super(context);
		initView(context);
	}
	
	public InternalBatchListView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		initView(context);
	}
	
	public InternalBatchListView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		initView(context);
	}
	
	protected void onRefresh()
	{
		removeFooter();
	}
	
	protected void onSetModel() {}
	
	public void onFirstItemVisible()
	{
		animationChild(getHeaderViewsCount());
	}
	
	public void onLastItemVisible()
	{
		if (getFooterViewsCount() == 0)
		{
			animationChild(getChildCount() - 1);
		}
	}
	
	public void onContinueLoad()
	{
		if (getProtoAdapter().getPageNumber() < getProtoAdapter().getPageCount() && getProtoAdapter().getModelCount() != 0)
		{
	        if (isFooterShowing == false && isMoreLoading == false)
	        {
	        	isMoreLoading = true;
	        	showLoadingFooter();
	        	reload();
	        }
		}
	}
}
