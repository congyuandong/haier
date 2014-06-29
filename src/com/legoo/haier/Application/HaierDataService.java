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
	private String repairTVResult;
	private String consulting;
	private String consultingList;
	
	
	
	
	public HaierDataService(Haier application)
	{
		this._application = application;
		initDataService();
		login = "customer/loginCustomer.action";
		register = "customer/regeditCustomer.action";
		sampleShow = "binding/ShowMsg.action?info_id=%1$s";
		bindTV = "binding/bindMac.action";
		repairTV = "repair/shenqingBaoxiu.action";
		repairTVResult = "repair/lookResult.action";
		consulting = "onlinemessage/addOnlineMess.action";
		consultingList = "onlinemessage/searchOnlineMess.action";
	}
	
	private void initDataService()
	{
		_base = "http://222.171.251.98:8090/Haier";		
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
		return combineUrl(String.format(sampleShow, "1"));
	}
	public String getQuestion()
	{
		return combineUrl(String.format(sampleShow, "2"));
	}
	public String getRecommend()
	{
		return combineUrl(String.format(sampleShow, "3"));
	}
	public String postBindTV()
	{
		return combineUrl(bindTV);		
	}
	public String postRepairTV()
	{
		return combineUrl(repairTV);		
	}
	public String postRepairTVResult()
	{
		return combineUrl(repairTVResult);
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
