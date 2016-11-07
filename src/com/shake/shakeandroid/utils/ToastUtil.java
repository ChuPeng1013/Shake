package com.shake.shakeandroid.utils;


import com.example.shakeandroid.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil 
{
	@SuppressWarnings("unused")
	private static boolean isLoading = false;

	@SuppressLint("InflateParams")
	public static void toastCenter(Context context, String text) 
	{
		if (context == null)
		{
			return;
		}
		Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		// ��ȡlayoutInflater����
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// ��layout�ļ�����һ��view ����
		View layout = inflater.inflate(R.layout.toast, null);
		// ʵ����Imageview��textview����
		TextView textView = (TextView) layout.findViewById(R.id.message);
		textView.setText(text);
		t.setView(layout);
		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();
	}

}
