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
	 * ListViewʹ�ô˷���ȷ���б�����
	 */
	public int getCount() {
		return modelList.size();
	}
	/**
	 * ���û��������һ��ʱ������ʹ�ô˷��������һ�������
	 * position ��ʾ����λ��
	 */
	public ModelInterface getItem(int position) {
		return modelList.get(position);
	}
	/**
	 * ÿ���б�����ͼ�����Է���һ��IDֵ��
	 * ID����ʹ�����ݵ�ID�����ʹ������ֵ��ΪID
	 */
	public long getItemId(int position) {
		return position;
	}
	/**
	 * �����ݣ�ת��Ϊ�б�����ͼ
	 * ����convertView�ظ����õ��б�����ͼʵ��
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ConsultingItemLayout view = getConvertView(convertView);
		ConsultingModel model = (ConsultingModel)getItem(position);
		view.setMessage(model.getMessage());
//		View view =getConvertView(convertView);//�ظ����� �������ʹ��
//		view=inflater.inflate(R.layout.list_item_comment_total, parent);
//		ViewHolder vh =(ViewHolder)view.getTag();//�ظ�ʹ��
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
//����һ����ͼ�ڵĶ���ؼ�������ÿ�ζ�findViewById
class ViewHolder{
	TextView text;
}
