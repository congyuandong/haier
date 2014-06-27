package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.HospitalSettings;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @class More Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class AboutActivity extends NavigationActivity
{
	private TextView _textVersion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_about);
		initView();
	}
	
	private void initView() 
	{
		_textVersion = (TextView) findViewById(R.id.textAboutCurrentVersion);
		_textVersion.setText(String.format(getString(R.string.about_button_current), HospitalSettings.VERSION_NAME));
		
		getNavigation().setTitle(getString(R.string.navigation_title_about));
	}
}
