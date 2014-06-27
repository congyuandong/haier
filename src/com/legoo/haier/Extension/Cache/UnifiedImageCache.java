package com.legoo.haier.Extension.Cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.WeakHashMap;

import com.legoo.haier.Extension.ApplicationUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @class Unified Image Cache
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class UnifiedImageCache extends WeakHashMap<String, Bitmap> 
{
	private long _memoryCacheExpectedSize;
	private long _memoryCacheExpectedCount;
	private long _stroageCacheExpectedCount;
	
	private boolean isComputing = false;
	
	public UnifiedImageCache(long memoryExpected, long stroageExpected) 
	{ 
		_memoryCacheExpectedCount = memoryExpected;
		_stroageCacheExpectedCount = stroageExpected;
	}
	
	public boolean isCached(String url)
	{
		if (containsKey(url) == true)
		{
			if (get(url) != null)
			{
				return true;
			}
			else
			{
				remove(url);
				return false;	
			}
		}
		return false;
	}
	
	public synchronized void putCache(String url, Bitmap bitmap, UnifiedImageFormat format)
	{
		if (isCached(url) == false)
		{
			if (format.getForce() == UnifiedImageFormat.FORCE_MEMORY || format.getForce() == UnifiedImageFormat.FORCE_RESOURCE)
			{
				putMemoryCache(url, bitmap);
			}
			else if (format.getForce() == UnifiedImageFormat.FORCE_STROAGE)
			{
				putStroageCache(url, bitmap, format);
			}
		}
	}
	
	public synchronized Bitmap getCache(String url, UnifiedImageFormat format)
	{
		if (isCached(url) == false)
		{
			if (format.getForce() == UnifiedImageFormat.FORCE_MEMORY || format.getForce() == UnifiedImageFormat.FORCE_RESOURCE)
			{
				return getMemoryCache(url);
			}
			else if (format.getForce() == UnifiedImageFormat.FORCE_STROAGE)
			{
				return getStroageCache(url, format);
			}
			else 
			{
				return null;
			}
		}
		else
		{
			return get(url);
		}
	}
	
	private void putMemoryCache(String url, Bitmap bitmap)
	{
		put(url, bitmap);
		addMemoryCacheSize(bitmap);
	}

	private void putStroageCache(String url, Bitmap bitmap, UnifiedImageFormat format)
	{
		if (saveStroageCache(url, bitmap, format) == false)
		{
			putMemoryCache(url, bitmap);
		}
	}
	
	private Bitmap getMemoryCache(String url)
	{
		return get(url);	
	}
	
	private Bitmap getStroageCache(String url, UnifiedImageFormat format)
	{
		Bitmap bitmap;
		boolean getBitmap = false;
		int retryCount = 0;
		do 
		{
			bitmap = loadStroageCache(url, format);
			if (bitmap == null)
			{
				return null;
			}
			else 
			{
				putMemoryCache(url, bitmap);
				bitmap = null;
				if (get(url) == null)
				{
					retryCount += 1;
				}
				else 
				{
					getBitmap = true;
				}
			}
		} 
		while (getBitmap == false && retryCount <= 1); 
		return get(url);
	}
	
	private void addMemoryCacheSize(Bitmap bitmap)
	{
		_memoryCacheExpectedSize += computeBitmapSize(bitmap);
		bitmap = null;

		if (_memoryCacheExpectedSize >= _memoryCacheExpectedCount)
		{
			if (isComputing == false)
			{
				_memoryCacheExpectedSize = computeMemoryCacheSize();
				if (_memoryCacheExpectedSize >= _memoryCacheExpectedCount)
				{
					clear();
					_memoryCacheExpectedSize = 0;
				}
			}
		}
	}
	
	private Bitmap loadStroageCache(String url, UnifiedImageFormat format)
	{
		if (ApplicationUtils.detectSDMounted() == true) 
		{ 
			String name = getNameByURL(url);
			if (name != null)
			{
				String fullname = new StringBuilder(format.getStroage()).append(name).toString();
				name = null;
				File loadFile = new File(fullname);
		        if (loadFile.exists() == true)
		        {
			        try 
			        {
				        return BitmapFactory.decodeFile(fullname);
					} 
			        catch (OutOfMemoryError e) 
			        {
			        	clear();
					}
			        catch (Exception e) { }
			        finally
			        {
			        	loadFile = null;
			        	fullname = null;
				        format = null;
				        url = null;
			        }
		        }
			}
		}
		return null;
	}
	
	private Boolean saveStroageCache(String url, Bitmap saveBitmap, UnifiedImageFormat format) 
	{
		if (saveBitmap != null && ApplicationUtils.detectSDMounted() == true)
		{
			String name = getNameByURL(url);
			if (name != null)
			{
				File path = new File(format.getStroage()); 
				if(path.exists() == true || path.mkdirs() == true)
				{
					path = null;
					File saveFile = new File(new StringBuilder(format.getStroage()).append(name).toString());
			        name = null;
			        
			        try 
			        {
						saveFile.createNewFile();
						FileOutputStream outputStream = new FileOutputStream(saveFile);
			        	saveFile = null;
			        	if (format.getFormat() == UnifiedImageFormat.FORMAT_PNG)
			        	{
			        		saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
			        	}
			        	else if (format.getFormat() == UnifiedImageFormat.FORMAT_JPG)
			        	{
			        		saveBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
						}
			        	outputStream.flush();
			        	outputStream.close();
			        	saveBitmap.recycle();
			        	outputStream = null;
			        	return true;
					} 
			        catch (FileNotFoundException e) { }
			        catch (IOException e) { }
			        finally 
			        {
			        	saveBitmap = null;
				        format = null;
				        url = null;
			        }
				}
			}
		}
		return false;
	}
	
	private String getNameByURL(String url)
	{
		Integer lastIndex = url.lastIndexOf("/");
		Integer length = url.length();
		String name;
		if (lastIndex == -1 || lastIndex + 1 > length)
		{
			return null;
		}
		else
		{
			name = url.substring(lastIndex + 1).replace("?", ".");
			lastIndex = null;
			length = null;
			return name;
		}
	}
	
	public synchronized boolean recycleStroageCache()
	{
		return recycleStroageCache(UnifiedImageFormat.TYPE_ALL);
	}
	
	public synchronized boolean recycleStroageCache(int formatType)
	{
		String stroage = UnifiedImageFormat.getStroage(formatType);
		if (computeStroageCacheSize(stroage) >= _stroageCacheExpectedCount)
		{
			clearStroageCache(stroage);
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	private long computeStroageCacheSize(String stroage)
	{
		File file = new File(stroage);
		long usedStroageCacheSize;
		try 
		{
			usedStroageCacheSize = getFileSize(file);
		}
		catch (IOException e) 
		{
			usedStroageCacheSize = -1;
		}
		return usedStroageCacheSize;
	}
	
	private boolean clearStroageCache(String stroage)
	{
		if (ApplicationUtils.detectSDMounted() == false) 
		{ 
			return false;
		}
		else 
		{
			try
			{
				deleteFile(new File(stroage));
			} catch (IOException e) { }
			return true;
		}
	}

	private long computeMemoryCacheSize()
	{
		isComputing = true;
		long usedMemoryCacheSize = 0;
		try
		{
			for(Bitmap bitmap : values())
			{
				if (bitmap != null)
				{
					usedMemoryCacheSize += computeBitmapSize(bitmap);
					bitmap = null;
				}
			}
		}
		catch (Exception ex) 
		{
			usedMemoryCacheSize = -1;
		}
		finally
		{
			isComputing = false;
			System.gc();
		}
		return usedMemoryCacheSize;
	}
	
	private int computeBitmapSize(Bitmap bitmap) 
	{
		Integer baseConfig;
		if (bitmap.getConfig() == Bitmap.Config.ARGB_4444)
		{
			baseConfig = 2;
		}
		else if (bitmap.getConfig() == Bitmap.Config.ARGB_8888)
		{
			baseConfig = 4;
		}
		else if (bitmap.getConfig() == Bitmap.Config.RGB_565)
		{
			baseConfig = 2;
		}
		else 
		{
			baseConfig = 1;
		}
		Integer bitmapWidth = bitmap.getWidth();
		Integer bitmapHeight = bitmap.getHeight();
		bitmap = null;
		return bitmapWidth * bitmapHeight * baseConfig;
	}
	
	private void deleteFile(File filepath) throws IOException
	{       
		if(filepath.exists() == true && filepath.isDirectory() == true)
		{
			if(filepath.listFiles().length == 0)
			{  
				filepath.delete();  
		    }
			else
			{
				File[] delFile = filepath.listFiles();  
				int count = filepath.listFiles().length;  
				for(int i = 0; i < count; i++)
				{  
					if(delFile[i].isDirectory() == true)
					{  
			        	deleteFile(new File(delFile[i].getAbsolutePath()));  
			        }  
			        delFile[i].delete();
			    }  
			}
		}  
	}
	
	private long getFileSize(File filepath) throws IOException  
    {  
        long size = 0; 
        if(filepath.exists() == true)
		{
        	File[] flist = filepath.listFiles(); 
        	for (int i = 0 ; i < flist.length; i++)  
	        {  
	            if (flist[i].isDirectory() == true)  
	            {  
	                size = size + getFileSize(flist[i]);  
	            } 
	            else   
	            {  
	                size = size + flist[i].length();  
	            }  
	        }  
		}
        return size;  
    } 
}
