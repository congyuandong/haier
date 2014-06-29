package com.legoo.haier.Activity;


import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCancelListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.BindTVAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Handler.Json.JsonHandler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * MyTvActivity
 * @author Congyuandong
 */
public class MyTVActivity extends NavigationActivity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	public final static String EXTRA_AUTO = "AUTOBIND";
	private TextView textDeviceid ;
	private Button btn_bind;
	
	private TaskArchon taskArchon;
	private boolean auto_bind;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_mytv);
		verifyExtras();
		initView();
		initTask();
		setDeviceid(Haier.getInstance().getUser().getCurrent().getDeviceid());
	}

	private void initView() {
		textDeviceid = (TextView) findViewById(R.id.deviceid); 
		btn_bind = (Button) findViewById(R.id.bind);
		btn_bind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyTVActivity.this, QRScanActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		getNavigation().setTitle(getString(R.string.navigation_title_user_mytv));
        getNavigation().setReturn(getString(R.string.navigation_return));
	}
	
	private void initTask()
	{
		taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		taskArchon.setWaittingEnabled(true);
		taskArchon.setOnLoadedListener(new OnLoadedListener() {
			@Override
			public void OnLoaded(JsonEvent event) {
				if (auto_bind) {
					if(event.getError() == JsonHandler.ERROR_NONE)
					{
						setResult(RESULT_OK);						
					}
					finish();					
				} else {
					setDeviceid(Haier.getInstance().getUser().getCurrent().getDeviceid());
				}
			}
		});
		taskArchon.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel() {
				finish();
			}
		});
		taskArchon.setOnConfirmListener(new OnConfirmListener() {
			
			@Override
			public void onConfirm() {
				submit();
			}
		});
	}
	@SuppressLint("NewApi")
	private void setDeviceid(String deviceid)
	{
		if(deviceid ==null || deviceid.equals(""))
		{
			textDeviceid.setVisibility(View.GONE);
			if(auto_bind)
			{
				btn_bind.callOnClick();
			}
		}else
		{
			textDeviceid.setVisibility(View.VISIBLE);
			textDeviceid.setText(deviceid);
		}
	}
	private void submit()
	{
		taskArchon.executeAsyncTask(new BindTVAsyncTask(textDeviceid.getText().toString()));
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				String deviceID = bundle.getString("result");
				Haier.getInstance().getUser().getCurrent().setDeviceid(deviceID);
				setDeviceid(deviceID);
				if(auto_bind)
				{
					submit();
				}
			}
			break;
		}
    }	

	private void verifyExtras() {
		setSource(getIntent().getIntExtra(EXTRA_SOURCE, SOURCE_OTHER));
		auto_bind = getIntent().getBooleanExtra(EXTRA_AUTO, false);
		
	}

}
