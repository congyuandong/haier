package com.legoo.haier.Dialog.Base;

import com.legoo.haier.R;
import com.legoo.haier.Extension.ApplicationUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.View;
import android.view.WindowManager;

/**
 * @class Base Dialog
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class BaseDialog
{
	public static final float RATIO_FULL = 1.0f;
	public static final float RATIO_MIDDLE = 0.75f;
	public static final float RATIO_LARGE = 0.8f;
	public static final float RATIO_SMALL = 0.66f;
	
	private AlertDialog.Builder _builder;
	private AlertDialog _dialog;
	private Activity _activity;
	private float _ratio = RATIO_LARGE;
	private View _view;
	private boolean _ready;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected BaseDialog(Activity activity, int layout) 
	{
		init(activity, layout, false, AlertDialog.THEME_HOLO_LIGHT);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected BaseDialog(Activity activity, int layout, boolean cancelable) 
	{
		init(activity, layout, cancelable, AlertDialog.THEME_HOLO_LIGHT);
	}
	
	protected BaseDialog(Activity activity, int layout, boolean cancelable, int theme) 
	{
		init(activity, layout, cancelable, theme);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void init(Activity activity, int layout, boolean cancelable, int theme)
	{
		_activity = activity;
		if (VERSION.SDK_INT < 11)
		{
			_builder = new AlertDialog.Builder(activity);
		}
		else 
		{
			_builder = new AlertDialog.Builder(activity, theme);
		}
		_builder.setCancelable(cancelable);
		_view = activity.getLayoutInflater().inflate(layout, null);  
		_dialog = _builder.create();
		activity = null;
		_ready = true;
		
		setOnDismissListener(new OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialog) 
			{
				_ready = true;
			}
		});
	}
	
	protected boolean hasPrepared()
	{
		return _builder != null && isShowing() == false && _ready == true;
	}
	
	public boolean isShowing()
	{
		return _dialog != null && _dialog.isShowing();
	}
	
	public void show()
	{
		try 
		{
			if (hasPrepared() == true)
			{
				_ready = false;
				_dialog.show();
				WindowManager.LayoutParams params = _dialog.getWindow().getAttributes();
				params.width = (int) (ApplicationUtils.getDisplayMetrics(_activity).widthPixels * _ratio);
				_dialog.getWindow().setAttributes(params);
				_dialog.getWindow().setBackgroundDrawableResource(R.drawable.no);
				_dialog.getWindow().setContentView(_view);
			}
		} 
		catch (Exception e) {}
	}
	
	public void dismiss()
	{
		_ready = true;
		if (isShowing() == true)
		{
			_dialog.dismiss();
		}
	}
	
	public boolean isDismiss()
	{
		return _ready;
	}
	
	protected View getView()
	{
		return _view;
	}
	
	protected Activity getActivity()
	{
		return _activity;
	}
	
	protected void setCancelable(boolean cancelable)
	{
		_builder.setCancelable(cancelable);
	}
	
	public void setWidthRatio(float ratio)
	{
		_ratio = ratio;
	}
	
	public void setTitle(String title)
	{
		_builder.setTitle(title);
	}
	
	public void setTitle(int title)
	{
		_builder.setTitle(title);
	}
	
	public void setOnDismissListener(OnDismissListener dismissListener)
	{
		_dialog.setOnDismissListener(dismissListener);
	}
	
	protected void dispose()
	{
		_view = null;
		_dialog = null;
		_builder = null;
	}
	
	public static void prepare(BaseDialog dialog)
	{
		if (dialog != null && dialog.isShowing())
		{
			dialog.dismiss();
		}
	}
}
