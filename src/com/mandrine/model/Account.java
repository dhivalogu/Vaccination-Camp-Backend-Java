
package com.mandrine.model;

public class Account {
	private String username;
	private String password;
	private int accessLevel;
	
	public String getUsername()
	{
		return this.username;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setAccessLevel(int accessLevel)
	{
		this.accessLevel=accessLevel;
	}
	public int getAccessLevel()
	{
		return this.accessLevel;
	}
}
