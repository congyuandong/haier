package com.legoo.haier.Model;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Category Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class CategoryModel extends BaseModel implements ModelInterface
{
	private String _id;
	private String _name;
	
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public CategoryModel()
	{
		
	}
	
	public CategoryModel(String id, String name)
	{
		_id = id;
		_name = name;
	}
	
	@Override
	public void dispose()
	{
		_name = null;
	}
}
