package edu.northeastern.cs5200.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Student extends Person {
	
	private int gradYear;
	private Long scholarship;
	@OneToMany(mappedBy="student", orphanRemoval=true)
	private List<Enrollment> enrollments;
	
	public Student() {}
	
	public Student(String username, String password, String firstName, String lastName, int gradYear, Long scholarship) {
		super(username, password, firstName, lastName);
		this.gradYear = gradYear;
		this.scholarship = scholarship;
	}
	
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

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

}

