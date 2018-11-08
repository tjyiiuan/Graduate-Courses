package edu.northeastern.cs5200.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course {
	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String label;
	
	@ManyToOne
	private Faculty author;
	
	@OneToMany(mappedBy="course")
	private List<Section> courseSections;
	
	
	public Course() {}
	
	public Course(String label) {
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Faculty getAuthor() {
		return author;
	}
	public void setAuthor(Faculty author) {
		this.author = author;
	}
}
