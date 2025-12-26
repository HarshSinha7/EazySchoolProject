package com.project.controller;

import com.project.model.Classes;
import com.project.model.Person;
import com.project.repository.ClassesRepository;
import com.project.repository.RegisterRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    RegisterRepo registerRepo;

    @RequestMapping(value = "/displayClasses")
    public ModelAndView classesController(){
        List<Classes> classList=classesRepository.findAll();
        ModelAndView view=new ModelAndView("classes.html");
        view.addObject("classes", classList);
        view.addObject("class", new Classes());
        return view;
    }

    @PostMapping(value = "/addNewClass")
    public ModelAndView addNewClass(@Valid @ModelAttribute("class") Classes classes, Errors error){
        if(error.hasErrors()){
            throw new RuntimeException("Class name contains errors");
        }
        classesRepository.save(classes);
        ModelAndView view=new ModelAndView("redirect:/admin/displayClasses");
        return view;
    }

    @RequestMapping(value = "/deleteClass")
    public ModelAndView deleteClass(@PathVariable("id") int id){
        Optional<Classes> optional=classesRepository.findById(id);
        for(Person person:optional.get().getPersons()){
            person.setClasses(null);
            registerRepo.save(person);
        }
        classesRepository.deleteById(id);
        ModelAndView view=new ModelAndView("redirect:/admin/displayClasses");
        return view;
    }
}
