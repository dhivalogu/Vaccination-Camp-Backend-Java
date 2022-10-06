
package com.mandrine.model;

import com.mandrine.db.Column;

public class Account extends Resource {
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="ACCESS_LEVEL")
	private int accessLevel;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public int getAccessLevel() {
		return this.accessLevel;
	}
}
