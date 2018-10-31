package edu.northeastern.cs5200.daos;

public interface RoleImpl {
	
	void assignWebsiteRole(int developerId, int websiteId, int role);
    
	void assignPageRole(int developerId, int pageId, int role);
    
    void deleteWebsiteRole(int developerId, int websiteId, int role);
    
    void deletePageRole(int developerId, int pageId, int role);
}
