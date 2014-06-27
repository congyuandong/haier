package com.legoo.haier.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Adapter.FeedbackAdapter;
import com.legoo.haier.Archon.RefreshListArchon;
import com.legoo.haier.Archon.RefreshListArchon.OnLoadDataListener;
import com.legoo.haier.AsyncTask.FeedbackListAsyncTask;
import com.legoo.haier.Model.FeedbackModel;

/**
 * @class Feedback List Activity
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-08
 */
public class FeedbackListActivity extends NavigationActivity{
	private static final int RESULT_REFRESH = 102;
	
	private RefreshListArchon _listArchon;
	private Button _buttonAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_feedback_list);
		initView();
		loadData();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{  
		if (resultCode == RESULT_OK) 
        {  
        	if (requestCode == RESULT_REFRESH) 
	        {  
        		_listArchon.manualRefresh();
	        }
        } 
	} 
	
	protected void initView()
	{
		_listArchon = new RefreshListArchon(this, R.id.listView);
		_listArchon.setOnLoadDataListener(new OnLoadDataListener()
		{
			@Override
			public void onLoadData() 
			{
				loadData();
			}
		});
		_listArchon.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				if (_listArchon.isListViewItem(position) == true)
				{
					clickItem((FeedbackModel) _listArchon.getItem(position));
				}
			}
		});
		_listArchon.setRefreshing(true);
		_listArchon.setModel(new FeedbackAdapter(this, _listArchon.getListView()));
		
		getNavigation().setTitle(getString(R.string.user_center_feedback));
		getNavigation().setReturn(getSource());
		getNavigation().setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				if (_listArchon != null)
				{
					_listArchon.getListView().scrollToReady();
				}
			}
		});
		_buttonAdd= (Button)findViewById(R.id.buttonMainNavigationAdd);
		_buttonAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(FeedbackListActivity.this, FeedbackDetailActivity.class)
				.putExtra(FeedbackDetailActivity.TYPE_MODEL, FeedbackDetailActivity.ADD),RESULT_REFRESH);
			}
		});
	}
	
	private void loadData()
	{
		_listArchon.setAsyncTask(new FeedbackListAsyncTask());
		_listArchon.executeAsyncTask();
	}
	
	private void clickItem(FeedbackModel model)
	{
		startActivityForResult(new Intent(FeedbackListActivity.this, FeedbackDetailActivity.class)
			.putExtra(FeedbackDetailActivity.EXTRA_MODEL, model)
			.putExtra(FeedbackDetailActivity.TYPE_MODEL, FeedbackDetailActivity.EDIT), RESULT_REFRESH);
	}
	
	private void verifyExtras()
	{
		setSource(getIntent().getIntExtra(EXTRA_SOURCE, SOURCE_OTHER));
	}

}
