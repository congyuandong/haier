package com.legoo.haier.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Adapter.ConsultingAdapter;
import com.legoo.haier.Application.Haier;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCancelListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.ConsultingAsyncTask;
import com.legoo.haier.AsyncTask.ConsultingListAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelListEvent;
import com.legoo.haier.Model.ConsultingModel;
import com.legoo.haier.Model.Base.ModelInterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @class More Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class ConsultingActivity extends NavigationActivity
{
	private ListView listView;
	private EditText testSubmit;
	private TaskArchon getArchon;
	private TaskArchon submitArchon;
	
	private TimerTask timerTask;
	private Timer timer;
	
	private List<ConsultingModel> listModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_consulting);
		initView();
		initTask();
		loadData();
	}
	private void initView() 
	{
		getNavigation().setTitle(getString(R.string.public_consulting));
		testSubmit = (EditText) findViewById(R.id.textCommentInput);
		listView = (ListView)findViewById(R.id.listView);
		listModel = new ArrayList<ConsultingModel>();
//		listView.setAdapter(new ConsultingAdapter(this,listModel,Haier.getInstance().getUser().getCurrent().getName()));
	}
	private void initTask()
	{
		submitArchon =new TaskArchon(this, TaskArchon.ACCESS_TYPE_SUBMIT);
		submitArchon.setWaittingEnabled(false);
		submitArchon.setSubmitButton(R.id.buttonCommentSubmit);
		submitArchon.setOnLoadedListener(new OnLoadedListener() {
			
			@Override
			public void OnLoaded(JsonEvent event) {
//				timer.schedule(timerTask, 250,5000);	
				testSubmit.setText("");
				
			}
		});
		submitArchon.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel() {
				finish();
			}
		});
		submitArchon.setOnConfirmListener(new OnConfirmListener() {
			
			@Override
			public void onConfirm() {
				submit();
			}
		});
		getArchon = new TaskArchon(this,TaskArchon.ACCESS_TYPE_GET);
		getArchon.setWaittingEnabled(false);
		getArchon.setOnLoadedListener(new OnLoadedListener() {
			
			@Override
			public void OnLoaded(JsonEvent event) {
				listModel.clear();
				for (ModelInterface model : ((ModelListEvent)event).getModelList() )
				{
					listModel.add((ConsultingModel)model);
				}
				initList();
			}
		});
		getArchon.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel() {
				timer.cancel();
				finish();
			}
		});
		getArchon.setOnConfirmListener(new OnConfirmListener() {
			
			@Override
			public void onConfirm() {
				loadData();
			}
		});
		timerTask= new TimerTask() {
			
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};
		timer = new Timer(true);
		timer.schedule(timerTask, 5000,5000);
	}
	private void loadData()
	{
		if(listModel!=null && listModel.size()>1)
		{
			ConsultingModel model = listModel.get(listModel.size()-1);
			getArchon.executeAsyncTask(new ConsultingListAsyncTask(model.getTime()));			
		}else
		{
			getArchon.executeAsyncTask(new ConsultingListAsyncTask(""));			
		}
	}
	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case 1:
				loadData();				
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	private void submit()
	{
		submitArchon.executeAsyncTask(new ConsultingAsyncTask(testSubmit.getText().toString()));
	}
	private void initList()
	{
		listView.setAdapter(new ConsultingAdapter(this,listModel,Haier.getInstance().getUser().getCurrent().getName()));		
	}
	private void verifyExtras()
	{}
}
