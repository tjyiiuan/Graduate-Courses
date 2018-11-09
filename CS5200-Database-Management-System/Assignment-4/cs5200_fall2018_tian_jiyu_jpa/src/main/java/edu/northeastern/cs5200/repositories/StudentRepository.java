package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>{
	
	@Query("SELECT student FROM Student student WHERE student.username = :username")
	public Student findStudentByUsername(@Param("username") String username);

	@Query("SELECT student FROM Student student WHERE student.username=:username AND student.password=:password")
	public Student findStudentByCredentials(@Param("username") String username, @Param("password") String password);
}
