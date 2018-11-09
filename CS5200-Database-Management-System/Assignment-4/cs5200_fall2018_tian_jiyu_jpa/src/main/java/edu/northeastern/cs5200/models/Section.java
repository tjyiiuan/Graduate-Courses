package edu.northeastern.cs5200.models;

import java.util.List;
import javax.persistence.*;

@Entity
public class Section {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private int seats;
	@ManyToOne
	private Course course;
	@OneToMany(mappedBy="section")
	private List<Enrollment> enrollments;
	
	public Section() {}
	
	public Section(String title, int seats, Course course) {
		this.title = title;
		this.seats = seats;
		this.course = course;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

}
