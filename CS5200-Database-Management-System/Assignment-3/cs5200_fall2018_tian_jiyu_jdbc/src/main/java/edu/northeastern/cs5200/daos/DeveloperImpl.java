package edu.northeastern.cs5200.daos;

import java.util.Collection;

import edu.northeastern.cs5200.models.Developer;

public interface DeveloperImpl {

	void createDeveloper(Developer developer);
	
	Collection<Developer> findAllDevelopers();
	
	Developer findDeveloperById(int developerId);
	
	Developer findDeveloperByUsername(String username);
	
	Developer findDeveloperByCredentials(String username, String password);
	
	int updateDeveloper(int developerId, Developer developer);
	
	int deleteDeveloper(int developerId);
}
