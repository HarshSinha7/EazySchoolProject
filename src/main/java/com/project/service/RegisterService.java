package com.project.service;

import com.project.model.Person;
import com.project.model.Roles;
import com.project.repository.RegisterRepo;
import com.project.repository.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    RolesRepo rolesRepo;
    @Autowired
    RegisterRepo registerRepo;

    public boolean saveDetails(String role, Person person){
        Roles roles=rolesRepo.getByRoleName(role);
        person.setRoles(roles);
        Person person1=registerRepo.save(person);
        if(person1!=null && person1.getPersonId()>1){
            return true;
        }
        return false;
    }
}
