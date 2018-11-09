package edu.northeastern.cs5200.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Course;
import edu.northeastern.cs5200.models.Section;

public interface SectionRepository extends CrudRepository<Section, Integer>{
	
	@Query("SELECT section FROM Section section WHERE section.title=:title")
	public Section findSectionByTitle(@Param("title") String title);

	@Query("SELECT section FROM Section section WHERE section.course=:course")
	public List<Section> findSectionForCourse(@Param("course") Course course);

}
