package com.project.controller;

import com.project.model.Person;
import com.project.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping(value = "public")
public class RegisterController {
    @Autowired
    RegisterService registerService;
    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String registerController(Model model){
        model.addAttribute("person",new Person());
        return "register.html";
    }
    @RequestMapping(value = "/createUser", method = {RequestMethod.POST})
    public String saveRegisterDetails(@Valid @ModelAttribute("person") Person person, Errors error, Model model){
        if(error.hasErrors()){
            return "register.html";
        }
        if(!person.getEmail().equals(person.getConfirmEmail())){
            model.addAttribute("emailValidate", true);
            model.addAttribute("emailValidateMessage", "Emails didn't match");
            return "register.html";
        }
        if(!person.getPassword().equals(person.getConfirmPassword())){
            model.addAttribute("passwordValidate", true);
            model.addAttribute("passwordValidateMessage", "Passwords didn't match");
            return "register.html";
        }
        if(registerService.saveDetails("Student", person)){
            return "redirect:/login?register=true";
        }
        return "register.html";
    }
}
