package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Faculty;

public interface FacultyRepository extends CrudRepository<Faculty, Integer>{
	
	@Query("SELECT faculty FROM Faculty faculty WHERE faculty.username=:username")
	public Faculty findFacultyByUsername(@Param("username") String username);
	
	@Query("SELECT faculty FROM Faculty faculty WHERE faculty.username=:username AND faculty.password=:password")
	public Faculty findFacultyByCredentials(@Param("username") String username, @Param("password") String password);

}
