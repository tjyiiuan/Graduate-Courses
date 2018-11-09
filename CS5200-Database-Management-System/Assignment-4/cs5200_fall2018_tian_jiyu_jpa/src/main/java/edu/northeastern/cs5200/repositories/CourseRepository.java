package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Course;

public interface CourseRepository extends CrudRepository<Course, Integer>{
	
	@Query("SELECT course FROM Course course WHERE course.label = :label")
	public Course findCourseByLabel(@Param("label") String label);

}
