package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.models.Phone;

public interface PhoneImpl {

    void addPhone(int personId, Phone phone);
    
    void updatePrimaryPhone(int personId, Phone phone);
    
    void deletePrimaryPhone(int personId, Phone phone);
}
