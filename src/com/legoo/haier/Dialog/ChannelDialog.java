package com.legoo.haier.Dialog;

import java.util.ArrayList;
import java.util.List;
import com.legoo.haier.R;
import com.legoo.haier.Adapter.ChannelAdapter;
import com.legoo.haier.Archon.ListArchon;
import com.legoo.haier.Dialog.Base.BaseDialog;
import com.legoo.haier.Extension.UltraIdeal.BatchListView;
import com.legoo.haier.Model.ChannelModel;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @class Channel Dialog
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class ChannelDialog extends BaseDialog
{
	private static final String SELECT_NONE = "";
	
	private TextView _textTitle;
	private BatchListView _listView;
	
	private ListArchon _listArchon;
	private List<ChannelModel> _models;
	
	private OnItemSelectedListener _itemSelectedListener;
	
	private String _id = SELECT_NONE;
	private String _text;
	
	public ChannelDialog(Activity activity, String title, String[] arrays) 
	{
		super(activity, R.layout.dialog_channel, true);
		setWidthRatio(BaseDialog.RATIO_MIDDLE);
		
		_textTitle = (TextView) getView().findViewById(R.id.textDialogChannelTitle);
		_textTitle.setText(title);
		
		initList(activity, getModelListByArray(arrays));
	}
	
	public ChannelDialog(Activity activity, String title, List<ChannelModel> models) 
	{
		super(activity, R.layout.dialog_channel, true);
		setWidthRatio(BaseDialog.RATIO_MIDDLE);
		
		_textTitle = (TextView) getView().findViewById(R.id.textDialogChannelTitle);
		_textTitle.setText(title);
		
		initList(activity, models);
	}
	
	private void initList(Activity activity, List<ChannelModel> models)
	{
		_models = models;
		
		_listView = (BatchListView) getView().findViewById(R.id.listDialogChannelContent);
		
		_listArchon = new ListArchon(activity, _listView);
		_listArchon.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				ChannelModel model = (ChannelModel) _listArchon.getItem(position);
				_id = model.getID();
				_text = model.getTitle();
				if (model != null && _itemSelectedListener != null)
				{
					_itemSelectedListener.onItemSelected(model);
				}
				dismiss();
			}
		});
		_listArchon.setModel(new ChannelAdapter(activity, _listArchon.getListView()));
		_listArchon.fill(_models);
	}
	
	public void addAllView()
	{
		if (_listArchon != null)
		{
			ChannelAdapter adapter = (ChannelAdapter) _listArchon.getAdapter();
			_models.add(0, adapter.addAllView());
		}
	}
	
	private List<ChannelModel> getModelListByArray(String[] arrays)
	{
		List<ChannelModel> channelList = new ArrayList<ChannelModel>();
		int count = arrays.length;
		for (int i = 0; i < count; i++)
		{
			channelList.add(new ChannelModel(String.valueOf(i), arrays[i]));
		}
		return channelList;
	}
	
	public String getSelectID()
	{
		return _id;
	}
	
	public String getSelectValue()
	{
		return _id == SELECT_NONE ? "" : String.valueOf(_id);
	}
	
	public String getSelectText()
	{
		return _text;
	}
	
	public void setSelectID(String id)
	{
		_id = id;
		for (ChannelModel channel : _models)
		{
			if (_id.equals(channel.getID()) == true)
			{
				_text = channel.getTitle();
				break;
			}
		}
	}
	
	public void setOnItemSelectedListener(OnItemSelectedListener listener)
	{
		_itemSelectedListener = listener;
	}
	
	public static interface OnItemSelectedListener
	{
		public void onItemSelected(ChannelModel model);
	}
}
