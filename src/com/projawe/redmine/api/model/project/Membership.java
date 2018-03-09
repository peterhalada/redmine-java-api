package com.projawe.redmine.api.model.project;

public class Membership {

	protected int id;
	
	public MembershipUser user;
	public MembershipProject project;
	public MembershipRoles roles;
	
	public Membership()
	{
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
