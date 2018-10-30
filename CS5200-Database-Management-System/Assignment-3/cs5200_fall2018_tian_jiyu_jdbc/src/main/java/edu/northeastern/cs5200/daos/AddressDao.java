package edu.northeastern.cs5200.daos;

import java.sql.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.Address;

public class AddressDao implements AddressImpl {
	
    public static AddressDao instance = null;
    
    private AddressDao() {
    	
    }  
    
    public static AddressDao getInstance(){
        if (instance==null){
            instance=new AddressDao();
        }
        return instance;
    }

    private PreparedStatement pStatement = null;
    
	private final String CREATE_ADDRESS = "INSERT INTO address (street1, street2, city, state, zip, `primary`, person_id) VALUES(?,?,?,?,?,?,?)";
	private final String UPDATE_ADDRESS = "UPDATE address SET street1 = ?, street2 = ?,city= ?, state = ?, zip =?, `primary` = ?, person_id = ? Where person_id = ? and `primary` = ? ";
	private final String DELETE_ADDRESS = "DELETE FROM address WHERE person_id = ? AND `primary` = ?";

    @Override
    public void addAddress(int personId, Address address) {
    	
        try {
            pStatement = Connection.getConnection().prepareStatement(CREATE_ADDRESS);
            pStatement.setString(1, address.getStreet1());
            pStatement.setString(2, address.getStreet2());
            pStatement.setString(3, address.getCity());
            pStatement.setString(4, address.getState());
            pStatement.setString(5, address.getZip());
            pStatement.setBoolean(6, address.isPrimary());
            pStatement.setInt(7, personId);
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
    public void updatePrimaryAddress(int personId, Address address) {
    	
        try {
        	pStatement = Connection.getConnection().prepareStatement(UPDATE_ADDRESS);
        	pStatement.setString(1, address.getStreet1());
        	pStatement.setString(2, address.getStreet2());
        	pStatement.setString(3, address.getCity());
        	pStatement.setString(4, address.getState());
        	pStatement.setString(5, address.getZip());
        	pStatement.setBoolean(6, true);
        	pStatement.setInt(7, personId);
            int res = pStatement.executeUpdate();
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
    public void deletePrimaryAddress(int personId, Address address) {
    	
            try {
                pStatement = Connection.getConnection().prepareStatement(DELETE_ADDRESS);
                pStatement.setBoolean(1, true);
                pStatement.setInt(2, personId);
                int res = pStatement.executeUpdate();
            } catch(ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
                } finally {
                	try {    
                		pStatement.close();
                		Connection.closeConnection();
                		} catch(SQLException e) {
                			e.printStackTrace();
                			}
                	}
            }
    }