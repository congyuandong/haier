package com.legoo.haier.Archon;

import java.util.Collection;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Base.AdapterInterface;
import com.legoo.haier.Extension.UltraIdeal.BatchListView;
import com.legoo.haier.Model.Base.ModelInterface;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @class List Archon
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class ListArchon 
{
	private Activity _activity;
	
	private BatchListView _listView;
	
	public ListArchon(Activity activity, int listview)
	{
		_activity = activity;
		initList(listview);
		initFooter(0);
	}
	
	public ListArchon(Activity activity, View listview)
	{
		_activity = activity;
		_listView = (BatchListView) listview;
		initFooter(0);
	}
	
	public ListArchon(Activity activity, int listview, int footerlayout)
	{
		_activity = activity;
		initList(listview);
		initFooter(footerlayout);
	}

	public ListArchon(Activity activity, View listview, int footerlayout)
	{
		_activity = activity;
		_listView = (BatchListView) listview;
		initFooter(footerlayout);
	}
	
	private void initList(int listview)
	{
		_listView = (BatchListView) _activity.findViewById(listview);
	}
	
	private void initFooter(int footerlayout)
	{
		if (footerlayout == 0)
		{
			footerlayout = R.layout.list_item_footer;
		}
		_listView.setFooterLayout(footerlayout);
	}
	
	public BatchListView getListView()
	{
		return _listView;
	}
	
	public AdapterInterface<ModelInterface> getAdapter()
	{
		return _listView.getProtoAdapter();
	}
	
	public void setModel(AdapterInterface<ModelInterface> adapter)
	{
		_listView.setModel(adapter);
	}
	
	public ModelInterface getItem(int position)
	{
		return _listView.getModelItem(position);
	}
	
	public void notifyDataSetChanged()
	{
		getAdapter().notifyDataSetChanged();
	}
	
	public boolean isListViewItem(int position)
	{
		return _listView.isListViewItem(position);
	}
	
	public int getCount()
	{
		return getAdapter().getModelCount();
	}
	
	public void refresh()
	{
		_listView.refresh();
	}
	
	public int fill(Collection<? extends ModelInterface> modelList)
	{
		getAdapter().clear();
		int size = (modelList == null ? 0 : modelList.size());
		getAdapter().addAll(modelList);
		overLoad();
		return size;
	}
	
	public void overLoad()
	{
		if (getCount() == 0)
		{
			_listView.doNoticeFooter();
		}
		else
		{
			_listView.removeFooter();
		}
		_listView.over();
	}
	
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		_listView.setOnItemClickListener(listener);
	}
}
