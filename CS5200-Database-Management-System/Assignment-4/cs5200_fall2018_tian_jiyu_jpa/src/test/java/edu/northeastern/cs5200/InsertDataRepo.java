package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertDataRepo {

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
	public void testCreateFaculties() {
		Faculty alan = new Faculty("alan", "password", "Alan", "Turin", "123A", true);
		Faculty ada = new Faculty("ada", "password", "Ada", "Lovelace", "123B", true);
		fr.save(alan);
		fr.save(ada);
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
		
		stur.save(alice);
		stur.save(bob);
		stur.save(charlie);
		stur.save(dan);
		stur.save(edward);
		stur.save(frank);
		stur.save(gregory);
	}
	
	@Test
	public void testCreateCourses() {
		
		Faculty alan = (Faculty) fr.findFacultyByUsername("alan");
		Faculty ada = (Faculty) fr.findFacultyByUsername("ada");
		
		Course c1 = new Course("CS1234", alan);
		Course c2 = new Course("CS2345", alan);
		Course c3 = new Course("CS3456", ada);
		Course c4 = new Course("CS4567", ada);
		
		cr.save(c1);
		cr.save(c2);
		cr.save(c3);
		cr.save(c4);
	}
	
	@Test
	public void testCreateSections() {
		
		Course c1 = (Course) cr.findCourseByLabel("CS1234");
		Course c2 = (Course) cr.findCourseByLabel("CS2345");
		Course c3 = (Course) cr.findCourseByLabel("CS3456");
		
		Section s1 = new Section("SEC4321", 50, c1);
		Section s2 = new Section("SEC5432", 50, c1);
		Section s3 = new Section("SEC6543", 50, c2);
		Section s4 = new Section("SEC7654", 50, c3);
		
		secr.save(s1);
		secr.save(s2);
		secr.save(s3);
		secr.save(s4);
	}
	
	@Test
	public void testEnrollStudentInSection() {
		
		Section sec1 = secr.findSectionByTitle("SEC4321");
		Section sec2 = secr.findSectionByTitle("SEC5432");
		Section sec3 = secr.findSectionByTitle("SEC6543");

		Student alice = stur.findStudentByUsername("alice");
		Student bob = stur.findStudentByUsername("bob");
		Student charlie = stur.findStudentByUsername("charlie");
		
		Enrollment enr1 = new Enrollment(alice, sec1);
		Enrollment enr2 = new Enrollment(alice, sec2);
		Enrollment enr3 = new Enrollment(bob, sec2);
		Enrollment enr4 = new Enrollment(charlie, sec3);
		
		er.save(enr1);
		er.save(enr2);
		er.save(enr3);
		er.save(enr4);
	}
	
	
}
