package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Schedule Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-11
 */
public class ScheduleModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = -7325097615684741669L;
	
	private String _id;
	private String _date;
	private String _time;
	private String _number;
	private String _departmentName;
	private String _count;
	private String _department;
	private boolean _isfull;
	
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	public String getDate()
	{
		return _date;
	}
	
	public void setDate(String date)
	{
		_date = date;
	}
	
	public String getTime()
	{
		return _time;
	}
	
	public void setTime(String time)
	{
		_time = time;
	}
	
	public String getNumber()
	{
		return _number;
	}
	
	public void setNumber(String number)
	{
		_number = number;
	}
	
	public boolean getIsFull()
	{
		return _isfull;
	}
	
	public void setIsFull(boolean isfull)
	{
		_isfull = isfull;
	}
	
	public ScheduleModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_id = null;
		_time = null;
		_number = null;
	}

	public String getDepartmentName() {
		return _departmentName;
	}

	public void setDepartmentName(String departmentName) {
		_departmentName = departmentName;
	}

	public String getDepartment() {
		return _department;
	}

	public void setDepartment(String department) {
		_department = department;
	}
	public String getCount()
	{
		return _count;
	}
	public void setCount(String count)
	{
		_count = count;
	}
}
