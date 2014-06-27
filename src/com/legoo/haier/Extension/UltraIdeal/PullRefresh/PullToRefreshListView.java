package com.legoo.haier.Extension.UltraIdeal.PullRefresh;

import com.legoo.haier.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {

	private LoadingLayout mHeaderLoadingView;
	private LoadingLayout mFooterLoadingView;

	private FrameLayout mLvFooterLoadingFrame;

	class InternalListView extends InternalBatchListView implements EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshListView(Context context) {
		super(context);
		setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, int mode) {
		super(context, mode);
		setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	public void setReleaseLabel(String releaseLabel) {
		super.setReleaseLabel(releaseLabel);

		if (null != mHeaderLoadingView) {
			mHeaderLoadingView.setReleaseLabel(releaseLabel);
		}
		if (null != mFooterLoadingView) {
			mFooterLoadingView.setReleaseLabel(releaseLabel);
		}
	}

	public void setPullLabel(String pullLabel) {
		super.setPullLabel(pullLabel);

		if (null != mHeaderLoadingView) {
			mHeaderLoadingView.setPullLabel(pullLabel);
		}
		if (null != mFooterLoadingView) {
			mFooterLoadingView.setPullLabel(pullLabel);
		}
	}

	public void setRefreshingLabel(String refreshingLabel) {
		super.setRefreshingLabel(refreshingLabel);

		if (null != mHeaderLoadingView) {
			mHeaderLoadingView.setRefreshingLabel(refreshingLabel);
		}
		if (null != mFooterLoadingView) {
			mFooterLoadingView.setRefreshingLabel(refreshingLabel);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);

		final int mode = getMode();

		String pullLabel = context.getString(R.string.list_item_pull_refresh_pull);
		String refreshingLabel = context.getString(R.string.list_item_pull_refresh_refreshing);
		String releaseLabel = context.getString(R.string.list_item_pull_refresh_release);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
		
		if (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) {
			FrameLayout frame = new FrameLayout(context);
			mHeaderLoadingView = new LoadingLayout(context, MODE_PULL_DOWN_TO_REFRESH, releaseLabel, pullLabel,
			        refreshingLabel, a);
			frame.addView(mHeaderLoadingView, FrameLayout.LayoutParams.FILL_PARENT,
			        FrameLayout.LayoutParams.WRAP_CONTENT);
			mHeaderLoadingView.setVisibility(View.GONE);
			lv.addHeaderView(frame, null, false);
		}
		if (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) {
			mLvFooterLoadingFrame = new FrameLayout(context);
			mFooterLoadingView = new LoadingLayout(context, MODE_PULL_UP_TO_REFRESH, releaseLabel, pullLabel,
			        refreshingLabel, a);
			mLvFooterLoadingFrame.addView(mFooterLoadingView, FrameLayout.LayoutParams.FILL_PARENT,
			        FrameLayout.LayoutParams.WRAP_CONTENT);
			mFooterLoadingView.setVisibility(View.GONE);
		}
		
		a.recycle();

		lv.setId(android.R.id.list);
		return lv;
	}

	@Override
	protected void setRefreshingInternal(boolean doScroll) {

		ListAdapter adapter = mRefreshableView.getAdapter();
		if (null == adapter) // || adapter.isEmpty()) 
		{
			super.setRefreshingInternal(doScroll);
			return;
		}

		super.setRefreshingInternal(false);

		final LoadingLayout originalLoadingLayout, listViewLoadingLayout;
		final int selection, scrollToY;

		switch (getCurrentMode()) {
			case MODE_PULL_UP_TO_REFRESH:
				originalLoadingLayout = getFooterLayout();
				listViewLoadingLayout = mFooterLoadingView;
				selection = mRefreshableView.getCount() - 1;
				scrollToY = getScrollY() - getHeaderHeight();
				break;
			case MODE_PULL_DOWN_TO_REFRESH:
			default:
				originalLoadingLayout = getHeaderLayout();
				listViewLoadingLayout = mHeaderLoadingView;
				selection = 0;
				scrollToY = getScrollY() + getHeaderHeight();
				break;
		}

		if (doScroll) {
			setHeaderScroll(scrollToY);
		}

		originalLoadingLayout.setVisibility(View.INVISIBLE);

		listViewLoadingLayout.setVisibility(View.VISIBLE);
		listViewLoadingLayout.refreshing();

		if (doScroll) {
			mRefreshableView.setSelection(selection);

			smoothScrollTo(0);
		}
	}

	@Override
	protected void resetHeader() {

		ListAdapter adapter = mRefreshableView.getAdapter();
		if (null == adapter) // || adapter.isEmpty()) 
		{
			super.resetHeader();
			return;
		}

		LoadingLayout originalLoadingLayout;
		LoadingLayout listViewLoadingLayout;

		int scrollToHeight = getHeaderHeight();
		final boolean doScroll;

		switch (getCurrentMode()) {
			case MODE_PULL_UP_TO_REFRESH:
				originalLoadingLayout = getFooterLayout();
				listViewLoadingLayout = mFooterLoadingView;
				doScroll = isReadyForPullUp();
				break;
			case MODE_PULL_DOWN_TO_REFRESH:
			default:
				originalLoadingLayout = getHeaderLayout();
				listViewLoadingLayout = mHeaderLoadingView;
				scrollToHeight *= -1;
				doScroll = isReadyForPullDown();
				break;
		}

		originalLoadingLayout.setVisibility(View.VISIBLE);

		if (doScroll) {
			setHeaderScroll(scrollToHeight);
		}

		listViewLoadingLayout.setVisibility(View.GONE);

		super.resetHeader();
	}

	protected int getNumberInternalHeaderViews() {
		return null != mHeaderLoadingView ? 1 : 0;
	}

	protected int getNumberInternalFooterViews() {
		return null != mFooterLoadingView ? 1 : 0;
	}

}
