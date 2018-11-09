package edu.northeastern.cs5200;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateData {

    @Autowired
	PersonRepository pr;
    
    @Autowired
	FacultyRepository fr;
    
    @Autowired
	StudentRepository stur;
	
	@Autowired
	CourseRepository cr;
	
	@Autowired
	SectionRepository secr;
	
	@Autowired
	EnrollmentRepository er;
	

	@Test
	public void validatePersons() {
		
		System.out.println(pr.count());
		
	}
	
	@Test
	public void validateFaculties() {
		
		System.out.println(fr.count());
		
	}
	
	@Test
	public void validateStudents() {
		
		System.out.println(stur.count());
		
	}
	
	
	@Test
	public void validateCourses() {
		
		System.out.println(cr.count());
		
	}
	
	@Test
	public void validateSections() {
		
		System.out.println(secr.count());
		
	}
	
	@Test
	public void validateCourseAuthorship() {
		
		List<Faculty> faculties = (List<Faculty>) fr.findAll();
		
		for(Faculty faculty: faculties) {
			List<Course> courses = faculty.getAuthoredCourses();
			System.out.println(faculty.getFirstName());
			System.out.println(courses.size());
		}
	}
	
	
	@Test
	public void validateSectionPerCourse() {
		
		List<Course> courses = (List<Course>) cr.findAll();
		
		for(Course course: courses) {
			List<Section> sections = course.getCourseSections();
			System.out.println(course.getLabel());
			System.out.println(sections.size());
		}
	}
	
	@Test
	public void validateSectionEnrollments() {
		
	}
		

}
