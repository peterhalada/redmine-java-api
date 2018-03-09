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
		String key = "f29ddbd9af55aea74232723d094e78a098bf66df";
		String project = "ZltyMelon";

		RedmineAPI api = new RedmineAPI();
		api.init(server, key, project);
		
		Project prj = api.findProjectByName("ZltyMelon");
		int projectId = prj.getId();
		
		List<Issue> issues = api.getIssues(projectId);
		
		for(int i = 0; i < 10; i++)
		{
			Issue iss = issues.get(i);
			
			if ( iss != null )
			{
				System.out.println("Issue " + iss.getId() + "." + iss.getSubject() );
			}
		}
	}
}