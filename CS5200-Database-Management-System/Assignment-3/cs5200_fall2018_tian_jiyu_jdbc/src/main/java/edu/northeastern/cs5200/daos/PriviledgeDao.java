package edu.northeastern.cs5200.daos;

import java.sql.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Priviledge;

public class PriviledgeDao implements PriviledgeImpl {
		
		private static PriviledgeDao instance = null;
		
		private PriviledgeDao( ) {
			
		}
		
		public static PriviledgeDao getInstance() {
			if(instance == null) {
				instance = new PriviledgeDao();
			}
			return instance;
		}
		
	    private PreparedStatement pStatement = null;
	    private ResultSet rSet = null;
	    
		private final String CREATE_WEB_PRIVILEDGE = "INSERT INTO websitePriviledge (priviledge, developerId, websiteId) VALUES (?,?,?)";	
		private final String CREATE_PAGE_PRIVILEDGE = "INSERT INTO pagePriviledge (priviledge, developerId, pageId) VALUES (?,?,?)";	
		private final String DELETE_WEB_PRIVILEDGE = "DELETE from websitePriviledge where priviledge = ? and developerId =? and websiteId = ?";
		private final String DELETE_PAGE_PRIVILEDGE = "DELETE from pagePriviledge where priviledge = ? and developerId =? and pageId = ?";
		
		@Override
		public void assignWebsitePriviledge(int developerId, int websiteId, String priviledge) {
			
			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(CREATE_WEB_PRIVILEDGE);
				pStatement.setString(1, priviledge);
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
		public void assignPagePriviledge(int developerId, int pageId, String priviledge){

			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(CREATE_PAGE_PRIVILEDGE);
				pStatement.setString(1, priviledge);
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
		public void deleteWebsitePriviledge(int developerId, int websiteId, String priviledge) {

			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(DELETE_WEB_PRIVILEDGE);
				pStatement.setString(1, priviledge);
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
		public void deletePagePriviledge(int developerId, int pageId, String priviledge) {

			int res = 0;
			
			try {
				
				pStatement = Connection.getConnection().prepareStatement(DELETE_PAGE_PRIVILEDGE);
				pStatement.setString(1, priviledge);
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