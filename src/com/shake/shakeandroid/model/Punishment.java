package com.shake.shakeandroid.model;

public class Punishment 
{
	private int id;
	private String punishmentContent;
	private boolean isSelect = false;
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getPunishmentContent() 
	{
		return punishmentContent;
	}
	public void setPunishmentContent(String punishmentContent) 
	{
		this.punishmentContent = punishmentContent;
	}
	public boolean getIsSelect() 
	{
		return isSelect;
	}
	public void setIsSelect(boolean isSelect) 
	{
		this.isSelect = isSelect;
	}
	
}
