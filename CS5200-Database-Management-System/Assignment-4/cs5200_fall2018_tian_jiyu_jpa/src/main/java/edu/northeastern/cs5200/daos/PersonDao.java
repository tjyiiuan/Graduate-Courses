package edu.northeastern.cs5200.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@Component
public class PersonDao {
	
    @Autowired
	PersonRepository pr;
    
	private static PersonDao instance = null;

	private PersonDao() {}

	public static PersonDao getInstance() {
		if (instance == null) {
			instance = new PersonDao();
		}
		return instance;
	}


	
	public List<Person> findAllPersons() {
		
		return (List<Person>) pr.findAll();
	}
	
	public Person findPersonByUsername(String username) {
		return pr.findPersonByUsername(username);
	}
	
	public Person findPersonByCredentials(String username, String password) {
		return pr.findPersonByCredentials(username, password);
	}
	
	public void emptyPersonTable() {
		pr.deleteAll();
	}
	

}
