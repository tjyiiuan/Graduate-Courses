package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	@Query("SELECT person FROM Person person WHERE person.username=:username")
	public Person findPersonByUsername(@Param("username") String username);
	
	@Query("SELECT person FROM Person person WHERE person.username=:username AND person.password=:password")
	public Person findPersonByCredentials(@Param("username") String username, @Param("password") String password);

	
}
