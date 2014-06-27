package com.legoo.haier.Adapter;

import java.util.ArrayList;
import java.util.List;
import com.legoo.haier.Adapter.Base.BaseSlideAdapter;
import com.legoo.haier.Adapter.Layout.PagerItemLayout;
import com.legoo.haier.Model.PagerModel;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.view.View;

/**
 * @class Pager Adapter
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public class PagerAdapter extends BaseSlideAdapter
{

	public PagerAdapter(Context context, List<ModelInterface> list)
	{
		super(context, list);
	}

	@Override
	public List<View> getViews() 
	{
		List<View> views = new ArrayList<View>();
		PagerItemLayout view;
		PagerModel pager;
		for (ModelInterface model : getAll())
		{
			pager = (PagerModel) model;
			view = new PagerItemLayout(getContext());
			view.setImage(pager.getImage());
			views.add(view);
		}
		return views;
	}
	
	
}
