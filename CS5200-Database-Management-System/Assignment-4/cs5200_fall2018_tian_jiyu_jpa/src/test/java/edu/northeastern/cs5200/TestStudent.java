package edu.northeastern.cs5200;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.Student;
import edu.northeastern.cs5200.repositories.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStudent {

	@Autowired
	StudentRepository sr;
	
	@Test
	public void testCreateStudents() {
		
		Student alice = new Student("alice", "password", "Alice", "Wonderland", 202, 12000L);
		Student bob = new Student("bob", "password", "Bob", "Hope", 2021, 23000L);
		Student charlie = new Student("charlie", "password", "Charlie", "Brown", 2019, 21000L);
		Student dan = new Student("dan", "password", "dan", "Craig", 2019, 0L);
		Student edward = new Student("edward", "password", "Edward", "Scissorhands", 2022, 11000L);
		Student frank = new Student("frank", "password", "Frank", "Herbert", 2018, 0L);
		Student gregory = new Student("gregory", "password", "gregory", "Peck", 2023, 10000L);
		
		sr.save(alice);
		sr.save(bob);
		sr.save(charlie);
		sr.save(dan);
		sr.save(edward);
		sr.save(frank);
		sr.save(gregory);
	}

	@Test
	public void testStudentCount() {
		
		System.out.println(sr.count());
		
	}
}
