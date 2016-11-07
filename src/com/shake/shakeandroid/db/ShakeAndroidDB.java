package com.shake.shakeandroid.db;

import java.util.ArrayList;
import java.util.List;
import com.shake.shakeandroid.model.Punishment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ShakeAndroidDB 
{
	public static final String DB_NAME = "shake_android";
	public static final int VERSION = 1;
	private static ShakeAndroidDB shakeAndroidDB;
	private SQLiteDatabase db;
	public ShakeAndroidDB(Context context)
	{
		ShakeAndroidOpenHelper dbHelper = new ShakeAndroidOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	public synchronized static ShakeAndroidDB getInstance(Context context)
	{
		if(shakeAndroidDB == null)
		{
			shakeAndroidDB = new ShakeAndroidDB(context);
		}
		return shakeAndroidDB;
	}
	//将Punishment实例存储到数据库
	public void savePunishment(Punishment punishment)
	{
		if(punishment != null)
		{
			ContentValues values = new ContentValues();
			values.put("punishment_content", punishment.getPunishmentContent());
			db.insert("Punishment", null, values);
		}
	}
	//从数据库读取到实例中
	public List<Punishment> loadPunishment()
	{
		List<Punishment> list = new ArrayList<Punishment>();
		Cursor cursor = db.query("Punishment", null, null, null, null, null, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Punishment punishment = new Punishment();
				punishment.setId(cursor.getInt(cursor.getColumnIndex("id")));
				punishment.setPunishmentContent(cursor.getString(cursor.getColumnIndex("punishment_content")));
				list.add(punishment);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	//给数据库中添加一条数据
	public boolean addData(String content)
	{
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("punishment_content", content);
		db.insert("Punishment", null, values);
		values.clear();
		return true;
	}
	//给数据库删除一条数据
	public boolean removeData(String content)
	{
		db.delete("Punishment", "punishment_content = ?", new String[]{content});
		return true;
	}
}

