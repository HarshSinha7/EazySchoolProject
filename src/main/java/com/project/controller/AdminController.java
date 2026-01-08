package com.project.controller;

import com.project.model.Classes;
import com.project.model.Courses;
import com.project.model.Person;
import com.project.repository.ClassesRepository;
import com.project.repository.CourseRepository;
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

    @Autowired
    CourseRepository courseRepository;

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
    @GetMapping(value = "/displayCourses")
    public ModelAndView displayCourses(){
        ModelAndView view=new ModelAndView("courses_secure.html");
        List<Courses> courses=courseRepository.findAll();
        view.addObject("course", new Courses());
        view.addObject("courses",courses);
        return view;
    }

    @PostMapping(value = "/addNewCourse")
    public ModelAndView addNewCourses(@ModelAttribute(value = "course")Courses courses){
        ModelAndView view=new ModelAndView("course_secure.html");
        Courses courses1=courseRepository.save(courses);
        view.setViewName("redirect:/admin/displayCourses");
        return view;
    }

    @RequestMapping(value = "/viewStudents")
    public ModelAndView viewStudents(HttpSession session, @RequestParam(name = "id")int id,@RequestParam(name = "error", required = false)String error){
        ModelAndView view=new ModelAndView("course_students.html");
        Optional<Courses> courses=courseRepository.findById(id);
        view.addObject("courses", courses.get());
        view.addObject("person", new Person());
        session.setAttribute("course", courses.get());
        if(error!=null){
            String message="Invalid email address";
            view.addObject("errorMessage", message);
        }
        return view;
    }

    @PostMapping(value = "/addStudentToCourse")
    public ModelAndView addStudentsToCourses(HttpSession session, @ModelAttribute(value = "person")Person person){
        ModelAndView view=new ModelAndView();
        Courses course=(Courses)session.getAttribute("course");
        Person personEntity=registerRepo.getByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            view.setViewName("redirect:/admin/viewStudents?id="+course.getCourseId()
                    +"&error=true");
            return view;
        }
        personEntity.getCourses().add(course);
        course.getPersons().add(person);
        registerRepo.save(person);
        session.setAttribute("course", course);
        view.setViewName("redirect:/admin/viewStudents/?id="+course.getCourseId());
        return view;
    }

    @RequestMapping(value = "/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(HttpSession session, @RequestParam(name = "personId")int id){
        ModelAndView view=new ModelAndView();
        Courses course=(Courses)session.getAttribute("course");
        Optional<Person> person=registerRepo.findById(id);
        person.get().getCourses().remove(course);
        course.getPersons().remove(person);
        registerRepo.save(person.get());
        session.setAttribute("course", course);
        view.setViewName("redirect:/admin/viewStudents?id="+course.getCourseId());
        return view;
    }
}
