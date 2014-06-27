package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCancelListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.QuestionDetailAsyncTask;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Model.QuestionListModel;
import com.legoo.haier.Model.QuestionDetailModel;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

/**
 * @class Question Activity
 * @author MLZX
 * @version 1.0
 * @date 2014-05-21
 */
public class QuestionDetailActivity extends NavigationActivity
{
	public static final String EXTRA_MODEL = "EXTRA_MODEL";
	private static final String WEB_ENCODING = "utf-8";
	private static final String WEB_MINE_TYPE = "text/html";

	private QuestionListModel _model;
    private WebView _webView;
	
    private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		super.onCreate(savedInstanceState, R.layout.activity_link_web);
		initWebView();
		initTask();
		loadData();
		initView();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() 
	{
		_webView = (WebView) findViewById(R.id.webLinkDetail);
		_webView.setBackgroundColor(0);
		_webView.setScrollBarStyle(0);
		_webView.getSettings().setSavePassword(false);
		_webView.getSettings().setJavaScriptEnabled(true);
		_webView.getSettings().setSupportZoom(true); 
		_webView.getSettings().setBuiltInZoomControls(true);
		_webView.getSettings().setDefaultTextEncodingName(WEB_ENCODING);
		_webView.setBackgroundColor(Color.WHITE);
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB)
		{
			_webView.getSettings().setDisplayZoomControls(false);
		}
		else 
		{
			new ZoomButtonsController(_webView).getZoomControls().setVisibility(View.GONE);
		}
		_webView.setWebChromeClient(new WebChromeClient()
	    {
	        	@Override
	        	public void onProgressChanged(WebView view, int newProgress) 
	        	{
	        		super.onProgressChanged(view, newProgress);
	        		if (newProgress >= 100)
	        		{
	        			getNavigation().hideProgress();
	        		}
	        	}
	    });
		_webView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) 
			{
				if (url.equalsIgnoreCase("about:blank") == false)
				{
					loadUrl(url);
				}
				return true;
			}
		});
	}
	private void loadUrl(String url)
	{
		getNavigation().showProgress();
		_webView.loadUrl(url);
	}
	
	private void initTask()
	{
		_taskArchon = new TaskArchon(this, TaskArchon.ACCESS_TYPE_GET);
		_taskArchon.setWaittingEnabled(true);
		_taskArchon.setOnLoadedListener(new OnLoadedListener() 
		{
			@Override
			public void OnLoaded(final JsonEvent event) 
			{
				initWebData((QuestionDetailModel) ((ModelEvent) event).getModel());
			}
		});
		_taskArchon.setOnCancelListener(new OnCancelListener()
		{
			@Override
			public void onCancel() 
			{
				finish();
			}
		});
		_taskArchon.setOnConfirmListener(new OnConfirmListener() 
		{
			@Override
			public void onConfirm() 
			{
				loadData();
			}
		});
	}

	private void initView()
	{
        getNavigation().setTitle(_model.getName());
	}
	
	private void initWebData(QuestionDetailModel model)
	{
		_webView.loadDataWithBaseURL(null, model.getContent(), WEB_MINE_TYPE, WEB_ENCODING, null);
	}
	private void loadData()
	{
		_taskArchon.executeAsyncTask(new QuestionDetailAsyncTask(_model.getID()));
	}
	
	private void verifyExtras()
	{
		_model = (QuestionListModel) getIntent().getSerializableExtra(EXTRA_MODEL);
	}
}
