package edu.northeastern.cs5200.daos;

import java.util.Collection;


import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Developer;
import edu.northeastern.cs5200.models.Address;
import edu.northeastern.cs5200.models.Phone;
public class DeveloperDao implements DeveloperImpl{
	
	private static DeveloperDao instance = null;

	private DeveloperDao() {
	}

	public static DeveloperDao getInstance() {
		if (instance == null)
			instance = new DeveloperDao();
		return instance;
	}

	String CREATE_PERSON = "INSERT INTO person(id, first_Name, last_name, username, password, email, dob) VALUES(?,?,?,?,?,?,?)";
	String CREATE_DEVELOPER = "INSERT INTO developer(id, developer_key) VALUES(?,?)";
	String CREATE_PHONE = "INSERT INTO phone(id, phone, `primary`, person_id) VALUES(?,?,?,?)";
	String CREATE_ADDRESS = "INSERT INTO address(id, street1, street2, city, state, zip, `primary`, person_id) VALUES(?,?,?,?,?,?,?,?)";
	String FIND_ALL_DEVELOPERS = "SELECT * FROM person, developer WHERE person.id=developer.id";
	String FIND_DEVELOPER_BY_ID = "SELECT * FROM person, developer WHERE person.id=? AND person.id=developer.id";
	String FIND_DEVELOPER_BY_USERNAME = "SELECT * FROM person, developer WHERE person.username=? AND person.id=developer.id";
	String FIND_DEVELOPER_BY_CREDENTIALS = "SELECT * FROM developer,person WHERE username=? AND password=? AND person.id=developer.id";
	String FIND_ADDRESSES_BY_PERSONID = "SELECT * FROM address WHERE person_id=?";
	String FIND_PHONES_BY_PERSONID = "SELECT * FROM phone WHERE person_id=?";
	String UPDATE_DEVELOPER = "UPDATE developer, person SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, dob = ? WHERE person.id = developer.id and developer.id = ?";
	
	String UPDATE_PHONE = "UPDATE phone SET phone = ? WHERE person_id = ? AND `primary` = 1";
	String DELETE_DEVELOPER = "DELETE FROM developer, person WHERE developer.id = ? AND person.id = developer.id";
	String DELETE_ADDRESS = "DELETE FROM address WHERE person_id=?";
	String DELETE_PHONE = "DELETE FROM phone WHERE person_id=?";
	
	
	@Override
	public void createDeveloper(Developer developer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Developer> findAllDevelopers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Developer findDeveloperById(int developerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Developer findDeveloperByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Developer findDeveloperByCredentials(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateDeveloper(int developerId, Developer developer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteDeveloper(int developerId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
