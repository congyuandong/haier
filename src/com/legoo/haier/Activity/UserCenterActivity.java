package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Extension.ApplicationUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * @class User Center Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-14
 */
public class UserCenterActivity extends NavigationActivity
{
	private RelativeLayout _layoutUserCenterMytv;
	private RelativeLayout _layoutConsulting;
	private RelativeLayout _layoutOnKeyRepair;
	
	private Button _buttonLogout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_user_center);
		initView();
	}
	
	private void initView() 
	{
		_layoutUserCenterMytv = (RelativeLayout) findViewById(R.id.layoutUserCenterMytv);
		_layoutConsulting = (RelativeLayout) findViewById(R.id.layoutUserCenterConsulting);
		_layoutOnKeyRepair = (RelativeLayout) findViewById(R.id.layoutUserCenterReapir);
		_buttonLogout = (Button) findViewById(R.id.buttonUserCenterLogout);
		
		_layoutUserCenterMytv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(UserCenterActivity.this, MyTVActivity.class));
			}	
		});
		_layoutConsulting.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(UserCenterActivity.this, ConsultingActivity.class));
			}	
		});
		_layoutOnKeyRepair.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(UserCenterActivity.this, RepairActivity.class));
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
