package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Report Model
 * @author MLZX
 * @version 1.0
 * @date 2014-05-19
 */
public class ReportDetailModel extends BaseModel implements ModelInterface, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1374607977939558479L;

	private String _URL;
	private String _content;
	
	public String getURL()
	{
		return _URL;
	}
	
	public void setURL(String url)
	{
		_URL = url;
	}
	
	public ReportDetailModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
		_URL = null;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String _content) {
		this._content = _content;
	}
}
