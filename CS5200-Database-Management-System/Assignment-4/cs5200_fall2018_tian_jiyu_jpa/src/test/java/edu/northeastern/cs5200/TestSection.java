package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.Course;
import edu.northeastern.cs5200.models.Section;
import edu.northeastern.cs5200.repositories.CourseRepository;
import edu.northeastern.cs5200.repositories.SectionRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSection {

	@Autowired
	CourseRepository cr;
	
	@Autowired
	SectionRepository secr;
	
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
	public void testSectionCount() {
		
		System.out.println(secr.count());
		
	}
}
