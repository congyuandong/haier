package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Application.HaierSettings;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCheckInputListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.Archon.TaskArchon.OnSucessedListener;
import com.legoo.haier.AsyncTask.FeedbackAddAsyncTask;
import com.legoo.haier.AsyncTask.FeedbackDeleteAsyncTask;
import com.legoo.haier.AsyncTask.FeedbackEditAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.Model.FeedbackModel;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @class Feedback Detail Activity
 * @author Mlzx
 * @version 1.0
 * @date 2014-05-08
 */
public class FeedbackDetailActivity extends NavigationActivity
{
	public static final String EXTRA_MODEL = "EXTRA_MODEL";
	public static final String TYPE_MODEL = "TYPE";
	public static final int EDIT = 0;
	public static final int DELETE = 1;
	public static final int ADD = 2;
	
	private FeedbackModel _model;
	private int _type;
	
	private TextView _textName;
	private TextView _textIDCard;
	private TextView _textOpinion;
	private TextView _textSuggestion;
	
	
	private LinearLayout _layoutAdd;
	private LinearLayout _layoutEdit;
	
	private TaskArchon _taskEdit;
	private TaskArchon _taskDelete;
	private TaskArchon _taskAdd;

	
	private OnCheckInputListener checkinput=new OnCheckInputListener() {
		
		@Override
		public boolean onCheckInput() {
			// TODO Auto-generated method stub

//			Hospital.getInstance().getToast().show(_textOpinion.getText().length()+"");
			if(_textName.getText().length() == 0)
			{
				Haier.getInstance().getAnimation().startShake(_textName);
				return false;
			}
			else if (_textIDCard.getText().length() == 0)
			{
				Haier.getInstance().getAnimation().startShake(_textIDCard);
				return false;
			}
			else if (_textOpinion.getText().length()==0)
			{
				Haier.getInstance().getAnimation().startShake(_textOpinion);
				return false;
			}
			else if (_textSuggestion.getText().length()==0)
			{
				Haier.getInstance().getAnimation().startShake(_textSuggestion);
				return false;
			}
			else if (_textOpinion.getText().length() >= HaierSettings.FEEDBACK_MAX_LONGTH)
			{
				Haier.getInstance().getToast().show(getString(R.string.toast_data_long_error));
				Haier.getInstance().getAnimation().startShake(_textOpinion);
				return false;
			}
			else if (_textSuggestion.getText().length() >= HaierSettings.FEEDBACK_MAX_LONGTH)
			{
				Haier.getInstance().getToast().show(getString(R.string.toast_data_long_error));
				Haier.getInstance().getAnimation().startShake(_textSuggestion);
				return false;
			}
			return true;
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_feedback_detail);
		initView();
		initData();
		loadData();
		if(_type==ADD)
		{
			initSubmitAdd();
		}
		else
		{
			initSubmitEdit();
			initSubmitDelete();
		}
	}
	
	private void initView() 
	{
		_textName = (TextView) findViewById(R.id.textFeedbackDetailName);
		_textIDCard = (TextView) findViewById(R.id.textFeedbackDetailIDCard);
		_textOpinion = (TextView) findViewById(R.id.textFeedbackDetailOpinion);
		_textSuggestion = (TextView) findViewById(R.id.textFeedbackDetailSuggestion);
		
		_layoutAdd = (LinearLayout) findViewById(R.id.layoutFeedbackAdd);
		_layoutEdit = (LinearLayout)findViewById(R.id.layoutFeedbackEdit);
		if(_type==ADD)
		{
	        getNavigation().setTitle(getString(R.string.navigation_title_feedback_add));
	        _layoutEdit.setVisibility(View.GONE);
	        _layoutAdd.setVisibility(View.VISIBLE);
		}
		else
		{
	        getNavigation().setTitle(getString(R.string.navigation_title_feedback_detail));
	        _layoutEdit.setVisibility(View.VISIBLE);
	        _layoutAdd.setVisibility(View.GONE);
		}
	}
	
	private void initData()
	{
		if(_type==ADD)
		{
			_textName.setText("");
			_textIDCard.setText("");
			_textOpinion.setText("");
			_textSuggestion.setText("");
		}
		else
		{
			_textName.setText(_model.getName());
			_textIDCard.setText(_model.getIDCard());
			_textOpinion.setText(_model.getOpinion());
			_textSuggestion.setText(_model.getSuggestions());
		}
	}
	
	private void initSubmitEdit()
	{
		_taskEdit = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskEdit.setWaittingEnabled(true);
		_taskEdit.setSubmitButton(R.id.buttonFeedbackDetailSubmit);
		_taskEdit.setOnCheckInputListener(checkinput);
		_taskEdit.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				_taskEdit.showSucessDialog(getString(R.string.public_submit_succeed));
			}
		});
		_taskEdit.setOnSucessedListener(new OnSucessedListener()
		{
			@Override
			public void onSucessed() 
			{
				setResult(RESULT_OK);
				finish();
			}
		});
		_taskEdit.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				submitDataEdit();
			}
		});
	}

	private void initSubmitDelete()
	{
		_taskDelete = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskDelete.setWaittingEnabled(true);
		_taskDelete.setSubmitButton(R.id.buttonFeedbackDetailDelete);
		_taskDelete.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				_taskDelete.showSucessDialog(getString(R.string.public_submit_succeed));
			}
		});
		_taskDelete.setOnSucessedListener(new OnSucessedListener()
		{
			@Override
			public void onSucessed() 
			{
				setResult(RESULT_OK);
				finish();
			}
		});
		_taskDelete.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				submitDataDelete();
			}
		});
	}

	private void initSubmitAdd()
	{
		_taskAdd = new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT, true);
		_taskAdd.setWaittingEnabled(true);
		_taskAdd.setSubmitButton(R.id.buttonFeedbackDetailAdd);
		_taskAdd.setOnCheckInputListener(checkinput);
		_taskAdd.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				_taskAdd.showSucessDialog(getString(R.string.public_submit_succeed));
			}
		});
		_taskAdd.setOnSucessedListener(new OnSucessedListener()
		{
			@Override
			public void onSucessed() 
			{
				setResult(RESULT_OK);
				finish();
			}
		});
		_taskAdd.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				submitDataAdd();
			}
		});
		
	}
	private void loadData()
	{
	}
	private void submitDataEdit()
	{
		_taskEdit.executeAsyncTask(new FeedbackEditAsyncTask(
				_textName.getText().toString(),
				_textOpinion.getText().toString(),
				_textSuggestion.getText().toString(),
				_textIDCard.getText().toString(),
				_model.getID()));
	}

	private void submitDataDelete()
	{
		_taskDelete.executeAsyncTask(new FeedbackDeleteAsyncTask(_model.getID()));
	}
	
	private void submitDataAdd()
	{
		_taskAdd.executeAsyncTask(new FeedbackAddAsyncTask(
				_textName.getText().toString(),
				_textOpinion.getText().toString(),
				_textSuggestion.getText().toString(),
				_textIDCard.getText().toString()));		
	}
	
	private void verifyExtras()
	{
		_model = (FeedbackModel) getIntent().getSerializableExtra(EXTRA_MODEL);
		_type =  (Integer) getIntent().getSerializableExtra(TYPE_MODEL);
	}
}
