package edu.northeastern.cs5200.models;

import javax.persistence.*;

@Entity
public class Student extends Person {
	
	private int gradYear;
	private Long scholarship;
  
	public int getGradYear() {
		return gradYear;
	}
	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
	}
	public Long getScholarship() {
		return scholarship;
	}
	public void setScholarship(Long scholarship) {
		this.scholarship = scholarship;
	}
}

