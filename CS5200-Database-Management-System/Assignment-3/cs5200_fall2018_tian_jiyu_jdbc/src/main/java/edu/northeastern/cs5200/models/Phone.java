package edu.northeastern.cs5200.models;

public class Phone {
	
	private String phone;
	private boolean primary;
	private int personId;
	
	public Phone() {
		super();
	}

	public Phone(String phone, boolean primary, int personId) {
		super();
		this.phone = phone;
		this.primary = primary;
		this.personId = personId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

}