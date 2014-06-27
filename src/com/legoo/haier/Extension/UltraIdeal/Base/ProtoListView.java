package com.legoo.haier.Extension.UltraIdeal.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.AdapterInterface;
import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.BaseListener;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import com.legoo.haier.AsyncTask.Callback.SampleEvent;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @class Proto ListView
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public abstract class ProtoListView extends ListView 
{
	protected static final int FOOTER_LOADING = 0;
	protected static final int FOOTER_NOTICE = 1;
	
	private Context _context;
	
	private AdapterInterface<ModelInterface> _adapter;
	public AdapterInterface<ModelInterface> getProtoAdapter()
	{
		return _adapter;
	}
	
	private boolean _isRefreshing;
	protected boolean isMoreLoading;
	protected boolean isFooterShowing;

	
	private int footer;
	
	
	protected int footerType;
	private View listFooterView;
	
	private Vector<BaseListener> vectorListeners;
	
	private Handler readyHandler;
	private Runnable readyRunnable;
	
	private List<ManualMotionPoint> eventActionList;
	private int currentActionIndex;
	
	public ProtoListView(Context context) 
	{
		super(context);
		initView(context);
	}
	
	public ProtoListView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		initView(context);
	}
	
	public ProtoListView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		initView(context);
	}
	
	protected void initView(Context context) 
	{
		_context = context;
		isMoreLoading = false;
		_isRefreshing = false;
		isFooterShowing = false;
		vectorListeners = new Vector<BaseListener>();
	}
	
	public void over()
	{
		isMoreLoading = false;
		_isRefreshing = false;
	}
	
	public void reload() 
	{
		activateReloadEvent(new SampleEvent(this));
	}
	
	public void refresh()
	{
		if (_isRefreshing == false)
		{
			_isRefreshing = true;

			onRefresh();
			
			_adapter.clear();
			
			reload();
		}
	}
	
	protected abstract void onRefresh();
	
	public void setModel(AdapterInterface<ModelInterface> adapter)
	{
		_adapter = adapter;
		
		onSetModel();
		
		_adapter.bind(this);
	}
	
	protected abstract void onSetModel();
	
	protected void onScrollToReady() 
	{
		animationChild(getHeaderViewsCount());
	}
	
	public void scrollToReady()
	{
		if (_adapter != null && _adapter.getModelCount() > 0)
		{
			if (getFirstVisiblePosition() > 0 && readyHandler == null)
			{
				startMotion();
			}
			else 
			{
				overMotion();
			}
		}
	}
	
	public void showLoadingFooter()
	{
		if (isFooterShowing == true && footerType == FOOTER_NOTICE)
		{
			removeFooter();
		}
		if (getFooterViewsCount() == 0) 
        {
			if (listFooterView == null)
			{
				listFooterView = ((LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(footer, null, false);
			}
			
			((ImageView)listFooterView.findViewById(R.id.imageListItemProgress)).setVisibility(View.VISIBLE);
			((TextView)listFooterView.findViewById(R.id.textListItemFooter)).setText(_context.getString(R.string.list_item_loading));
			Hospital.getInstance().getAnimation().startProgress((ImageView)listFooterView.findViewById(R.id.imageListItemProgress));
			addFooterView(listFooterView); 
			footerType = FOOTER_LOADING;
			isFooterShowing = true;
			Hospital.getInstance().getAnimation().startFade(listFooterView);
        }
	}
	
	private void showNoticeFooter()
	{
		removeFooter();
		if (listFooterView == null)
		{
			listFooterView = ((LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(footer, null, false);
		}
		((ImageView)listFooterView.findViewById(R.id.imageListItemProgress)).setVisibility(View.GONE);
		((TextView)listFooterView.findViewById(R.id.textListItemFooter)).setText(_context.getString(R.string.list_item_notice));
		addFooterView(listFooterView); 
		footerType = FOOTER_NOTICE;
		isFooterShowing = true;
		Hospital.getInstance().getAnimation().startFade(listFooterView);
	}
	
	public void removeFooter()
	{
		if (getFooterViewsCount() > 0) 
        {
			((ImageView)listFooterView.findViewById(R.id.imageListItemProgress)).clearAnimation();
			listFooterView.clearAnimation();
			removeFooterView(listFooterView);
			listFooterView = null;
        }
		isFooterShowing = false;
	}
	
	public void doNoticeFooter()
	{
		showNoticeFooter();
	}
	
	public boolean isListViewItem(int position)
	{
		if (getFooterViewsCount() > 0 && position >= _adapter.getModelCount())
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	public ModelInterface getModelItem(int position)
	{
		return (ModelInterface) getProtoAdapter().get(position);
	}
	
	public void setFooterLayout(int layout)
	{
		footer = layout;
	}

	public synchronized void addReloadListener(BaseListener listener)
	{
		vectorListeners.addElement(listener);
		listener = null;
	}

	public synchronized void removeReloadListener(BaseListener listener)
	{
		vectorListeners.removeElement(listener);
		listener = null;
	}
	
	@SuppressWarnings("unchecked")
	protected void activateReloadEvent(EventInterface event)
    {
        Vector<BaseListener> tempVector = null;

        synchronized(this)
        {
            tempVector = (Vector<BaseListener>) vectorListeners.clone();
            for(int i=0; i < tempVector.size(); i++)
            {
            	BaseListener listener = (BaseListener)tempVector.elementAt(i);
            	listener.EventActivated(event);
            	listener = null;
            }
        }

        tempVector = null;
        event.dispose();
        event = null;
    }
	
	public void animationChild(int position)
	{
		View child = getChildAt(position);
		if (child != null)
		{
			Hospital.getInstance().getAnimation().startFade(child);
			child = null;
		}
	}
	
	private class ManualMotionPoint
	{
		public int Action;
		
		public float X;
		
		public float Y;
		
		public ManualMotionPoint(int action, float x, float y)
		{
			Action = action;
			X = x;
			Y = y;
		}
	}
	
	private void initManualMotion()
	{
		if (eventActionList == null)
		{
			float viewWidth = getWidth();
			float viewHeight = getHeight();
			
			eventActionList = new ArrayList<ManualMotionPoint>();
			
			int maxStep = 14;
			int action;
			
			for (int i = 1; i < maxStep; i++)
			{
				if (i == 1)
				{
					action = MotionEvent.ACTION_DOWN;
				}
				else if (i == maxStep - 1)
				{
					action = MotionEvent.ACTION_UP;
				}
				else 
				{
					action = MotionEvent.ACTION_MOVE;
				}
				eventActionList.add(new ManualMotionPoint(action, viewWidth / 2, viewHeight / maxStep * i));
			}
		}
	}
	
	private MotionEvent makeMotionEvent(ManualMotionPoint point)
	{
		return MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(), point.Action, point.X, point.Y, 0);
	}
	
	private void startMotion()
	{
		initManualMotion();
		
		readyHandler = new Handler();
		readyRunnable = new Runnable() 
		{
			@Override
			public void run() 
			{
				if (currentActionIndex == eventActionList.size())
				{
					overMotion();
				}
				else 
				{
					onTouchEvent(makeMotionEvent(eventActionList.get(currentActionIndex)));
					currentActionIndex += 1;
					readyHandler.postDelayed(readyRunnable, 1);
				}
			}
		};
		readyHandler.post(readyRunnable);
	}
	
	private void overMotion()
	{
		setSelection(0);
		onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0));
		if (readyHandler != null && readyRunnable != null)
		{
			readyHandler.removeCallbacks(readyRunnable);
			currentActionIndex = 0;
			readyRunnable = null;
			readyHandler = null;
		}
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				onScrollToReady();
			}
		}, 100);
	}
}
