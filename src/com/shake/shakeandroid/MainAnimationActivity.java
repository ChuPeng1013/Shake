package com.shake.shakeandroid;

import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.example.shakeandroid.R;
import com.shake.shakeandroid.model.ShakeData;
import com.shake.shakeandroid.utils.ConstantsUtil;
import com.shake.shakeandroid.utils.LoadUtil;
import com.shake.shakeandroid.utils.ParseUtil;
import com.shake.shakeandroid.utils.ToastUtil;
import com.shake.shakeandroid.utils.VibratorUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressWarnings("deprecation")
public class MainAnimationActivity extends Activity 
{
	private RelativeLayout mImgUp;
	private RelativeLayout mImgDn;
	private RelativeLayout mTitle;
	private SlidingDrawer mDrawer;
	private Button mDrawerBtn;
	private Button back;
	private ImageButton check;
	private ListView mListview;
	private List<PunishmentInfo> list;
	private SensorManager sensorManager;
	private Sensor sensor;
	private AlertDialog alertDialog;
	private String resultContent;
	private IWXAPI api;
	private MediaPlayer player;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_animation_activity);
		//Bmob的初始化
		Bmob.initialize(MainAnimationActivity.this, ConstantsUtil.Application_ID);
		//摇一摇向上半部分的布局
		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
		//摇一摇向下半部分的布局
		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
		//标题栏布局
		mTitle = (RelativeLayout) findViewById(R.id.shake_title_bar);
		//滑动式抽屉控件
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		//滑动式抽屉控件的按钮
		mDrawerBtn = (Button) findViewById(R.id.handle);
		//滑动式抽屉中的列表
		mListview = (ListView) findViewById(R.id.listview);
		//标题栏返回按钮
		back = (Button) findViewById(R.id.back);
		//标题栏检查按钮
		check = (ImageButton) findViewById(R.id.check);
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		api = WXAPIFactory.createWXAPI(MainAnimationActivity.this, ConstantsUtil.APP_ID, false);
		api.registerApp(ConstantsUtil.APP_ID);
		
		
		mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() 
		{
			
			public void onDrawerOpened() 
			{
				mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_down));
				TranslateAnimation titleup = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f);
				titleup.setDuration(200);
				titleup.setFillAfter(true);
				mTitle.startAnimation(titleup);
				String xmlData = LoadUtil.load(MainAnimationActivity.this);
				System.out.println(xmlData);
				list = ParseUtil.parseXMLWithPull(xmlData);
				
				if(list.size() > 0)
				{
					String[] info = new String[list.size()];
					for(int i = 0; i < list.size(); i++)
					{
						info[i] = list.get(i).getContent();
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainAnimationActivity.this, android.R.layout.simple_expandable_list_item_1, info);
					Animation animation = AnimationUtils.loadAnimation(MainAnimationActivity.this, R.anim.anim_listview_item);
					LayoutAnimationController controller = new LayoutAnimationController(animation);
					controller.setDelay(0.5f);
					controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
					mListview.setLayoutAnimation(controller);
					mListview.setAdapter(adapter);
				}
				else
				{
					ToastUtil.toastCenter(MainAnimationActivity.this, "�����XML�ļ�");
				}
			}
		});
		mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() 
		{
			
			public void onDrawerClosed() 
			{
				mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_up));
				TranslateAnimation titledn = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0f);
				titledn.setDuration(200);
				titledn.setFillAfter(false);
				mTitle.startAnimation(titledn);
			}
		});
		back.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				finish();
			}
		});
		check.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				//startAnimation();
				showPopupMenu(check);
			}
		});
		
	}
	
	private void showPopupMenu(View view)
	{
	    PopupMenu popupMenu = new PopupMenu(this, view);
	    popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
	    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
	    {
			public boolean onMenuItemClick(MenuItem item) 
			{
				
				switch (item.getItemId()) 
				{
				case R.id.shake:
					//ToastUtil.toastCenter(MainAnimationActivity.this, item.getTitle().toString());
					startAnimation();
					break;

				case R.id.feedback:
					//ToastUtil.toastCenter(MainAnimationActivity.this, item.getTitle().toString());
					FeedbackDialog();
				default:
					break;
				}
				return false;
			}
		});
	    popupMenu.setOnDismissListener(new OnDismissListener() 
	    {
			
			public void onDismiss(PopupMenu menu) 
			{
				//ToastUtil.toastCenter(MainAnimationActivity.this, "�ر�PopupMenu");
			}
		});
	    
	    popupMenu.show();
	    
	}
	

	private void startAnimation()
	{
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		mImgUp.startAnimation(animup);
		animup.setAnimationListener(new AnimationListener() 
		{
			public void onAnimationStart(Animation animation) 
			{
				
				player = MediaPlayer.create(MainAnimationActivity.this, R.raw.sound);
				player.setLooping(false);
		        player.start();
			}
			
			public void onAnimationRepeat(Animation animation) 
			{
			}
			
			public void onAnimationEnd(Animation animation) 
			{
			}
		});
				
		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		mImgDn.startAnimation(animdn);
		animdn.setAnimationListener(new AnimationListener() 
		{
			
			public void onAnimationStart(Animation animation) 
			{
				
			}
			
			public void onAnimationRepeat(Animation animation) 
			{
				
			}
			
			public void onAnimationEnd(Animation animation) 
			{
				//��100ms
				VibratorUtil.Vibrate(MainAnimationActivity.this, 1000);
				dialog("���ȷ�����͵�΢�Ų鿴���");
			}
		});
	}
	private SensorEventListener listener = new SensorEventListener()
	{

		public void onSensorChanged(SensorEvent event) 
		{
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			if(xValue > 15 || yValue > 15 || zValue > 15)
			{
				String xmlData = LoadUtil.load(MainAnimationActivity.this);
				list = ParseUtil.parseXMLWithPull(xmlData);
				if(list.size() > 0)
				{
					int index=(int)(Math.random()*list.size());
					resultContent = list.get(index).getContent();
					startAnimation();
				}
				else
				{
					ToastUtil.toastCenter(MainAnimationActivity.this, "��������");
				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) 
		{
			
		}
	};
	
	//�����ʾ��Ϣ�����Ի��򵯳��Ի���
	private void dialog(String result)
	{
		alertDialog = new AlertDialog.Builder(MainAnimationActivity.this).create();
		alertDialog.setView(new EditText(MainAnimationActivity.this));
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
				/*ShakeData opinion = new ShakeData();
				opinion.setOpinion(resultContent);
				opinion.save(new SaveListener<String>() 
				{
					public void done(String objectId, BmobException e) 
					{
						
					}
				});*/
				
				//����appע�ᵽ΢��
				api.registerApp(ConstantsUtil.APP_ID);
				//��ʼ��һ��WXTextObject����
				WXTextObject textObj = new WXTextObject();
				//textObj.text = content.getText().toString();
				textObj.text = resultContent;
				//��WXTextObject�����ʼ��һ��WXMediaMessage����
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = content.getText().toString();
				//msg.description = resultContent;
				//����һ��Req
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				//transaction�ֶ�����Ψһ��ʶһ������
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneSession;
				//����api�ӿڷ�����ݵ�΢��
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
	
	//�������Ի���
	private void FeedbackDialog()
	{
		alertDialog = new AlertDialog.Builder(MainAnimationActivity.this).create();
		alertDialog.setView(new EditText(MainAnimationActivity.this));
		alertDialog.setCancelable(false);
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.feedback_dialog);
		Button submit = (Button) window.findViewById(R.id.submit);
		Button cancel = (Button) window.findViewById(R.id.cancel);
		final EditText content = (EditText) window.findViewById(R.id.content);
		submit.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				if(TextUtils.isEmpty(content.getText().toString()))
				{
					ToastUtil.toastCenter(MainAnimationActivity.this, "����д�������ύ");
				}
				else
				{
					ShakeData opinion = new ShakeData();
					opinion.setOpinion(content.getText().toString());
					opinion.save(new SaveListener<String>() 
					{
						public void done(String objectId, BmobException e) 
						{
							if(e == null)
							{
								ToastUtil.toastCenter(MainAnimationActivity.this, "�ύ�ɹ�");
								alertDialog.cancel();
							}
							else
							{
								ToastUtil.toastCenter(MainAnimationActivity.this, "�ύʧ�ܣ����Ժ�����");
							}
						}
					});
				}
				
				//System.out.println(content.getText().toString());
			}
		});
		cancel.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				alertDialog.cancel();
			}
		});
	}
	
}
