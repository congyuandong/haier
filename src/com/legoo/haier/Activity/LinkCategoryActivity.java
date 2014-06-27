package com.legoo.haier.Activity;

import java.util.Collection;
import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivityGroup;
import com.legoo.haier.Archon.TabArchon;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCancelListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Model.CategoryModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * @class Expert Detail Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-11
 */
public class LinkCategoryActivity extends NavigationActivityGroup
{
	public static final String EXTRA_TYPE = "EXTRA_TYPE";
	
	public static final int TYPE_SERVICE = 0;
	public static final int TYPE_TCM = 1;
	
	private int _type;
	
	private TabArchon _tabArchon;

	private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_category);
		initView();
		loadData();
	}
	
	private void initView()
	{
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_GET);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				initTabHost(((ModelListEvent) event).getModelList());
			}
		});
		_taskArchon.setOnCancelListener(new OnCancelListener()
		{
			@Override
			public void onCancel() 
			{
				finish();
			}
		});
		_taskArchon.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				loadData();
			}
		});
		
//		getNavigation().setTitle(_type == TYPE_SERVICE ? getString(R.string.navigation_title_service) : getString(R.string.navigation_title_tcm));
		getNavigation().setReturn(getString(R.string.navigation_home));
	}
	
	private void loadData()
	{
//		_taskArchon.executeAsyncTask(_type == TYPE_SERVICE ? new ServiceCategoriesAsyncTask() : new TcmCategoriesAsyncTask());
	}
	
	private void initTabHost(Collection<ModelInterface> modelList)
	{
		_tabArchon = new TabArchon(this, R.layout.tab_button_wrap);
		CategoryModel category;
		for (ModelInterface model : modelList)
		{
			category = (CategoryModel) model;
			_tabArchon.addText(category.getName(), new Intent().setClass(this, LinkListActivity.class)
					.putExtra(LinkCategoryActivity.EXTRA_TYPE, _type)
					.putExtra(LinkListActivity.EXTRA_ID, category.getID()), category.getName());
		}
		_tabArchon.requestFocus();
	}
	
	private void verifyExtras()
	{
		_type = getIntent().getIntExtra(EXTRA_TYPE, TYPE_SERVICE);
	}
}
