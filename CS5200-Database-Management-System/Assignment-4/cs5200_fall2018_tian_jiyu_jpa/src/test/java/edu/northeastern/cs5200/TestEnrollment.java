package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.northeastern.cs5200.models.Enrollment;
import edu.northeastern.cs5200.models.Section;
import edu.northeastern.cs5200.models.Student;
import edu.northeastern.cs5200.repositories.EnrollmentRepository;
import edu.northeastern.cs5200.repositories.SectionRepository;
import edu.northeastern.cs5200.repositories.StudentRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestEnrollment {

	@Autowired
	EnrollmentRepository er;
	
	@Autowired
	SectionRepository secr;
	
	@Autowired
	StudentRepository stur;
	
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
		
		stur.save(alice);
		stur.save(bob);
		stur.save(charlie);
		
		secr.save(sec1);
		secr.save(sec2);
		secr.save(sec3);
	}
	
}
