package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.Course;
import edu.northeastern.cs5200.models.Faculty;
import edu.northeastern.cs5200.repositories.CourseRepository;
import edu.northeastern.cs5200.repositories.FacultyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCourse {

	@Autowired
	CourseRepository cr;
	
	@Autowired
	FacultyRepository fr;
	
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
	public void testCourseCount() {
		
		System.out.println(cr.count());
		
	}
	
	@Test
	public void testSectionPerCourse() {
		
		System.out.println(cr.count());
		
	}
}
