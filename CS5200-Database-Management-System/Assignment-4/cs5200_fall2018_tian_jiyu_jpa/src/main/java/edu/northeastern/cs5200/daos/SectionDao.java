package edu.northeastern.cs5200.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@Component
public class SectionDao {

    @Autowired
    SectionRepository sr;
    
	@Autowired
	CourseRepository cr;
    
	private static SectionDao instance = null;

	private SectionDao() {}

	public static SectionDao getInstance() {
		if (instance == null) {
			instance = new SectionDao();
		}
		return instance;
	}
	
	
	
	
	public void createSection(Section section) {
		if (this.findSectionByTitle(section.getTitle()) == null) {
			sr.save(section);
		} else {
			System.out.println("Entity already exists.");
		}
	}
    
	public List<Section> findAllSections() {
		
		return (List<Section>) sr.findAll();
	}
	
	public List<Section> findSectionForCourse(Course course) {
		
		return (List<Section>) sr.findSectionForCourse(course);
	}
	
	public Section findSectionByTitle(String title) {
		return sr.findSectionByTitle(title);
	}
	
	
}
