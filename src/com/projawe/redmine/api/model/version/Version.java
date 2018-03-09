package com.projawe.redmine.api.model.version;

public class Version {

	protected int id;
	
	public String project, name, identifier, description, status, due_date, sharing, created_on, updated_on;

	public Version()
	{
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

}
