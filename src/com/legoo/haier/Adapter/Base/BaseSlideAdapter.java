package com.legoo.haier.Adapter.Base;

import java.util.Collection;
import java.util.List;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import com.legoo.haier.Model.Base.ModelInterface;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @class Base Slide Adapter
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public abstract class BaseSlideAdapter extends PagerAdapter implements AdapterInterface<ModelInterface>
{
	private Context _context;
	private List<ModelInterface> _models;
	private List<View> _views;
	private EventInterface _event;
	private OnItemClickListener _itemClickListener;
	
	public BaseSlideAdapter(Context context, List<ModelInterface> list)
	{
		super();
		_context = context;
		_models = list;
		_views = getViews();
	}
	
	public Context getContext()
	{
		return _context;
	}
	
	@Override
	public int getCount() 
	{
		return getModelCount();
	}

	@Override
	public int getModelCount() 
    {
		return _models != null ? _models.size() : 0;
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) 
	{
		return arg0 == arg1;
	}

	@Override  
    public int getItemPosition(Object object) 
	{  
        return super.getItemPosition(object);
    }  

    @Override  
    public void destroyItem(View arg0, int arg1, Object arg2) 
    {  
        ((ViewPager) arg0).removeView(getView(arg1));  
    }  

    @Override  
    public Object instantiateItem(View arg0, int arg1) 
    {  
    	View view = getView(arg1);
    	view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				if (_itemClickListener != null)
				{
					_itemClickListener.onItemClick(v);
				}
			}
		});
    	((ViewPager) arg0).addView(view);
        return view;  
    }  
    
    public View getView(int position)
    {
    	return _views.get(position);
    }
    
    public abstract List<View> getViews();
    
    public List<ModelInterface> getAll()
    {
    	return _models;
    }
    
    @Override
    public ModelInterface get(int arg1)
    {
    	return _models.get(arg1);
    }
    
    @Override
	public void add(ModelInterface model) 
    {
    	_models.add(model);
	}

	@Override
	public void addAll(Collection<? extends ModelInterface> list) 
	{
		_models.addAll(list);
	}
	
	@Override
    public void clear()
	{
		ModelInterface model;
		int count = getCount();
		for (int i = 0; i < count; i++)
		{
			model = get(i);
			model.dispose();
			model = null;
		}
		_models.clear();
		_views.clear();
	}
	
	@Override
	public int getPageCount() 
	{
		return 1;
	}

	@Override
	public void setPageCount(int value) { }

	@Override
	public int getPageNumber() 
	{
		return 1;
	}
	
	@Override
	public void setPageNumber(int value) { }
	
	@Override
	public void setLast(int last) { }

	@Override
	public void bind(View view) { }

	public void setOnItemClickListener(OnItemClickListener listener)
	{
		_itemClickListener = listener;
	}
	
	public void setEvent(EventInterface event)
    {
    	_event = event;
    }
    
    public EventInterface getEvent()
    {
    	return _event;
    }
	
	@Override
	public void dispose() { }
	
	public static interface OnItemClickListener
	{
		public void onItemClick(View view);
	}
}
