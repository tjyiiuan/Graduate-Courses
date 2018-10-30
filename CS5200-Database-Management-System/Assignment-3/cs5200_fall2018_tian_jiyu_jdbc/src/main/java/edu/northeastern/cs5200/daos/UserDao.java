package edu.northeastern.cs5200.daos;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.*;

public class UserDao implements UserImpl{
	
	private static UserDao instance = null;

	private UserDao() {
		
	}

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
    PhoneDao phonedao = PhoneDao.getInstance();
    AddressDao addressdao = AddressDao.getInstance();
    
    private PreparedStatement pStatement1 = null;
    private PreparedStatement pStatement2 = null;
    private ResultSet rSet = null;
    
	private final String CREATE_PERSON = "INSERT INTO person (id, firstName, lastName, username, password, email, dob) VALUES(?,?,?,?,?,?,?)";
	private final String CREATE_USER = "INSERT INTO user (id, userAgreement, userKey) VALUES(?,?,?)";
	
	@Override
    public void createUser(User user) {

        try{
            pStatement1 = Connection.getConnection().prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS);
            pStatement1.setInt(1,user.getId());
            pStatement1.setString(2,user.getFirstName());
            pStatement1.setString(3,user.getLastName());
            pStatement1.setString(4,user.getUserName());
            pStatement1.setString(5,user.getPassword());
            pStatement1.setString(6,user.getEmail());
            pStatement1.setDate(7, (java.sql.Date) user.getDob());

            int res1 = pStatement1.executeUpdate();
            
            if(res1==1) {
            	pStatement2 = Connection.getConnection().prepareStatement(CREATE_USER);
            	pStatement2.setInt(1, user.getId());
            	pStatement2.setBoolean(2, true);
            	pStatement2.setString(3, user.getUserKey());
                int res2 = pStatement2.executeUpdate();

            for(Phone ph : user.getPhoneNumbers()){
                phonedao.addPhone(user.getId(),ph);
            }

            for (Address add : user.getAddresses()) {
                addressdao.addAddress(user.getId(),add);
                }
            }
            } catch(ClassNotFoundException | SQLException e) {
            	e.printStackTrace();
            	} finally {
            		try {
            			pStatement1.close();
            			pStatement2.close();
            			Connection.closeConnection();
            			} catch(SQLException e) {
            				e.printStackTrace();
            				}
            		}
        }
	}
