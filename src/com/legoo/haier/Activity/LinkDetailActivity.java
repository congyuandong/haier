package com.legoo.haier.Activity;

import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Archon.TaskArchon;
import com.legoo.haier.Archon.TaskArchon.OnCancelListener;
import com.legoo.haier.Archon.TaskArchon.OnConfirmListener;
import com.legoo.haier.Archon.TaskArchon.OnLoadedListener;
import com.legoo.haier.AsyncTask.Base.JsonEvent;
import com.legoo.haier.AsyncTask.Callback.ModelEvent;
import com.legoo.haier.Model.LinkModel;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

/**
 * @class Link Detail Activity
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-13
 */
public class LinkDetailActivity extends NavigationActivity
{
	public static final String EXTRA_URL = "EXTRA_URL";
	public static final String EXTRA_TYPE = "EXTRA_TYPE";
	public static final String EXTRA_KIND = "EXTRA_KIND";
	public static final String EXTRA_ID = "EXTRA_ID";
	public static final String EXTRA_TITLE = "EXTRA_TITLE";
	public static final String EXTRA_MODEL = "EXTRA_MODEL";
	
	public static final int TYPE_SERVICE = 0;
	public static final int TYPE_TCM = 1;
	
	public static final int KIND_TEXT = 0;
	public static final int KIND_LINK = 1;
	public static final int KIND_TASK = 2;
	
	private static final String WEB_ENCODING = "utf-8";
	private static final String WEB_MINE_TYPE = "text/html";

	private String _url;
	private String _id;
	private String _title;
	private LinkModel _model;
	private int _kind;
	private int _type;
    private WebView _webView;
	private TextView _textTitle;
	private TextView _textTime;
	private TextView _textBody;
	
    private TaskArchon _taskArchon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		verifyExtras();
		if (_kind == KIND_TEXT)
		{
			super.onCreate(savedInstanceState, R.layout.activity_link_text);
			initTextView();
			initTextData(_model);
		}
		else if (_kind == KIND_TASK)
		{
			super.onCreate(savedInstanceState, R.layout.activity_link_web);
			initWebView();
			initTask();
			loadData();
		}
		else 
		{
			super.onCreate(savedInstanceState, R.layout.activity_link_web);
			initWebView();
			loadUrl(_url);
		}
		initView();
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	private void initWebView() 
	{
		_webView = (WebView) findViewById(R.id.webLinkDetail);
		_webView.setBackgroundColor(0);
		_webView.setScrollBarStyle(0);
		_webView.getSettings().setSavePassword(false);
		_webView.getSettings().setJavaScriptEnabled(true);
		_webView.getSettings().setSupportZoom(true); 
		_webView.getSettings().setBuiltInZoomControls(true);
		
//		_webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
//		_webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
//		_webView.getSettings().setUseWideViewPort(true);
		_webView.getSettings().setLoadWithOverviewMode(true);
		
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
//				if (url.equalsIgnoreCase("about:blank") == false)
//				{
//					loadUrl(url);
//				}
				return true;
			}
		});
	}
	
	private void initTextView()
	{
		_textTitle = (TextView) findViewById(R.id.textLinkDetailTitle);
		_textTime = (TextView) findViewById(R.id.textLinkDetailTime);
		_textBody = (TextView) findViewById(R.id.textLinkDetailBody);
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
				initWebData((LinkModel) ((ModelEvent) event).getModel());
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
        getNavigation().setTitle(_title == null || _title.length() == 0 ? getString(R.string.navigation_title_link_detail) : _title);
	}
	
	private void initWebData(LinkModel model)
	{
		_webView.loadDataWithBaseURL(null, model.getBody(), WEB_MINE_TYPE, WEB_ENCODING, null);
	}
	
	private void initTextData(LinkModel model)
	{
		_textTitle.setText(model.getTitle());
		_textTime.setText(model.getTime());
		_textBody.setText(Html.fromHtml(model.getBody()));
	}
	
	private void loadData()
	{
//		_taskArchon.executeAsyncTask(_type == TYPE_SERVICE ? new ServiceDetailAsyncTask(_id) : new TcmDetailAsyncTask(_id));
	}
	
	private void verifyExtras()
	{
		_kind = getIntent().getIntExtra(EXTRA_KIND, KIND_TEXT);
		_type = getIntent().getIntExtra(EXTRA_TYPE, TYPE_SERVICE);
		_url = getIntent().getStringExtra(EXTRA_URL);
		_id = getIntent().getStringExtra(EXTRA_ID);
		_title = getIntent().getStringExtra(EXTRA_TITLE);
		_model = (LinkModel) getIntent().getSerializableExtra(EXTRA_MODEL);
	}
}
