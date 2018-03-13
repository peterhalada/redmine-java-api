package com.projawe.redmine;

import java.util.List;

import com.projawe.redmine.api.RedmineAPI;
import com.projawe.redmine.api.model.issue.Issue;
import com.projawe.redmine.api.model.project.Project;

public class Test
{
	public static void main(String []args)
	{
		String server = "http://redmine.projawe.com";
		String key = "19d6c98934b935aa7744a821b44e75f002d4a7d5"; // "f29ddbd9af55aea74232723d094e78a098bf66df";
		String projectName = "GuestProject";

		RedmineAPI api = new RedmineAPI();
		api.init(server, key, projectName);
		
		Project prj = api.findProjectByName(projectName);
		int projectId = prj.getId();
		
		/*List<Issue> issues = api.getIssues(projectId);
		
		for(Issue iss : issues)
		{
			if ( iss != null )
			{
				System.out.println("Issue " + iss.getId() + "." + iss.getSubject() + " ->> " + iss.getStatus().getName() );
			}
		}*/
		
		Issue i = new Issue();
		i.setProject_Id(projectId);
		i.setSubject("New task");
		i.setDescription("Description");
		//i.setPriority_Id(2);
		api.createIssue(i);
	}
}