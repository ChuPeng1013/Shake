package com.shake.shakeandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.example.shakeandroid.R;
import com.shake.shakeandroid.adapter.InformationContentAdapter;
import com.shake.shakeandroid.db.ShakeAndroidDB;
import com.shake.shakeandroid.model.Punishment;
import com.shake.shakeandroid.utils.ToastUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class InformationActivity extends Activity 
{
	private ListView listview;
	private Button add;
	private Button remove;
	private InformationContentAdapter adapter;
	private List<Punishment> datas;
	private ShakeAndroidDB shakeDB;
	private boolean delete = false;
	@SuppressWarnings("rawtypes")
	private Iterator iter;
	@SuppressWarnings("rawtypes")
	private Map.Entry entry;
	private  static HashMap<Integer, Punishment> trueMap;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_listview);
		listview = (ListView) findViewById(R.id.ListView);
		add = (Button) findViewById(R.id.add);
		remove = (Button) findViewById(R.id.remove);
		shakeDB = new ShakeAndroidDB(InformationActivity.this);
		datas = shakeDB.loadPunishment();
		if(datas.size() > 0)
		{
			adapter = new InformationContentAdapter(InformationActivity.this, datas);
			listview.setAdapter(adapter);
		}
		else
		{
			ToastUtil.toastCenter(InformationActivity.this, "�����");
		}
		add.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(InformationActivity.this, AddInformationActivity.class);
				startActivity(intent);
				finish();
			}
		});
		remove.setOnClickListener(new OnClickListener() 
		{
			@SuppressWarnings("rawtypes")
			public void onClick(View v) 
			{
				List<Punishment> info = new ArrayList<Punishment>();
				trueMap = adapter.getSelectedItem();
				iter = trueMap.entrySet().iterator();
				while (iter.hasNext())
				{
					entry = (Map.Entry) iter.next();
					Punishment punishment = (Punishment) entry.getValue();
					info.add(punishment);
				}
				if(info.size() > 0)
				{
					for(int i = 0; i < info.size(); i++)
					{
						delete = shakeDB.removeData(info.get(i).getPunishmentContent());
					}
					if(delete)
					{
						ToastUtil.toastCenter(InformationActivity.this, "ɾ��ɹ�");
						Intent intent = new Intent(InformationActivity.this, InformationActivity.class);
						startActivity(intent);
						finish();
					}
					else
					{
						ToastUtil.toastCenter(InformationActivity.this, "ɾ��ʧ��");
					}
				}
				else
				{
					ToastUtil.toastCenter(InformationActivity.this, "��ѡ�����е�һ��");
				}
			}
		});
	}
}
