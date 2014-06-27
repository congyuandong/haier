package com.legoo.haier.Model;

import java.io.Serializable;

import com.legoo.haier.Model.Base.BaseModel;
import com.legoo.haier.Model.Base.ModelInterface;

/**
 * @class Contact Model
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class UpdateModel extends BaseModel implements ModelInterface, Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3824543516139913655L;
	private String _id;
	private String _url;
	private String _versionName;
	private String _versionCode;
	
	public UpdateModel()
	{
		
	}
	
	@Override
	public void dispose()
	{
	}

	public String getID() {
		return _id;
	}

	public void setID(String _id) {
		this._id = _id;
	}

	public String getURL() {
		return _url;
	}

	public void setURL(String _url) {
		this._url = _url;
	}

	public String getVersionName() {
		return _versionName;
	}

	public void setVersionName(String _versionName) {
		this._versionName = _versionName;
	}

	public String getVersionCode() {
		return _versionCode;
	}

	public void setVersionCode(String _versionCode) {
		this._versionCode = _versionCode;
	}
}
