package edu.northeastern.cs5200.models;

public class Priviledge {
	
	private int id;
	private String priviledge;

	public Priviledge() {
		super();
	}
	
	public Priviledge(int id, String priviledge) {
		super();
		this.id = id;
		this.priviledge = priviledge;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPriviledge() {
		return priviledge;
	}

	public void setPriviledge(String priviledge) {
		this.priviledge = priviledge;
	}
}