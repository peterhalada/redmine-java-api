package com.projawe.redmine.api.model.issue;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class IssueFixedVersion {

	@XStreamAsAttribute
	protected int id;
	
	@XStreamAsAttribute
	protected String name;

	public IssueFixedVersion(int id, String name)
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
}
