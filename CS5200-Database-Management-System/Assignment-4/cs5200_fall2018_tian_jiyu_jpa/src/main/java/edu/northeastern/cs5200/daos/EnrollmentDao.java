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
	
	
	
	public boolean createEnrollment(Enrollment enrollment) {
		
		Section section = enrollment.getSection();
		int seats = section.getSeats();
		if (this.findOneEnrollment(enrollment.getStudent(), section) == null) {
			if (seats > 0) {
				seats -= 1;
				section.setSeats(seats);
				er.save(enrollment);
				secr.save(section);
			} else {
				System.out.println("No seats left.");
				return false;
			}
		} else {
			System.out.println("Entity already exists.");
			return false;
		}
		
		return true;
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

	public Enrollment findOneEnrollment(Student student, Section section) {
		
		return er.findOneEnrollment(student, section);
	}
	
	public void removeOneEnrollment(Student student, Section section) {
		
		Enrollment enrollment = this.findOneEnrollment(student, section);
		if (enrollment != null) {
			
			int seats = section.getSeats();
			er.delete(enrollment);
			seats += 1;
			section.setSeats(seats);
			secr.save(section);
		}

	}

	public void emptyEnrollmentTable() {
		er.deleteAll();
	}
	
}
