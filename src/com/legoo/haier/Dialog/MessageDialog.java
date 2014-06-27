package com.legoo.haier.Dialog;

import com.legoo.haier.R;
import com.legoo.haier.Dialog.Base.BaseDialog;
import android.app.Activity;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @class Message Dialog
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-10
 */
public class MessageDialog extends BaseDialog
{
	private TextView _textBody;
	
	public MessageDialog(Activity activity, String body) 
	{
		super(activity, R.layout.dialog_message);
		
		_textBody = (TextView) getView().findViewById(R.id.textDialogMessageBody);
		_textBody.setText(body);

		body = null;
	}
	
	public void setConfirmButton(String text, OnClickListener listener)
	{
		if (hasPrepared() == true)
		{
			Button buttonConfirm = (Button) getView().findViewById(R.id.buttonDialogMessageConfirm);
			buttonConfirm.setText(text);
			buttonConfirm.setOnClickListener(listener);
		}
	}
	
	public void setConfirmButton(OnClickListener listener)
	{
		setConfirmButton(getActivity().getString(R.string.dialog_button_confirm), listener);
	}
	
	public void setCancelButton(String text, OnClickListener listener)
	{
		if (hasPrepared() == true)
		{
			ViewStub stubNotify = (ViewStub) getView().findViewById(R.id.stubDialogMessageButtonCancel);
			stubNotify.inflate();
			stubNotify = null;
			Button buttonCancel = (Button) getView().findViewById(R.id.buttonDialogMessageCancel);
			buttonCancel.setText(text);
			buttonCancel.setOnClickListener(listener);
		}
	}
	
	public void setCancelButton(OnClickListener listener)
	{
		setCancelButton(getActivity().getString(R.string.dialog_button_cancel), listener);
	}
	
}
