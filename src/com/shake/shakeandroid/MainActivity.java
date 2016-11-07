package com.shake.shakeandroid;

import java.io.Serializable;
import java.util.List;
import com.example.shakeandroid.R;
import com.shake.shakeandroid.utils.LoadUtil;
import com.shake.shakeandroid.utils.ParseUtil;
import com.shake.shakeandroid.utils.ToastUtil;
import com.shake.shakeandroid.utils.VibratorUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private SensorManager sensorManager;
	@SuppressWarnings("unused")
	private TextView content;
	private AlertDialog alertDialog;
	//private Button shake;
	private Button check;
	//private ShakeAndroidDB shakeDB;
	//private List<Punishment> datas;
	private String resultContent;
	private static final String APP_ID = "wx36eeaa4b8972e28e";
	private IWXAPI api;
	private List<PunishmentInfo> list;
	private Sensor sensor;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		content = (TextView) findViewById(R.id.content);
		//shake = (Button) findViewById(R.id.shake);
		check = (Button) findViewById(R.id.check);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		regToWx();
		/*shake.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				shakeDB = new ShakeAndroidDB(MainActivity.this);
				datas = shakeDB.loadPunishment();
				System.out.println(datas.size());
				if(datas.size() > 0)
				{
					int index=(int)(Math.random()*datas.size());
					resultContent = datas.get(index).getPunishmentContent();
					//震动100ms
					VibratorUtil.Vibrate(MainActivity.this, 1000);
					dialog("点击确定发送到微信查看结果");
				}
				else
				{
					ToastUtil.toastCenter(MainActivity.this, "请添加数据");
				}
			}
		});*/
		/*shake.setOnClickListener(new OnClickListener() 
		{
			
			public void onClick(View v) 
			{
				String xmlData = LoadUtil.load(MainActivity.this);
				list = ParseUtil.parseXMLWithPull(xmlData);
				if(list.size() > 0)
				{
					int index=(int)(Math.random()*list.size());
					resultContent = list.get(index).getContent();
					//震动100ms
					VibratorUtil.Vibrate(MainActivity.this, 1000);
					dialog("点击确定发送到微信查看结果");
				}
				else
				{
					ToastUtil.toastCenter(MainActivity.this, "请添加数据");
				}
			}
		});*/
		check.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				String xmlData = LoadUtil.load(MainActivity.this);
				System.out.println(xmlData);
				list = ParseUtil.parseXMLWithPull(xmlData);
				if(list.size() > 0)
				{
					Intent intent = new Intent(MainActivity.this, InformationForXmlActivity.class);
					intent.putExtra("info", (Serializable)list);
					startActivity(intent);
				}
				else
				{
					ToastUtil.toastCenter(MainActivity.this, "请添加XML文件");
				}
			}
		});
	}
	
	private void regToWx() 
	{
		api = WXAPIFactory.createWXAPI(MainActivity.this, APP_ID, false);
		api.registerApp(APP_ID);
	}

	protected void onDestroy() 
	{
		super.onDestroy();
		if(sensorManager != null)
		{
			sensorManager.unregisterListener(listener);
		}
	}
	
	/*private SensorEventListener listener = new SensorEventListener()
	{

		public void onSensorChanged(SensorEvent event) 
		{
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			if(xValue > 15 || yValue > 15 || zValue > 15)
			{
				shakeDB = new ShakeAndroidDB(MainActivity.this);
				datas = shakeDB.loadPunishment();
				System.out.println(datas.size());
				if(datas.size() > 0)
				{
					int index=(int)(Math.random()*datas.size());
					resultContent = datas.get(index).getPunishmentContent();
					//震动100ms
					VibratorUtil.Vibrate(MainActivity.this, 1000);
					dialog("点击确定发送到微信查看结果");
				}
				else
				{
					ToastUtil.toastCenter(MainActivity.this, "请添加数据");
				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
			
		}
	};*/
	
	
	private SensorEventListener listener = new SensorEventListener()
	{

		public void onSensorChanged(SensorEvent event) 
		{
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			if(xValue > 15 || yValue > 15 || zValue > 15)
			{
				String xmlData = LoadUtil.load(MainActivity.this);
				list = ParseUtil.parseXMLWithPull(xmlData);
				if(list.size() > 0)
				{
					int index=(int)(Math.random()*list.size());
					resultContent = list.get(index).getContent();
					//震动100ms
					VibratorUtil.Vibrate(MainActivity.this, 1000);
					dialog("点击确定发送到微信查看结果");
				}
				else
				{
					ToastUtil.toastCenter(MainActivity.this, "请添加数据");
				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
			
		}
	};
	
	
	/*public boolean onCreateOptionsMenu(android.view.Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	};
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case R.id.view:
				Toast.makeText(this, "You clicked View", Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}*/
	private void dialog(String result)//根据提示信息弹出对话框弹出对话框
	{
		alertDialog = new AlertDialog.Builder(MainActivity.this).create();
		alertDialog.setView(new EditText(MainActivity.this));
		alertDialog.setCancelable(false);
		alertDialog.show();
		sensorManager.unregisterListener(listener);
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.shelf_dialog);
		Button submit = (Button) window.findViewById(R.id.submit);
		Button cancle = (Button) window.findViewById(R.id.cancel);
		final TextView content = (TextView) window.findViewById(R.id.content);
		content.setText(result);
		submit.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				//将该app注册到微信
				api.registerApp(APP_ID);
				//初始化一个WXTextObject对象
				WXTextObject textObj = new WXTextObject();
				//textObj.text = content.getText().toString();
				textObj.text = resultContent;
				//用WXTextObject对象初始化一个WXMediaMessage对象
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = content.getText().toString();
				//msg.description = resultContent;
				//构造一个Req
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				//transaction字段用于唯一标识一个请求
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneSession;
				//调用api接口发送数据到微信
				api.sendReq(req);
				alertDialog.cancel();
			}
		});
		cancle.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
				alertDialog.cancel();
			}
		});

	}
}
