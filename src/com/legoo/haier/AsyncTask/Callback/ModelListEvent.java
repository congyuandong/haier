package com.legoo.haier.AsyncTask.Callback;

import java.util.Collection;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Model List Event
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class ModelListEvent extends JsonEvent
{
	private static final long serialVersionUID = -213729819171995613L;

	private Collection<ModelInterface> _modellist;

	private int _pageCount;
	private int _pageNumber;
	
	public ModelListEvent(Object source)
    {
        super(source);
        source = null;
    }
	
	public void setModelList(Collection<ModelInterface> modellist)
	{
		_modellist = modellist;
	}
	
	public Collection<ModelInterface> getModelList()
	{
		return _modellist;
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
	
	@Override
	public void dispose()
	{
		_modellist = null;
	}
}
