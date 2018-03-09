package com.projawe.redmine.api.model.user;

public class User {

	protected int id;
	
	public String login, firstname, lastname, mail, created_on, last_login_on;

	public User()
	{
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}
}
