package com.projawe.redmine.api.model.status;

public class Status {

	protected int id;
	
	protected String name;

	protected boolean is_closed;

	public Status(int id, String name)
	{
		this.id = id;
		this.name = name;
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

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsClosed() {
		return is_closed;
	}
}
