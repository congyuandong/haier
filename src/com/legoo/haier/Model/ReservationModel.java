package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Reservation Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-17
 */
public class ReservationModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = -2740932845879912893L;
	
	private String _id;
	private String _department;
	private String _expert;
	private String _date;
	private String _time;
	private String _patient;
	private String _idcard;
	private String _telephone;
	private String _sex;
	private String _credential;
	private String _cost;
	private String _address;
	public String getID()
	{
		return _id;
	}
	
	public void setID(String id)
	{
		_id = id;
	}
	
	public String getDepartment()
	{
		return _department;
	}
	
	public void setDepartment(String department)
	{
		_department = department;
	}
	
	public String getExpert()
	{
		return _expert;
	}
	
	public void setExpert(String expert)
	{
		_expert = expert;
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
	
	public String getPatient()
	{
		return _patient;
	}
	
	public void setPatient(String patient)
	{
		_patient = patient;
	}
	
	public String getIDCard()
	{
		return _idcard;
	}
	
	public void setIDCard(String idcard)
	{
		_idcard = idcard;
	}
	
	public String getTelephone()
	{
		return _telephone;
	}
	
	public void setTelephone(String telephone)
	{
		_telephone = telephone;
	}
	
	public String getSex()
	{
		return _sex;
	}
	
	public void setSex(String sex)
	{
		_sex = sex;
	}
	
	public String getCredential()
	{
		return _credential;
	}
	
	public void setCredential(String credential)
	{
		_credential = credential;
	}

	public String getCost()
	{
		return _cost;
	}
	
	public void setCost(String cost)
	{
		_cost = cost;
	}
	
	public String getAddress()
	{
		return _address;
	}
	
	public void setAddress(String address)
	{
		_address = address;
	}
	
	
	public ReservationModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_department = null;
		_expert = null;
		_date = null;
		_time = null;
		_patient = null;
		_idcard = null;
		_telephone = null;
		_sex = null;
		_credential = null;
		_cost = null;
	}
}
