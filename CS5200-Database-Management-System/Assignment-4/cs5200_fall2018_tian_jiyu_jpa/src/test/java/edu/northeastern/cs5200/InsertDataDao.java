package edu.northeastern.cs5200;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.daos.*;
import edu.northeastern.cs5200.models.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertDataDao {
	
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
	public void emptyDatabase() {
		persondao.emptyPersonTable();
		coursedao.emptyCourseTable();
		sectiondao.emptySectionTable();
		enrollmentdao.emptyEnrollmentTable();
	}
	
	
	
	@Test
	public void testACreateFaculties() {
		Faculty alan = new Faculty("alan", "password", "Alan", "Turin", "123A", true);
		Faculty ada = new Faculty("ada", "password", "Ada", "Lovelace", "123B", true);
		facultydao.createFaculty(alan);
		facultydao.createFaculty(ada);
	}

	@Test
	public void testCreateStudents() {
		
		Student alice = new Student("alice", "password", "Alice", "Wonderland", 202, 12000L);
		Student bob = new Student("bob", "password", "Bob", "Hope", 2021, 23000L);
		Student charlie = new Student("charlie", "password", "Charlie", "Brown", 2019, 21000L);
		Student dan = new Student("dan", "password", "dan", "Craig", 2019, 0L);
		Student edward = new Student("edward", "password", "Edward", "Scissorhands", 2022, 11000L);
		Student frank = new Student("frank", "password", "Frank", "Herbert", 2018, 0L);
		Student gregory = new Student("gregory", "password", "gregory", "Peck", 2023, 10000L);
		
		studentdao.createStudent(alice);
		studentdao.createStudent(bob);
		studentdao.createStudent(charlie);
		studentdao.createStudent(dan);
		studentdao.createStudent(edward);
		studentdao.createStudent(frank);
		studentdao.createStudent(gregory);
	}

	@Test
	public void testCreateCourses() {
		
		Faculty alan = (Faculty) facultydao.findFacultyByUsername("alan");
		Faculty ada = (Faculty) facultydao.findFacultyByUsername("ada");
		
		Course c1 = new Course("CS1234", alan);
		Course c2 = new Course("CS2345", alan);
		Course c3 = new Course("CS3456", ada);
		Course c4 = new Course("CS4567", ada);
		
		coursedao.createCourse(c1);
		coursedao.createCourse(c2);
		coursedao.createCourse(c3);
		coursedao.createCourse(c4);
	}
	
	@Test
	public void testDCreateSections() {
		
		Course c1 = (Course) coursedao.findCourseByLabel("CS1234");
		Course c2 = (Course) coursedao.findCourseByLabel("CS2345");
		Course c3 = (Course) coursedao.findCourseByLabel("CS3456");
		
		Section s1 = new Section("SEC4321", 50, c1);
		Section s2 = new Section("SEC5432", 50, c1);
		Section s3 = new Section("SEC6543", 50, c2);
		Section s4 = new Section("SEC7654", 50, c3);
		
		sectiondao.createSection(s1);
		sectiondao.createSection(s2);
		sectiondao.createSection(s3);
		sectiondao.createSection(s4);
	}
	
	
	@Test
	public void testEnrollStudentInSection() {
		
		Section sec1 = sectiondao.findSectionByTitle("SEC4321");
		Section sec2 = sectiondao.findSectionByTitle("SEC5432");
		Section sec3 = sectiondao.findSectionByTitle("SEC6543");

		Student alice = studentdao.findStudentByUsername("alice");
		Student bob = studentdao.findStudentByUsername("bob");
		Student charlie = studentdao.findStudentByUsername("charlie");
		
		Enrollment enr1 = new Enrollment(alice, sec1);
		Enrollment enr2 = new Enrollment(alice, sec2);
		Enrollment enr3 = new Enrollment(bob, sec2);
		Enrollment enr4 = new Enrollment(charlie, sec3);
		
		enrollmentdao.createEnrollment(enr1);
		enrollmentdao.createEnrollment(enr2);
		enrollmentdao.createEnrollment(enr3);
		enrollmentdao.createEnrollment(enr4);
	}
	
	
}
