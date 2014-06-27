package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Cost Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-17
 */
public class CostModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = 4613850974644102959L;
	
	private String _date;
	private String _name;
	private String _amount;
	
	public String getDate()
	{
		return _date;
	}
	
	public void setDate(String date)
	{
		_date = date;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getAmount()
	{
		return _amount;
	}
	
	public void setAmount(String amount)
	{
		_amount = amount;
	}
	
	
	public CostModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_date = null;
		_name = null;
		_amount = null;
	}
}
