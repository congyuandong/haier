package com.legoo.haier.Extension.UltraIdeal.PullRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public abstract class PullToRefreshAdapterViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements OnScrollListener{

	//private int mLastSavedFirstVisibleItem = -1;
	private OnScrollListener mOnScrollListener;
	private OnLastItemVisibleListener mOnLastItemVisibleListener;
	private OnFirstItemVisibleListener mOnFirstItemVisibleListener;
	private OnLoadMoreListener mOnLoadMoreListener;
	private View mEmptyView;
	private FrameLayout mRefreshableViewHolder;
	private GestureDetector listDetector;
	private float lastScrollY;
	private boolean ignoreAction = false;
	private boolean isActionFromUser = false;
	
	private static final int FLING_NONE = 0;
	private static final int FLING_UP = 1;
	private static final int FLING_DOWN = 2;
	
	private int flingType;
	
	public PullToRefreshAdapterViewBase(Context context) {
		super(context);
		initView();
	}

	public PullToRefreshAdapterViewBase(Context context, int mode) {
		super(context, mode);
		initView();
	}

	public PullToRefreshAdapterViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView()
	{
		mRefreshableView.setOnScrollListener(this);
		
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
		
		mRefreshableView.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				return listDetector.onTouchEvent(event);
			}
		});
	}
	
	abstract public ContextMenuInfo getContextMenuInfo();

	public final void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {

		if (null != mOnLoadMoreListener) 
		{
			if (visibleItemCount > 0 && (firstVisibleItem + visibleItemCount == totalItemCount)) 
			{
				if (isActionFromUser == true) 
				{
					if (ignoreAction == false)
					{
						isActionFromUser = false;
						mOnLoadMoreListener.OnLoadMore();
					}
					else 
					{
						ignoreAction = false;
					}
				}
			}
		}

		if (null != mOnScrollListener) {
			mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	public final void onScrollStateChanged(final AbsListView view, final int scrollState) {
		if (null != mOnFirstItemVisibleListener && null != mOnLastItemVisibleListener)
		{
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) 
			{
				if (flingType == FLING_DOWN && view.getFirstVisiblePosition() == 0)
				{
					mOnFirstItemVisibleListener.onFirstItemVisible();
				}
				else if (flingType == FLING_UP && view.getLastVisiblePosition() == view.getCount() - 1)
				{
					mOnLastItemVisibleListener.onLastItemVisible();
				}
			}
		}
		if (null != mOnScrollListener) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@SuppressWarnings("deprecation")
	public final void setEmptyView(View newEmptyView) {
		if (null != mEmptyView) {
			mRefreshableViewHolder.removeView(mEmptyView);
		}

		if (null != newEmptyView) {
			newEmptyView.setClickable(true);

			ViewParent newEmptyViewParent = newEmptyView.getParent();
			if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
				((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
			}

			mRefreshableViewHolder.addView(newEmptyView, ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);

			if (mRefreshableView instanceof EmptyViewMethodAccessor) {
				((EmptyViewMethodAccessor) mRefreshableView).setEmptyViewInternal(newEmptyView);
			} else {
				mRefreshableView.setEmptyView(newEmptyView);
			}
		}
	}

	public final void setOnLoadMoreListener(OnLoadMoreListener listener)
	{
		mOnLoadMoreListener = listener;
	}
	
	public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
		mOnLastItemVisibleListener = listener;
	}
	
	public final void setOnFirstItemVisibleListener(OnFirstItemVisibleListener listener) 
	{
		mOnFirstItemVisibleListener = listener;
	}

	public final void setOnScrollListener(OnScrollListener listener) {
		mOnScrollListener = listener;
	}

	@SuppressWarnings("deprecation")
	protected void addRefreshableView(Context context, T refreshableView) {
		mRefreshableViewHolder = new FrameLayout(context);
		mRefreshableViewHolder.addView(refreshableView, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		addView(mRefreshableViewHolder, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1.0f));
	};

	protected boolean isReadyForPullDown() {
		return isFirstItemVisible();
	}

	protected boolean isReadyForPullUp() {
		return isLastItemVisible();
	}

	private boolean isFirstItemVisible() {
		if (mRefreshableView.getCount() <= getNumberInternalViews()) {
			return true;
		} else if (mRefreshableView.getFirstVisiblePosition() == 0) {

			final View firstVisibleChild = mRefreshableView.getChildAt(0);

			if (firstVisibleChild != null) {
				return firstVisibleChild.getTop() >= mRefreshableView.getTop();
			}
		}

		return false;
	}

	private boolean isLastItemVisible() {
		final int count = mRefreshableView.getCount();
		final int lastVisiblePosition = mRefreshableView.getLastVisiblePosition();
		
		if (DEBUG) {
			Log.d(LOG_TAG, "isLastItemVisible. Count: " + count + " Last Visible Pos: " + lastVisiblePosition);
		}

		if (count <= getNumberInternalViews()) {
			return true;
		} else if (lastVisiblePosition == count - 1) {

			final int childIndex = lastVisiblePosition - mRefreshableView.getFirstVisiblePosition();
			final View lastVisibleChild = mRefreshableView.getChildAt(childIndex);

			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= mRefreshableView.getBottom();
			}
		}

		return false;
	}

	protected int getNumberInternalViews() {
		return getNumberInternalHeaderViews() + getNumberInternalFooterViews();
	}

	protected int getNumberInternalHeaderViews() {
		return 0;
	}

	protected int getNumberInternalFooterViews() {
		return 0;
	}
}
