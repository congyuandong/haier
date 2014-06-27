package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.TabViewActivity;
import com.legoo.haier.Adapter.LinkAdapter;
import com.legoo.haier.Archon.RefreshListArchon;
import com.legoo.haier.Archon.RefreshListArchon.OnLoadDataListener;
import com.legoo.haier.Model.LinkModel;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @class Service Tab Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class LinkListActivity extends TabViewActivity
{
	public static final String EXTRA_ID = "EXTRA_ID";
	
	private RefreshListArchon _listArchon;
	
	private String _id;
	private int _type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.list_pull_refresh);
		initView();
		loadData();
	}

	private void initView()
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
					clickItem((LinkModel) _listArchon.getItem(position));
				}
			}
		});
		_listArchon.setRefreshing(true);
		_listArchon.setModel(new LinkAdapter(this, _listArchon.getListView()));
		_listArchon.setDialogBuilder(getParent());
	}
	
	private void loadData()
	{
//		_listArchon.setAsyncTask(_type == LinkCategoryActivity.TYPE_SERVICE ? new ServiceLinksAsyncTask(_id) : new TcmLinksAsyncTask(_id));
		_listArchon.executeAsyncTask();
	}
	
	private void clickItem(LinkModel link)
	{
		startActivity(new Intent(LinkListActivity.this, LinkDetailActivity.class)
				.putExtra(LinkDetailActivity.EXTRA_TYPE, _type)
				.putExtra(LinkDetailActivity.EXTRA_KIND, link.getIsExternal() == true ? LinkDetailActivity.KIND_LINK : LinkDetailActivity.KIND_TASK)
				.putExtra(LinkDetailActivity.EXTRA_ID, link.getID())
				.putExtra(LinkDetailActivity.EXTRA_TITLE, link.getTitle())
				.putExtra(LinkDetailActivity.EXTRA_URL, link.getUrl()));
	}
	
	private void verifyExtras()
	{
		_type = getIntent().getIntExtra(LinkCategoryActivity.EXTRA_TYPE, LinkCategoryActivity.TYPE_SERVICE);
		_id = getIntent().getStringExtra(EXTRA_ID);
	}
}
