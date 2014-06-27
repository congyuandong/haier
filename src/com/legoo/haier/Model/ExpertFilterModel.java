package com.legoo.haier.Model;

import java.util.List;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Channel Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class ExpertFilterModel extends BaseModel implements ModelInterface
{
	private List<ChannelModel> _titles;
	private List<ChannelModel> _professions;
	private List<ChannelModel> _weeks;
	private List<ChannelModel> _appointments;
	
	public List<ChannelModel> getTitles()
	{
		return _titles;
	}
	
	public void setTitles(List<ChannelModel> titles)
	{
		_titles = titles;
	}
	
	public List<ChannelModel> getProfessions()
	{
		return _professions;
	}
	
	public void setProfessions(List<ChannelModel> professions)
	{
		_professions = professions;
	}
	
	public List<ChannelModel> getWeeks()
	{
		return _weeks;
	}
	
	public void setWeeks(List<ChannelModel> weeks)
	{
		_weeks = weeks;
	}

	public List<ChannelModel> getAppointments()
	{
		return _appointments;
	}
	
	public void setAppointments(List<ChannelModel> appointments)
	{
		_appointments = appointments;
	}
	
	public ExpertFilterModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_titles = null;
		_professions = null;
		_weeks = null;
		_appointments = null;
	}
}
