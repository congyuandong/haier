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
	
	private String _getHome;
	private String _getDepartments;
	private String _getExperts;
	private String _getExpertFilter;
	private String _getExpert;
	private String _getSchedules;
	private String _getServiceCategories;
	private String _getServiceLinks;
	private String _getServiceDetail;
	private String _getTcmCategories;
	private String _getTcmLinks;
	private String _getTcmDetail;
	private String _getAgreement;
	private String _getContact;
	private String _getQuestionList;
	private String _getQuestionDetail;
	private String _getUpdate;
	
	private String _postReserve;
	private String _postAuthenticate;
	private String _postModify;
	private String _postPasswordChange;
	private String _postRegister;
	private String _postCosts;
	private String _postCostsDaily;
	private String _postReports;
	private String _postReportsDetail;
	private String _postReportsDetailURL;
	private String _postReservations;
	private String _postReservationCancel;
	private String _postContacts;
	private String _postContactsUpdate;
	private String _postContactsDelete;
	private String _postContactsAdd;
	private String _postDrugs;
	private String _postVerification;
	private String _postFeedbackList;
	private String _postFeedbackAdd;
	private String _postFeedbackDelete;
	private String _postFeedbackEdit;
	private String _postQueryLimit;
//	private String _postCostVerification;
//	private String _postReportsVerification;
//	private String _postExpertConfirmVerification;
	
	
	public HaierDataService(Haier application)
	{
		this._application = application;
		initDataService();
	}
	
	private void initDataService()
	{
		_base = _application.getString(R.string.dataservice_base);		
		
		_getHome = _application.getString(R.string.dataservice_get_home);
		_getDepartments = _application.getString(R.string.dataservice_get_departments);
		_getExperts = _application.getString(R.string.dataservice_get_experts);
		_getExpertFilter = _application.getString(R.string.dataservice_get_expert_filter);
		_getExpert = _application.getString(R.string.dataservice_get_expert);
		_getSchedules = _application.getString(R.string.dataservice_get_schedules);
		_getServiceCategories = _application.getString(R.string.dataservice_get_service_categories);
		_getServiceLinks = _application.getString(R.string.dataservice_get_service_links);
		_getServiceDetail = _application.getString(R.string.dataservice_get_service_detail);
		_getTcmCategories = _application.getString(R.string.dataservice_get_tcm_categories);
		_getTcmLinks = _application.getString(R.string.dataservice_get_tcm_links);
		_getTcmDetail = _application.getString(R.string.dataservice_get_tcm_detail);
		_getAgreement = _application.getString(R.string.dataservice_get_agreement);
		_getContact = _application.getString(R.string.dataservice_get_contact);
		_getQuestionList = _application.getString(R.string.dataservice_get_question_list);
		_getQuestionDetail = _application.getString(R.string.dataservice_get_question_detail);
		_getUpdate = _application.getString(R.string.dataservice_get_update);
		
		_postReserve = _application.getString(R.string.dataservice_post_reserve);
		_postAuthenticate = _application.getString(R.string.dataservice_post_authenticate);
		_postModify = _application.getString(R.string.dataservice_post_modify);
		_postPasswordChange = _application.getString(R.string.dataservice_post_password_change);
		_postRegister = _application.getString(R.string.dataservice_post_register);
		_postCosts = _application.getString(R.string.dataservice_post_costs);
		_postCostsDaily = _application.getString(R.string.dataservice_post_costs_daily);
		_postReports = _application.getString(R.string.dataservice_post_reports);
		_postReportsDetail = _application.getString(R.string.dataservice_post_reports_detail);
		_postReportsDetailURL = _application.getString(R.string.dataservice_post_reports_detail_url);
		_postReservations = _application.getString(R.string.dataservice_post_reservations);
		_postReservationCancel = _application.getString(R.string.dataservice_post_reservation_cancel);
		_postContacts = _application.getString(R.string.dataservice_post_contacts);
		_postContactsUpdate = _application.getString(R.string.dataservice_post_contacts_update);
		_postContactsDelete = _application.getString(R.string.dataservice_post_contacts_delete);
		_postContactsAdd = _application.getString(R.string.dataservice_post_contacts_add);
		_postDrugs = _application.getString(R.string.dataservice_post_drugs);
		_postVerification = _application.getString(R.string.dataservice_post_verification);
		_postFeedbackList = _application.getString(R.string.dataservice_post_feedback);
		_postFeedbackAdd = _application.getString(R.string.dataservice_post_feedback_add);
		_postFeedbackDelete = _application.getString(R.string.dataservice_post_feedback_delete);
		_postFeedbackEdit = _application.getString(R.string.dataservice_post_feedback_update);
		_postQueryLimit = _application.getString(R.string.dataservice_post_query_limit);
//		_postCostVerification = _application.getString(R.string.dataservice_post_cost_verification);
//		_postReportsVerification = _application.getString(R.string.dataservice_post_reports_verification);
//		_postExpertConfirmVerification = _application.getString(R.string.dataservice_post_expert_confirm_verification);
	}
	
	public String getHome()
	{
		return combineUrl(_getHome);
	}
	
	public String getDepartments(String id)
	{
		return combineUrl(String.format(_getDepartments, id));
	}

	public String getExperts(String department, String title, String profession, String week, int page,String name,String appointment)
	{
		return combineUrl(String.format(_getExperts, department, title, profession, week, page, name, appointment));
	}
	
	public String getExpertFilter()
	{
		return combineUrl(_getExpertFilter);
	}
	
	public String getExpert(String id)
	{
		return combineUrl(String.format(_getExpert, id));
	}
	
	public String getSchedules(String id)
	{
		return combineUrl(String.format(_getSchedules, id));
	}
	
	public String getServiceCategories()
	{
		return combineUrl(_getServiceCategories);
	}
	
	public String getServiceLinks(String id)
	{
		return combineUrl(String.format(_getServiceLinks, id));
	}
	
	public String getServiceDetail(String id)
	{
		return combineUrl(String.format(_getServiceDetail, id));
	}
	
	public String getTcmCategories()
	{
		return combineUrl(_getTcmCategories);
	}
	
	public String getTcmLinks(String id)
	{
		return combineUrl(String.format(_getTcmLinks, id));
	}
	
	public String getTcmDetail(String id)
	{
		return combineUrl(String.format(_getTcmDetail, id));
	}

	public String getAgreement()
	{
		return combineUrl(_getAgreement);
	}
	
	public String getContact()
	{
		return combineUrl(_getContact);
	}

	public String getQuestionList()
	{
		return combineUrl(_getQuestionList);
	}
	
	public String getQuestionDetail(String id)
	{
		return combineUrl(String.format(_getQuestionDetail,id));
	}
	
	public String getUpdate()
	{
		return combineUrl(_getUpdate);
	}
		
	public String getReserve()
	{
		return combineUrl(_postReserve);
	}
	
	public String getAuthenticate()
	{
		return combineUrl(_postAuthenticate);
	}
	
	public String getModify()
	{
		return combineUrl(_postModify);
	}
	
	public String getPasswordChange()
	{
		return combineUrl(_postPasswordChange);
	}
	
	public String getRegister()
	{
		return combineUrl(_postRegister);
	}
	
	
	public String getCosts()
	{
		return combineUrl(_postCosts);
	}
	public String getCostsDaily()
	{
		return combineUrl(_postCostsDaily);
	}
	
	public String getReports()
	{
		return combineUrl(_postReports);
	}
	
	public String getReportsDetail()
	{
		return combineUrl(_postReportsDetail);
	}
	
	public String getReportsDetailURL(String appid,String reportid)
	{
		return combineUrl(String.format(_postReportsDetailURL,appid,reportid));
	}
	
	public String getReportsDetailURL(String url)
	{
		return combineUrl(url);
	}
	
	public String getReservations()
	{
		return combineUrl(_postReservations);
	}
	
	public String getReservationCancel()
	{
		return combineUrl(_postReservationCancel);
	}
	
	public String getContacts()
	{
		return combineUrl(_postContacts);
	}
	public String getContactsUpdate()
	{
		return combineUrl(_postContactsUpdate);
	}
	
	public String getContactsDelete()
	{
		return combineUrl(_postContactsDelete);
	}
	
	public String getContactsAdd()
	{
		return combineUrl(_postContactsAdd);
	}

	public String getFeedbackList()
	{
		return combineUrl(_postFeedbackList);
	}

	public String getFeedbackAdd()
	{
		return combineUrl(_postFeedbackAdd);
	}

	public String getFeedbackDelete()
	{
		return combineUrl(_postFeedbackDelete);
	}

	public String getFeedbackEdit()
	{
		return combineUrl(_postFeedbackEdit);
	}
	public String getDrugs()
	{
		return combineUrl(_postDrugs);
	}
	
	public String getVerification()
	{
		return combineUrl(_postVerification);
	}
	
	public String getQueryLimit() {
		return combineUrl(_postQueryLimit);
	}
//	public String getCostVerification()
//	{
//		return combineUrl(_postCostVerification);
//	}
//	
//	public String getReportsVerification()
//	{
//		return combineUrl(_postReportsVerification);
//	}
//	
//	public String getExpertConfirmVerification()
//	{
//		return combineUrl(_postExpertConfirmVerification);
//	}
	
	private String combineUrl(String uri)
	{
		return new StringBuilder(_base).append('/').append(uri).toString();
	}
}
