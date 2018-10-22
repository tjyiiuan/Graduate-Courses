package edu.northeastern.cs5200.models;

public class Role {
	
    private int id;
    private String role;
    
	public Role() {
		super();
	}
	
    public Role(int id, String role) {
    	super();
        this.id = id;
        this.setRole(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}