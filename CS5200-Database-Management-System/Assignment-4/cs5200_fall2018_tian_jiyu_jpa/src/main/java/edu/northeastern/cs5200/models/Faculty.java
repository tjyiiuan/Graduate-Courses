package edu.northeastern.cs5200.models;

import java.util.List;
import javax.persistence.*;

@Entity
public class Faculty extends Person {
	
	private String office;
	private Boolean tenure;
	@OneToMany(mappedBy="author")
	private List<Course> authoredCourses;
	
	
	public Faculty() {}
	
	public Faculty (String username, String password, String firstName, String lastName, String office, Boolean tenure) {
		super(username, password, firstName, lastName);
		this.office = office;
		this.tenure = tenure;
	}
	
	public Faculty (String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		this.office = "None";
		this.tenure = false;
	}
	
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public Boolean getTenure() {
		return tenure;
	}
	public void setTenure(Boolean tenure) {
		this.tenure = tenure;
	}
	public List<Course> getAuthoredCourses() {
		return authoredCourses;
	}
	public void setAuthoredCourses(List<Course> authoredCourses) {
		this.authoredCourses = authoredCourses;
	}
	public void addCourse(Course course) {
		this.authoredCourses.add(course);
	}
	public void removeCourse(Course course) {
		this.authoredCourses.remove(course);
	}
}

