package com.legoo.haier.Application;

import java.io.File;

import com.legoo.haier.Extension.ApplicationUtils;
import com.legoo.haier.Extension.Cache.UnifiedImageCache;

/**
 * @class Hospital Cache
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HospitalCache
{
	public static final int CACHE_READY = 0;
	public static final int CACHE_COMPUTING = 1;
	public static final int CACHE_COMPUTED = 2;
	public static final int CACHE_RECYCLING = 3;
	
	
	private UnifiedImageCache _image;
	
	public UnifiedImageCache getImage()
	{
		return _image;
	}
	
	public HospitalCache()
	{
		initCache();
	}
	
	private void initCache()
	{
		_image = new UnifiedImageCache(HospitalSettings.IMAGECACHE_MEMORY_EXPECTED_COUNT,
				HospitalSettings.IMAGECACHE_STROAGE_EXPECTED_COUNT);
	}
	
	public boolean recycleCache()
	{
		try 
		{
			ApplicationUtils.deleteFolderFile(HospitalSettings.CACHE_STROAGE_PATH, false);
			_image.clear();
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	public String computeCacheSize()
	{
		if (ApplicationUtils.detectSDMounted() == true)
		{
			try 
			{
				return ApplicationUtils.getFileSize(ApplicationUtils.getFolderSize(
							new File(HospitalSettings.CACHE_STROAGE_PATH)));
			} 
			catch (Exception e) { }
		}
		return ApplicationUtils.getFileSize(0);
	}
}
