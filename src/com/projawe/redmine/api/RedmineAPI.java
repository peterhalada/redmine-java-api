package com.projawe.redmine.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.projawe.redmine.api.model.issue.Issue;
import com.projawe.redmine.api.model.issue.IssueAssignedTo;
import com.projawe.redmine.api.model.issue.IssueAuthor;
import com.projawe.redmine.api.model.issue.IssueFixedVersion;
import com.projawe.redmine.api.model.issue.IssuePriority;
import com.projawe.redmine.api.model.issue.IssueProject;
import com.projawe.redmine.api.model.issue.IssueStatus;
import com.projawe.redmine.api.model.issue.IssueTracker;
import com.projawe.redmine.api.model.issue.IssueVersion;
import com.projawe.redmine.api.model.issue.Issues;
import com.projawe.redmine.api.model.project.Membership;
import com.projawe.redmine.api.model.project.MembershipProject;
import com.projawe.redmine.api.model.project.MembershipRole;
import com.projawe.redmine.api.model.project.MembershipRoles;
import com.projawe.redmine.api.model.project.MembershipUser;
import com.projawe.redmine.api.model.project.Memberships;
import com.projawe.redmine.api.model.project.Project;
import com.projawe.redmine.api.model.project.Projects;
import com.projawe.redmine.api.model.status.Status;
import com.projawe.redmine.api.model.status.Statuses;
import com.projawe.redmine.api.model.user.User;
import com.projawe.redmine.api.model.user.Users;
import com.projawe.redmine.api.model.version.Version;
import com.projawe.redmine.api.model.version.Versions;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Main Redmine API controller, controls sending and receiving HTTP data to redmine server.
 * 
 * @author peter
 *
 */
public class RedmineAPI 
{
	protected String url, key, currentProject;
	
	protected Users users;
	
	protected Statuses statuses;

	protected Projects projects;
	
	protected Versions versions;

	protected Memberships memberships;

	/**
	 * Initializes redmine API handler
	 * 
	 * @param url
	 * @param key
	 */
	public void init(String url, String key, String currentProject) 
	{
		this.url = url;
		this.key = key;
		this.currentProject = currentProject;
	}

	public void setCurrentProject(String currentProject)
	{
		this.currentProject = currentProject;
	}
	
	protected XStream xstreamIssues()
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("issues", Issues.class);
		xstream.addImplicitCollection(Issues.class, "issues", Issue.class);
		xstream.alias("issue", Issue.class);
		
		xstream.aliasField("id", Integer.class, "id");
		xstream.alias("project", IssueProject.class);
		xstream.aliasAttribute(IssueProject.class, "id", "id");
		xstream.aliasAttribute(IssueProject.class, "name", "name");
		xstream.alias("tracker", IssueTracker.class);
		xstream.aliasAttribute(IssueTracker.class, "id", "id");
		xstream.aliasAttribute(IssueTracker.class, "name", "name");
		xstream.alias("priority", IssuePriority.class);
		xstream.aliasAttribute(IssuePriority.class, "id", "id");
		xstream.aliasAttribute(IssuePriority.class, "name", "name");
		xstream.alias("status", IssueStatus.class);
		xstream.aliasAttribute(IssueStatus.class, "id", "id");
		xstream.aliasAttribute(IssueStatus.class, "name", "name");
		xstream.alias("version", IssueVersion.class);
		xstream.aliasAttribute(IssueVersion.class, "id", "id");
		xstream.aliasAttribute(IssueVersion.class, "name", "name");
		xstream.alias("fixed_version", IssueFixedVersion.class);
		xstream.aliasAttribute(IssueFixedVersion.class, "id", "id");
		xstream.aliasAttribute(IssueFixedVersion.class, "name", "name");
		xstream.alias("assigned_to", IssueAssignedTo.class);
		xstream.aliasAttribute(IssueAssignedTo.class, "id", "id");
		xstream.aliasAttribute(IssueAssignedTo.class, "name", "name");
		xstream.alias("author", IssueAuthor.class);
		xstream.aliasAttribute(IssueAuthor.class, "id", "id");
		xstream.aliasAttribute(IssueAuthor.class, "name", "name");
		xstream.alias("subject", String.class);
		xstream.alias("description", String.class);
		xstream.alias("start_date", String.class);
		xstream.alias("due_date", String.class);
		xstream.alias("done_ratio", String.class);
		xstream.alias("is_private", String.class);
		xstream.alias("estimated_hours", String.class);
		xstream.alias("created_on", String.class);
		xstream.alias("updated_on", String.class);
		xstream.alias("closed_on", String.class);

		return xstream;
	}
	
	protected XStream xstreamCreateIssue()
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("issue", Issue.class);
		
		//xstream.omitField(Issue.class, "tracker_id");
		//xstream.omitField(Issue.class, "status_id");
		//xstream.omitField(Issue.class, "priority_id");
		//xstream.omitField(Issue.class, "category_id");
		//xstream.omitField(Issue.class, "fixed_version_id");
		//xstream.omitField(Issue.class, "assigned_to_id");
		//xstream.omitField(Issue.class, "estimated_hours");
		xstream.omitField(Issue.class, "start_date");
		xstream.omitField(Issue.class, "due_date");
		xstream.omitField(Issue.class, "done_ratio");
		xstream.omitField(Issue.class, "is_private");
		xstream.omitField(Issue.class, "created_on");
		xstream.omitField(Issue.class, "updated_on");
		xstream.omitField(Issue.class, "closed_on");
		//xstream.omitField(Issue.class, "parent_issue_id");
		xstream.omitField(Issue.class, "id");

		return xstream;
	}

	/**************************************************************************
	 * Reads list of current issues
	 * 
	 * @param id_project
	 * @return
	 **************************************************************************/
	public List<Issue> getIssues(int projectId)
	{
		try
		{
			URL url = new URL(this.url + "/issues.xml?project_id=" + projectId + "&key=" + this.key);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
	
			if (conn.getResponseCode() != 200)
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	
			// Process string buffer to XML 
			//XStream xstream = new XStream();
			XStream xstream = this.xstreamIssues();
			
			Issues issues = (Issues)xstream.fromXML(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			conn.disconnect();
			
			return issues.issues;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  
		return null;
	}

	/**
	 * Create new issue
	 * 
	 * @param issue
	 */
	public void createIssue(Issue issue) 
	{
		try
		{
			URL url = new URL(this.url + "/issues.xml?key=" + this.key);

			XStream xstream = this.xstreamCreateIssue();
			String strIssue = xstream.toXML(issue).replace("__", "_");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty ( "Content-Type", "text/xml" );
			
			System.out.println(strIssue);
			OutputStreamWriter writer = null;
			try {
			    writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			    writer.write(strIssue); // Write POST query string (if any needed).
			} finally {
			    if (writer != null) try { writer.close(); } catch (IOException logOrIgnore) {}
			}

			int responseCode = conn.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			/*InputStream result = conn.getInputStream();
			
			int res;
			
			while ( (res=result.read()) != -1 )
			{
				System.out.print((char)res);
			}*/
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Update issue
	 * 
	 * @param issue
	 */
	public void updateIssue(Issue issue) 
	{
		try
		{
			URL url = new URL(this.url + "/issues/" + issue.getId() + ".xml?key=" + this.key);

			XStream xstream = this.xstreamIssues();
			String strIssue = xstream.toXML(issue);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty ( "Content-Type", "text/xml" );
			
			OutputStreamWriter writer = null;
			try {
			    writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			    writer.write(strIssue); // Write POST query string (if any needed).
			} finally {
			    if (writer != null) try { writer.close(); } catch (IOException logOrIgnore) {}
			}

			int responseCode = conn.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			InputStream result = conn.getInputStream();
			
			int res;
			
			while ( (res=result.read()) != -1 )
			{
				System.out.print((char)res);
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**************************************************************************
	 * Get list of statuses
	 * 
	 * @param projectId
	 * @return
	 **************************************************************************/
	public List<Status> getStatuses()
	{
		if ( this.statuses == null )
		{
			try
			{
				URL url = new URL(this.url + "/issue_statuses.xml?key=" + this.key);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/xml");
		
				if (conn.getResponseCode() != 200)
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		
				// Process string buffer to XML 
				//XStream xstream = new XStream();
				XStream xstream = new XStream(new DomDriver());
				xstream.alias("issue_statuses", Statuses.class);
				xstream.addImplicitCollection(Statuses.class, "issue_statuses", Status.class);
				xstream.alias("issue_status", Status.class);
				
				xstream.aliasField("id", Integer.class, "id");
				xstream.aliasField("name", String.class, "name");
				xstream.aliasField("is_closed", Boolean.class, "is_closed");
				
				Statuses statuses = (Statuses)xstream.fromXML(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				conn.disconnect();
				
				this.statuses = statuses;
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return this.statuses.issue_statuses;
	}

	/**************************************************************************
	 * Reads list of users
	 * 
	 * @return
	 **************************************************************************/
	public List<User> getUsers()
	{
		if ( this.users == null )
		{
			try
			{
				URL url = new URL(this.url + "/users.xml?key=" + this.key);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/xml");
		
				if (conn.getResponseCode() == 200)
				{
					// Process string buffer to XML 
					//XStream xstream = new XStream();
					XStream xstream = new XStream(new DomDriver());
					xstream.alias("users", Users.class);
					xstream.addImplicitCollection(Users.class, "users", User.class);
					xstream.alias("user", User.class);
					
					xstream.aliasField("id", Integer.class, "id");
					xstream.aliasField("login", String.class, "login");
					xstream.aliasField("firstname", String.class, "firstname");
					xstream.aliasField("lastname", String.class, "lastname");
					xstream.aliasField("mail", String.class, "mail");
					xstream.aliasField("created_on", String.class, "created_on");
					xstream.aliasField("last_login_on", String.class, "last_login_on");
					
					Users users = (Users)xstream.fromXML(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					conn.disconnect();
		
					this.users = users;
				}
				else
				{
					System.out.println("getUsers response code " + conn.getResponseCode() + " - " + conn.getResponseMessage() );
					this.users = new Users();
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  
		return this.users.users;
	}

	/**************************************************************************
	 * Gets list of projects
	 * 
	 * @return
	 **************************************************************************/
	public List<Project> getProjects()
	{
		if ( this.projects == null )
		{
			try
			{
				URL url = new URL(this.url + "/projects.xml?key=" + this.key);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/xml");
		
				if (conn.getResponseCode() != 200)
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		
				// Process string buffer to XML 
				//XStream xstream = new XStream();
				XStream xstream = new XStream(new DomDriver());
				xstream.alias("projects", Projects.class);
				xstream.addImplicitCollection(Projects.class, "projects", Project.class);
				xstream.alias("project", Project.class);
				
				xstream.aliasField("id", Integer.class, "id");
				xstream.aliasField("name", String.class, "name");
				xstream.aliasField("identifier", String.class, "identifier");
				xstream.aliasField("description", String.class, "description");
				xstream.aliasField("status", String.class, "status");
				xstream.aliasField("is_public", String.class, "is_public");
				xstream.aliasField("created_on", String.class, "created_on");
				xstream.aliasField("updated_on", String.class, "updated_on");
				
				Projects projects = (Projects)xstream.fromXML(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				conn.disconnect();
				
				return projects.projects;
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  
		return this.projects.projects;
	}

	/**************************************************************************
	 * Gets list of versions for specific project
	 * 
	 * @param projectId
	 * @return
	 **************************************************************************/
	public List<Version> getVersions(int projectId)
	{
		// TODO check also project
		if ( this.versions == null )
		{
			try
			{
				URL url = new URL(this.url + "/projects/" + projectId + "/versions.xml?key=" + this.key);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/xml");
		
				if (conn.getResponseCode() != 200)
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		
				// Process string buffer to XML 
				//XStream xstream = new XStream();
				XStream xstream = new XStream(new DomDriver());
				xstream.alias("versions", Versions.class);
				xstream.addImplicitCollection(Versions.class, "versions", Version.class);
				xstream.alias("version", Version.class);
				
				xstream.aliasField("id", Integer.class, "id");
				xstream.aliasField("project", String.class, "project");
				xstream.aliasField("name", String.class, "name");
				xstream.aliasField("description", String.class, "description");
				xstream.aliasField("status", String.class, "status");
				xstream.aliasField("due_date", String.class, "due_date");
				xstream.aliasField("sharing", String.class, "sharing");
				xstream.aliasField("created_on", String.class, "created_on");
				xstream.aliasField("updated_on", String.class, "updated_on");
				
				Versions versions = (Versions)xstream.fromXML(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				conn.disconnect();
				
				this.versions = versions;
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return this.versions.versions;
	}

	/**
	 * Get list of memberships for specific project
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Membership> getMemberships(int projectId)
	{
		// TODO check also project
		if ( this.memberships == null )
		{
			try
			{
				URL url = new URL(this.url + "/projects/" + projectId + "/memberships.xml?key=" + this.key);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/xml");
		
				if (conn.getResponseCode() != 200)
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		
				// Process string buffer to XML 
				//XStream xstream = new XStream();
				XStream xstream = new XStream(new DomDriver());
				xstream.alias("memberships", Memberships.class);
				xstream.addImplicitCollection(Memberships.class, "memberships", Membership.class);
				xstream.alias("membership", Membership.class);
				
				xstream.aliasField("id", Integer.class, "id");
				xstream.aliasField("project", MembershipProject.class, "project");
				xstream.aliasAttribute(MembershipProject.class, "id", "id");
				xstream.aliasAttribute(MembershipProject.class, "name", "name");
				xstream.aliasField("user", MembershipUser.class, "user");
				xstream.aliasAttribute(MembershipUser.class, "id", "id");
				xstream.aliasAttribute(MembershipUser.class, "name", "name");
				xstream.aliasField("roles", MembershipRoles.class, "roles");
				xstream.addImplicitCollection(MembershipRoles.class, "roles", MembershipRole.class);
				xstream.alias("role", MembershipRole.class);
				xstream.aliasAttribute(MembershipRole.class, "id", "id");
				xstream.aliasAttribute(MembershipRole.class, "name", "name");
				
				Memberships memberships = (Memberships)xstream.fromXML(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				conn.disconnect();
				
				this.memberships = memberships;
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return this.memberships.memberships;
	}

	/**
	 * Get Membership users for specific project
	 * 
	 * @param projectId
	 * @return
	 */
	public List<MembershipUser> getProjectUsers(int projectId)
	{
		this.getMemberships(projectId);
		
		List<MembershipUser> users = new ArrayList<MembershipUser>();
		
		for(Membership mem : this.memberships.memberships)
		{
			users.add(mem.user);
		}
		
		return users;
	}


	public Project findProjectByName(String projectName) 
	{
		List<Project> projects = this.getProjects();
		
		for(Project p : projects)
		{
			if ( projectName.equals(p.getName()))
			{
				return p;
			}
		}
		
		return null;
	}

}
