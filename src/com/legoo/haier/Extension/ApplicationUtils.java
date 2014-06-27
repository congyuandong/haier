package com.legoo.haier.Extension;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import com.legoo.haier.R;
import com.legoo.haier.Activity.LoadActivity;
import com.legoo.haier.Activity.MainActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Application.HaierSettings;
import com.legoo.haier.Dialog.MessageDialog;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @class Application Utils
 * @author LeonNoW
 * @version 1.0
 * @date 2013-8-28
 */
public class ApplicationUtils
{
	private static Context _context = Haier.getInstance();
	
	public static void setTitleHidden(Activity activity)
    { 
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity = null;
	}
	
    public static void setFullScreen(Activity activity)
    {
    	activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
    	activity = null;
    }
    
	public static void setNormalScreen(Activity activity)
	{
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity = null;
	}
	
	public static boolean detectNetworkState()
	{ 
		NetworkInfo info = ((ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();     
	    if(info == null || info.isConnected() == false)
	    {
	        return false;     
	    }
	    return true;      
	}
	
	public static int getNetworkType()
	{
		NetworkInfo info = ((ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();     
	    if(info == null || info.isConnected() == false)
	    {
	        return -1;     
	    }       
	    else 
	    {
	    	return info.getType();
		}
	}
	
	public static DisplayMetrics getDisplayMetrics(Activity activity)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
	
	public static void openNetworkConfig(Activity activity)
	{
		Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		activity.startActivityForResult(intent, 0);
		intent = null;
		activity = null;
	}
	
	public static void OpenExternalUrl(Activity activity, String url)
	{
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
	public static void verifyDirectory(String directory)
	{
		File file = new File(directory);
		if (file.exists() == true)
		{
			if (file.isDirectory() == true)
			{
				return;
			}
			else 
			{
				file.delete();
			}
		}
		file.mkdirs();
	}
	
	public static boolean detectSDMounted()
	{
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	public static String getDeviceID()
	{
		return Secure.getString(Haier.getInstance().getContentResolver(), Secure.ANDROID_ID);
	}
	
	public static String getPlatformName()
	{
		return "Android OS";
	}
	
	public static String getSystemVersion()
	{
		return new StringBuilder(Build.VERSION.RELEASE == null ? "Unknown" : Build.VERSION.RELEASE).append(" ").append("SDK").append(Build.VERSION.SDK_INT).toString(); 
	}
	
	public static String getDeviceName()
	{
		return new StringBuilder(Build.BRAND == null ? "Unknown" : Build.BRAND).append(" ").append(Build.MODEL == null ? "Unknown" : Build.MODEL).toString();
	}
	
	public static boolean isAppOnForeground() 
	{  
		ActivityManager activityManager = (ActivityManager) Haier.getInstance().getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();  
        if (appProcesses == null)
        {
        	return false;  
        }
        for (RunningAppProcessInfo appProcess : appProcesses) 
        {   
            if (appProcess.processName.equals(Haier.getInstance().getPackageName()) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) 
            {  
                return true;  
            }  
        }  
        return false;  
    }
	
	public static void openInputWindow(View inputView)
	{
		InputMethodManager inputMethodManager = (InputMethodManager) inputView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(inputView, 0);
	}
	
	public static void closeInputWindow(View inputView)
	{
		InputMethodManager inputMethodManager = (InputMethodManager) inputView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(inputView.getWindowToken(), 0);
	}
	
	public static long getFolderSize(File file)
	{  
        long size = 0;  
        File[] fileList = file.listFiles();  
        for (int i = 0; i < fileList.length; i++)  
        {  
            if (fileList[i].isDirectory())  
            {  
                size = size + getFolderSize(fileList[i]);  
            } 
            else  
            {  
                size = size + fileList[i].length();  
            }  
        }  
        return size;  
    }  
	
	public static String getFileSize(long size) 
	{  
        DecimalFormat format = new DecimalFormat("###.##");  
        float f = ((float) size / (1024 * 1024));  
        return format.format(Float.valueOf(f).doubleValue()) + " MB";  
    }  
	
	public static void deleteFolderFile(String filePath, boolean deleteThisPath)  
    {  
        if (filePath != null && filePath.length() > 0) 
        {  
            File file = new File(filePath);  
  
            if (file.isDirectory()) 
            {
                File files[] = file.listFiles();  
                for (int i = 0; i < files.length; i++) 
                {  
                    deleteFolderFile(files[i].getAbsolutePath(), true);  
                }  
            }  
            if (deleteThisPath) 
            {  
                if (!file.isDirectory()) 
                { 
                    file.delete();  
                } 
                else 
                {
                    if (file.listFiles().length == 0) 
                    { 
                        file.delete();  
                    }  
                }  
            }  
        }  
    }  
	
	public static void showUpdateDialog(final Activity activity, final String version, final String url)
	{
		final MessageDialog dialog = new MessageDialog(activity, 
				String.format(activity.getString(R.string.dialog_body_update), 
						HaierSettings.VERSION_NAME,
						version));
		dialog.setConfirmButton(activity.getString(R.string.dialog_button_update), new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				OpenExternalUrl(activity, url);
				dialog.dismiss();
			}
		});
		dialog.setCancelButton(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	public static boolean isCompareVersionNewest(String compare)
	{
		String[] currentVersion = HaierSettings.VERSION_NAME.split("\\.");
		String[] compareVersion = compare.split("\\.");
		
		int currentCount = currentVersion.length;
		int compareCount = compareVersion.length;
		int versionCount = currentCount >= compareCount ? currentCount : compareCount;
		int currentNum;
		int compareNum;
		try 
		{
			for (int i = 0; i < versionCount; i++)
			{
				currentNum = i < currentCount ? Integer.valueOf(currentVersion[i]) : 0;
				compareNum = i < compareCount ? Integer.valueOf(compareVersion[i]) : 0;
				if (currentNum < compareNum)
				{
					return true;
				}
				else if (currentNum > compareNum)
				{
					return false;
				}
			}
		} 
		catch (NumberFormatException e) {}
		return false;
	}
	
	public static boolean hasMainReady(Activity activity)
	{
		Intent mainIntent = new Intent(activity, MainActivity.class);  
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningTaskInfo> appTask = manager.getRunningTasks(1);  
        if (appTask.size() > 0 && appTask.get(0).baseActivity.equals(mainIntent.getComponent())) 
        {  
            return true;
        } 
        else 
        {  
        	return false;
        } 
	}
	
	public static void restoreApplication(Activity activity)
	{
		startApplicationFromActivity(activity, MainActivity.class);
	}
	
	public static void restartApplication(Activity activity)
	{
		startApplicationFromActivity(activity, LoadActivity.class);
	}
	
	private static void startApplicationFromActivity(Activity activity, Class<?> cls)
	{
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out); 
	}
	
	public static boolean isAppInstalled(Activity activity, String packageName)
	{
		if (packageName == null || packageName.length() == 0)
		{
            return false;
		}
		else
        {
            final PackageManager packageManager = activity.getPackageManager();  
            List<PackageInfo> infos = packageManager.getInstalledPackages(0);  
            boolean flag = false;  
            if(infos != null)  
            {  
                String name = null;  
                for(int i = 0; i < infos.size(); i++)  
                {   
                	name = infos.get(i).packageName;  
                    if(name != null && name.equalsIgnoreCase(packageName))  
                    {  
                        flag = true;  
                        break;  
                    }  
                }  
            }  
            return flag;  
        } 
	}
}
