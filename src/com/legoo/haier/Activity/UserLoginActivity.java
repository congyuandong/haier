package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCheckInputListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.LoginAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Extension.ApplicationUtils;
import com.legoo.haier.Model.UserModel;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @class User Login Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class UserLoginActivity extends NavigationActivity
{
	public static final String EXTRA_TARGET = "EXTRA_TARGET";
	public static final String EXTRA_AUTO = "EXTRA_AUTO";
	
	public static final String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";
	public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
	
	private static final int RESULT_LOGIN = 105;
	
	
	public static final int TARGET_NULL = 0;
	public static final int TARGET_USER_CENTER = 1;
	public static final int TARGET_REPAIR = 2;
	public static final int TARGET_MyTv = 3;
	public static final int TARGET_OneKeyRepair = 4;
	public static final int TARGET_BindTv = 5;
	public static final int TARGET_Consulting = 6;
	public static final int TARGET_Question = 7;
	
	private int _target;
	private boolean _auto;
	
	private EditText _textAccount;
	private EditText _textPassword;
	private TextView _textForget;
	
	private Button _buttonRegister;
	
	private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_user_login);
		initView();
		
		if (_auto == true)
		{
			autoLogin();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{  
		if (resultCode == RESULT_OK) 
        {  
        	if (requestCode == RESULT_LOGIN) 
	        {  
        		_textAccount.setText(data.getStringExtra(EXTRA_ACCOUNT));
        		_textPassword.setText(data.getStringExtra(EXTRA_PASSWORD));
        		autoLogin();
	        } 
        } 
	} 
	
	private void initView() 
	{
		_textAccount = (EditText) findViewById(R.id.textUserLoginAccount);
		_textPassword = (EditText) findViewById(R.id.textUserLoginPassword);
		_textForget = (TextView) findViewById(R.id.textUserLoginForget);
		
		_textForget.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				if (_textAccount.getText().length() == 0)
				{
					Haier.getInstance().getAnimation().startShake(_textAccount);
					
				}
				else 
				{
					ApplicationUtils.closeInputWindow(_textAccount);
					//startActivity(new Intent(UserLoginActivity.this, null));
				}
			}
		});
		
		_buttonRegister = (Button) findViewById(R.id.buttonUserLoginRegister);
		_buttonRegister.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				ApplicationUtils.closeInputWindow(_textAccount);
				startActivityForResult(
						new Intent(UserLoginActivity.this, UserRegisterActivity.class),
						RESULT_LOGIN);
			}
		});
		
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setSubmitButton(R.id.buttonUserLoginLogon);
		_taskArchon.setOnCheckInputListener(new OnCheckInputListener()
		{
			@Override
			public boolean onCheckInput() 
			{
				return checkInput();
			}
		});
		_taskArchon.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				if (Haier.getInstance().getUser().login((UserModel) ((ModelEvent) event).getModel()) == true)
				{
					switch (_target)
					{
						case TARGET_USER_CENTER:
						{
							startActivity(new Intent(UserLoginActivity.this, UserCenterActivity.class));
							break;
						}
						case TARGET_MyTv:
						{
							startActivity(new Intent(UserLoginActivity.this, MyTVActivity.class));
							break;
						}
						case TARGET_OneKeyRepair:
						{
							startActivity(new Intent(UserLoginActivity.this, RepairActivity.class));
							break;
						}
					}
					setResult(RESULT_OK);
					finish();
				}
			}
		});
		_taskArchon.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				ApplicationUtils.closeInputWindow(_textPassword);
				submitData();
			}
		});
		
		if (Haier.getInstance().getUser().hasPrepared() == true)
		{
			_textAccount.setText(Haier.getInstance().getUser().getRemember().getName());
			_textPassword.setText(Haier.getInstance().getUser().getRemember().getPassword());
		}
		
        getNavigation().setTitle(getString(R.string.navigation_title_user_login));
        getNavigation().setReturn(getSource());
	}
	
	private void autoLogin()
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				submitData();
			}
		}, 250);
	}
	
	private void submitData()
	{
		_taskArchon.executeAsyncTask(new LoginAsyncTask(
				_textAccount.getText().toString(), 
				_textPassword.getText().toString()));
	}
	
	private boolean checkInput()
	{
		if (_textAccount.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textAccount);
			return false;
		}
		else if (_textPassword.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textPassword);
			return false;
		}
		return true;
	}
	
	private void verifyExtras()
	{
		setSource(getIntent().getIntExtra(EXTRA_SOURCE, SOURCE_OTHER));
		_target = getIntent().getIntExtra(EXTRA_TARGET, TARGET_NULL);
		_auto = getIntent().getBooleanExtra(EXTRA_AUTO, false);
	}
}
