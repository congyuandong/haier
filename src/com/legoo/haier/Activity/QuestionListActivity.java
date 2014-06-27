package com.legoo.haier.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Adapter.QuestionListAdapter;
import com.legoo.haier.Archon.RefreshListArchon;
import com.legoo.haier.Archon.RefreshListArchon.OnLoadDataListener;
import com.legoo.haier.AsyncTask.QuestionListAsyncTask;
import com.legoo.haier.Model.QuestionListModel;

/**
 * @class Expert List Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-11
 */
public class QuestionListActivity extends NavigationActivity
{
	
	public static final int RESULT_REFRESH = 100;
	
	private RefreshListArchon _listArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_question_list);
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
					clickItem((QuestionListModel) _listArchon.getItem(position));
				}
			}
		});
		_listArchon.setRefreshing(true);
		_listArchon.setModel(new QuestionListAdapter(this, _listArchon.getListView()));
		
		getNavigation().setTitle(getString(R.string.navigation_title_question_list));
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
	}
	
	private void loadData()
	{
		_listArchon.setAsyncTask(new QuestionListAsyncTask());
		_listArchon.executeAsyncTask();
	}
	
	private void clickItem(QuestionListModel model)
	{
		startActivity(new Intent(QuestionListActivity.this, QuestionDetailActivity.class)
				.putExtra(QuestionDetailActivity.EXTRA_MODEL, model)
				);
	}
	
	private void verifyExtras()
	{
	}
}
