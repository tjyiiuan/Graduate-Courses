package edu.northeastern.cs5200.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@Component
public class FacultyDao {
	
    @Autowired
	FacultyRepository fr;
    
	private static FacultyDao instance = null;

	private FacultyDao() {}

	public static FacultyDao getInstance() {
		if (instance == null) {
			instance = new FacultyDao();
		}
		return instance;
	}

	
	
	public void createFaculty(Faculty faculty) {
		if (this.findFacultyByCredentials(faculty.getUsername(), faculty.getPassword()) == null) {
			fr.save(faculty);
		} else {
			System.out.println("Entity already exists.");
		}
	}
	
	public List<Faculty> findAllFaculties() {
		
		return (List<Faculty>) fr.findAll();
	}
	
	public Faculty findFacultyByUsername(String username) {
		return fr.findFacultyByUsername(username);
	}
	
	public Faculty findFacultyByCredentials(String username, String password) {
		return fr.findFacultyByCredentials(username, password);
	}
	
	

}
