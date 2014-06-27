package com.legoo.haier.Extension.UltraIdeal.PullRefresh;

import com.legoo.haier.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout {

	final class SmoothScrollRunnable implements Runnable {

		static final int ANIMATION_DURATION_MS = 190;
		static final int ANIMATION_FPS = 1000 / 60;

		private final Interpolator mInterpolator;
		private final int mScrollToY;
		private final int mScrollFromY;
		private final Handler mHandler;

		private boolean mContinueRunning = true;
		private long mStartTime = -1;
		private int mCurrentY = -1;

		public SmoothScrollRunnable(Handler handler, int fromY, int toY) {
			mHandler = handler;
			mScrollFromY = fromY;
			mScrollToY = toY;
			mInterpolator = new AccelerateDecelerateInterpolator();
		}

		@Override
		public void run() {
			if (mStartTime == -1) {
				mStartTime = System.currentTimeMillis();
			} else {
				long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / ANIMATION_DURATION_MS;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

				final int deltaY = Math.round((mScrollFromY - mScrollToY)
						* mInterpolator.getInterpolation(normalizedTime / 1000f));
				mCurrentY = mScrollFromY - deltaY;
				setHeaderScroll(mCurrentY);
			}

			if (mContinueRunning && mScrollToY != mCurrentY) {
				mHandler.postDelayed(this, ANIMATION_FPS);
			}
		}

		public void stop() {
			mContinueRunning = false;
			mHandler.removeCallbacks(this);
		}
	}

	static final boolean DEBUG = false;
	static final String LOG_TAG = "PullToRefresh";

	static final float FRICTION = 2.5f;

	static final int PULL_TO_REFRESH = 0x0;
	static final int RELEASE_TO_REFRESH = 0x1;
	static final int REFRESHING = 0x2;
	static final int MANUAL_REFRESHING = 0x3;

	public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;
	public static final int MODE_PULL_UP_TO_REFRESH = 0x2;
	public static final int MODE_BOTH = 0x3;

	private int mTouchSlop;

	private float mInitialMotionY;
	private float mLastMotionX;
	private float mLastMotionY;
	private boolean mIsBeingDragged = false;

	private int mState = PULL_TO_REFRESH;
	private int mMode = MODE_PULL_DOWN_TO_REFRESH;
	private int mCurrentMode;

	private boolean mDisableScrollingWhileRefreshing = true;

	T mRefreshableView;
	private boolean mIsPullToRefreshEnabled = true;

	private LoadingLayout mHeaderLayout;
	private LoadingLayout mFooterLayout;
	private int mHeaderHeight;

	private final Handler mHandler = new Handler();

	private OnRefreshListener mOnRefreshListener;
	private OnRefreshListener2 mOnRefreshListener2;

	private SmoothScrollRunnable mCurrentSmoothScrollRunnable;


	public PullToRefreshBase(Context context) {
		super(context);
		init(context, null);
	}

	public PullToRefreshBase(Context context, int mode) {
		super(context);
		mMode = mode;
		init(context, null);
	}

	public PullToRefreshBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public final T getAdapterView() {
		return mRefreshableView;
	}

	public final T getRefreshableView() {
		return mRefreshableView;
	}

	public final boolean isPullToRefreshEnabled() {
		return mIsPullToRefreshEnabled;
	}

	public final boolean isDisableScrollingWhileRefreshing() {
		return mDisableScrollingWhileRefreshing;
	}

	public final boolean isRefreshing() {
		return mState == REFRESHING || mState == MANUAL_REFRESHING;
	}

	public final void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
		mDisableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
	}

	public final void onRefreshComplete() {
		if (mState != PULL_TO_REFRESH) {
			resetHeader();
		}
	}

	public final void setOnRefreshListener(OnRefreshListener listener) {
		mOnRefreshListener = listener;
	}

	public final void setOnRefreshListener(OnRefreshListener2 listener) {
		mOnRefreshListener2 = listener;
	}

	public final void setPullToRefreshEnabled(boolean enable) {
		mIsPullToRefreshEnabled = enable;
	}

	public void setReleaseLabel(String releaseLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setReleaseLabel(releaseLabel);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setReleaseLabel(releaseLabel);
		}
	}

	public void setPullLabel(String pullLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setPullLabel(pullLabel);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setPullLabel(pullLabel);
		}
	}

	public void setRefreshingLabel(String refreshingLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setRefreshingLabel(refreshingLabel);
		}
		if (null != mFooterLayout) {
			mFooterLayout.setRefreshingLabel(refreshingLabel);
		}
	}

	public final void setRefreshing() {
		setRefreshing(true);
	}

	public final void setRefreshing(boolean doScroll) {
		if (!isRefreshing()) {
			setRefreshingInternal(doScroll);
			mState = MANUAL_REFRESHING;
		}
	}

	public final boolean hasPullFromTop() {
		return mCurrentMode != MODE_PULL_UP_TO_REFRESH;
	}
	
	@Override
	public final boolean onTouchEvent(MotionEvent event) {
		if (!mIsPullToRefreshEnabled) {
			return false;
		}

		if (isRefreshing() && mDisableScrollingWhileRefreshing) {
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
			return false;
		}

		switch (event.getAction()) {

			case MotionEvent.ACTION_MOVE: {
				if (mIsBeingDragged) {
					mLastMotionY = event.getY();
					pullEvent();
					return true;
				}
				break;
			}

			case MotionEvent.ACTION_DOWN: {
				if (isReadyForPull()) {
					mLastMotionY = mInitialMotionY = event.getY();
					return true;
				}
				break;
			}

			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP: {
				if (mIsBeingDragged) {
					mIsBeingDragged = false;

					if (mState == RELEASE_TO_REFRESH) {

						if (null != mOnRefreshListener) {
							setRefreshingInternal(true);
							mOnRefreshListener.onRefresh();
							return true;

						} else if (null != mOnRefreshListener2) {
							setRefreshingInternal(true);
							if (mCurrentMode == MODE_PULL_DOWN_TO_REFRESH) {
								mOnRefreshListener2.onPullDownToRefresh();
							} else if (mCurrentMode == MODE_PULL_UP_TO_REFRESH) {
								mOnRefreshListener2.onPullUpToRefresh();
							}
							return true;
						}

						return true;
					}

					smoothScrollTo(0);
					return true;
				}
				break;
			}
		}

		return false;
	}

	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {

		if (!mIsPullToRefreshEnabled) {
			return false;
		}

		if (isRefreshing() && mDisableScrollingWhileRefreshing) {
			return true;
		}

		final int action = event.getAction();

		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mIsBeingDragged = false;
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
			return true;
		}

		switch (action) {
			case MotionEvent.ACTION_MOVE: {
				if (isReadyForPull()) {

					final float y = event.getY();
					final float dy = y - mLastMotionY;
					final float yDiff = Math.abs(dy);
					final float xDiff = Math.abs(event.getX() - mLastMotionX);

					if (yDiff > mTouchSlop && yDiff > xDiff) {
						if ((mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH) && dy >= 0.0001f
								&& isReadyForPullDown()) {
							mLastMotionY = y;
							mIsBeingDragged = true;
							if (mMode == MODE_BOTH) {
								mCurrentMode = MODE_PULL_DOWN_TO_REFRESH;
							}
						} else if ((mMode == MODE_PULL_UP_TO_REFRESH || mMode == MODE_BOTH) && dy <= 0.0001f
								&& isReadyForPullUp()) {
							mLastMotionY = y;
							mIsBeingDragged = true;
							if (mMode == MODE_BOTH) {
								mCurrentMode = MODE_PULL_UP_TO_REFRESH;
							}
						}
					}
				}
				break;
			}
			case MotionEvent.ACTION_DOWN: {
				if (isReadyForPull()) {
					mLastMotionY = mInitialMotionY = event.getY();
					mLastMotionX = event.getX();
					mIsBeingDragged = false;
				}
				break;
			}
		}

		return mIsBeingDragged;
	}

	@SuppressWarnings("deprecation")
	protected void addRefreshableView(Context context, T refreshableView) {
		addView(refreshableView, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1.0f));
	}

	protected abstract T createRefreshableView(Context context, AttributeSet attrs);

	protected final int getCurrentMode() {
		return mCurrentMode;
	}

	protected final LoadingLayout getFooterLayout() {
		return mFooterLayout;
	}

	protected final LoadingLayout getHeaderLayout() {
		return mHeaderLayout;
	}

	protected final int getHeaderHeight() {
		return mHeaderHeight;
	}

	protected final int getMode() {
		return mMode;
	}

	protected abstract boolean isReadyForPullDown();

	protected abstract boolean isReadyForPullUp();

	protected void resetHeader() {
		mState = PULL_TO_REFRESH;
		mIsBeingDragged = false;

		if (null != mHeaderLayout) {
			mHeaderLayout.reset();
		}
		if (null != mFooterLayout) {
			mFooterLayout.reset();
		}

		smoothScrollTo(0);
	}

	protected void setRefreshingInternal(boolean doScroll) {
		mState = REFRESHING;

		if (null != mHeaderLayout) {
			mHeaderLayout.refreshing();
		}
		if (null != mFooterLayout) {
			mFooterLayout.refreshing();
		}

		if (doScroll) {
			smoothScrollTo(mCurrentMode == MODE_PULL_DOWN_TO_REFRESH ? -mHeaderHeight : mHeaderHeight);
		}
	}

	protected final void setHeaderScroll(int y) {
		scrollTo(0, y);
	}

	protected final void smoothScrollTo(int y) {
		if (null != mCurrentSmoothScrollRunnable) {
			mCurrentSmoothScrollRunnable.stop();
		}

		if (getScrollY() != y) {
			mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(mHandler, getScrollY(), y);
			mHandler.post(mCurrentSmoothScrollRunnable);
		}
	}

	@SuppressWarnings("deprecation")
	private void init(Context context, AttributeSet attrs) {

		setOrientation(LinearLayout.VERTICAL);

		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
		if (a.hasValue(R.styleable.PullToRefresh_mode)) {
			mMode = a.getInteger(R.styleable.PullToRefresh_mode, MODE_PULL_DOWN_TO_REFRESH);
		}

		mRefreshableView = createRefreshableView(context, attrs);
		addRefreshableView(context, mRefreshableView);

		String pullLabel = context.getString(R.string.list_item_pull_refresh_pull);
		String refreshingLabel = context.getString(R.string.list_item_pull_refresh_refreshing);
		String releaseLabel = context.getString(R.string.list_item_pull_refresh_release);

		if (mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH) {
			mHeaderLayout = new LoadingLayout(context, MODE_PULL_DOWN_TO_REFRESH, releaseLabel, pullLabel,
					refreshingLabel, a);
			addView(mHeaderLayout, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(mHeaderLayout);
			mHeaderHeight = mHeaderLayout.getMeasuredHeight();
		}
		if (mMode == MODE_PULL_UP_TO_REFRESH || mMode == MODE_BOTH) {
			mFooterLayout = new LoadingLayout(context, MODE_PULL_UP_TO_REFRESH, releaseLabel, pullLabel,
					refreshingLabel, a);
			addView(mFooterLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(mFooterLayout);
			mHeaderHeight = mFooterLayout.getMeasuredHeight();
		}

		if (a.hasValue(R.styleable.PullToRefresh_headerBackground)) {
			setBackgroundResource(a.getResourceId(R.styleable.PullToRefresh_headerBackground, Color.WHITE));
		}
		if (a.hasValue(R.styleable.PullToRefresh_adapterViewBackground)) {
			mRefreshableView.setBackgroundResource(a.getResourceId(R.styleable.PullToRefresh_adapterViewBackground,
					Color.WHITE));
		}
		a.recycle();
		a = null;

		switch (mMode) {
			case MODE_BOTH:
				setPadding(0, -mHeaderHeight, 0, -mHeaderHeight);
				break;
			case MODE_PULL_UP_TO_REFRESH:
				setPadding(0, 0, 0, -mHeaderHeight);
				break;
			case MODE_PULL_DOWN_TO_REFRESH:
			default:
				setPadding(0, -mHeaderHeight, 0, 0);
				break;
		}

		if (mMode != MODE_BOTH) {
			mCurrentMode = mMode;
		}
	}

	@SuppressWarnings("deprecation")
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	private boolean pullEvent() {

		final int newHeight;
		final int oldHeight = getScrollY();

		switch (mCurrentMode) {
			case MODE_PULL_UP_TO_REFRESH:
				newHeight = Math.round(Math.max(mInitialMotionY - mLastMotionY, 0) / FRICTION);
				break;
			case MODE_PULL_DOWN_TO_REFRESH:
			default:
				newHeight = Math.round(Math.min(mInitialMotionY - mLastMotionY, 0) / FRICTION);
				break;
		}

		setHeaderScroll(newHeight);

		if (newHeight != 0) {
			if (mState == PULL_TO_REFRESH && mHeaderHeight < Math.abs(newHeight)) {
				mState = RELEASE_TO_REFRESH;

				switch (mCurrentMode) {
					case MODE_PULL_UP_TO_REFRESH:
						mFooterLayout.releaseToRefresh();
						break;
					case MODE_PULL_DOWN_TO_REFRESH:
						mHeaderLayout.releaseToRefresh();
						break;
				}

				return true;

			} else if (mState == RELEASE_TO_REFRESH && mHeaderHeight >= Math.abs(newHeight)) {
				mState = PULL_TO_REFRESH;

				switch (mCurrentMode) {
					case MODE_PULL_UP_TO_REFRESH:
						mFooterLayout.pullToRefresh();
						break;
					case MODE_PULL_DOWN_TO_REFRESH:
						mHeaderLayout.pullToRefresh();
						break;
				}

				return true;
			}
		}

		return oldHeight != newHeight;
	}

	private boolean isReadyForPull() {
		switch (mMode) {
			case MODE_PULL_DOWN_TO_REFRESH:
				return isReadyForPullDown();
			case MODE_PULL_UP_TO_REFRESH:
				return isReadyForPullUp();
			case MODE_BOTH:
				return isReadyForPullUp() || isReadyForPullDown();
		}
		return false;
	}

	public static interface OnRefreshListener {
		public void onRefresh();

	}

	public static interface OnRefreshListener2 {
		public void onPullDownToRefresh();
		public void onPullUpToRefresh();
	}

	public static interface OnLastItemVisibleListener {
		public void onLastItemVisible();

	}
	
	public static interface OnFirstItemVisibleListener {
		public void onFirstItemVisible();
	}

	public static interface OnLoadMoreListener {
		public void OnLoadMore();
	}
	
	@Override
	public void setLongClickable(boolean longClickable) {
		getRefreshableView().setLongClickable(longClickable);
	}
}
