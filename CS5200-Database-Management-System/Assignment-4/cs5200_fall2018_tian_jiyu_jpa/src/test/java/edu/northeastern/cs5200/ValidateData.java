package edu.northeastern.cs5200;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.daos.*;
import edu.northeastern.cs5200.models.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateData {
	
	@Autowired
	PersonDao persondao;
	
	@Autowired
	FacultyDao facultydao;
	
	@Autowired
	StudentDao studentdao;
	
	@Autowired
	CourseDao coursedao;
	
	@Autowired
	SectionDao sectiondao;
	
	@Autowired
	EnrollmentDao enrollmentdao;

	
	@Test
	public void validatePersons() {
		
		System.out.println(persondao.findAllPersons().size());
		
	}
	
	@Test
	public void validateFaculties() {
		
		System.out.println(facultydao.findAllFaculties().size());
		
	}
	
	@Test
	public void validateStudents() {
		
		System.out.println(studentdao.findAllStudents().size());
		
	}
	
	
	@Test
	public void validateCourses() {
		
		System.out.println(coursedao.findAllCourses().size());
		
	}
	
	@Test
	public void validateSections() {
		
		System.out.println(sectiondao.findAllSections().size());
		
	}
	
	@Test
	public void validateCourseAuthorship() {
		
		List<Faculty> faculties = facultydao.findAllFaculties();
		
		for(Faculty faculty: faculties) {
			List<Course> courses = faculty.getAuthoredCourses();
			System.out.println(faculty.getFirstName() + " " + courses.size());
		}
	}
	
	@Test
	public void validateSectionPerCourse() {
		
		List<Course> courses = coursedao.findAllCourses();
		
		for(Course course: courses) {
			List<Section> sections = course.getCourseSections();
			System.out.println(course.getLabel() + " " + sections.size());
		}
	}
	
	@Test
	public void validateSectionEnrollments() {
		
		List<Section> sections = sectiondao.findAllSections();
		for(Section section: sections) {
			int numStudents = enrollmentdao.findStudentsInSection(section).size();
			System.out.println(section.getTitle() + " " + numStudents);
		}
	}
		
	@Test
	public void validateStudentEnrollments() {
		
		List<Student> students = studentdao.findAllStudents();
		for(Student student: students) {
			int numSections = enrollmentdao.findSectionsForStudent(student).size();
			System.out.println(student.getFirstName() + " " + numSections);
		}
	}
	
	@Test
	public void validateSectionSeats() {
		
		List<Section> sections = sectiondao.findAllSections();
		for(Section section: sections) {
			System.out.println(section.getTitle() + " " + section.getSeats());
		}
	}
		

}
