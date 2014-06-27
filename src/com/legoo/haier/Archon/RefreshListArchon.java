package com.legoo.haier.Archon;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.AdapterInterface;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.AsyncTask.Base.BaseListener;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Base.NetworkAsyncTask;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Dialog.MessageDialog;
import com.legoo.haier.Dialog.Base.BaseDialog;
import com.legoo.haier.Extension.ApplicationUtils;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.InternalBatchListView;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshBase.OnFirstItemVisibleListener;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshBase.OnLoadMoreListener;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshBase.OnRefreshListener;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshListView;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Model.Base.ModelInterface;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;


/**
 * @class Refresh List Archon
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class RefreshListArchon 
{
	private Activity _activity;
	private Activity _activityDialog;
	
	private PullToRefreshListView _layout;
	private InternalBatchListView _listView;
	
	private NetworkAsyncTask _listAsyncTask;
	
	private MessageDialog _messageDialog;
	
	private boolean _isTaskWorking;
	private boolean _readyMessage;
	private boolean _hasMessage;
	private boolean _isBackground;
	private int _lastMark;
	private boolean _isManualRefreshDisable = false;
	
	private OnLoadDataListener _loadDataListener;
	private OnFirstLoadedListener _firstLoadedListener;
	private OnLoadedListener _loadedListener;
	private OnPrepareLoadListener _prepareLoadListener;
	private OnFirstPrepareLoadListener _firstPrepareLoadListener;
	private OnErrorListener _errorListener;
	private OnCancelListener _cancelListener;
	
	private String _customErrorMessage;
	
	public RefreshListArchon(Activity activity, int listid)
	{
		_activity = activity;
		_layout = (PullToRefreshListView) _activity.findViewById(listid);
		initArchon();
	}
	
	public RefreshListArchon(Activity activity, View listView)
	{
		_activity = activity;
		_layout = (PullToRefreshListView) listView;
		initArchon();
	}
	
	private void initArchon()
	{
		_listView = (InternalBatchListView) _layout.getRefreshableView();
		_listView.setFooterLayout(R.layout.list_item_footer);
		_listView.addReloadListener(new BaseListener()
		{
			@Override
			public void EventActivated(EventInterface event) 
			{
				_loadDataListener.onLoadData();
			}
		});
		_layout.setOnRefreshListener(new OnRefreshListener() 
		{
			@Override
			public void onRefresh() 
			{
				refresh();
			}
		});
		_layout.setOnLoadMoreListener(new OnLoadMoreListener()
		{
			@Override
			public void OnLoadMore() 
			{
				_listView.onContinueLoad();
			}
		});
		_layout.setOnLastItemVisibleListener(new OnLastItemVisibleListener()
		{
			@Override
			public void onLastItemVisible()
			{
				_listView.onLastItemVisible();
			}
		});
		_layout.setOnFirstItemVisibleListener(new OnFirstItemVisibleListener()
		{
			@Override
			public void onFirstItemVisible() 
			{
				_listView.onFirstItemVisible();
			}
		});
		
		_isTaskWorking = false;
		_isBackground = true;
		_hasMessage = false;
		_lastMark = 0;
	}
	

	public void resume()
	{
		_isBackground = false;
		if (_readyMessage && _hasMessage && _layout != null)
		{
			_hasMessage = false;
			refresh();
		}
	}
	
	public boolean manualRefresh()
	{
		if (_isManualRefreshDisable == false)
		{
			refresh();
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	private void refresh()
	{
		getAdapter().setPageCount(0);
		getAdapter().setPageNumber(0);
		_listView.refresh();
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				_layout.setRefreshing(true);
			}
		}, 100);
	}
	
	public void overLoad()
	{
		_layout.onRefreshComplete();
		_listView.over();
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				_isManualRefreshDisable = false;
			}
		}, 1000);
	}
	
	public void prepare()
	{
		if (getCount() == 0)
		{
			_listView.doNoticeFooter();
		}
		else
		{
			_listView.removeFooter();
		}
		overLoad();
	}
	
	public void clear()
	{
		getAdapter().setPageCount(0);
		getAdapter().setPageNumber(0);
		getAdapter().clear();
		prepare();
	}
	
	public void setPullToRefreshEnabled(boolean enable)
	{
		_layout.setPullToRefreshEnabled(enable);
	}
	
	public void setAsyncTask(NetworkAsyncTask asyncTask)
	{
		_listAsyncTask = asyncTask;
		_listAsyncTask.setMark(getMark());
		_listAsyncTask.addOnFinishListener(new BaseListener()
		{
			@Override
			public void EventActivated(EventInterface event) 
			{
				JsonEvent xmlEvent = (JsonEvent)event;
				if (xmlEvent.getError() == JsonHandler.ERROR_NONE)
				{
					doAsyncTaskCallBack(xmlEvent);
				}
				else 
				{
					_listView.removeFooter();
					_listView.doNoticeFooter();
					if (_errorListener != null)
					{
						_errorListener.onError(xmlEvent);
					}
					showMessageDialog();
					//childListView.scrollToReady();
					overLoad();
				}
			}
		});
	}
	
	private void doAsyncTaskCallBack(JsonEvent event) 
	{
		final ModelListEvent model = (ModelListEvent)event;
		getAdapter().setLast(getCount());
		getAdapter().setPageCount(model.getPageCount());
		getAdapter().setPageNumber(model.getPageNumber());
		getAdapter().setEvent(event);
		if (model.getModelList() != null && model.getModelList().size() > 0 
				&& model.getMark() == _lastMark)
		{
			overLoad();
			new Handler().postDelayed(new Runnable() 
			{
				@Override
				public void run() 
				{
					boolean firstLoad = getCount() == 0;
					_listView.removeFooter();
					getAdapter().addAll(model.getModelList());
					if (firstLoad == true)
					{
						Haier.getInstance().getAnimation().startViewGroup(_listView);
						if (_firstLoadedListener != null)
						{
							_firstLoadedListener.onFirstLoaded();
						}
						else if (_loadedListener != null) 
						{
							_loadedListener.onLoaded();
						}
					}
					else if (_loadedListener != null) 
					{
						_loadedListener.onLoaded();
					}
				}
			}, 200);
		}
		else
		{
			_listView.doNoticeFooter();
			if (_loadedListener != null) 
			{
				_loadedListener.onLoaded();
			}
			overLoad();
		}
	}
	
	public void executeAsyncTask()
	{
		if (ApplicationUtils.detectNetworkState() == true)
		{
			if (_isTaskWorking == false)
			{
				_isManualRefreshDisable = true;
				_isTaskWorking = true;
				if (getCount() == 0 && _firstPrepareLoadListener != null)
				{
					_firstPrepareLoadListener.onFirstPrepareLoad();
				}
				else if (_prepareLoadListener != null)
				{
					_prepareLoadListener.onPrepareLoad();
				}
				new Handler().postDelayed(new Runnable() 
				{
					@Override
					public void run() 
					{
						_listAsyncTask.executeEnhanced();
						_isTaskWorking = false;
					}
				}, 250);
			}
		}
		else
		{
			_isManualRefreshDisable = true;
			new Handler().postDelayed(new Runnable() 
			{
				@Override
				public void run() 
				{
					_listView.removeFooter();
					_listView.doNoticeFooter();

					overLoad();
					showNetworkDialog();
				}
			}, 200);
		}
	}
	
	private void readyMessageDialog()
	{
		if (_readyMessage && _isBackground)
		{
			_hasMessage = true;
		}
		else 
		{
			_hasMessage = false;
			new Handler().postDelayed(new Runnable() 
			{
				@Override
				public void run() 
				{
					_messageDialog.show();
				}
			}, 250);
			
		}
	}
	
	private void showMessageDialog()
	{
		BaseDialog.prepare(_messageDialog);
		_messageDialog = new MessageDialog(getDialogBuilder(),  
				_customErrorMessage == null ? String.format(_activity.getString(R.string.dialog_body_retry), _activity.getString(R.string.dialog_do_get)) : _customErrorMessage);
		_messageDialog.setConfirmButton(_activity.getString(R.string.dialog_button_retry), new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_messageDialog.dismiss();
				_listView.removeFooter();
				_layout.setRefreshing(true);
				_loadDataListener.onLoadData();
			}
		});
		_messageDialog.setCancelButton(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_messageDialog.dismiss();
				if (_cancelListener != null)
				{
					_cancelListener.onCancel();
				}
			}
		});
		readyMessageDialog();
	}
	
	private void showNetworkDialog()
	{
		BaseDialog.prepare(_messageDialog);
		_messageDialog = new MessageDialog(getDialogBuilder(), _activity.getString(R.string.dialog_body_network));
		_messageDialog.setConfirmButton(_activity.getString(R.string.dialog_button_network), new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				ApplicationUtils.openNetworkConfig(_activity);
				_messageDialog.dismiss();
				showMessageDialog();
			}
		});
		_messageDialog.setCancelButton(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_messageDialog.dismiss();
			}
		});
		readyMessageDialog();
	}
	
	private int getMark()
	{
		if (_lastMark == Integer.MAX_VALUE)
		{
			_lastMark = 0;
		}
		else
		{
			_lastMark++;
		}
		return _lastMark;
	}
	
	public InternalBatchListView getListView()
	{
		return _listView;
	}
	
	public AdapterInterface<ModelInterface> getAdapter()
	{
		return _listView.getProtoAdapter();
	}
	
	public PullToRefreshListView getLayout()
	{
		return _layout;
	}
	
	public ModelInterface getItem(int position)
	{
		return (ModelInterface) _listView.getModelItem(position);
	}
	
	public boolean isListViewItem(int position)
	{
		return _listView.isListViewItem(position);
	}
	
	public void notifyDataSetChanged()
	{
		getAdapter().notifyDataSetChanged();
	}
	
	public void setReadyMessage(boolean readyMessage)
	{
		_readyMessage = readyMessage;
	}
	
	public void setRefreshing(boolean doScroll)
	{
		_layout.setRefreshing(doScroll);
	}
	
	public void setModel(AdapterInterface<ModelInterface> adapter)
	{
		_listView.setModel(adapter);
	}
	
	public void setCursorModel(AdapterInterface<ModelInterface> adapter)
	{
		setModel(adapter);
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				boolean firstLoad = getCount() == 0;
				if (firstLoad == true)
				{
					Haier.getInstance().getAnimation().startViewGroup(_listView);
					if (_firstLoadedListener != null)
					{
						_firstLoadedListener.onFirstLoaded();
					}
					else if (_loadedListener != null) 
					{
						_loadedListener.onLoaded();
					}
				}
				else if (_loadedListener != null) 
				{
					_loadedListener.onLoaded();
				}
				prepare();
			}
		}, 200);
	}
	
	public void setDialogBuilder(Activity activity)
	{
		_activityDialog = activity;
	}
	
	private Activity getDialogBuilder()
	{
		return _activityDialog != null ? _activityDialog : _activity;
	}
	
	public int getCount()
	{
		return getAdapter().getModelCount();
	}
	
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		_listView.setOnItemClickListener(listener);
	}
	
	public void setOnLoadDataListener(OnLoadDataListener listener)
	{
		_loadDataListener = listener;
	}
	
	public void setOnFirstLoadedListener(OnFirstLoadedListener listener)
	{
		_firstLoadedListener = listener;
	}
	
	public void setOnLoadedListener(OnLoadedListener listener)
	{
		_loadedListener = listener;
	}
	
	public void setOnPrepareLoadListener(OnPrepareLoadListener listener)
	{
		_prepareLoadListener = listener;
	}
	
	public void setOnFirstPrepareLoadListener(OnFirstPrepareLoadListener listener)
	{
		_firstPrepareLoadListener = listener;
	}
	
	public void setOnErrorListener(OnErrorListener listener)
	{
		_errorListener = listener;
	}
	
	public void setOnCancelListener(OnCancelListener listener)
	{
		_cancelListener = listener;
	}
	
	public static interface OnLoadDataListener
	{
		public void onLoadData();
	}
	
	public static interface OnFirstLoadedListener
	{
		public void onFirstLoaded();
	}
	
	public static interface OnLoadedListener
	{
		public void onLoaded();
	}
	
	public static interface OnErrorListener
	{
		public void onError(JsonEvent event);
	}
	
	public static interface OnPrepareLoadListener
	{
		public void onPrepareLoad();
	}
	
	public static interface OnFirstPrepareLoadListener
	{
		public void onFirstPrepareLoad();
	}
	
	public static interface OnCancelListener
	{
		public void onCancel();
	}
	
	public void setErrorMessage(String message)
	{
		_customErrorMessage = message;
	}
}
