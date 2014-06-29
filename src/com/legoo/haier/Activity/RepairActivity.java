package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.Archon.TaskArchon.OnSucessedListener;
import com.legoo.haier.AsyncTask.RepairTVAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Model.RepairTVModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @class More Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class RepairActivity extends NavigationActivity
{
	public final int RESULT_LOGIN = 1;
	public final int RESULT_TV = 2;
	
	private Button buttonSubmit;
	private TaskArchon _taskArchon;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_repair);
		initView();
		initTask();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{  
		if (resultCode == RESULT_OK) 
        {  
			switch(requestCode)
			{
			case RESULT_LOGIN:
				checkTV();
			break;
			case RESULT_TV:
				submit();
			break;
			}
        } 
	} 
	
	private void initView() 
	{
		getNavigation().setTitle(getString(R.string.public_one_key_repair));
		buttonSubmit = (Button) findViewById(R.id.repair_submit);
		buttonSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkTV();
			}
		});
	}
	private void initTask()
	{
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				RepairTVModel model = (RepairTVModel)((ModelEvent)event).getModel();
				if(model.getCode().equals("0"))
				{
					_taskArchon.showSucessDialog(getString(R.string.public_submit_succeed));					
				}
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
				submit();
			}
		});
	}
	
	private void submit()
	{
		_taskArchon.executeAsyncTask(new RepairTVAsyncTask());
	}
	
	
	private void checkTV()
	{
		if(Haier.getInstance().getUser().getCurrent().isRegister())
		{
			submit();
		}else
		{
			startActivityForResult(new Intent(RepairActivity.this, MyTVActivity.class)
				.putExtra(MyTVActivity.EXTRA_AUTO, true)
				,RESULT_TV
			);			
		}
	}
}
