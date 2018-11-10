package edu.northeastern.cs5200.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.*;

@Component
public class EnrollmentDao {

    @Autowired
    SectionRepository secr;
    
	@Autowired
	StudentRepository stur;
	
	@Autowired
	EnrollmentRepository er;
	
    
	private static EnrollmentDao instance = null;

	private EnrollmentDao() {}

	public static EnrollmentDao getInstance() {
		if (instance == null) {
			instance = new EnrollmentDao();
		}
		return instance;
	}
	
	
	
	public void createEnrollment(Enrollment enrollment) {
		
		Section section = enrollment.getSection();
		if (this.findOneEnrollment(enrollment.getStudent(), section) == null) {
			if (this.numSeatsLeftInSection(section) > 0) {
				er.save(enrollment);
			} else {
				System.out.println("No seats left.");
			}
		} else {
			System.out.println("Entity already exists.");
		}
	}

	public List<Student> findStudentsInSection(Section section) {
		
		List<Student> students = new ArrayList<Student>();
		List<Enrollment> enrolls = er.findEnrollmentBySection(section);
		for(Enrollment enrollment: enrolls) {
			Student student = enrollment.getStudent();
			students.add(student);
		}
		
		return students;
	}
	
	public List<Section> findSectionsForStudent(Student student) {
		
		List<Section> sections = new ArrayList<Section>();
		List<Enrollment> enrolls = er.findEnrollmentByStudent(student);
		for(Enrollment enrollment: enrolls) {
			Section section = enrollment.getSection();
			sections.add(section);
		}
		
		return sections;
	}

	
	public int numSeatsLeftInSection(Section section) {
		
		return section.getSeats() - this.findStudentsInSection(section).size();
	}
	
	public Enrollment findOneEnrollment(Student student, Section section) {
		
		return er.findOneEnrollment(student, section);
	}

	public void emptyEnrollmentTable() {
		er.deleteAll();
	}
	
}
