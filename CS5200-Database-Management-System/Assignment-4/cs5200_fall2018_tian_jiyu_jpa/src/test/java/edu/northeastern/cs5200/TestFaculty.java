package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.Faculty;
import edu.northeastern.cs5200.repositories.FacultyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFaculty {

	@Autowired
	FacultyRepository fr;
	
	@Test
	public void testCreateFaculties() {
		Faculty alan = new Faculty("alan", "password", "Alan", "Turin", "123A", true);
		Faculty ada = new Faculty("ada", "password", "Ada", "Lovelace", "123B", true);
		fr.save(alan);
		fr.save(ada);
	}
	
	@Test
	public void testFacultyCount() {
		
		System.out.println(fr.count());
		
	}

}
