package edu.northeastern.cs5200;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs5200.models.Person;
import edu.northeastern.cs5200.repositories.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPerson {
	
	@Autowired
	PersonRepository pr;
	
	@Test
	public void testPersonAll() {
		List<Person> persons = (List<Person>) pr.findAll();
		
		for(Person person: persons) {
			System.out.println(person.getUsername());
		}
	}
	
	@Test
	public void testPersonCount() {
		
		System.out.println(pr.count());
		
	}

}
