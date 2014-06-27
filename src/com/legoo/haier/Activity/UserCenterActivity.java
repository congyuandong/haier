package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Extension.ApplicationUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @class User Center Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class UserCenterActivity extends NavigationActivity
{
	private RelativeLayout _layoutPassword;
	private RelativeLayout _layoutFeedback;
	private RelativeLayout _layoutUserCenterMytv;
	private Button _buttonLogout;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_user_center);
		initView();
	}
	
	private void initView() 
	{
		_layoutPassword = (RelativeLayout) findViewById(R.id.layoutUserCenterPassword);
		_layoutFeedback = (RelativeLayout) findViewById(R.id.layoutMoreFeedback);
		_layoutUserCenterMytv = (RelativeLayout) findViewById(R.id.layoutUserCenterMytv);
		_buttonLogout = (Button) findViewById(R.id.buttonUserCenterLogout);
		
		_layoutUserCenterMytv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(UserCenterActivity.this, MyTVActivity.class));
			}	
		});
		
		_layoutPassword.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(UserCenterActivity.this, UserPasswordChangeActivity.class));
			}
		});
		_layoutFeedback.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
//				startActivity(new Intent(UserCenterActivity.this, FeedbackListActivity.class));				
			}
		});
		_buttonLogout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Haier.getInstance().getUser().logout();
				ApplicationUtils.restoreApplication(UserCenterActivity.this);
				finish();
			}
		});
		
        getNavigation().setTitle(getString(R.string.navigation_title_user_center));
        getNavigation().setReturn(getString(R.string.navigation_home));
	}

}
