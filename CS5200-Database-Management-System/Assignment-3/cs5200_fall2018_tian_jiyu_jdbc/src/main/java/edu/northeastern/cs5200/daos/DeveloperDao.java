package edu.northeastern.cs5200.daos;

import java.sql.*;
import java.util.*;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.models.*;

public class DeveloperDao implements DeveloperImpl{
	
	private static DeveloperDao instance = null;

	private DeveloperDao() {
		
	}

	public static DeveloperDao getInstance() {
		if (instance == null) {
			instance = new DeveloperDao();
		}
		return instance;
	}
	
    PhoneDao phonedao = PhoneDao.getInstance();
    AddressDao addressdao = AddressDao.getInstance();
    
    private PreparedStatement pStatement1 = null;
    private PreparedStatement pStatement2 = null;
    private ResultSet rSet = null;
    
	private final String CREATE_PERSON = "INSERT INTO person (id, firstName, lastName, username, password, email, dob) VALUES(?,?,?,?,?,?,?)";
	private final String CREATE_DEVELOPER = "INSERT INTO developer (id, developerKey) VALUES(?,?)";
	private final String CREATE_PHONE = "INSERT INTO phone (phone, `primary`, person_id) VALUES(?,?,?)";
	private final String CREATE_ADDRESS = "INSERT INTO address (street1, street2, city, state, zip, `primary`, person_id) VALUES(?,?,?,?,?,?,?)";
	
	private final String FIND_ALL_DEVELOPERS = "SELECT * FROM person, developer WHERE person.id = developer.id";
	private final String FIND_DEVELOPER_BY_ID = "SELECT * FROM person, developer WHERE person.id = ? AND person.id = developer.id";
	private final String FIND_DEVELOPER_BY_USERNAME = "SELECT * FROM person, developer WHERE person.username = ? AND person.id = developer.id";
	private final String FIND_DEVELOPER_BY_CREDENTIALS = "SELECT * FROM developer, person WHERE person.id = developer.id AND password = ? AND username = ?";

	private final String UPDATE_PERSON = "UPDATE person SET firstName = ?, lastName = ?, username = ?, password = ?, email = ?, dob = ? WHERE person.id = ?";
	private final String UPDATE_DEVELOPER = "UPDATE developer SET developer.developerkey = ? WHERE developer.id = ?";
	private final String UPDATE_PHONE = "UPDATE phone SET phone = ? WHERE person_id = ? AND `primary` = ?";
	private final String UPDATE_ADDRESS = "UPDATE address SET street1 = ?, street2 = ?,city= ?, state = ?, zip =?, `primary` = ?, person_id = ? Where person_id = ? and `primary` = ? ";
	
	private final String DELETE_DEVELOPER = "DELETE FROM person WHERE person.id = ?";
	private final String DELETE_ADDRESS = "DELETE FROM address WHERE person_id = ? AND `primary` = ?";
	private final String DELETE_PHONE = "DELETE FROM phone WHERE person_id = ? AND `primary` = ?";
	
	@Override
    public void createDeveloper(Developer developer) {

        try{
            pStatement1 = Connection.getConnection().prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS);
            pStatement1.setInt(1,developer.getId());
            pStatement1.setString(2,developer.getFirstName());
            pStatement1.setString(3,developer.getLastName());
            pStatement1.setString(4,developer.getUserName());
            pStatement1.setString(5,developer.getPassword());
            pStatement1.setString(6,developer.getEmail());
            pStatement1.setDate(7, null);

            int keys = pStatement1.executeUpdate();
            
            if(keys==1) {
            	pStatement2 = Connection.getConnection().prepareStatement(CREATE_DEVELOPER);
            	pStatement2.setInt(1, developer.getId());
            	pStatement2.setString(2, developer.getDeveloperKey());
                int keys2 = pStatement2.executeUpdate();

            for(Phone ph : developer.getPhoneNumbers()){
                phonedao.addPhone(developer.getId(),ph);
            }

            for (Address add : developer.getAddresses()) {
                addressdao.addAddress(developer.getId(),add);
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


	@Override
	public Collection<Developer> findAllDevelopers() {
		
		List<Developer> allDevelopers = new ArrayList<Developer>();
		
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_ALL_DEVELOPERS);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id = rSet.getInt("id");
                String firstName = rSet.getString("firstname");
                String lastName = rSet.getString("lastname");
                String userName = rSet.getString("username");
                String password = rSet.getString("password");
                String email = rSet.getString("email");
                String developerKey = rSet.getString("developerKey");
                java.sql.Date dob = rSet.getDate("dob");

                Developer dev = new Developer(id,firstName,lastName,userName,password,email,dob,developerKey);
                allDevelopers.add(dev);
            }
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rSet.close();
                pStatement1.close();
                Connection.closeConnection();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return allDevelopers;
    }

	@Override
	public Developer findDeveloperById(int developerId) {
		
	       Developer developer = null;
	       
	        try{
	            pStatement1 = Connection.getConnection().prepareStatement(FIND_DEVELOPER_BY_ID);
	            pStatement1.setInt(1,developerId);
	            rSet = pStatement1.executeQuery();
	            while(rSet.next())
	            {
	                int id = rSet.getInt("id");
	                String firstName = rSet.getString("firstname");
	                String lastName = rSet.getString("lastname");
	                String userName = rSet.getString("username");
	                String password = rSet.getString("password");
	                String email = rSet.getString("email");
	                String developerKey = rSet.getString("developerKey");
	                java.sql.Date dob = rSet.getDate("dob");
	                developer = new Developer(id,firstName,lastName,userName,password,email,dob,developerKey);
	            }
	        } catch(ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                rSet.close();
	                pStatement1.close();
	                Connection.closeConnection();
	            } catch(SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return developer;
	    }

	@Override
	public Developer findDeveloperByUsername(String username) {
		
	       Developer developer= null;
	       
	        try{
	            pStatement1 = Connection.getConnection().prepareStatement(FIND_DEVELOPER_BY_USERNAME);
	            pStatement1.setString(1,username);
	            rSet = pStatement1.executeQuery();
	            while(rSet.next())
	            {
	                int id = rSet.getInt("id");
	                String firstName = rSet.getString("firstname");
	                String lastName = rSet.getString("lastname");
	                String userName = rSet.getString("username");
	                String password = rSet.getString("password");
	                String email = rSet.getString("email");
	                String developerKey = rSet.getString("developerKey");
	                java.sql.Date dob = rSet.getDate("dob");
	                developer = new Developer(id,firstName,lastName,userName,password,email,dob, developerKey);
	            }
	        } catch(ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                rSet.close();
	                pStatement1.close();
	                Connection.closeConnection();
	            } catch(SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return developer;
	    }

	@Override
	public Developer findDeveloperByCredentials(String username, String password) {

        Developer developer= null;
        try{
            pStatement1 = Connection.getConnection().prepareStatement(FIND_DEVELOPER_BY_CREDENTIALS);
            pStatement1.setString(1,username);
            pStatement1.setString(2,password);
            rSet = pStatement1.executeQuery();
            while(rSet.next())
            {
                int id = rSet.getInt("id");
                String firstName = rSet.getString("firstname");
                String lastName = rSet.getString("lastname");
                username = rSet.getString("username");
                password = rSet.getString("password");
                String email = rSet.getString("email");
                String developerKey = rSet.getString("developerKey");
                java.sql.Date dob = rSet.getDate("dob");
                developer = new Developer(id,firstName,lastName,username,password,email,dob, developerKey);
            }
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rSet.close();
                pStatement1.close();
                Connection.closeConnection();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return developer;
    }

	@Override
	public int updateDeveloper(int developerId, Developer developer) {
        int res = 0;
        
        try{
            pStatement1 = Connection.getConnection().prepareStatement(UPDATE_DEVELOPER);
            pStatement1.setString(1,developer.getDeveloperKey());
            pStatement1.setInt(2,developerId);
            int res1 = pStatement1.executeUpdate();
            if(res1==1)
            {
            	pStatement2 = Connection.getConnection().prepareStatement(UPDATE_PERSON);
            	pStatement2.setString(1,developer.getFirstName());
            	pStatement2.setString(2,developer.getLastName());
            	pStatement2.setString(3,developer.getUserName());
            	pStatement2.setString(4,developer.getPassword());
            	pStatement2.setString(5,developer.getEmail());
            	pStatement2.setDate(6,null);
            	pStatement2.setInt(7,developerId);
                int res2 = pStatement2.executeUpdate();

                for(Phone ph : developer.getPhoneNumbers()){
                    if(ph.isPrimary())
                        phonedao.updatePrimaryPhone(developerId,ph);
                }

                for (Address add : developer.getAddresses()) {
                    if(add.isPrimary())
                        addressdao.updatePrimaryAddress(developerId,add);
                    }
                }
            } catch(ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                	pStatement2.close();
                    pStatement1.close();
                    Connection.closeConnection();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }

	@Override
	public int deleteDeveloper(int developerId) {
		
		int res = 0;
		
        try {
            pStatement1 = Connection.getConnection().prepareStatement(DELETE_DEVELOPER);
            pStatement1.setInt(1, developerId);
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
