package com.project.service;

import com.project.config.ProjectSecurityConfig;
import com.project.model.Person;
import com.project.model.Roles;
import com.project.repository.RegisterRepo;
import com.project.repository.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    RolesRepo rolesRepo;
    @Autowired
    RegisterRepo registerRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean saveDetails(String role, Person person){
        Roles roles=rolesRepo.getByRoleName(role);
        person.setRoles(roles);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        Person person1=registerRepo.save(person);
        return person1.getPersonId() > 1;
    }
}
