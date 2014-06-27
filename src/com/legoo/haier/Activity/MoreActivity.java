package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Application.HaierSettings;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.UpdateAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Extension.ApplicationUtils;
import com.legoo.haier.Model.UpdateModel;
import android.content.Intent;
//import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * @class More Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class MoreActivity extends NavigationActivity
{
	private RelativeLayout _layoutUpdate;
	private RelativeLayout _layoutAgreement;
	private RelativeLayout _layoutQuestion;
	private RelativeLayout _layoutHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_more);
		initView();
	}
	
	private void initView() 
	{
		_layoutUpdate = (RelativeLayout) findViewById(R.id.layoutMoreUpdate);
		_layoutAgreement = (RelativeLayout) findViewById(R.id.layoutMoreAgreement);
		_layoutQuestion = (RelativeLayout) findViewById(R.id.layoutMoreQuestion);
		_layoutHelp= (RelativeLayout) findViewById(R.id.layoutMoreHelp);
		
		_layoutUpdate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				checkUpdate();
			}
		});
		
		_layoutAgreement.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
//				startActivity(new Intent(MoreActivity.this,AgreementActivity.class));
			}
		});
		
		_layoutQuestion.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
//				startActivity(new Intent(MoreActivity.this,QuestionListActivity.class));
			}
		});
		
		_layoutHelp.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
//				startActivity(new Intent(MoreActivity.this,QuestionListActivity.class));
			}
		});
		
		
        getNavigation().setTitle(getString(R.string.navigation_title_more));
        getNavigation().setReturn(getString(R.string.navigation_home));
	}
	private void checkUpdate()
	{
		TaskArchon updateTaskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_GET);
		updateTaskArchon.setConfirmEnabled(false);
		updateTaskArchon.setWaittingEnabled(false);
		updateTaskArchon.setOnLoadedListener(new OnLoadedListener()
		{
			@Override
			public void OnLoaded(final JsonEvent event) {
				// TODO Auto-generated method stub
				UpdateModel model = (UpdateModel)((ModelEvent)event).getModel();
				if (ApplicationUtils.isCompareVersionNewest(model.getVersionName()) == true)
				{
					ApplicationUtils.showUpdateDialog(MoreActivity.this, 
							model.getVersionName(), model.getURL());
				}else
				{
					Haier.getInstance().getToast().show(String.format(getString(R.string.toast_update_none),HaierSettings.VERSION_NAME));
				}
				
			}
		});
		updateTaskArchon.executeAsyncTask(new UpdateAsyncTask());
	}
}
