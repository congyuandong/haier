package com.legoo.haier.Handler.Json;

import java.util.ArrayList;
import java.util.List;

import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Model List Json Handler
 * @author LeonNoW
 * @version 1.0
 * @date 2013-9-10
 */
public abstract class ModelListJsonHandler extends JsonHandler
{      	
	private List<ModelInterface> _modellist;
	
    private int _pageCount;
    private int _pageNumber;
    
	public List<ModelInterface> getModelList()
	{
		return _modellist;
	}
	 
	protected void add(ModelInterface model)
	{
		_modellist.add(model);
	}
	
	protected void initModelList()
	{
		_modellist = new ArrayList<ModelInterface>();
	}
	
	protected boolean hasModelList()
	{
		return (_modellist != null);
	}
	
    public int getPageCount()
    {
    	return _pageCount;
    }
    
    protected void setPageCount(int value)
    {
    	_pageCount = value;
    }
    
    public int getPageNumber()
    {
    	return _pageNumber;
    }
    
    protected void setPageNumber(int value)
    {
    	_pageNumber = value;
    }
    
    @Override
	public boolean isValid() 
	{
		return _modellist != null;
	}
    
	public ModelListJsonHandler() {}
} 