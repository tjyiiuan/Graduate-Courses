package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.models.Address;

public interface AddressImpl {

    void addAddress(int personId, Address address);
    
    void updatePrimaryAddress(int personId, Address address);
    
    void deletePrimaryAddress(int personId, Address address);
}
