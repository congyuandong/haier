package com.legoo.haier.Application;

import java.util.Random;

import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.UpdateAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Extension.ApplicationUtils;
import com.legoo.haier.Model.UpdateModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * @class Hospital Application
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class Haier extends Application
{
	public static final String TAG = "Hospital";
	
	private static Haier _instance;
	
	private HaierAnimation _animation;
	private HaierCache _cache;
	private HaierDataService _dataService;
	private HaierPreferences _preferences;
	private HaierSettings _settings;
	private HaierToast _toast;
	private HaierUser _user;
	
	public static String getVerificationCode()
	{
		String verificationCode = "";
		String[] array = {"0","1","2","3","4","5","6","7","8","9"};
		Random rand = new Random();
		for(int i=0;i<6;i++)
		{
			int index = rand.nextInt(9);
			verificationCode+=array[index];
		}
		return verificationCode;
	}
	public static Haier getInstance()
	{
		return _instance;
	}
	

	public void checkUpdate(Context content,final Activity activity)
	{
		TaskArchon updateTaskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_GET);
		updateTaskArchon.setConfirmEnabled(false);
		updateTaskArchon.setWaittingEnabled(false);
		updateTaskArchon.setOnLoadedListener(new OnLoadedListener()
		{
			@Override
			public void OnLoaded(final JsonEvent event) {
				// TODO Auto-generated method stub
				UpdateModel model = (UpdateModel)((ModelEvent)event).getModel();
				if (ApplicationUtils.isCompareVersionNewest(model.getVersionName()) == true)
				{
					ApplicationUtils.showUpdateDialog(activity, 
							model.getVersionName(), model.getURL());
				}			
			}
		});
		updateTaskArchon.executeAsyncTask(new UpdateAsyncTask());
	}
	
	public HaierAnimation getAnimation()
	{
		return _animation;
	}
	
	public HaierCache getCache()
	{
		return _cache;
	}
	
	public HaierDataService getDataService()
	{
		return _dataService;
	}
	
	public HaierPreferences getPreferences()
	{
		return _preferences;
	}
	
	public HaierSettings getSettings()
	{
		return _settings;
	}
	
	public HaierToast getToast()
	{
		return _toast;
	}
	
	public HaierUser getUser()
	{
		return _user;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		_instance = this;
		
		initPreferences();
		initSetting();
		initDataService();
		initToast();
		initUser();
		initCache();
		initAnimation();
	}
	
	private void initSetting()
	{
		_settings = new HaierSettings(this);
	}
	
	private void initCache()
	{
		_cache = new HaierCache();
	}
	
	private void initPreferences()
	{
		_preferences = new HaierPreferences(this);
	}
	
	private void initDataService()
	{
		_dataService = new HaierDataService(this);
	}
	
	private void initUser()
	{
		_user = new HaierUser(this);
	}
	
	private void initToast()
	{
		_toast = new HaierToast(this);
	}
	
	private void initAnimation()
	{
		_animation = new HaierAnimation(this);
	}
}
