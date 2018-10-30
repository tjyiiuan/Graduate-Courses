package edu.northeastern.cs5200.daos;

import java.sql.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Phone;

public class PhoneDao implements PhoneImpl {
	
    public static PhoneDao instance = null;
    
    private PhoneDao(){
    	
    }
    
    public static PhoneDao getInstance(){
        if (instance==null){
            instance=new PhoneDao();
        }
        return instance;
    }

    private PreparedStatement pStatement = null;
    
	private final String CREATE_PHONE = "INSERT INTO phone (phone, `primary`, person_id) VALUES(?,?,?)";
	private final String UPDATE_PHONE = "UPDATE phone SET phone = ? WHERE person_id = ? AND `primary` = ?";
	private final String DELETE_PHONE = "DELETE FROM phone WHERE person_id = ? AND `primary` = ?";


    @Override
    public void addPhone(int personId, Phone phone) {
    	
        try {
        	pStatement = Connection.getConnection().prepareStatement(CREATE_PHONE);
        	pStatement.setString(1, phone.getPhone());
        	pStatement.setBoolean(2, phone.isPrimary());
        	pStatement.setInt(3, personId);
        	
            int res= pStatement.executeUpdate();
            		
        } catch(ClassNotFoundException | SQLException e) {
        	e.printStackTrace();
        	} finally {
        		try {
        			pStatement.close();
        			Connection.closeConnection();
        			}
        		catch(SQLException e) {
        			e.printStackTrace();
        			}
        		}
        }

    @Override
    public void updatePrimaryPhone(int personId, Phone phone) {
    	
        try {
        	
            pStatement = Connection.getConnection().prepareStatement(UPDATE_PHONE);
            pStatement.setString(1, phone.getPhone());
            pStatement.setBoolean(2, true);
            pStatement.setInt(3, personId);
            
            int res = pStatement.executeUpdate();
            } catch(ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
            	} finally {
	            try {
	            	pStatement.close();
	                Connection.closeConnection();
	            }
	            catch(SQLException e) {
	                e.printStackTrace();
	                }
	            }
        }


    @Override
    public void deletePrimaryPhone(int personId, Phone phone) {
    	
        try {
        	
        	pStatement = Connection.getConnection().prepareStatement(DELETE_PHONE);
        	pStatement.setBoolean(1, true);
        	pStatement.setInt(2, personId);
        	
            int res= pStatement.executeUpdate();
            } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
	        } finally {
	            try {
	            	pStatement.close();
	                Connection.closeConnection();
	            }
	            catch(SQLException e) {
	                e.printStackTrace();
	                }
	            }
	        }
    }