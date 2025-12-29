package com.project.controller;

import com.project.model.Classes;
import com.project.model.Person;
import com.project.repository.ClassesRepository;
import com.project.repository.RegisterRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/displayClasses", method = RequestMethod.GET)
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

    @PostMapping(value = "/deleteClass")
    public ModelAndView deleteClass(@RequestParam int id){
        Optional<Classes> optional=classesRepository.findById(id);
        for(Person person:optional.get().getPersons()){
            person.setClasses(null);
            registerRepo.save(person);
        }
        classesRepository.deleteById(id);
        ModelAndView view=new ModelAndView("redirect:/admin/displayClasses");
        return view;
    }

    @GetMapping(value = "/displayStudents")
    public ModelAndView displayStudents(@RequestParam(value = "classId") int id,@RequestParam(value="error", required = false)boolean error, HttpSession session){
        ModelAndView view=new ModelAndView("students.html");
        Optional<Classes> classes=classesRepository.findById(id);
        view.addObject("person", new Person());
        view.addObject("eazyClass", classes.get());
        session.setAttribute("class",classes.get());
        if(error){
            String errorMessage = "Invalid Email entered!!";
            view.addObject("errorMessage", errorMessage);
        }
        return view;
    }

    @PostMapping(value = "/addStudent")
    public ModelAndView addStudents(@ModelAttribute("person") Person person, Errors errors, HttpSession httpSession){
        ModelAndView view=new ModelAndView("students.html");
        Person person1=registerRepo.getByEmail(person.getEmail());
        if(person1!=null || !(person1.getPersonId()>0)){
            view.setViewName("redirect:/admin/displayStudents?error=true");
            return view;
        }
        Classes classes=(Classes)httpSession.getAttribute("class");
        person1.setClasses(classes);
        registerRepo.save(person1);
        classes.getPersons().add(person1);
        classes.getPersons().add(person1);
        view.setViewName("redirect:/admin/displayStudents?classId="+classes.getClassId());
        return view;
    }
    @PostMapping(value = "/deleteStudent")
    public ModelAndView deleteStudents(@RequestParam(value = "classId")int id, HttpSession session){
        Classes classes=(Classes)session.getAttribute("class");
        Optional<Person> person=registerRepo.findById(id);
        person.get().setClasses(null);
        classes.getPersons().remove(person);
        Classes classes1=classesRepository.save(classes);
        session.setAttribute("class", classes1);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+classes1.getClassId());
        return modelAndView;
    }
}
