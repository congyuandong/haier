package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.BaseActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.LoginAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Dialog.MessageDialog;
import com.legoo.haier.Dialog.Base.BaseDialog;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshBase.OnRefreshListener;
import com.legoo.haier.Extension.UltraIdeal.PullRefresh.PullToRefreshScrollView;
import com.legoo.haier.Model.UserModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
/**
 * @class Main Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-2-8
 */
public class MainActivity extends BaseActivity
{
	
	
	private ImageButton _buttonToolbarMore;
	private ImageButton _buttonToolbarUser;
	
	private Button buttonMyTV;
	private Button buttonMyTask;
	private Button buttonConsulting;
	private Button buttonOneKeyRepair;
	private Button buttonQuestion;
	private Button buttonSearch;
	
	
	private PullToRefreshScrollView _scrollView;
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
		
		buttonMyTV = (Button)findViewById(R.id.buttonMainFuctionMyTv);
		buttonMyTask = (Button)findViewById(R.id.buttonMainFuctionMyTask);
		buttonConsulting = (Button)findViewById(R.id.buttonMainFuctionConsulting);
		buttonOneKeyRepair = (Button)findViewById(R.id.buttonMainFuctionOneKeyRepair);
		buttonQuestion = (Button)findViewById(R.id.buttonMainFuctionQuestion);
		buttonSearch = (Button)findViewById(R.id.buttonMainFuctionSearch);

		buttonMyTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTargetActivity(MyTVActivity.class, UserLoginActivity.TARGET_MyTv);			
			}
		});
		buttonMyTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startTargetActivity(RepairActivity.class, UserLoginActivity.TARGET_REPAIR);	
			}
		});
		buttonConsulting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTargetActivity(ConsultingActivity.class, UserLoginActivity.TARGET_Consulting);
			}
		});
		buttonOneKeyRepair.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTargetActivity(RepairActivity.class, UserLoginActivity.TARGET_OneKeyRepair);
			}
		});
		buttonQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, LinkDetailActivity.class)
					.putExtra(LinkDetailActivity.EXTRA_TYPE, LinkDetailActivity.TYPE_QUESTION)
					.putExtra(LinkDetailActivity.EXTRA_TITLE, getString(R.string.more_question))
					.putExtra(UserLoginActivity.EXTRA_SOURCE, UserLoginActivity.SOURCE_HOME));
			}
		});
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(MainActivity.this, LinkDetailActivity.class)
//				.putExtra(UserLoginActivity.EXTRA_SOURCE, UserLoginActivity.SOURCE_HOME));
			}
		});
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
		
	}
	
	private void startMoreActivity()
	{
		startActivity(new Intent(MainActivity.this, MoreActivity.class));
	}
	
	private void startTargetActivity(Class<?> cls, int target)
	{
		if (Haier.getInstance().getUser().hasLogin() == true)
		{
			startActivity(new Intent(MainActivity.this, cls)
					.putExtra(UserLoginActivity.EXTRA_SOURCE, UserLoginActivity.SOURCE_HOME));
		}
		else 
		{
			startActivity(new Intent(MainActivity.this, UserLoginActivity.class)
					.putExtra(UserLoginActivity.EXTRA_SOURCE, UserLoginActivity.SOURCE_HOME)
					.putExtra(UserLoginActivity.EXTRA_TARGET, target)
					.putExtra(UserLoginActivity.EXTRA_AUTO, Haier.getInstance().getUser().hasPrepared()));
		}
	}
	
	private void loadData()
	{
		_scrollView.onRefreshComplete();
	}
	
	private void initTaskDelay(final Context context)
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				if (Haier.getInstance().getUser().hasLogin() == false
						&& Haier.getInstance().getUser().hasPrepared() == true)
				{
					loginAuto();
				}
				Haier.getInstance().checkUpdate(context,MainActivity.this);
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
				if (Haier.getInstance().getUser().login(user) == true)
				{
					Haier.getInstance().getToast().show(String.format(getString(R.string.user_login_succeed), user.getName()));
				}
			}
		});
		loginTaskArchon.executeAsyncTask(new LoginAsyncTask(
				Haier.getInstance().getUser().getRemember().getName(),
				Haier.getInstance().getUser().getRemember().getPassword()));
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
