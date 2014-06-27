package com.legoo.haier.Archon;

import java.util.Collection;
import com.legoo.haier.R;
import com.legoo.haier.AsyncTask.Base.BaseAsyncTask;
import com.legoo.haier.AsyncTask.Base.BaseListener;
import com.legoo.haier.AsyncTask.Base.EventInterface;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Dialog.MessageDialog;
import com.legoo.haier.Dialog.WaitingDialog;
import com.legoo.haier.Dialog.Base.BaseDialog;
import com.legoo.haier.Extension.ApplicationUtils;
import com.legoo.haier.Handler.Json.JsonHandler;
import com.legoo.haier.Model.Base.ModelInterface;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @class Task Archon
 * @author LeonNoW
 * @version 1.0
 * @date 2013-10-10
 */
public class TaskArchon 
{
	public static final int ACCESS_TYPE_GET = 0;
	public static final int ACCESS_TYPE_SUBMIT = 1;
	
	private Context _context;
	private Activity _contextActivity;
	
	private ModelInterface _model;
	private Collection<? extends ModelInterface> _modelList;
	
	private BaseAsyncTask _modelAsyncTask;
	private WaitingDialog _waittingDialog;
	private MessageDialog _messageDialog;
	
	private View _buttonSubmit;
	
	private int _doType;
	private String _doMessage;
	private String _errorMessage;
	private boolean _isCustomError;
	
	private boolean _confirmEnabled = true;
	private boolean _waitingEnabled = true;
	private boolean _skipDetectNetwork = false;
	
	private OnLoadedListener _loadedListener;
	private OnConfirmListener _confirmListener;
	private OnCancelListener _cancelListener;
	private OnSucessedListener _sucessedListener;
	private OnCheckInputListener _checkInputListener;
	
	private boolean _isRunning;
	
	public TaskArchon(Context context, int accesstype)
	{
		_context = context;
		_doType = accesstype;
		_isCustomError = false;
		initArchon();
	}
	
	public TaskArchon(Context context, int accesstype, boolean customerror)
	{
		_context = context;
		_doType = accesstype;
		_isCustomError = customerror;
		initArchon();
	}
	
	private void initArchon()
	{
		if (_doType == ACCESS_TYPE_GET)
		{
			_doMessage = _context.getString(R.string.dialog_do_get);
		}
		else 
		{
			_doMessage = _context.getString(R.string.dialog_do_submit);
		}
	}
	
	private String getDefaultMessage()
	{
		return String.format(_context.getString(R.string.dialog_body_error), _doMessage);
	}
	
	public void setUseResponseError()
	{
		_isCustomError = true;
	}
	
	private void setModel(ModelInterface model)
	{
		_model = model;
	}
	
	private void setModelList(Collection<? extends ModelInterface> modelList)
	{
		_modelList = modelList;
	}
	
	public ModelInterface getModel()
	{
		return _model;
	}
	
	public Collection<? extends ModelInterface> getModelList()
	{
		return _modelList;
	}
	
	
	private void setAsyncTask(BaseAsyncTask asyncTask)
	{
		_modelAsyncTask = asyncTask;
		_modelAsyncTask.addOnFinishListener(new BaseListener()
		{
			@Override
			public void EventActivated(EventInterface event) 
			{
				_isRunning = false;
				JsonEvent xmlEvent = (JsonEvent)event;
				if (xmlEvent.getError() == JsonHandler.ERROR_CONNECTION)
				{
					hideWaittingDialog();
					showRetryDialog();
				}
				else if (xmlEvent.getError() == JsonHandler.ERROR_HANDLER)
				{
					hideWaittingDialog();
					if (_isCustomError == true && xmlEvent.getMessage() != null && xmlEvent.getMessage().length() > 0)
					{
						setErrorMessage(xmlEvent.getMessage());
					}
					else if (_errorMessage != null && _errorMessage.length() > 0)
					{
						setErrorMessage(_errorMessage);
					}
					else 
					{
						setErrorMessage(getDefaultMessage());
					}
					showErrorDialog();
				}
				else if (_context != null)
				{
					doAsyncTaskCallBack(xmlEvent);
				}
			}
		});
	}
	
	private void doAsyncTaskCallBack(JsonEvent event) 
	{
		if (event instanceof ModelEvent)
		{
			ModelEvent model = (ModelEvent) event;
			setModel(model.getModel());
		}
		else if (event instanceof ModelListEvent)
		{
			ModelListEvent modelList = (ModelListEvent) event;
			setModelList(modelList.getModelList());
		}
		onLoaded(event);
	}
	
	public void executeAsyncTask(BaseAsyncTask asyncTask)
	{
		if (_isRunning == false)
		{
			_isRunning = true;
			setAsyncTask(asyncTask);
			executeAsyncTask();
		}
	}
	
	private void executeAsyncTask()
	{
		if (_skipDetectNetwork == true || ApplicationUtils.detectNetworkState() == true)
		{
			readyWaittingDialog();
			new Handler().postDelayed(new Runnable() 
			{
				@Override
				public void run() 
				{
					_modelAsyncTask.executeEnhanced();
				}
			}, 500);
		}
		else
		{
			_isRunning = false;
			showNetworkDialog();
		}
	}
	
	protected void readyWaittingDialog()
	{
		showWaittingDialog();
	}
	
	private void showWaittingDialog()
	{
		if (_waitingEnabled)
		{
			BaseDialog.prepare(_waittingDialog);
			_waittingDialog = new WaitingDialog(((Activity)_context));
			_waittingDialog.show();
		}
	}
	
	private void hideWaittingDialog()
	{
		if (_waittingDialog != null)
		{
			_waittingDialog.dismiss();
		}
	}
	
	private void showRetryDialog()
	{
		if (_confirmEnabled)
		{
			BaseDialog.prepare(_messageDialog);
			_messageDialog = new MessageDialog(getDialogBuilder(), String.format(_context.getString(R.string.dialog_body_retry), _doMessage));
			_messageDialog.setConfirmButton(_context.getString(R.string.dialog_button_retry), new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					_messageDialog.dismiss();
					onConfirm();
				}
			});
			_messageDialog.setCancelButton(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					_messageDialog.dismiss();
					onCancel();
				}
			});
			_messageDialog.setOnDismissListener(new OnDismissListener() 
			{
				@Override
				public void onDismiss(DialogInterface dialog) 
				{
					if (_messageDialog.isDismiss() == false)
					{
						onCancel();
					}
				}
			});
			_messageDialog.show();
		}
		else 
		{
			onCancel();
		}
	}
	
	private void showErrorDialog()
	{
		if (_confirmEnabled && _context != null)
		{
			BaseDialog.prepare(_messageDialog);
			_messageDialog = new MessageDialog(getDialogBuilder(), _errorMessage);
			_messageDialog.setConfirmButton(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					_messageDialog.dismiss();
					onCancel();
				}
			});
			_messageDialog.setOnDismissListener(new OnDismissListener() 
			{
				@Override
				public void onDismiss(DialogInterface dialog) 
				{
					onCancel();
				}
			});
			_messageDialog.show();
		}
		else 
		{
			onCancel();
		}
	}
	
	private void showNetworkDialog()
	{
		if (_confirmEnabled && _context != null)
		{
			BaseDialog.prepare(_messageDialog);
			_messageDialog = new MessageDialog(getDialogBuilder(), _context.getString(R.string.dialog_body_network));
			_messageDialog.setConfirmButton(_context.getString(R.string.dialog_button_network), new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					ApplicationUtils.openNetworkConfig(((Activity)_context));
					_messageDialog.dismiss();
					showRetryDialog();
				}
			});
			_messageDialog.setCancelButton(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					_messageDialog.dismiss();
					onCancel();
				}
			});
			_messageDialog.setOnDismissListener(new OnDismissListener() 
			{
				@Override
				public void onDismiss(DialogInterface dialog) 
				{
					if (_messageDialog.isDismiss() == false)
					{
						onCancel();
					}
				}
			});
			_messageDialog.show();
		}
		else 
		{
			onCancel();
		}
	}
	
	public void showSucessDialog(String text)
	{
		if (_confirmEnabled)
		{
			BaseDialog.prepare(_messageDialog);
			_messageDialog = new MessageDialog(getDialogBuilder(), text);
			_messageDialog.setConfirmButton(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					_messageDialog.dismiss();
				}
			});
			_messageDialog.setOnDismissListener(new OnDismissListener() 
			{	
				@Override
				public void onDismiss(DialogInterface dialog) 
				{
					onSucessed();
				}
			});
			_messageDialog.show();
		}
		else 
		{
			onSucessed();
		}
	}
	
	public void setDialogBuilder(Activity activity)
	{
		_contextActivity = activity;
	}
	
	private Activity getDialogBuilder()
	{
		return _contextActivity != null ? _contextActivity : (Activity) _context;
	}
	
	public void setDoMessage(String message)
	{
		_doMessage = message;
	}
	
	public void setErrorMessage(String message)
	{
		_errorMessage = message;
	}

	public void setConfirmEnabled(boolean enabled)
	{
		_confirmEnabled = enabled;
	}
	
	public void setWaittingEnabled(boolean enabled)
	{
		_waitingEnabled = enabled;
	}
	
	public void setSkipDetectNetwork(boolean skip)
	{
		_skipDetectNetwork = skip;
	}
	
	public void setSubmitButton(int button)
	{
		setSubmitButton((Button) ((Activity)_context).findViewById(button));
	}
	
	public void setSubmitButton(Button button)
	{
		_buttonSubmit = button;
		_buttonSubmit.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (onCheckInput() == true)
				{
					onConfirm();
				}
			}
		});
	}
	
	private void onLoaded(JsonEvent event) 
	{
		if (_loadedListener != null)
		{
			_loadedListener.OnLoaded(event);
		}
		hideWaittingDialog();
	}
	
	private void onConfirm() 
	{
		if (_confirmListener != null)
		{
			_confirmListener.onConfirm();
		}
	}
	
	private void onCancel() 
	{
		if (_cancelListener != null)
		{
			_cancelListener.onCancel();
		}
	}
	
	private void onSucessed() 
	{
		if (_sucessedListener != null)
		{
			_sucessedListener.onSucessed();
		}
		else 
		{
			((Activity)_context).finish();
		}
	}
	
	private boolean onCheckInput() 
	{
		if (_checkInputListener != null)
		{
			return _checkInputListener.onCheckInput();
		}
		else 
		{
			return true;
		}
	}
	
	public void setOnLoadedListener(OnLoadedListener listener)
	{
		_loadedListener = listener;
	}
	
	public static interface OnLoadedListener
	{
		public void OnLoaded(JsonEvent event);
	}
	
	public void setOnSucessedListener(OnSucessedListener listener)
	{
		_sucessedListener = listener;
	}
	
	public static interface OnSucessedListener
	{
		public void onSucessed();
	}
	
	public void setOnConfirmListener(OnConfirmListener listener)
	{
		_confirmListener = listener;
	}
	
	public static interface OnConfirmListener
	{
		public void onConfirm();
	}
	
	public void setOnCancelListener(OnCancelListener listener)
	{
		_cancelListener = listener;
	}
	
	public static interface OnCancelListener
	{
		public void onCancel();
	}
	
	public void setOnCheckInputListener(OnCheckInputListener listener)
	{
		_checkInputListener = listener;
	}
	
	public static interface OnCheckInputListener
	{
		public boolean onCheckInput();
	}
}
