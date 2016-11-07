package com.shake.shakeandroid.model;

import cn.bmob.v3.BmobObject;

@SuppressWarnings("serial")
public class ShakeData extends BmobObject 
{
	private String opinion;
	private String name;
	private String punishmentContent;
	public String getOpinion() 
	{
		return opinion;
	}
	public void setOpinion(String opinion) 
	{
		this.opinion = opinion;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getPunishmentContent() 
	{
		return punishmentContent;
	}
	public void setPunishmentContent(String punishmentContent) 
	{
		this.punishmentContent = punishmentContent;
	}
	
}
