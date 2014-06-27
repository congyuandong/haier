package com.legoo.haier.Activity;

import java.util.List;
import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.BaseActivity;
import com.legoo.haier.Adapter.PagerAdapter;
import com.legoo.haier.Adapter.Base.BaseSlideAdapter.OnItemClickListener;
import com.legoo.haier.Application.Hospital;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCancelListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.Archon.ViewPagerArchon;
import com.legoo.haier.AsyncTask.AuthenticateAsyncTask;
import com.legoo.haier.AsyncTask.HomeAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Dialog.MessageDialog;
import com.legoo.haier.Dialog.Base.BaseDialog;
import com.legoo.haier.Extension.UltraIdeal.EmbedViewPager;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshBase.OnRefreshListener;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshScrollView;
import com.legoo.haier.Model.HomeModel;
import com.legoo.haier.Model.LinkModel;
import com.legoo.haier.Model.PagerModel;
import com.legoo.haier.Model.UserModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @class Main Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-8
 */
public class MainActivity extends BaseActivity
{
	private static final int LOAD_DELAY = 300;
	private static final int NOTICE_DELAY = 5000;
	
	
	private ImageButton _buttonToolbarMore;
	private ImageButton _buttonToolbarUser;
	
	private ViewPagerArchon _pagerArchon;
	private PullToRefreshScrollView _scrollView;
	private TextView _textNotice;
	
	private TaskArchon _taskArchon;
	
	private LinkModel _currentLink;
	private Handler _handlerNotice;
	private Runnable _runnableNotice;
	
	private MessageDialog _exitDialog;
	
	private static final int TASK_DELAY = 500;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_main);
		initView();
		initTaskDelay(this);
		loadData();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{     
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
        	showConfirmExitDialog();
        	return true;
        }
        else
        {        
            return super.onKeyDown(keyCode, event);
        }
    }
	
	private void initView() 
	{
		_scrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
		_scrollView.fill(R.layout.main_content);
		_scrollView.setRefreshing(true);
		_scrollView.setOnRefreshListener(new OnRefreshListener()
		{
			@Override
			public void onRefresh() 
			{
				loadData();
			}
		});
		
		_buttonToolbarMore = (ImageButton) findViewById(R.id.buttonMainToolbarMore);
		_buttonToolbarUser = (ImageButton) findViewById(R.id.buttonMainToolbarUser);
		
		
		_buttonToolbarUser.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startTargetActivity(UserCenterActivity.class, UserLoginActivity.TARGET_USER_CENTER);
			}
		});
		
		_buttonToolbarMore.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startMoreActivity();
			}
		});
		
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_GET);
		_taskArchon.setWaittingEnabled(false);
		_taskArchon.setWaittingEnabled(false);
		_taskArchon.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				_scrollView.onRefreshComplete();
				
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run() 
					{
						initData((HomeModel) ((ModelEvent) event).getModel());
					}
				}, LOAD_DELAY);
			}
		});
		_taskArchon.setOnCancelListener(new OnCancelListener()
		{
			@Override
			public void onCancel() 
			{
				_scrollView.onRefreshComplete();
			}
		});
		_taskArchon.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				_scrollView.setRefreshing(true);
				loadData();
			}
		});
	}
	
	private void startMoreActivity()
	{
		startActivity(new Intent(MainActivity.this, MoreActivity.class));
	}
	
	private void startTargetActivity(Class<?> cls, int target)
	{
		if (Hospital.getInstance().getUser().hasLogin() == true)
		{
			startActivity(new Intent(MainActivity.this, cls)
					.putExtra(UserLoginActivity.EXTRA_SOURCE, UserLoginActivity.SOURCE_HOME));
		}
		else 
		{
			startActivity(new Intent(MainActivity.this, UserLoginActivity.class)
					.putExtra(UserLoginActivity.EXTRA_SOURCE, UserLoginActivity.SOURCE_HOME)
					.putExtra(UserLoginActivity.EXTRA_TARGET, target)
					.putExtra(UserLoginActivity.EXTRA_AUTO, Hospital.getInstance().getUser().hasPrepared()));
		}
	}
	
	private void loadData()
	{
		_taskArchon.executeAsyncTask(new HomeAsyncTask());
	}
	
	private void initData(HomeModel home)
	{
		initPager(home.getTopics());
		initNotice(home.getNotices());
	}
	
	private void initPager(List<ModelInterface> topics)
	{
		final PagerAdapter adapter = new PagerAdapter(this, topics);
		adapter.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(View view) 
			{
				PagerModel pager = (PagerModel) adapter.get(_pagerArchon.getPosition());
				clickPagerItem(pager);
			}
		});
		
		EmbedViewPager paper = (EmbedViewPager) findViewById(R.id.pagerMainTopic);
		_pagerArchon = new ViewPagerArchon(this);
		_pagerArchon.setPager(paper);
		_pagerArchon.setPoints((LinearLayout) findViewById(R.id.layoutMainPagerPoint), R.layout.main_pager_point);
		_pagerArchon.setNoPointWhenSinglePager(true);
		_pagerArchon.setAdapter(adapter);
		
		Hospital.getInstance().getAnimation().startFade(paper);
	}
	
	private void clickPagerItem(PagerModel pager)
	{
		startActivity(new Intent(this, LinkDetailActivity.class)
				.putExtra(LinkDetailActivity.EXTRA_KIND, LinkDetailActivity.KIND_LINK)
				.putExtra(LinkDetailActivity.EXTRA_URL, pager.getUrl()));
	}
	
	private void initNotice(List<ModelInterface> notices)
	{
		if (_handlerNotice != null && _runnableNotice != null)
		{
			_handlerNotice.removeCallbacks(_runnableNotice);
			_handlerNotice = null;
			_runnableNotice = null;
		}
		if (notices.size() >= 1)
		{
			_textNotice = (TextView) findViewById(R.id.textMainNoticeContent);
			_textNotice.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					clickNoticeItem(_currentLink);
				}
			});
			initNoticeTask(notices);
		}
	}
	
	private void initNoticeTask(final List<ModelInterface> notices)
	{
		_currentLink = (LinkModel) notices.get(0);
		setCurrentNotice();
		_handlerNotice = new Handler();
		_runnableNotice = new Runnable()
		{
			@Override
			public void run() 
			{
				int index = notices.indexOf(_currentLink) + 1;
				if (index >= notices.size())
				{
					index = 0;
				}
				_currentLink = (LinkModel) notices.get(index);
				setCurrentNotice();
				_handlerNotice.postDelayed(_runnableNotice, NOTICE_DELAY);
			}
		};
		_handlerNotice.postDelayed(_runnableNotice, NOTICE_DELAY);
	}
	
	private void setCurrentNotice()
	{
		if (_currentLink != null)
		{
			_textNotice.setText(_currentLink.getTitle());
			Hospital.getInstance().getAnimation().startFade(_textNotice);
		}
	}
	
	private void clickNoticeItem(LinkModel link)
	{
		startActivity(new Intent(this, LinkDetailActivity.class)
				.putExtra(LinkDetailActivity.EXTRA_KIND, LinkDetailActivity.KIND_TEXT)
				.putExtra(LinkDetailActivity.EXTRA_MODEL, link));
	}
	
	private void initTaskDelay(final Context context)
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				if (Hospital.getInstance().getUser().hasLogin() == false
						&& Hospital.getInstance().getUser().hasPrepared() == true)
				{
					loginAuto();
				}
				Hospital.getInstance().checkUpdate(context,MainActivity.this);
			}
		}, TASK_DELAY);
	}
	
	private void loginAuto()
	{
		final TaskArchon loginTaskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT);
		loginTaskArchon.setConfirmEnabled(false);
		loginTaskArchon.setWaittingEnabled(false);
		loginTaskArchon.setOnLoadedListener(new OnLoadedListener()
		{
			@Override
			public void OnLoaded(JsonEvent event) 
			{
				UserModel user = (UserModel) loginTaskArchon.getModel();
				if (Hospital.getInstance().getUser().login(user) == true)
				{
					Hospital.getInstance().getToast().show(String.format(getString(R.string.user_login_succeed), user.getAccount()));
				}
			}
		});
		loginTaskArchon.executeAsyncTask(new AuthenticateAsyncTask(
				Hospital.getInstance().getUser().getRemember().getAccount(),
				Hospital.getInstance().getUser().getRemember().getPassword()));
	}
	
	private void showConfirmExitDialog()
	{
		BaseDialog.prepare(_exitDialog);
		_exitDialog = new MessageDialog(this, String.format(getString(R.string.dialog_body_exit), getString(R.string.app_name)));
		_exitDialog.setConfirmButton(new  OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_exitDialog.dismiss();
				MainActivity.this.finish();
			}
		});
		_exitDialog.setCancelButton(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_exitDialog.dismiss();
			}
		});
		_exitDialog.show();
	}
}
