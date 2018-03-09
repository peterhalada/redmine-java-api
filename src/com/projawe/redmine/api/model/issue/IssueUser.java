package com.projawe.redmine.api.model.issue;

/**
 * User entity
 * 
 * @author peter
 *
 */
public class IssueUser {

	protected int idUser;
	
	protected String username, firstname, surname;

	public IssueUser(int idUser, String username)
	{
		this.idUser = idUser;
		this.username = username;
	}
	
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
