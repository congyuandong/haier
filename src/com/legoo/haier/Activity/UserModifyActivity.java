package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCheckInputListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.Archon.TaskArchon.OnSucessedListener;
import com.legoo.haier.AsyncTask.ModifyAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Model.UserModel;
import android.os.Bundle;
import android.widget.EditText;

/**
 * @class User Modify Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-15
 */
public class UserModifyActivity extends NavigationActivity
{
	private EditText _textName;
	private EditText _textTelephone;
	private EditText _textIDCard;
	
	private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_user_modify);
		initView();
	}
	
	private void initView() 
	{
		_textName = (EditText) findViewById(R.id.textUserModifyName);
		_textTelephone = (EditText) findViewById(R.id.textUserModifyTelephone);
		_textIDCard = (EditText) findViewById(R.id.textUserModifyIDCard);

		UserModel current = Haier.getInstance().getUser().getCurrent();
		_textName.setText(current.getName());
		_textTelephone.setText(current.getTelephone());
		_textIDCard.setText(current.getIDCard());
		_textIDCard.requestFocus();
		
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setSubmitButton(R.id.buttonUserModifySubmit);
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
				UserModel current = Haier.getInstance().getUser().getCurrent();
				current.setName(_textName.getText().toString());
				current.setTelephone(_textTelephone.getText().toString());
				current.setIDCard(_textIDCard.getText().toString());
				
				_taskArchon.showSucessDialog(getString(R.string.user_modify_submit_succeed));
			}
		});
		_taskArchon.setOnSucessedListener(new OnSucessedListener()
		{
			@Override
			public void onSucessed() 
			{
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
		
        getNavigation().setTitle(getString(R.string.navigation_title_user_modify));
	}
	
	private void submitData()
	{
		_taskArchon.executeAsyncTask(new ModifyAsyncTask(
				_textName.getText().toString(), 
				_textTelephone.getText().toString(), 
				_textIDCard.getText().toString()));
	}
	
	private boolean checkInput()
	{
		if (_textName.getText().length() == 0)
		{
			Haier.getInstance().getAnimation().startShake(_textName);
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
		}
		return true;
	}
}
