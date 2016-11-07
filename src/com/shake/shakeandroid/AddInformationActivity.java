package com.shake.shakeandroid;

import com.example.shakeandroid.R;
import com.shake.shakeandroid.db.ShakeAndroidDB;
import com.shake.shakeandroid.db.ShakeAndroidOpenHelper;
import com.shake.shakeandroid.utils.ToastUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddInformationActivity extends Activity
{
	private EditText fromcode;
	@SuppressWarnings("unused")
	private ShakeAndroidOpenHelper dbHelper;
	private ShakeAndroidDB shakeDB;
	@SuppressWarnings("unused")
	private LinearLayout mainLayout;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_information);
		fromcode = (EditText) findViewById(R.id.fromcode);
		mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
		fromcode.setOnKeyListener(new OnKeyListener()
		{
			public boolean onKey(View v, int keyCode, KeyEvent event) 
			{
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) 
				{
					shakeDB = new ShakeAndroidDB(AddInformationActivity.this);
					if(shakeDB.addData(fromcode.getText().toString()))
					{
						ToastUtil.toastCenter(AddInformationActivity.this, "添加成功");
						fromcode.setText("");
						fromcode.setFocusable(true);
						fromcode.setFocusableInTouchMode(true);
						fromcode.requestFocus();
						fromcode.findFocus();
					}
					else
					{
						ToastUtil.toastCenter(AddInformationActivity.this, "添加失败");
						fromcode.setText("");
						fromcode.setFocusable(true);
						fromcode.setFocusableInTouchMode(true);
						fromcode.requestFocus();
						fromcode.findFocus();
					}
				}
				return false;
			}
		});
	}
	
	public void onBackPressed() 
	{
		Intent intent = new Intent(AddInformationActivity.this, InformationActivity.class);
		startActivity(intent);
		finish();
	}
}
