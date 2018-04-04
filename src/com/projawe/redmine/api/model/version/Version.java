package com.projawe.redmine.api.model.version;

public class Version {

	protected int id;
	
	public String project, name, identifier, description, status, due_date, sharing, created_on, updated_on;

	public static final String STATUS_OPEN = "open";
	public static final String STATUS_CLOSED = "closed";
	
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

	public String getStatus() {
		return this.status;
	}

	public boolean isOpen()
	{
		return STATUS_OPEN.equals(this.status);
	}
}
