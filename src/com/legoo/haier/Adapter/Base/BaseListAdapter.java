package com.legoo.haier.Adapter.Base;

import java.util.Collection;
import java.util.List;

import com.legoo.haier.Application.Hospital;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import com.legoo.haier.Model.Base.ModelInterface;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * @class Base List Adapter
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-5
 */
public abstract class BaseListAdapter extends ArrayAdapter<ModelInterface> implements AdapterInterface<ModelInterface>
{ 
    private int _pageCount;
    private int _pageNumber;
    private int _last;
    private EventInterface _event;
    private ListView _listView;
    
    public BaseListAdapter(Context context, List<ModelInterface> list, ListView listview) 
    {   
    	super(context, 0, list); 
    	_listView = listview;
    	list = null;
    	context = null;
    	listview = null;
    	_pageCount = 0;
    	_pageNumber = 0;
    }   

    @TargetApi(VERSION_CODES.HONEYCOMB)
	public void addAll(Collection<? extends ModelInterface> collection)
    {
    	if (VERSION.SDK_INT < VERSION_CODES.HONEYCOMB)
    	{
    		for (ModelInterface model : collection)
    		{
    			super.add(model);
    		}
    	}
    	else 
    	{
			super.addAll(collection);
		}
    }
    
    @Override
	public ModelInterface get(int position) 
	{
		return (ModelInterface) _listView.getAdapter().getItem(position);
	}
	
    @Override
	public int getModelCount() 
    {
		return getCount();
	}
    
    @Override
	public void bind(View view) 
	{
		((ListView) view).setAdapter(this);
	}
    
    @Override
    public void clear()
	{
		ModelInterface model;
		int count = getCount();
		for (int i = 0; i < count; i++)
		{
			model = getItem(i);
			model.dispose();
			model = null;
		}
		super.clear();
	}
    
    public void setPageCount(int value)
    {
    	_pageCount = value;
    }
    
    public int getPageCount()
    {
    	return _pageCount;
    }
    
    public void setPageNumber(int value)
    {
    	_pageNumber = value;
    }
    
    public int getPageNumber()
    {
    	return _pageNumber;
    }
    
    public void setLast(int last)
    {
    	_last = last;
    }
    
    public int getLast()
    {
    	return _last;
    }
    
    protected void doAnimation(View view, int position)
    {
    	if (position >= getLast())
        {
    		doAnimation(view);
        	setLast(getCount());
        }
    }
    
    protected void doAnimation(View view) 
    {
    	Hospital.getInstance().getAnimation().startFade(view);
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
    public void dispose()
    {

    }
}
