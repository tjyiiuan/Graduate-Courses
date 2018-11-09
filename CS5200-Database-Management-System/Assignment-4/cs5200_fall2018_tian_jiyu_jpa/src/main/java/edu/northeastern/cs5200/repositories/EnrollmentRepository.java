package edu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5200.models.Enrollment;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer>{
	

}
