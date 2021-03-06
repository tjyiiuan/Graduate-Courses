package edu.northeastern.cs5200.models;

import java.sql.Date;
import java.util.Collection;

public class Developer extends Person {
	
	private String developerKey;

	public Developer() {
		super();
	}
	
	public Developer(int id, String firstName, String lastName, String username, String password, String email, Date dob, String developerKey) {
		super(id, firstName, lastName, username, password, email, dob);
		this.developerKey = developerKey;
	}
	
	public Developer(int id, String firstName, String lastName, String username, String password, String email, Date dob, Collection<Address> addresses, Collection<Phone> phoneNumbers, String developerKey) {
		super(id, firstName, lastName, username, password, email, dob, addresses, phoneNumbers);
		this.developerKey = developerKey;
	}
		
	public Developer(String firstName, String lastName, String username, String password, String email, String developerKey) {
		super(firstName, lastName, username, password, email);
		this.developerKey = developerKey;
	}
	
	public Developer(int id, String firstName, String lastName, String developerKey) {
		super(id, firstName, lastName);
		this.developerKey = developerKey;
	}
	
	public String getDeveloperKey() {
		return developerKey;
	}
	
	public void setDeveloperKey(String developerKey) {
		this.developerKey = developerKey;
	}
}