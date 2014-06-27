package com.legoo.haier.Extension.UltraIdeal.PullRefresh;

import com.legoo.haier.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

/**
 * @class Pull To Refresh ScrollView
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-20
 */
public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> 
{
	private Context _context;
	
	public PullToRefreshScrollView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		_context = context;
	}

	@Override
	protected ScrollView createRefreshableView(Context context, AttributeSet attrs) 
	{
		ScrollView scrollView = new ScrollView(context, attrs);
		scrollView.setId(R.id.scrollview);
		return scrollView;
	}

	@Override
	protected boolean isReadyForPullDown() 
	{
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullUp() 
	{
		View scrollViewChild = mRefreshableView.getChildAt(0);
		if (null != scrollViewChild) {
			return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
		}
		return false;
	}
	
	public void fill(int layoutId)
	{
		LayoutInflater.from(_context).inflate(layoutId, getRefreshableView(), true);
	}
}
