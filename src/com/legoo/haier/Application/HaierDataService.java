package com.legoo.haier.Application;

import com.legoo.haier.R;


/**
 * @class Hospital Data Service
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HaierDataService
{
	private Haier _application;
	
	private String _base;
	
	
	
	public HaierDataService(Haier application)
	{
		this._application = application;
		initDataService();
	}
	
	private void initDataService()
	{
		_base = _application.getString(R.string.dataservice_base);		
	}
	
	
	private String combineUrl(String uri)
	{
		return new StringBuilder(_base).append('/').append(uri).toString();
	}
}
