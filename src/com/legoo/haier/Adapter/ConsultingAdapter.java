package com.legoo.haier.Adapter;

import java.util.List;

import com.legoo.haier.R;
import com.legoo.haier.Adapter.Layout.ConsultingItemLayout;
import com.legoo.haier.Model.ConsultingModel;
import com.legoo.haier.Model.Base.ModelInterface;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConsultingAdapter extends BaseAdapter {
	
	private Context context;
	private List<ConsultingModel> modelList;
	private LayoutInflater inflater;
	private String userid;
	public ConsultingAdapter(Context context,List<ConsultingModel> modellist,String userid)
	{
		this.context = context;
		this.userid = userid;
		this.modelList = modellist;
	}
	/**
	 * ListView使用此方法确定列表数量
	 */
	public int getCount() {
		return modelList.size();
	}
	/**
	 * 当用户点击其中一项时，可以使用此方法获得这一项的数据
	 * position 表示索引位置
	 */
	public ModelInterface getItem(int position) {
		return modelList.get(position);
	}
	/**
	 * 每个列表项视图，可以分配一个ID值，
	 * ID可以使用数据的ID或可以使用索引值作为ID
	 */
	public long getItemId(int position) {
		return position;
	}
	/**
	 * 将数据，转换为列表项视图
	 * 参数convertView重复利用的列表项视图实例
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ConsultingItemLayout view = getConvertView(convertView);
		ConsultingModel model = (ConsultingModel)getItem(position);
		view.setMessage(model.getMessage());
//		View view =getConvertView(convertView);//重复利用 建议必须使用
//		view=inflater.inflate(R.layout.list_item_comment_total, parent);
//		ViewHolder vh =(ViewHolder)view.getTag();//重复使用
//		if(vh==null){
//			vh = new ViewHolder();
//			vh.text = (TextView) view.findViewById(R.id.textListItemConsulting);
//			view.setTag(vh);
//		}
//		ConsultingModel deom = modelList.get(position);
//		vh.text.setText(deom.getMessage());
		return view;
	}
	public ConsultingItemLayout getConvertView(View convertView)
	{
		return (convertView!=null&&convertView instanceof ConsultingItemLayout) ?
				(ConsultingItemLayout)convertView: new ConsultingItemLayout(context);
	}
}
//锁定一个视图内的多个控件，不用每次都findViewById
class ViewHolder{
	TextView text;
}
