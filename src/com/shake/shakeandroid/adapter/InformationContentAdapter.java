package com.shake.shakeandroid.adapter;

import java.util.HashMap;
import java.util.List;
import com.example.shakeandroid.R;
import com.shake.shakeandroid.model.Punishment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class InformationContentAdapter extends BaseAdapter 
{
	@SuppressWarnings("unused")
	private Context context;
	private LayoutInflater mInflater;
	private List<Punishment> infoList;
	private Punishment punishmentValues;
	@SuppressLint("UseSparseArrays")
	private  static HashMap<Integer, Punishment> trueMap = new HashMap<Integer, Punishment>();//����HashMap��ű�ѡ�еĶ���
	public InformationContentAdapter(Context context, List<Punishment> datas)
	{
		this.context = context;
		this.infoList = datas;
		mInflater = LayoutInflater.from(context);
		
	}
	public int getCount() 
	{
		return infoList.size();
	}

	public Object getItem(int position) 
	{
		return infoList.get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		final int index = position;
		Punishment punishment = (Punishment) getItem(position);
		View view = mInflater.inflate(R.layout.information_listview_item, null);
		CheckBox select = (CheckBox) view.findViewById(R.id.select);
		TextView content = (TextView) view.findViewById(R.id.content);
		content.setText(punishment.getPunishmentContent());
		select.setOnClickListener(new OnClickListener() 
		{
			
			public void onClick(View v) 
			{
				punishmentValues = infoList.get(index);
				if(punishmentValues.getIsSelect())
				{
					punishmentValues.setIsSelect(false);
					trueMap.remove(index);
				}
				else
				{
					punishmentValues.setIsSelect(true);
					trueMap.put(index, punishmentValues);
				}
				System.out.println("第"+ index +"项的值为" + punishmentValues.getIsSelect());
			}
		});
		
		return view;
	}
	
	public HashMap<Integer, Punishment> getSelectedItem()
	{
		return trueMap;
	}

}
