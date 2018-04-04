package com.projawe.redmine;

import java.util.List;

import com.projawe.redmine.api.RedmineAPI;
import com.projawe.redmine.api.model.project.Membership;
import com.projawe.redmine.api.model.project.Project;
import com.projawe.redmine.api.model.version.Version;

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
		
		List<Version> versions = api.getVersions(projectId);
		
		for(Version v : versions)
		{
			System.out.println(v.getName() + " " + v.getStatus() );
		}

		/*List<Issue> issues = api.getIssues(projectId);
		
		for(Issue iss : issues)
		{
			if ( iss != null )
			{
				System.out.println("Issue " + iss.getId() + "." + iss.getSubject() + " ->> " + iss.getStatus().getName() );
			}
		}*/
		
		/*Issue i = new Issue();
		i.setProject_Id(projectId);
		i.setSubject("New task");
		i.setDescription("Description");
		//i.setPriority_Id(2);
		api.createIssue(i);*/
		
		List<Membership> users = api.getMemberships(projectId);
		
		for(Membership m : users)
		{
			System.out.println(m.user.getId() + "." + m.user.getName());
		}
	}
}