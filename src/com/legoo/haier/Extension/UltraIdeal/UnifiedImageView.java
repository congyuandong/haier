package com.legoo.haier.Extension.UltraIdeal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.RejectedExecutionException;
import com.legoo.haier.Application.Hospital;
import com.legoo.haier.Application.HospitalThreadPool;
import com.legoo.haier.Extension.BitmapUtils;
import com.legoo.haier.Extension.Cache.UnifiedImageCache;
import com.legoo.haier.Extension.Cache.UnifiedImageFormat;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * @class Unified Image View
 * @author LeonNoW
 * @version 1.12
 * @date 2013-12-22
 */
public class UnifiedImageView extends ImageView
{
	public static UnifiedImageCache UNIFIED_IMAGE_CACHE = Hospital.getInstance().getCache().getImage();
	
	private static final boolean IMAGE_SIZE_LIMIT = true;
	private static final int IMAGE_SIZE_MAX = 800;
	
	private static final int TYPE_SELF = 0;
	private static final int TYPE_PARENT = 1;
	
	private static final int DOWNLOAD_MAX_FAILURES = 2;
	private static final int DOWNLOAD_TIMEOUT = 10000;
	
	private OnLoadedListener _loadedListener;
	
	private int _resizeWidth = 0;
	private int _resizeHeight = 0;
	
	private String _imageURL;
	
	private int _failureCount;

	private View _viewParent;
	
	private int _positionInParent;

	private int _viewType;
	
	private DownloadTask _downloadTask;
	
	private UnifiedImageFormat _imageFormat;
	
	private boolean _isDefault;
	
	public boolean getIsDefault()
	{
		return _isDefault;
	}
	
	private boolean _isLocal;
	
	public boolean getIsLocal()
	{
		return _isLocal;
	}
	
	
	public UnifiedImageView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
	}

	public UnifiedImageView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}

	public UnifiedImageView(Context context) 
	{
		super(context);
	}

	
	
	public void setImageUrl(String url, int type)
	{
		_imageFormat = new UnifiedImageFormat(type, UnifiedImageFormat.FORCE_CLOSE);
		initImageView(url);
	}
	
	public void setImageUrl(String url, int type, int force)
	{
		_imageFormat = new UnifiedImageFormat(type, force);
		initImageView(url);
	}
	
	public void setImageUrl(String url, int type, int force, boolean local)
	{
		_isLocal = local;
		_imageFormat = new UnifiedImageFormat(type, force);
		initImageView(url);
	}
	
	public void setImageUrl(String url, int type, int width, int height)
	{
		_imageFormat = new UnifiedImageFormat(type, UnifiedImageFormat.FORCE_CLOSE);
		resizeImageSource(width, height);
		initImageView(url);
	}

	public void setImageUrl(String url, int type, int width, int height, int force)
	{
		_imageFormat = new UnifiedImageFormat(type, force);
		resizeImageSource(width, height);
		initImageView(url);
	}
	
	public void setImageUrl(String url, int position, View parent, int type)
	{
		_imageFormat = new UnifiedImageFormat(type, UnifiedImageFormat.FORCE_CLOSE);
		initImageView(url, position, parent);
	}
	
	public void setImageUrl(String url, int position, View parent, int type, int force)
	{
		_imageFormat = new UnifiedImageFormat(type, force);
		initImageView(url, position, parent);
	}
	
	public void setImageUrl(String url, int position, View parent, int type, int width, int height)
	{
		_imageFormat = new UnifiedImageFormat(type, UnifiedImageFormat.FORCE_CLOSE);
		resizeImageSource(width, height);
		initImageView(url, position, parent);
	}
	
	public void setImageUrl(String url, int position, View parent, int type, int width, int height, int force)
	{
		_imageFormat = new UnifiedImageFormat(type, force);
		resizeImageSource(width, height);
		initImageView(url, position, parent);
	}
	
	
	
	private void setImage(String url)
	{
		if (_viewType != TYPE_SELF && _viewParent == null)
		{
			onFinish(false);
			return;
		}

		if(_imageURL != null && _imageURL.equals(url))
		{
			_failureCount++; 
			if(_failureCount > DOWNLOAD_MAX_FAILURES)
			{
				setFaultImage();
				onFinish(false);
				return;
			}
		} 
		else
		{
			_imageURL = url; 
			_failureCount = 0; 
		}

		Bitmap bitmap;
		if (_imageFormat.getForce() == UnifiedImageFormat.FORCE_RESOURCE)
		{
			if (UNIFIED_IMAGE_CACHE.getCache(url, _imageFormat) == null)
			{
				Integer readID = Integer.valueOf(url);
				if (readID != -1)
				{
					try
					{
						BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(readID);
						bitmap = drawable.getBitmap();
						drawable = null;
						UNIFIED_IMAGE_CACHE.putCache(url, bitmap, _imageFormat);
					}
					catch (Exception e){}
					bitmap = null; 

				}
				else 
				{
					return;
				}
			}
		}

		bitmap = UNIFIED_IMAGE_CACHE.getCache(url, _imageFormat);
		if (bitmap != null)
		{
			this.setImageBitmap(bitmap);
			onFinish(true);
		}
		else if (_isLocal == true)
		{
			setShieldImage();
			onFinish(false);
		}
		else 
		{
			try
			{
				_downloadTask = new DownloadTask();
				_downloadTask.executeEnhanced(url);
				url = null;
			} 
			catch (RejectedExecutionException e) { }
		}
		
		bitmap = null;
	}
	
	
	public void setDefaultImage()
	{
		setImageResource(_imageFormat.getDefaultResource());
	}
	
	private void setFaultImage()
	{
		setImageResource(_imageFormat.getFaultResource());
	}
	
	private void setShieldImage()
	{
		setImageResource(_imageFormat.getShieldResource());
	}
	
	private void initImageView(String url)
	{
		_failureCount = 0;
		_viewType = TYPE_SELF;

		if (url == null || url.trim().equals(""))
		{
			setFaultImage();
			onFinish(false);
		}
		else 
		{
			setImage(url);
		}
		
		url = null;
	}
	
	private void initImageView(String url, int position, View parent)
	{
		_failureCount = 0;
		_positionInParent = position;
		_viewParent = parent;
		_viewType = TYPE_PARENT;
		
		if (url == null || url.trim().equals(""))
		{
			setFaultImage();
			onFinish(false);
		}
		else 
		{
			setImage(url);
		}
		
		parent = null;
		url = null;
	}
	
	private void resizeImageSource(int width, int height)
	{
		_resizeWidth = width;
		_resizeHeight = height;
	}
	
	public void dispose()
	{
		_imageURL = null;
		_viewParent = null;
		if (_downloadTask != null)
		{
			if (_downloadTask.isCancelled() != true)
			{
				_downloadTask.cancel(true);
			}
			_downloadTask = null;
		}
	}
	
	
	public class DownloadTask extends AsyncTask<String, Void, String>
	{
		private String _taskUrl;

		@Override
		public void onPreExecute() 
		{
			_isDefault = true;
			setDefaultImage(); 
			super.onPreExecute();
		}

		@Override
		public String doInBackground(String... params) 
		{
			_taskUrl = params[0];
			
			if (_taskUrl != null)
			{
				Bitmap bitmap = getBitmapFormURL(_taskUrl);  
				if (bitmap != null)
				{
					UNIFIED_IMAGE_CACHE.putCache(_taskUrl, bitmap, _imageFormat);
					bitmap = null;
				}
				params[0] = null;
			}
			return _taskUrl;
		}

		@Override
		public void onPostExecute(String url) 
		{
			super.onPostExecute(url);
			
			if (_taskUrl != null)
			{
				if(!_taskUrl.equals(_imageURL)) 
				{
					return;
				}
				Bitmap bitmap = UNIFIED_IMAGE_CACHE.getCache(url, _imageFormat);
				if(bitmap == null) 
				{
					UnifiedImageView.this.setImage(url);
				} 
				else 
				{
					if (_viewType == TYPE_PARENT)
					{
						if(_viewParent != null)
						{
							if (ListView.class.isAssignableFrom(_viewParent.getClass()))
							{
								if(_positionInParent < ((ListView)_viewParent).getFirstVisiblePosition() 
										|| _positionInParent > ((ListView)_viewParent).getLastVisiblePosition())
								{
									return;
								}
							}
						}
						else 
						{
							return;
						}
					}
					else 
					{
						if (UnifiedImageView.this == null)
						{
							return;
						}
					}

					UnifiedImageView.this.setImageBitmap(bitmap);
					UnifiedImageView.this.onFinish(true);
				}
			}
		}  
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public AsyncTask<String, Void, String> executeEnhanced(String... params)
		{
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			{
				return executeOnExecutor(HospitalThreadPool.IMAGE_EXECUTOR, params);
		    }
			else
			{
		    	return execute(params);
		    }
		}
		
		private synchronized Bitmap getBitmapFormURL(String url)
		{
			URL myFileUrl = null;
			Bitmap bitmap = null;
			try
			{
				myFileUrl = new URL(url);
			}
			catch (Exception e)
			{
				return bitmap;
			}
			try
			{
				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.setConnectTimeout(DOWNLOAD_TIMEOUT);
				conn.connect();
				InputStream is = conn.getInputStream();
				Integer length = (int) conn.getContentLength();
				if (length != -1)
				{
					byte[] imgData = new byte[length];
					byte[] temp = new byte[512];
					Integer readLen = 0;
					Integer destPos = 0;
					while ((readLen = is.read(temp)) > 0)
					{
						System.arraycopy(temp, 0, imgData, destPos, readLen);
						destPos += readLen;
					}
					if (UNIFIED_IMAGE_CACHE.isCached(url) == false)
					{
						bitmap = BitmapUtils.getResizeBitmapFromByte(imgData, IMAGE_SIZE_LIMIT ? IMAGE_SIZE_MAX : 0, _resizeWidth, _resizeHeight);
					}
					temp = null;
					imgData = null;
					readLen = null;
					destPos = null;
				}
				length = null;
				is.close();
				is = null;
				conn.disconnect();
				conn = null;
			}
			catch (IOException e)
			{
				return bitmap;
			}
			return bitmap;
		}
	};
	
	public void setOnLoadedListener(OnLoadedListener listener)
	{
		_loadedListener = listener;
	}
	
	public static interface OnLoadedListener
	{
		public void onLoaded(UnifiedImageView view);
	}
	
	public void onLoaded()
	{
		if (_loadedListener != null)
		{
			_loadedListener.onLoaded(this);
		}
	}

	private void onFinish(boolean succesed)
	{
		_isDefault = !succesed;
		if (succesed == true)
		{
			onLoaded();
		}
	}
}
