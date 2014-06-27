package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCheckInputListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.Archon.TaskArchon.OnSucessedListener;
import com.legoo.haier.AsyncTask.PasswordChangeAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Extension.ApplicationUtils;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @class User Password Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class UserPasswordChangeActivity extends NavigationActivity
{
	private TextView _textAccount;
	private EditText _textOld;
	private EditText _textNew;
	private EditText _textConfirm;
	
	private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_user_password_change);
		initView();
	}
	
	private void initView() 
	{
		_textAccount = (TextView) findViewById(R.id.textUserPasswordChangeAccount);
		_textOld = (EditText) findViewById(R.id.textUserPasswordChangeOld);
		_textNew = (EditText) findViewById(R.id.textUserPasswordChangeNew);
		_textConfirm = (EditText) findViewById(R.id.textUserPasswordChangeConfirm);

		_textAccount.setText(Haier.getInstance().getUser().getCurrent().getAccount());
		
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setSubmitButton(R.id.buttonUserPasswordSubmit);
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
				_taskArchon.showSucessDialog(getString(R.string.user_password_submit_succeed));
			}
		});
		_taskArchon.setOnSucessedListener(new OnSucessedListener()
		{
			@Override
			public void onSucessed() 
			{
				Haier.getInstance().getUser().logout();
				ApplicationUtils.restoreApplication(UserPasswordChangeActivity.this);
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
		
        getNavigation().setTitle(getString(R.string.navigation_title_user_password_change));
	}
	
	private void submitData()
	{
		_taskArchon.executeAsyncTask(new PasswordChangeAsyncTask(
				_textOld.getText().toString(),
				_textNew.getText().toString()));
	}
	
	private boolean checkInput()
	{
		if (_textOld.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textOld);
			return false;
		}
		else if (_textNew.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textNew);
			return false;
		}
		else if (_textNew.getText().length() < 5 || _textNew.getText().length() > 16)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_password_long_error));
			Haier.getInstance().getAnimation().startShake(_textNew);
			return false;
		}
		else if (_textConfirm.getText().toString().equals(_textNew.getText().toString()) == false)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_password_confirm_error));
			Haier.getInstance().getAnimation().startShake(_textConfirm);
			return false;
		}
		return true;
	}
}
