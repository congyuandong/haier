package com.legoo.haier.Dialog;

import com.legoo.haier.R;
import com.legoo.haier.Dialog.Base.BaseDialog;
import android.app.Activity;
import android.widget.ImageView;

/**
 * @class Image Dialog
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-13
 */
public class ImageDialog extends BaseDialog 
{
	private ImageView imageProgress;
	
	public ImageDialog(Activity activity) 
	{
		super(activity, R.layout.dialog_image,true);
		setWidthRatio(BaseDialog.RATIO_FULL);
		
		imageProgress = (ImageView)getView().findViewById(R.id.imageDialogImage);
		imageProgress.setOnTouchListener(new MulitPointTouchListener(imageProgress));
	}
	
	public void show(ImageView image)
	{
		imageProgress.setImageDrawable(image.getDrawable());
		super.show();
	}
}
