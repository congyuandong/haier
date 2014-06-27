package com.legoo.haier.Application;


import com.legoo.haier.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;

/**
 * @class Hospital Animation
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HospitalAnimation
{
	private Hospital _application;
	
	public HospitalAnimation(Hospital application)
	{
		this._application = application;
	}
	
	private void initViewGroup(ViewGroup viewGroup)
	{
		viewGroup.setLayoutAnimation(getLayoutAnimationController(R.anim.view_fade_in, 0.25f, LayoutAnimationController.ORDER_NORMAL));
	}
	
	public void startViewGroup(ViewGroup viewGroup)
	{
		if (viewGroup.getLayoutAnimation() == null)
		{
			initViewGroup(viewGroup);
		}
		viewGroup.startLayoutAnimation();
	}

	public void startFade(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(_application.getApplicationContext(), R.anim.view_fade_in));
	}
	
	public void startFadeOut(final View cover)
	{
		Animation animation = AnimationUtils.loadAnimation(_application.getApplicationContext(), R.anim.view_fade_out);
		animation.setAnimationListener(new AnimationListener() 
		{
			@Override
			public void onAnimationStart(Animation animation) 
			{
				cover.setVisibility(View.VISIBLE);
				cover.setClickable(false);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) 
			{
				cover.setVisibility(View.GONE);
				cover.setClickable(true);
			}
		});
		cover.startAnimation(animation);
	}
	
	public void startFadeIn(final View cover)
	{
		Animation animation = AnimationUtils.loadAnimation(_application.getApplicationContext(), R.anim.view_fade_in);
		animation.setAnimationListener(new AnimationListener() 
		{
			@Override
			public void onAnimationStart(Animation animation) 
			{
				cover.setVisibility(View.VISIBLE);
				cover.setClickable(false);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) 
			{
				cover.setClickable(true);
			}
		});
		cover.startAnimation(animation);
	}
	
	public void startShake(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(_application.getApplicationContext(), R.anim.view_shake));
	}
	
	public void startProgress(View view)
	{
		view.startAnimation(getInterpolatorAnimation(R.anim.progress_rotate));
	}
	
	public void startRotate(View view, boolean clockwise, int duration)
	{
		float fromDegrees;
		float toDegrees;

		if (clockwise == true)
		{
			fromDegrees = 0f;
			toDegrees = 360f;
		}
		else 
		{
			fromDegrees = 360f;
			toDegrees = 0f;
		}
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(duration);
		animation.setRepeatCount(Animation.INFINITE);

		LinearInterpolator interpolator = new LinearInterpolator();  
        animation.setInterpolator(interpolator);
        
		view.startAnimation(animation);
	}
	
	public void startBreath(final View view, int duration, int offset)
	{
		final AlphaAnimation animationIn = new AlphaAnimation(0.0f, 1.0f);
		animationIn.setDuration(duration);
		animationIn.setStartOffset(offset);
		
		final AlphaAnimation animationOut = new AlphaAnimation(1.0f, 0.0f);
	    animationOut.setDuration(duration);
	    
	    animationIn.setAnimationListener(new AnimationListener()
	    {	        
	    	@Override
	        public void onAnimationEnd(Animation arg0) 
	    	{
	        	view.startAnimation(animationOut);
	        }

	        @Override
	        public void onAnimationRepeat(Animation arg0) {}

	        @Override
	        public void onAnimationStart(Animation arg0) 
	        {
	        	view.setVisibility(View.VISIBLE);
	        }
	    });
		
	    animationOut.setAnimationListener(new AnimationListener()
	    {	        
	    	@Override
	        public void onAnimationEnd(Animation arg0) 
	    	{
	        	view.startAnimation(animationIn);
	        	view.setVisibility(View.GONE);
	        }

	        @Override
	        public void onAnimationRepeat(Animation arg0) {}

	        @Override
	        public void onAnimationStart(Animation arg0) {}
	    });
	    
		view.startAnimation(animationIn);
	}
	
	private Animation getInterpolatorAnimation(int resource)
	{
		Animation animation = AnimationUtils.loadAnimation(_application.getApplicationContext(), resource);       
        LinearInterpolator interpolator = new LinearInterpolator();  
        animation.setInterpolator(interpolator);
        return animation;
	}
	
	private LayoutAnimationController getLayoutAnimationController(int anim, float delay, int order)
	{
		LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(_application.getApplicationContext(), anim));
		controller.setDelay(delay);
		controller.setOrder(order);
		return controller;
	}
}
