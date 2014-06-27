package com.legoo.haier.Dialog;

import com.legoo.haier.R;
import com.legoo.haier.Application.Hospital;
import com.legoo.haier.Dialog.Base.BaseDialog;
import android.app.Activity;
import android.widget.ImageView;

/**
 * @class Waiting Dialog
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class WaitingDialog extends BaseDialog 
{
	private ImageView imageProgress;
	
	public WaitingDialog(Activity activity) 
	{
		super(activity, R.layout.dialog_waiting);
		setWidthRatio(BaseDialog.RATIO_MIDDLE);
		
		imageProgress = (ImageView)getView().findViewById(R.id.imageDialogWaiting);
		Hospital.getInstance().getAnimation().startProgress(imageProgress);
	}
}
