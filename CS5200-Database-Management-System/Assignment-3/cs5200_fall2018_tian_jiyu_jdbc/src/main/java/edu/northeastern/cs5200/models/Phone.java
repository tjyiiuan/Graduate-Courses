package edu.northeastern.cs5200.models;

public class Phone {
	
	private String phone;
	private boolean primary;
	private int person_id;
	
	public Phone() {
		super();
	}

	public Phone(String phone, boolean primary, int person_id) {
		super();
		this.phone = phone;
		this.primary = primary;
		this.person_id = person_id;
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

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
}