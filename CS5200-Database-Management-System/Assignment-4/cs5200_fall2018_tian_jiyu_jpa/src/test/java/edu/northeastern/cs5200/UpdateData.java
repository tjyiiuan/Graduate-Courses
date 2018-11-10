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
public class UpdateData {
	
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
	public void removeOneEnroll() {
		
		Section sec1 = sectiondao.findSectionByTitle("SEC4321");

		Student alice = studentdao.findStudentByUsername("alice");
		
		enrollmentdao.removeOneEnrollment(alice, sec1);
	}
	
}
