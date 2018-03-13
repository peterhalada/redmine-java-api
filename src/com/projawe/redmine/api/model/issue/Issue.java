package com.projawe.redmine.api.model.issue;

public class Issue {

	protected int id;
	
	protected String description;
	protected String subject;
	protected String start_date;
	protected String due_date;
	protected String done_ratio;
	protected String is_private;
	protected String estimated_hours;
	protected String created_on;
	protected String updated_on;
	protected String closed_on;

	protected IssueStatus status;
	protected IssueTracker tracker;
	protected IssuePriority priority;
	protected IssueVersion version;
	protected IssueFixedVersion fixed_version;
	protected IssueProject project;
	protected IssueAssignedTo assigned_to;
	protected IssueAuthor author;
	
	/* Create issue fields */
	protected String project_id;
	protected String tracker_id;
	protected String status_id;
	protected String priority_id;
	protected String category_id;
	protected String fixed_version_id;
	protected String assigned_to_id;
	protected String parent_issue_id;

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
		else
			this.status = new IssueStatus(id);
	}

	public void setStatus_Id(int id)
	{
		this.status_id = "" + id;
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
		else
			this.project = new IssueProject(id);
	}

	public void setProject_Id(int id)
	{
		this.project_id = "" + id;
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
		this.assigned_to = new IssueAssignedTo(id);
	}

	public void setAssignedTo_Id(int id)
	{
		this.assigned_to_id = "" + id;
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
		this.fixed_version = new IssueFixedVersion(id);
	}

	public void setFixedVersion_Id(int id)
	{
		this.fixed_version_id = "" + id;
	}

	public void setPriority_Id(int id)
	{
		this.priority_id = "" + id;
	}
}
