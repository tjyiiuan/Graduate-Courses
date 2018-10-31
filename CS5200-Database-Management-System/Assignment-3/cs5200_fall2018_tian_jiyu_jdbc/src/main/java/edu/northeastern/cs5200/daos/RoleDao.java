package edu.northeastern.cs5200.daos;

import java.sql.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Role;

public class RoleDao implements RoleImpl {
		
		private static RoleDao instance = null;
		
		private RoleDao( ) {
			
		}
		
		public static RoleDao getInstance() {
			if(instance == null) {
				instance = new RoleDao();
			}
			return instance;
		}
		
	    private PreparedStatement pStatement = null;
	    private ResultSet rSet = null;
	    
		private final String CREATE_WEB_Role = "INSERT INTO websiteRole (Role, developerId, websiteId) VALUES (?,?,?)";	
		private final String CREATE_PAGE_Role = "INSERT INTO pageRole (Role, developerId, pageId) VALUES (?,?,?)";	
		private final String DELETE_WEB_Role = "DELETE from websiteRole where Role = ? and developerId =? and websiteId = ?";
		private final String DELETE_PAGE_Role = "DELETE from pageRole where Role = ? and developerId =? and pageId = ?";
		
		@Override
		public void assignWebsiteRole(int developerId, int websiteId, int Role) {
			
			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(CREATE_WEB_Role);
				pStatement.setInt(1, Role);
				pStatement.setInt(2, developerId);
				pStatement.setInt(3, websiteId);
				res = pStatement.executeUpdate();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					} finally {
						try {
							pStatement.close();
							Connection.closeConnection();
							} catch (SQLException e) {
								e.printStackTrace();
								}
						}      
			}
			
		@Override
		public void assignPageRole(int developerId, int pageId, int Role){

			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(CREATE_PAGE_Role);
				pStatement.setInt(1, Role);
				pStatement.setInt(2, developerId);
				pStatement.setInt(3, pageId);
				
				res = pStatement.executeUpdate();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					} finally {
						try {
							pStatement.close();
							Connection.closeConnection();
							} catch (SQLException e) {
								e.printStackTrace();
								}
						}      
			}
		
		@Override
		public void deleteWebsiteRole(int developerId, int websiteId, int Role) {

			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(DELETE_WEB_Role);
				pStatement.setInt(1, Role);
				pStatement.setInt(2, developerId);
				pStatement.setInt(3, websiteId);
				
				res = pStatement.executeUpdate();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					} finally {
						try {
							pStatement.close();
							Connection.closeConnection();
							} catch (SQLException e) {
								e.printStackTrace();
								}
						}      
			}

		@Override
		public void deletePageRole(int developerId, int pageId, int Role) {

			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(DELETE_PAGE_Role);
				pStatement.setInt(1, Role);
				pStatement.setInt(2, developerId);
				pStatement.setInt(3, pageId);
				
				res = pStatement.executeUpdate();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					} finally {
						try {
							pStatement.close();
							Connection.closeConnection();
							} catch (SQLException e) {
								e.printStackTrace();
								}
						}      
			}
	}