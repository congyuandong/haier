package com.legoo.haier.Extension;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @class Format Utils
 * @author LeonNoW
 * @version 1.0
 * @date 2013-8-28
 */
public class FormatUtils
{
	public static boolean compareVersion(String current, String target)
	{
		if (current != null && target != null)
		{
			if (current.equalsIgnoreCase(target) == false)
			{
				String[] currentVersion = current.split("\\.");
				String[] targetVersion = target.split("\\.");
				
				int currentCount = currentVersion.length;
				int targetCount = targetVersion.length;
				int versionCount = Math.max(currentCount, targetCount);
				int currentNum;
				int targetNum;
				try 
				{
					for (int i = 0; i < versionCount; i++)
					{
						currentNum = i < currentCount ? Integer.valueOf(currentVersion[i]) : 0;
						targetNum = i < targetCount ? Integer.valueOf(targetVersion[i]) : 0;
						if (currentNum < targetNum)
						{
							return false;
						}
						else if (currentNum > targetNum)
						{
							return true;
						}
					}
				} 
				catch (NumberFormatException e) {}
				return false;
			}
		}
		return false;
	}
	
	public static String convertInputStreamToString(InputStream inputStream) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;

		while ((line = reader.readLine()) != null) 
		{
			stringBuffer.append(line).append("\n");
		}
		
		reader.close();
		reader = null;
		
		return stringBuffer.toString();
	}
	
	public static String convertIntegerWithComma(int value)
	{
		return new DecimalFormat(",###").format(value);
	}
	
	@SuppressWarnings("deprecation")
	public static int convertDateToIntger(Date date)
	{
		return date.getHours() * 60 + date.getMinutes();
	}
	
	public static int convertDateToIntger(String date, String format)
	{
		try 
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
			return convertDateToIntger(dateFormat.parse(date));
		} 
		catch (ParseException e) 
		{
			return 0;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String convertDateToString(Date date)
	{
		if (date == null)
		{
			date = new Date();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public static String getNameByData()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSS", Locale.getDefault());
		return dateFormat.format(new Date());
	}
	
	public static String encodeStringSHA1(String text) 
	{ 
		String result;
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
		    md.update(text.getBytes("utf-8"), 0, text.length());
		    result = hex(md.digest());
		} 
		catch (NoSuchAlgorithmException e) 
		{
			result = "";
		}
		catch (UnsupportedEncodingException e) 
		{
			result = "";
		}
	    return result;
	} 
	
	private static String hex(byte[] arr) 
    {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < arr.length; ++i) {  
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));  
        }  
        return sb.toString();  
    }
	
	public static String convertTimeFromPosition(int position)
	{
		int temp = position / 1000;
		int minute = temp / 60;
		int hour = minute / 60;
		int second = temp % 60;
		minute %= 60;
		return hour > 0 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
				: String.format(Locale.getDefault(), "%02d:%02d", minute, second);
	}
	
	public static String getFileNameFromURL(String url)
	{
		return url.substring(url.lastIndexOf("/") + 1);
	}
	
	public static String converStringToBase64(String text)
	{
		if (text != null)
		{
			return Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
		}
		else 
		{
			return "";
		}
	}
	
	public static String converStringFromBase64(String base64)
	{
		try
		{
			return new String(Base64.decode(base64, Base64.DEFAULT));
		} 
		catch (Exception e)
		{
			return "";
		}
	}
	
	public static boolean isEmail(String email)
	{
		try
		{
			String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(email);

			return matcher.matches();
		} 
		catch (Exception e)
		{
			return false;
		}
	}
}
