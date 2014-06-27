package com.legoo.haier.Application;

import java.io.File;
import java.io.IOException;
import com.legoo.haier.Extension.ApplicationUtils;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

/**
 * @class Hospital Settings
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HaierSettings
{
	private Haier _application;
	
	public static final int LOAD_TIME = 1000;
	
	public static final int NETWORK_CONNECT_TIMEOUT = 10000;
	public static final int NETWORK_READ_TIMEOUT = 10000;
	public static final int NETWORK_TASK_RETRY_COUNT = 2;
	
	public static final int IMAGECACHE_MEMORY_EXPECTED_COUNT = 4096000;
	public static final int IMAGECACHE_STROAGE_EXPECTED_COUNT = 40960000;
	
	public static final String STROAGE_PATH = String.format("%1$s/HospitalR1/", Environment.getExternalStorageDirectory().getPath());
	
	public static final String CACHE_STROAGE_PATH = String.format("%1$sCaches/", STROAGE_PATH);
	public static final String CACHE_IMAGE_STROAGE_PATH = String.format("%1$sImages/", CACHE_STROAGE_PATH);
	public static final boolean CACHE_XML_POWER = false;
	public static final String CACHE_XML_STROAGE_PATH = String.format("%1$sXmls/", CACHE_STROAGE_PATH);
	
	private static final String NO_MEDIA = ".nomedia";
	
	public static final int MESSAGE_DURATION = 60000;
	public static final boolean MESSAGE_KEEP_SERVICE = true;
	public static final boolean DEFAULT_MESSAGE_POWER = true;
	
	public static int NEWS_FONT_NORMAL = 0;
	public static int NEWS_FONT_LARGE = 1;
	public static int NEWS_FONT_SMALL = 2;
	
	public static String TRUE = "1";
	public static String FALSE = "0";
	
	public static int FEEDBACK_MAX_LONGTH = 100;	
	public static int VERSION_CODE;
	public static String VERSION_NAME;
	
	public HaierSettings(Haier application)
	{
		this._application = application;
		
		initPackageInfo();
		markNoMedia();
	}

	private void initPackageInfo()
	{
		PackageInfo info;
		try 
		{
			info = _application.getPackageManager().getPackageInfo(_application.getPackageName(), 0);
			VERSION_CODE = info.versionCode;  
			VERSION_NAME = info.versionName;
			info = null;
		} 
		catch (NameNotFoundException e) {} 
	}

	private void markNoMedia()
	{
		if (ApplicationUtils.detectSDMounted() == true)
		{
			File nomedia = new File(String.format("%1$s%2$s", STROAGE_PATH, NO_MEDIA));
			if (nomedia.exists() == false)
			{
				File root = new File(STROAGE_PATH);
				if (root.exists() == true || root.mkdirs() == true)
				{
					try
					{
						nomedia.createNewFile();
						nomedia = null;
					} 
					catch (IOException e) { }
				}
			}
		}
	}
}
