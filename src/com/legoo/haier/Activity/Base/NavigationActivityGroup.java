package com.legoo.haier.Activity.Base;

import com.legoo.haier.R;
import com.legoo.haier.Archon.NavigationArchon;
import com.legoo.haier.Archon.NavigationArchon.OnReturnClickListener;
import com.legoo.haier.Dialog.MessageDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @class Navigation Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-4
 */
public class NavigationActivityGroup extends BaseActivityGroup
{
	private MessageDialog _dialogReturn;
	
	private boolean _confirmReturn = false;
	
	private NavigationArchon _navigationArchon;
	
	protected NavigationArchon getNavigation()
	{
		return _navigationArchon;
	}
	
	public void onCreate(Bundle savedInstanceState, int view) 
	{
		super.onCreate(savedInstanceState, view);
        initEvent();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{     
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
        	onReturn();
        	return true;
        }
        else
        {        
            return super.onKeyDown(keyCode, event);
        }
    }
	
	private void initEvent() 
	{
		_navigationArchon = new NavigationArchon(this);
		_navigationArchon.setOnReturnClickListener(new OnReturnClickListener()
		{
			@Override
			public void onReturnClick() 
			{
				onReturn();
			}
		});
	}
	
	private void showReturnDialog()
	{
		_dialogReturn = new MessageDialog(this, getString(R.string.dialog_body_finish));
		_dialogReturn.setConfirmButton(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_dialogReturn.dismiss();
				NavigationActivityGroup.this.finish();
			}
		});
		_dialogReturn.setCancelButton(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				_dialogReturn.dismiss();
			}
		});
		_dialogReturn.show();
	}
	
	protected void setReturnConfirm(boolean confirm)
	{
		_confirmReturn = confirm;
	}
	
	protected void onReturn()
	{
		if (_confirmReturn == true)
		{
			showReturnDialog();
		}
		else 
		{
			finishForRestore();
		}
	}
	
	@Override
	public void finish()
	{
		finishForRestore();
	}
}
