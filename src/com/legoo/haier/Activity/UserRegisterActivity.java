package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCheckInputListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.Archon.TaskArchon.OnSucessedListener;
import com.legoo.haier.AsyncTask.RegisterAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Extension.ApplicationUtils;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**
 * @class User Password Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class UserRegisterActivity extends NavigationActivity
{
	private EditText _textName;
	private EditText _textPassword;
	
	private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_user_register);
		initView();
	}
	
	private void initView() 
	{
		_textName = (EditText) findViewById(R.id.textUserRegisterName);
		_textPassword = (EditText) findViewById(R.id.textUserRegisterPassword);

		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setSubmitButton(R.id.buttonUserRegisterSubmit);
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
				_taskArchon.showSucessDialog(getString(R.string.user_register_submit_succeed));
			}
		});
		_taskArchon.setOnSucessedListener(new OnSucessedListener()
		{
			@Override
			public void onSucessed() 
			{
				setResult(RESULT_OK, new Intent()
						.putExtra(UserLoginActivity.EXTRA_ACCOUNT, _textName.getText().toString())
						.putExtra(UserLoginActivity.EXTRA_PASSWORD, _textPassword.getText().toString()));
				finish();
			}
		});
		_taskArchon.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				submitData();
			}
		});
		
        getNavigation().setTitle(getString(R.string.navigation_title_user_register));
	}
	
	private void submitData()
	{
		_taskArchon.executeAsyncTask(new RegisterAsyncTask(
				_textName.getText().toString(),
				_textPassword.getText().toString()
				));
	}
	
	private boolean checkInput()
	{if (_textName.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textName);
			return false;
		}
		else if (_textPassword.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textPassword);
			return false;
		}
		else if (_textPassword.getText().length() < 5 || _textPassword.getText().length() > 16)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_password_long_error));
			Haier.getInstance().getAnimation().startShake(_textPassword);
			return false;
		}
		return true;
	}
	
	@Override
	public void finish()
	{
		ApplicationUtils.closeInputWindow(_textName);
		super.finish();
	}
}
