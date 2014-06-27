package com.legoo.haier.Model;

import java.util.List;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Expert Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-11
 */
public class ExpertModel extends BaseModel implements ModelInterface
{
	private String _id;
	private String _name;
	private String _introduction;
	private String _photo;
	private String _title;
	private String _department;
	private String _position;
	private String _profession;
	private String _hisid;
	
	private List<ScheduleModel> _schedules;
	
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
	
	public String getIntroduction()
	{
		return _introduction;
	}
	
	public void setIntroduction(String introduction)
	{
		_introduction = introduction;
	}
	
	public String getPhoto()
	{
		return _photo;
	}
	
	public void setPhoto(String photo)
	{
		_photo = photo;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public String getDepartment()
	{
		return _department;
	}
	
	public void setDepartment(String department)
	{
		_department = department;
	}
	
	public String getPosition()
	{
		return _position;
	}
	
	public void setPosition(String position)
	{
		_position = position;
	}
	
	public String getProfession()
	{
		return _profession;
	}
	
	public void setProfession(String profession)
	{
		_profession = profession;
	}
	
	public List<ScheduleModel> getSchedules()
	{
		return _schedules;
	}
	
	public void setSchedules(List<ScheduleModel> schedules)
	{
		_schedules = schedules;
	}
	
	public ExpertModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_id = null;
		_name = null;
		_introduction = null;
		_photo = null;
		_title = null;
		_department = null;
		_position = null;
		_profession = null;
	}

	public String getHisid() {
		return _hisid;
	}

	public void setHisid(String _hisid) {
		this._hisid = _hisid;
	}
}
