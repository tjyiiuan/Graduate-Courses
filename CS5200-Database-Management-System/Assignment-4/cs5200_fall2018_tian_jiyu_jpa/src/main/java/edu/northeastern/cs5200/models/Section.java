package edu.northeastern.cs5200.models;

import javax.persistence.*;

@Entity
public class Section {
	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int seats;
	
	@ManyToOne
	private Course course;
	
	
	public Section() {}
	
	public Section(int seats) {
		this.seats = seats;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	

}
