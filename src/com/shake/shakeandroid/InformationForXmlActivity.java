package com.shake.shakeandroid;

import java.util.List;
import com.example.shakeandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InformationForXmlActivity extends Activity
{
	private List<PunishmentInfo> datas;
	private ListView listview;
	private String[] info;
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_xml_listview);
		listview = (ListView) findViewById(R.id.ListView);
		datas = (List<PunishmentInfo>) getIntent().getSerializableExtra("info");
		info = new String[datas.size()];
		for(int i = 0; i < datas.size(); i++)
		{
			info[i] = datas.get(i).getContent();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(InformationForXmlActivity.this, android.R.layout.simple_expandable_list_item_1, info);
		listview.setAdapter(adapter);
	}
}
