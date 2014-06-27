package com.legoo.haier.Archon;

import com.legoo.haier.R;
import com.legoo.haier.Application.Haier;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @class Navigation Archon
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class NavigationArchon 
{
	private Activity _activity;
	
	private Button _buttonReturn;
	private Button _buttonRight;
	private ImageButton _buttonCover;
	private View _viewNavigation;
	private TextView _textTitle;
	private TextView _textReturn;
	private TextView _textRight;
	private ImageView _imageProgress;
	private RelativeLayout _layoutProgress;
	
	private OnReturnClickListener _returnClickListener;
	private OnRightClickListener _rightClickListener;
	
	public NavigationArchon(Activity activity)
	{
		_activity = activity;
		init();
	}
	
	private void init()
	{
		_buttonCover = (ImageButton) _activity.findViewById(R.id.buttonNavigationCover);
		_viewNavigation = (View) _activity.findViewById(R.id.viewNavigationCover);
		if (_viewNavigation != null)
		{
			_viewNavigation.setVisibility(View.GONE);
		}
		
		_textTitle = (TextView) _activity.findViewById(R.id.textNavigationTitle);
		_textReturn = (TextView) _activity.findViewById(R.id.textNavigationReturn);
		_textRight = (TextView) _activity.findViewById(R.id.textNavigationButton);
		_imageProgress = (ImageView) _activity.findViewById(R.id.imageNavigationProgress);
		_layoutProgress = (RelativeLayout) _activity.findViewById(R.id.layoutNavigationProgress);
		
		_buttonReturn = (Button) _activity.findViewById(R.id.buttonNavigationReturn);
		if (_buttonReturn != null)
		{
			_buttonReturn.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					if (_returnClickListener != null)
					{
						_returnClickListener.onReturnClick();
					}
				}
			});
		}
		
		_buttonRight = (Button) _activity.findViewById(R.id.buttonNavigationButton);
		if (_buttonRight != null)
		{
			_buttonRight.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					if (_rightClickListener != null)
					{
						_rightClickListener.onRightClick();
					}
				}
			});
		}
	}

	public void setTitle(String text)
	{
		_textTitle.setText(text);
	}
	
	public void setReturn(String text)
	{
		_textReturn.setText(text);
	}
	
	public void setRight(String text)
	{
		_textRight.setText(text);
	}
	
	public void showProgress()
	{
		Haier.getInstance().getAnimation().startProgress(_imageProgress);
		Haier.getInstance().getAnimation().startFadeIn(_layoutProgress);
	}
	
	public void hideProgress()
	{
		Haier.getInstance().getAnimation().startFadeOut(_layoutProgress);
	}
	
	public void setOnClickListener(OnClickListener onClickListener)
	{
		if (_buttonCover != null)
		{
			_buttonCover.setOnTouchListener(new OnTouchListener() 
			{
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					if (event.getAction() == MotionEvent.ACTION_DOWN)
					{
						Haier.getInstance().getAnimation().startFadeIn(_viewNavigation);
					}
					else if (event.getAction() == MotionEvent.ACTION_UP)
					{
						Haier.getInstance().getAnimation().startFadeOut(_viewNavigation);
					}
					return false;
				}
			});
			_buttonCover.setOnClickListener(onClickListener);
		}
	}
	
	public void setOnReturnClickListener(OnReturnClickListener listener)
	{
		_returnClickListener = listener;
	}
	
	public void setOnRightClickListener(OnRightClickListener listener)
	{
		_rightClickListener = listener;
	}
	
	public static interface OnReturnClickListener
	{
		public void onReturnClick();
	}
	
	public static interface OnRightClickListener
	{
		public void onRightClick();
	}
}
