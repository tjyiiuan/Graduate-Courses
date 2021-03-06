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
	@OneToMany(mappedBy="course", orphanRemoval=true)
	private List<Section> courseSections;
	
	
	public Course() {}
	
	public Course(String label, Faculty faculty) {
		this.label = label;
		this.author = faculty;
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
	public List<Section> getCourseSections() {
		return courseSections;
	}
	public void setCourseSections(List<Section> courseSections) {
		this.courseSections = courseSections;
	}
	public void addSection(Section section) {
		this.courseSections.add(section);
	}
	public void removeSection(Section section) {
		this.courseSections.remove(section);
	}
}
