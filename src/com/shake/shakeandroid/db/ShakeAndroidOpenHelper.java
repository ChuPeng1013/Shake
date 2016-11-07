package com.shake.shakeandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ShakeAndroidOpenHelper extends SQLiteOpenHelper 
{

	public ShakeAndroidOpenHelper(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}

	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_CONTENT);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
	}
	
	//数据库新建一个表
	public static final String CREATE_CONTENT = "create table Punishment ("
											  + "id integer primary key autoincrement, "
											  + "punishment_content text)";

}
