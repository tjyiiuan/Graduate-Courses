package edu.northeastern.cs5200.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@Component
public class CourseDao {

    @Autowired
	FacultyRepository fr;
    
	@Autowired
	CourseRepository cr;
    
	private static CourseDao instance = null;

	private CourseDao() {}

	public static CourseDao getInstance() {
		if (instance == null) {
			instance = new CourseDao();
		}
		return instance;
	}
	
	
	
	
	public void createCourse(Course course) {
		if (this.findCourseByLabel(course.getLabel()) == null) {
			cr.save(course);
		} else {
			System.out.println("Entity already exists.");
		}
	}
    
	public List<Course> findAllCourses() {
		
		return (List<Course>) cr.findAll();
	}
	
	public List<Course> findCourseForAuthor(Faculty faculty) {
		
		return (List<Course>) cr.findCourseForAuthor(faculty);
	}
	
	public void setAuthorForCourse(Faculty faculty, Course course) {
		
		course.setAuthor(faculty);
		cr.save(course);
	}
	
	public Course findCourseByLabel(String label) {
		return cr.findCourseByLabel(label);
	}
	
	
}
