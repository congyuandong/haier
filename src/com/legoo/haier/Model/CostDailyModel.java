package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Cost Model
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-06
 */
public class CostDailyModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = 4613850974644102959L;
	
	private String _date;
	private String _name;
	private String _drugsName;
	private String _specifications;
	private String _form;
	private String _count;
	private String _price;
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
	
	public String getDrugsName()
	{
		return _drugsName;
	}
	
	public void setDrugsName(String drugsName)
	{
		_drugsName = drugsName;
	}
	
	public String getSpecifications()
	{
		return _specifications;
	}
	
	public void setSpecifications(String specifications)
	{
		_specifications = specifications;
	}
	
	public String getForm()
	{
		return _form;
	}
	
	public void setForm(String form)
	{
		_form = form;
	}
	
	public String getCount()
	{
		return _count;
	}
	
	public void setCount(String count)
	{
		_count = count;
	}
	
	public String getPrice()
	{
		return _price;
	}
	
	public void setPrice(String price)
	{
		_price = price;
	}
	public String getAmount()
	{
		return _amount;
	}
	
	public void setAmount(String amount)
	{
		_amount = amount;
	}
	
	public CostDailyModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_date = null;
		_name = null;
		_drugsName = null;
		_specifications = null;
		_form = null;
		_count = null;
		_price = null;
		_amount = null;
	}
}
