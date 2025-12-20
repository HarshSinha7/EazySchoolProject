package com.project.service;

import com.project.model.Contact;
import com.project.repository.ContactRepository;
import jakarta.validation.groups.ConvertGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public boolean saveMessageDetails(Contact contact) {
        contact.setStatus("OPEN");
        Contact contactObj = contactRepository.save(contact);
        if (contactObj.getContactId() > 0) {
            return true;
        }
        return false;
    }
    public boolean updateMsgStatus(int id){
        boolean sucessOperation=false;
        Optional<Contact> option=contactRepository.findById(id);
        if(option.isPresent()){
            option.get().setStatus("Closed");
            Contact updatedContact=contactRepository.save(option.get());
            sucessOperation=true;
        }
        else throw new RuntimeException("Something wrong is happened");
        return sucessOperation;
    }
    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> list=new ArrayList<>();
        for(Contact contact:contactRepository.findAll()){
            if(contact.getStatus().toLowerCase().equals("open")){
                list.add(contact);
            }
        }
        return list;
    }

}
