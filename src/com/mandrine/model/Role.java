//$Id$
package com.mandrine.model;

public enum Role {

	ADMIN(2),
	USER(1);
	
	private final int accessLevel;
	
	Role(int i) {
		this.accessLevel=i;
	}

	/**
	 * @return the accessLevel
	 */
	public int getAccessLevel() {
		return accessLevel;
	}
	static Role getRole(int accessLevel)
	{
		for(Role role:Role.values())
		{
			if(role.getAccessLevel()==accessLevel)
			{
				return role;
			}
		}
		return null;
	}
	
	
}
