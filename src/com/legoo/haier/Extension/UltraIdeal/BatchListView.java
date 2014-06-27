package com.legoo.haier.Extension.UltraIdeal;

import com.legoo.haier.Extension.UltraIdeal.Base.ProtoListView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.widget.AbsListView;

/**
 * @class BatchListView.java
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class BatchListView extends ProtoListView 
{
	private static final int FLING_NONE = 0;
	private static final int FLING_UP = 1;
	private static final int FLING_DOWN = 2;
	
	private boolean isActionFromUser;
	
	private float lastScrollY;
	private boolean ignoreAction = false;
	
	private GestureDetector listDetector;
	
	private int flingType;
	
	
	public BatchListView(Context context) 
	{
		super(context);
		initView(context);
	}
	
	public BatchListView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		initView(context);
	}
	
	public BatchListView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		initView(context);
	}
	
	protected void initView(Context context) 
	{
		super.initView(context);
		addScrollListener();
	}
	
	protected void onRefresh()
	{
		showLoadingFooter();
	}
	
	protected void onSetModel() 
	{
		showLoadingFooter();
	}
	
	@SuppressWarnings("deprecation")
	protected void addScrollListener()
	{
		setOnScrollListener(new OnScrollListener() 
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) 
			{
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) 
				{
					if (isFooterShowing == true && footerType == FOOTER_LOADING && isMoreLoading == false)
					{
						isMoreLoading = true;
						reload();
					}
					if (getFooterViewsCount() == 0)
					{
						if (flingType == FLING_UP && getLastVisiblePosition() == getCount() - 1)
						{
							animationChild(getChildCount() - 1);
						}
						else if (flingType == FLING_DOWN && getFirstVisiblePosition() == 0)
						{
							animationChild(getHeaderViewsCount());
						}
					}
			    }
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
			{
				if ((firstVisibleItem + visibleItemCount == totalItemCount) && totalItemCount != 0)
				{
					if (getProtoAdapter().getPageNumber() < getProtoAdapter().getPageCount())
					{
				        if (isFooterShowing == false && isActionFromUser == true)
				        {
				        	if (ignoreAction == false)
				        	{
				        		isActionFromUser = false;
					        	showLoadingFooter();
				        	}
				        	else 
				        	{
				        		ignoreAction = false;
							}
				        }
					}
			    }
			}
		});
		
		listDetector = new GestureDetector(new OnGestureListener() 
		{
			@Override
			public boolean onSingleTapUp(MotionEvent e) {return false;}
			
			@Override
			public void onShowPress(MotionEvent e) {}
			
			@Override
			public boolean onScroll(MotionEvent startEvent, MotionEvent finishEvent, float distanceX, float distanceY) 
			{
				if (finishEvent.getY() > lastScrollY)
				{
					ignoreAction = true;
				}
				else 
				{
					ignoreAction = false;
				}
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {}
			
			@Override
			public boolean onFling(MotionEvent startEvent, MotionEvent finishEvent, float velocityX, float velocityY) 
			{
				if (startEvent != null && finishEvent != null)
				{
					if (startEvent.getY() > finishEvent.getY())
					{
						flingType = FLING_UP;
					}
					else if (startEvent.getY() < finishEvent.getY())
					{
						flingType = FLING_DOWN;
					}
					else
					{
						flingType = FLING_NONE;
					}
				}
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) 
			{
				isActionFromUser = true;
				lastScrollY = e.getY();
				return false;
			}
		});
		
		setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				return listDetector.onTouchEvent(event);
			}
		});
	}
	
	public void setAdjustHeight()
	{
		int totalHeight = 0;  
		for (int i = 0; i < getCount(); i++) 
		{  
			View listItem = getAdapter().getView(i, null, this);  
			listItem.measure(0, 0);  
			totalHeight += listItem.getMeasuredHeight();  
		}  
		  
		ViewGroup.LayoutParams params = getLayoutParams();  
		params.height = totalHeight + (getDividerHeight() * (getCount() - 1));  
		setLayoutParams(params);
	}
}
