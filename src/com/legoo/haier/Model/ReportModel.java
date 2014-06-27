package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Report Model
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-17
 */
public class ReportModel extends BaseModel implements ModelInterface, Serializable
{
	private static final long serialVersionUID = -7806260949795745577L;
	
	private String _reportId;
	private String _appId;
	private String _name;
	private String _date;
	private String _projectName;
	private String _url;
	
	
	public String getReportId() {
		return _reportId;
	}

	public void setReportId(String _reportId) {
		this._reportId = _reportId;
	}

	public String getAppId() {
		return _appId;
	}

	public void setAppId(String _appId) {
		this._appId = _appId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getDate()
	{
		return _date;
	}
	
	public void setDate(String date)
	{
		_date = date;
	}
	
	
	public String getProjectName() {
		return _projectName;
	}

	public void setProjectName(String _projectName) {
		this._projectName = _projectName;
	}

	public ReportModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_reportId = null;
		_appId = null;
		_name = null;
		_date = null;
		_projectName = null;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String _url) {
		this._url = _url;
	}
}
