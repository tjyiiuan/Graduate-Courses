package edu.northeastern.cs5200.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@Component
public class StudentDao {
	
    @Autowired
	StudentRepository sr;
    
	private static StudentDao instance = null;

	private StudentDao() {}

	public static StudentDao getInstance() {
		if (instance == null) {
			instance = new StudentDao();
		}
		return instance;
	}
    
	
	
	public void createStudent(Student student) {
		if (this.findStudentByCredentials(student.getUsername(), student.getPassword()) == null) {
			sr.save(student);
		} else {
			System.out.println("Entity already exists.");
		}
	}
    
	public List<Student> findAllStudents() {
		
		return (List<Student>) sr.findAll();
	}
	
	public Student findStudentByUsername(String username) {
		return sr.findStudentByUsername(username);
	}
	
	public Student findStudentByCredentials(String username, String password) {
		return sr.findStudentByCredentials(username, password);
	}
	
    
    
}
