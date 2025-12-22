package com.project.security;

import com.project.model.Person;
import com.project.model.Roles;
import com.project.repository.RegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernameAndPasswordAuthenticator implements AuthenticationProvider {
    @Autowired
    RegisterRepo registerRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    public Authentication authenticate(Authentication authentication){
        String username=authentication.getName();
        String password= authentication.getCredentials().toString();
        Person person=registerRepo.getByEmail(username);
        if(person==null){
            throw new BadCredentialsException("User not found");
        }
        if(bCryptPasswordEncoder.matches(password, person.getPassword())){
            return new UsernamePasswordAuthenticationToken(person.getEmail(), null, grantedAuthorityList(person.getRoles()));
        }
        throw new BadCredentialsException("Invalid Credentials");
    }
    private List<GrantedAuthority> grantedAuthorityList(Roles roles){
        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return list;
    }
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
