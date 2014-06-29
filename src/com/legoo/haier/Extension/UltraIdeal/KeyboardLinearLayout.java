package com.legoo.haier.Extension.UltraIdeal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @class Keyboard¡¡LinearLayout
 * @author LeonNoW
 * @version 1.0
 * @date 2013-11-14
 */
public class KeyboardLinearLayout extends LinearLayout
{
	private OnKeyboardChangedListener _keyboardChangedlistener;
    
    public KeyboardLinearLayout(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
        super.onSizeChanged(w, h, oldw, oldh);
        
        if (_keyboardChangedlistener != null) 
        {
        	_keyboardChangedlistener.OnKeyboardChanged(w, h, oldw, oldh);
        }
    }
    
    public void setOnKeyboardChangedListener(OnKeyboardChangedListener listener) 
    {
    	_keyboardChangedlistener = listener;
    }
    
    public interface OnKeyboardChangedListener 
    {
        public void OnKeyboardChanged(int w, int h, int oldw, int oldh);
    }
}
