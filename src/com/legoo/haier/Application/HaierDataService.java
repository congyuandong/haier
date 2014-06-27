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
	private String login;
	private String register;
	private String sampleShow;
	private String bindTV;
	private String repairTV;
	private String consulting;
	private String consultingList;
	
	
	
	
	public HaierDataService(Haier application)
	{
		this._application = application;
		initDataService();
		login = "";
		register = "";
		sampleShow = "";
		bindTV = "";
		repairTV = "";
		consulting = "";
		consultingList = "";
	}
	
	private void initDataService()
	{
		_base = _application.getString(R.string.dataservice_base);		
	}
	
	public String postLogin()
	{
		return combineUrl(login);
	}
	public String postRegister()
	{
		return combineUrl(register);		
	}
	public String getHelp()
	{
		return combineUrl(String.format(sampleShow, 1));
	}
	public String getQuestion()
	{
		return combineUrl(String.format(sampleShow, 1));
	}
	public String getRecommend()
	{
		return combineUrl(String.format(sampleShow, 1));
	}
	public String postBindTV()
	{
		return combineUrl(bindTV);		
	}
	public String postRepairTV()
	{
		return combineUrl(repairTV);		
	}
	public String postConsulting()
	{
		return combineUrl(consulting);		
	}
	public String postConsultingList()
	{
		return combineUrl(consultingList);		
	}
	
	private String combineUrl(String uri)
	{
		return new StringBuilder(_base).append('/').append(uri).toString();
	}
}
