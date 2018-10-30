package edu.northeastern.cs5200.daos;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Page;

public class PageDao implements PageImpl {
	
	private static PageDao instance = null;

	private PageDao() {
		
	}

	public static PageDao getInstance() {
		if (instance == null) {
			instance = new PageDao();
		}
		return instance;
	}
	
    private PreparedStatement pStatement1 = null;
    private PreparedStatement pStatement2 = null;
    private ResultSet rSet = null;
    
	private final String CREATE_PAGE = "INSERT INTO page (id, title, description, created, updated, views, websiteId) VALUES (?,?,?,?,?,?,?)";
	private final String FIND_ALL_PAGES = "SELECT * FROM page";
	private final String FIND_PAGE_BY_ID = "SELECT * FROM page WHERE page.id=?";
	private final String FIND_PAGE_BY_WEBSITE = "SELECT * FROM page WHERE page.websiteId=?";
	private final String UPDATE_PAGE = "UPDATE page SET title=?, description=?, created=?, updated=?, views=?, websiteId = ? WHERE page.id = ?";
	private final String DELETE_PAGE = "DELETE FROM page WHERE page.id = ?";
    
	@Override
	public void createPageForWebsite(int websiteId, Page page) {
		
		int res = 0;
		
		try {
			pStatement1 = Connection.getConnection().prepareStatement(CREATE_PAGE);
			pStatement1.setInt(1, page.getId());
			pStatement1.setString(2, page.getTitle());
			pStatement1.setString(3, page.getDescription());
			pStatement1.setDate(4, (java.sql.Date) page.getCreated());
			pStatement1.setDate(5, (java.sql.Date) page.getUpdated());
			pStatement1.setInt(6, page.getViews());
			pStatement1.setInt(7, page.getWebsiteId());
			
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
	public Collection<Page> findAllPages() {
		
        List<Page> allPages = new ArrayList<Page>();
        
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_ALL_PAGES);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id  =rSet.getInt("id");
                String title = rSet.getString("title");
                String description = rSet.getString("description");
                java.sql.Date created = rSet.getDate("created");
                java.sql.Date updated = rSet.getDate("updated");
                int views = rSet.getInt("views");
                int websiteId = rSet.getInt("websiteId");

                Page page = new Page(id,title,description,created,updated,views,websiteId);
                allPages.add(page);
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
        return allPages;
        }
	                
	@Override
	public Page findPageById(int pageId) {
		
        Page page= null;
        
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_PAGE_BY_ID);
            pStatement1.setInt(1,pageId);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id = rSet.getInt("id");
                String title = rSet.getString("title");
                String description = rSet.getString("description");
                java.sql.Date created = rSet.getDate("created");
                java.sql.Date updated = rSet.getDate("updated");
                int visits = rSet.getInt("views");
                int websiteId = rSet.getInt("websiteId");

                page = new Page(id,title,description,created,updated,visits,websiteId);
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
        return page;
        }

	@Override
	public Collection<Page> findPagesForWebsite(int websiteId) {
		
		List<Page> pagebyweb = new ArrayList<Page>();
		
		try {
			pStatement1 = Connection.getConnection().prepareStatement(FIND_PAGE_BY_WEBSITE);
			pStatement1.setInt(1, websiteId);
			rSet = pStatement1.executeQuery();

			while(rSet.next()) { 
				int id = rSet.getInt("id");
				String title = rSet.getString("title");
				String description = rSet.getString("description");
				java.sql.Date created = rSet.getDate("created");
				java.sql.Date updated = rSet.getDate("updated");
				int views = rSet.getInt("views");
				
				Page page = new Page(id,title,description,created,updated,views,websiteId);
				pagebyweb.add(page);
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
	    return pagebyweb;
    }

	@Override
	public int updatePage(int pageId, Page page) {
		
        int res = 0;
        
        try{
            pStatement1 = Connection.getConnection().prepareStatement(UPDATE_PAGE);
            pStatement1.setString(1,page.getTitle());
            pStatement1.setString(2,page.getDescription());
            pStatement1.setDate(3,(java.sql.Date) page.getCreated());
            pStatement1.setDate(4,(java.sql.Date) page.getUpdated());
            pStatement1.setInt(5,page.getViews());
            pStatement1.setInt(6,page.getWebsiteId());
            pStatement1.setInt(7,pageId);
            
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
	public int deletePage(int pageId) {
        
		int res = 0;
        
		try {
			
            pStatement1 = Connection.getConnection().prepareStatement(DELETE_PAGE);
            pStatement1.setInt(1, pageId);
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
