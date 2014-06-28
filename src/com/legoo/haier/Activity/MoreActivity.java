package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;

import android.content.Intent;
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
//	private RelativeLayout _layoutUpdate;
//	private RelativeLayout _layoutAgreement;
	private RelativeLayout _layoutQuestion;
	private RelativeLayout _layoutHelp;
	private RelativeLayout _layoutRecommend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState, R.layout.activity_more);
		initView();
	}
	
	private void initView() 
	{
//		_layoutUpdate = (RelativeLayout) findViewById(R.id.layoutMoreUpdate);
//		_layoutAgreement = (RelativeLayout) findViewById(R.id.layoutMoreAgreement);
		_layoutQuestion = (RelativeLayout) findViewById(R.id.layoutMoreQuestion);
		_layoutHelp= (RelativeLayout) findViewById(R.id.layoutMoreHelp);
		_layoutRecommend= (RelativeLayout) findViewById(R.id.layoutMoreRecommend);
		
//		_layoutUpdate.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v) 
//			{
//				checkUpdate();
//			}
//		});
//		
//		_layoutAgreement.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v) 
//			{
////				startActivity(new Intent(MoreActivity.this,AgreementActivity.class));
//			}
//		});
		
		_layoutQuestion.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(MoreActivity.this, LinkDetailActivity.class)
					.putExtra(LinkDetailActivity.EXTRA_TYPE, LinkDetailActivity.TYPE_QUESTION)
					.putExtra(LinkDetailActivity.EXTRA_TITLE, getString(R.string.more_question))
				);
			}
		});
		
		_layoutHelp.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(MoreActivity.this, LinkDetailActivity.class)
					.putExtra(LinkDetailActivity.EXTRA_TYPE, LinkDetailActivity.TYPE_HELP)
					.putExtra(LinkDetailActivity.EXTRA_TITLE, getString(R.string.more_help))
				);
			}
		});
		
		_layoutRecommend.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(MoreActivity.this, LinkDetailActivity.class)
					.putExtra(LinkDetailActivity.EXTRA_TYPE, LinkDetailActivity.TYPE_RECOMMEND)
					.putExtra(LinkDetailActivity.EXTRA_TITLE, getString(R.string.more_recommend))
				);
			}
		});
		
		
        getNavigation().setTitle(getString(R.string.navigation_title_more));
        getNavigation().setReturn(getString(R.string.navigation_home));
	}
//	private void checkUpdate()
//	{
//		TaskArchon updateTaskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_GET);
//		updateTaskArchon.setConfirmEnabled(false);
//		updateTaskArchon.setWaittingEnabled(false);
//		updateTaskArchon.setOnLoadedListener(new OnLoadedListener()
//		{
//			@Override
//			public void OnLoaded(final JsonEvent event) {
//				// TODO Auto-generated method stub
//				UpdateModel model = (UpdateModel)((ModelEvent)event).getModel();
//				if (ApplicationUtils.isCompareVersionNewest(model.getVersionName()) == true)
//				{
//					ApplicationUtils.showUpdateDialog(MoreActivity.this, 
//							model.getVersionName(), model.getURL());
//				}else
//				{
//					Haier.getInstance().getToast().show(String.format(getString(R.string.toast_update_none),HaierSettings.VERSION_NAME));
//				}
//				
//			}
//		});
//		updateTaskArchon.executeAsyncTask(new UpdateAsyncTask());
//	}
}
