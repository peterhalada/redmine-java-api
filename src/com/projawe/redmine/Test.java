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
		String key = "253899f6aebfd78a97f772c377827e7794f17a4b";
		String projectName = "ZltyMelon";

		RedmineAPI api = new RedmineAPI();
		api.init(server, key, projectName);
		
		Project prj = api.findProjectByName(projectName);
		
		if ( prj != null )
		{
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
}