package com.projawe.redmine.api.model.project;

public class Project {

	protected int id;
	
	public String name, identifier, description, status, is_public, created_on, updated_on;

	public Project()
	{
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
}
