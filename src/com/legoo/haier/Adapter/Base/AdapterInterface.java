package com.legoo.haier.Adapter.Base;

import java.util.Collection;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import android.view.View;

/**
 * @class Adapter Interface
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public interface AdapterInterface<T>
{
	public int getPageCount();
	
	public void setPageCount(int count);
	
	public int getPageNumber();
	
	public void setPageNumber(int number);
	
	public int getModelCount();
	
	public void setLast(int last);
    
    public void add(T model);
    
    public void addAll(Collection<? extends T> modelList);
    
    public T get(int position);
    
    public void notifyDataSetChanged();
    
    public void clear();
    
    public void bind(View view);
    
    public void setEvent(EventInterface event);
    
    public EventInterface getEvent();
    
    public void dispose();
	
}
