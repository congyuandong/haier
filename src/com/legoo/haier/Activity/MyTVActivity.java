package com.legoo.haier.Activity;


import com.legoo.haier.R;
import com.legoo.haier.Activity.Base.NavigationActivity;
import com.legoo.haier.Application.Haier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * MyTvActivity
 * @author Congyuandong
 */
public class MyTVActivity extends NavigationActivity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private TextView deviceid,macaddress ;
	private Button btn_bind;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_mytv);
		initView();
		checkDeviceid();
		
		//点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		
		btn_bind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyTVActivity.this, QRScanActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		
		
	}
	
	
	private void checkDeviceid() {
		//isRegister() 用户已经登录
		if(Haier.getInstance().getUser().getCurrent().isRegister()){	
			
		}
		
	}


	private void initView() {
		deviceid = (TextView) findViewById(R.id.deviceid); 
		macaddress = (TextView) findViewById(R.id.macaddress); 
		btn_bind = (Button) findViewById(R.id.bind);
		getNavigation().setTitle(getString(R.string.navigation_title_user_mytv));
        getNavigation().setReturn(getString(R.string.navigation_return));
	}


	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				macaddress.setText(bundle.getString("result"));
				//显示
				//mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
    }	

}
