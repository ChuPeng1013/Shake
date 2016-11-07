package com.shake.shakeandroid;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PunishmentInfo implements Serializable
{
	private String id;
	private String content;
	public String getId() 
	{
		return id;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getContent() 
	{
		return content;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	
}
