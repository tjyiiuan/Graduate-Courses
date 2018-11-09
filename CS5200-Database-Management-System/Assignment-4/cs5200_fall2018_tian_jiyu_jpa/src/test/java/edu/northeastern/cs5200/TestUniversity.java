package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.northeastern.cs5200.repositories.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestUniversity {

    @Autowired
	PersonRepository pr;
	
	@Autowired
	CourseRepository cr;
	
	@Autowired
	SectionRepository sr;
	
	@Autowired
	EnrollmentRepository er;
	
	@Test
	public void truncateDatabase() {
		pr.deleteAll();
		cr.deleteAll();
		sr.deleteAll();
		er.deleteAll();
		
	}
		

}
