package com.project.controller;

import com.project.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/student")
public class StudentController {

    @GetMapping(value = "/displayCourses")
    public ModelAndView displayCourses(HttpSession session){
        ModelAndView view=new ModelAndView("courses_enrolled.html");
        Person person=(Person)session.getAttribute("personSession");
        view.addObject("person", person);
        return view;
    }
}
