package com.project.controller;

import com.project.model.Address;
import com.project.model.Person;
import com.project.model.Profile;
import com.project.repository.RegisterRepo;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
    @Autowired
    RegisterRepo registerRepo;
    @RequestMapping(value = "/displayProfile")
    public ModelAndView displayProfile(Authentication auth){
        Profile profile=new Profile();
        Person person=registerRepo.getByEmail(auth.getName());
        ModelAndView view=new ModelAndView("profile.html");
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNum());
        if(person.getAddress()!=null && person.getAddress().getAddressId()>1){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        view.addObject("profile", profile);
        return view;
    }
    @RequestMapping(value="/updateProfile")
    public ModelAndView updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, Authentication auth){
        ModelAndView view = new ModelAndView("profile.html");
        if(errors.hasErrors()){
            return view;
        }
        Person person=registerRepo.getByEmail(auth.getName());
        person.setAddress(new Address());
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        registerRepo.save(person);
        view.addObject("profile", profile);
        return view;
    }
}
