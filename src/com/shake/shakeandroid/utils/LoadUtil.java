package com.shake.shakeandroid.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;

public class LoadUtil 
{
	public static String load(Context context)
	{
		InputStream in = null;
		BufferedReader reader = null;
		StringBuffer content = new StringBuffer();
		try 
		{
			in = context.getAssets().open("punishment.xml");
			reader = new BufferedReader(new InputStreamReader(in, "GB2312"));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				content.append(line);
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return content.toString();
	}
	
}
