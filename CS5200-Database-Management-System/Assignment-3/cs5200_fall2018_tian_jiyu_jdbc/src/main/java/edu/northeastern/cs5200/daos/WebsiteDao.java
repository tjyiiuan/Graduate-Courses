package edu.northeastern.cs5200.daos;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Website;

public class WebsiteDao implements WebsiteImpl{

	private static WebsiteDao instance = null;

	private WebsiteDao() {
		
	}

	public static WebsiteDao getInstance() {
		if (instance == null) {
			instance = new WebsiteDao();
		}
		return instance;
	}
	
    private PreparedStatement pStatement1 = null;
    private PreparedStatement pStatement2 = null;
    private ResultSet rSet = null;
    
	private final String CREATE_WEBSITE = "INSERT INTO website (id,name,description,created,updated,visits,developerId) VALUES (?,?,?,?,?,?,?)";
	private final String FIND_ALL_WEBSITES = "SELECT * FROM website";
	private final String FIND_WEB_BY_DEVELOPER = "SELECT * FROM website WHERE website.developerId = ?";
	private final String FIND_WEB_BY_ID = "SELECT * FROM website WHERE website.id = ?";
	private final String UPDATE_WEBSITE = "UPDATE website SET name = ?, description = ?, created = ?, updated = ?, visits = ?, developerId = ? WHERE website.id = ?";
	private final String DELETE_WEBSITE = "DELETE FROM website WHERE website.id = ?";
    
    
	@Override
	public void createWebsiteForDeveloper(int developerId, Website website) {
		
		int res = 0;
		
		try {
			pStatement1 = Connection.getConnection().prepareStatement(CREATE_WEBSITE);
			pStatement1.setInt(1, website.getId());
			pStatement1.setString(2, website.getName());
			pStatement1.setString(3, website.getDescription());
			pStatement1.setDate(4, (java.sql.Date) website.getCreated());
			pStatement1.setDate(5, (java.sql.Date) website.getUpdated());
			pStatement1.setInt(6, website.getVisits());
			pStatement1.setInt(7, developerId);
			res = pStatement1.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				} finally {
					try {
						pStatement1.close();
						Connection.closeConnection();
						} catch (SQLException e) {
							e.printStackTrace();
							}
					}      
		}

	@Override
	public Collection<Website> findAllWebsites() {

        List<Website> allWebsites = new ArrayList<Website>();
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_ALL_WEBSITES);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id=rSet.getInt("id");
                String name=rSet.getString("name");
                String description=rSet.getString("description");
                java.sql.Date created=rSet.getDate("created");
                java.sql.Date updated=rSet.getDate("updated");
                int visits=rSet.getInt("visits");
                int developerId=rSet.getInt("developerId");

                Website web = new Website(id,name,description,created,updated,visits,developerId);
                allWebsites.add(web);
                }
            } catch (ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
            	} finally {
            		try {
            			rSet.close();
            			pStatement1.close();
            			Connection.closeConnection();
            			} catch (SQLException e) {
            				e.printStackTrace();
            				}
            		}      
        return allWebsites;
        }
	
	@Override
	public Collection<Website> findWebsitesForDeveloper(int developerId) {
		
        List<Website> dWebsites = new ArrayList<Website>();
        
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_WEB_BY_DEVELOPER);
            pStatement1.setInt(1,developerId);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id = rSet.getInt("id");
                String name = rSet.getString("name");
                String description = rSet.getString("description");
                java.sql.Date created = rSet.getDate("created");
                java.sql.Date updated = rSet.getDate("updated");
                int visits = rSet.getInt("visits");
                // int developerId = rSet.getInt("developerId");

                Website web = new Website(id,name,description,created,updated,visits,developerId);
                dWebsites.add(web);
                }
            } catch (ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
            	} finally {
            		try {
            			rSet.close();
            			pStatement1.close();
            			Connection.closeConnection();
            			} catch (SQLException e) {
                  e.printStackTrace();
                  }
            		}      
        return dWebsites;
        }

	@Override
	public Website findWebsiteById(int websiteId) {
		
		Website web = null;
		
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_WEB_BY_ID);
            pStatement1.setInt(1,websiteId);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id = rSet.getInt("id");
                String name = rSet.getString("name");
                String description = rSet.getString("description");
                java.sql.Date created = rSet.getDate("created");
                java.sql.Date updated = rSet.getDate("updated");
                int visits = rSet.getInt("visits");
                int developerId = rSet.getInt("developerId");

                web = new Website(id,name,description,created,updated,visits,developerId);
                }
            } catch (ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
            	} finally {
            		try {
            			rSet.close();
            			pStatement1.close();
            			Connection.closeConnection();
            			} catch (SQLException e) {
            				e.printStackTrace();
            				}
            		}      
        return web;
        }

	@Override
	public int updateWebsite(int websiteID, Website website) {
		
		int res = 0;
        
        try{
			pStatement1 = Connection.getConnection().prepareStatement(UPDATE_WEBSITE);
			pStatement1.setString(1, website.getName());
			pStatement1.setString(2, website.getDescription());
			pStatement1.setDate(3, (java.sql.Date) website.getCreated());
			pStatement1.setDate(4, (java.sql.Date) website.getUpdated());
			pStatement1.setInt(5, website.getVisits());
			pStatement1.setInt(6, website.getDeveloperId());
			pStatement1.setInt(7, websiteID);
			
			res = pStatement1.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				} finally {
					try {
						pStatement1.close();
						Connection.closeConnection();
						} catch (SQLException e) {
							e.printStackTrace();
							}
					}      
        return res;
        }

	@Override
	public int deleteWebsite(int websiteId) {
        
		int res = 0;
        
		try {
            
			pStatement1 = Connection.getConnection().prepareStatement(DELETE_WEBSITE);
            pStatement1.setInt(1, websiteId);
            res = pStatement1.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
            	} finally {
            		try {
            			pStatement1.close();
            			Connection.closeConnection();
            			} catch (SQLException e) {
            				e.printStackTrace();
            				}
            		}      
        return res;
        }
	}
