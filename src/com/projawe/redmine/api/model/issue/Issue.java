package com.projawe.redmine.api.model.issue;

public class Issue {

	protected int id;
	
	protected String description = "";
	protected String subject = "";
	protected String start_date = "";
	protected String due_date = "";
	protected String done_ratio = "";
	protected String is_private = "";
	protected String estimated_hours = "";
	protected String created_on = "";
	protected String updated_on = "";
	protected String closed_on = "";

	protected IssueStatus status;
	protected IssueTracker tracker;
	protected IssuePriority priority;
	protected IssueVersion version;
	protected IssueFixedVersion fixed_version;
	protected IssueProject project;
	protected IssueAssignedTo assigned_to;
	protected IssueAuthor author;
	
	public Issue()
	{
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public IssueStatus getStatus()
	{
		return this.status;
	}

	public int getStatusId() 
	{
		if ( this.status != null )
			return this.status.getId();
		
		return -1;
	}

	public void setStatusId(int id)
	{
		if ( this.status != null )
			this.status.setId(id);
	}

	public IssueProject getProject()
	{
		return this.project;
	}

	public int getProjectId()
	{
		if ( this.project != null )
			return this.project.getId();
		
		return -1;
	}

	public void setProjectId(int id)
	{
		if ( this.project != null )
			this.project.setId(id);
	}

	public IssueAssignedTo getAssignedTo()
	{
		return this.assigned_to;
	}

	public int getAssignedToId()
	{
		if ( this.assigned_to != null )
			return this.assigned_to.getId();
		
		return -1;
	}

	public void setAssignedToId(int id)
	{
		this.assigned_to.setId(id);
	}

	public IssueFixedVersion getFixedVersion()
	{
		return this.fixed_version;
	}

	public int getFixedVersionId()
	{
		if ( this.fixed_version != null )
			return this.fixed_version.getId();
		
		return -1;
	}

	public void setFixedVersionId(int id)
	{
		this.fixed_version.setId(id);
	}

}
