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
import com.legoo.haier.Extension.FormatUtils;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * @class User Password Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class UserRegisterActivity extends NavigationActivity
{
	private EditText _textAccount;
	private EditText _textName;
	private EditText _textPassword;
	private EditText _textConfirm;
	private EditText _textTelephone;
	private EditText _textEmail;
	private EditText _textIDCard;
	private CheckBox _checkAgreement;
	
	private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_user_register);
		initView();
	}
	
	private void initView() 
	{
		_textAccount = (EditText) findViewById(R.id.textUserRegisterAccount);
		_textName = (EditText) findViewById(R.id.textUserRegisterName);
		_textPassword = (EditText) findViewById(R.id.textUserRegisterPassword);
		_textConfirm = (EditText) findViewById(R.id.textUserRegisterConfirm);
		_textTelephone = (EditText) findViewById(R.id.textUserRegisterTelephone);
		_textEmail = (EditText) findViewById(R.id.textUserRegisterEmail);
		_textIDCard = (EditText) findViewById(R.id.textUserRegisterIDCard);
		_checkAgreement=(CheckBox) findViewById(R.id.checkUserRegisterAgreement);

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
						.putExtra(UserLoginActivity.EXTRA_ACCOUNT, _textAccount.getText().toString())
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
				_textAccount.getText().toString(),
				_textName.getText().toString(),
				_textPassword.getText().toString(),
				_textTelephone.getText().toString(),
				_textEmail.getText().toString(),
				_textIDCard.getText().toString()));
	}
	
	private boolean checkInput()
	{
		if (_textAccount.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textAccount);
			return false;
		}
		else if (_textAccount.getText().length() < 5 || _textAccount.getText().length() > 16)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_account_long_error));
			Haier.getInstance().getAnimation().startShake(_textAccount);
			return false;
		}
		else if (_textName.getText().length() == 0)
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
		else if (_textConfirm.getText().toString().equals(_textPassword.getText().toString()) == false)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_password_confirm_error));
			Haier.getInstance().getAnimation().startShake(_textConfirm);
			return false;
		}
		else if (_textTelephone.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textTelephone);
			return false;
		}
		else if (_textTelephone.getText().length() < 11)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_telephone_toshort));
			Haier.getInstance().getAnimation().startShake(_textTelephone);
			return false;
		}
		else if (_textEmail.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textEmail);
			return false;
		}
		else if (FormatUtils.isEmail(_textEmail.getText().toString()) == false)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_email_error));
			Haier.getInstance().getAnimation().startShake(_textEmail);
			return false;
		}
		else if (_textIDCard.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textIDCard);
			return false;
		}
		else if (_textIDCard.getText().length() != 15 && _textIDCard.getText().length() != 18)
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_idcard_error));
			Haier.getInstance().getAnimation().startShake(_textIDCard);
			return false;
		}else if (!_checkAgreement.isChecked())
		{
			Haier.getInstance().getToast().show(getString(R.string.toast_register_agreement));
			Haier.getInstance().getAnimation().startShake(_checkAgreement);
		}
		return true;
	}
	
	@Override
	public void finish()
	{
		ApplicationUtils.closeInputWindow(_textAccount);
		super.finish();
	}
}
