package edu.northeastern.cs5200.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Enrollment;
import edu.northeastern.cs5200.models.Section;
import edu.northeastern.cs5200.models.Student;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer>{
	
	@Query("SELECT enrollment FROM Enrollment enrollment WHERE enrollment.student=:student AND enrollment.section=:section")
	public Enrollment findOneEnrollment(@Param("student") Student student, @Param("section") Section section);

	@Query("SELECT enrollment FROM Enrollment enrollment WHERE enrollment.section=:section")
	public List<Enrollment> findEnrollmentBySection(@Param("section") Section section);

	@Query("SELECT enrollment FROM Enrollment enrollment WHERE enrollment.student=:student")
	public List<Enrollment> findEnrollmentByStudent(@Param("student") Student student);

}
