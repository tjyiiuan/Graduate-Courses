package edu.northeastern.cs5200.daos;

public interface RoleImpl {
	
	void assignWebsiteRole(int developerId, int websiteId, String role);
    
	void assignPageRole(int developerId, int pageId, String role);
    
    void deleteWebsiteRole(int developerId, int websiteId, String role);
    
    void deletePageRole(int developerId, int pageId, String role);
}
