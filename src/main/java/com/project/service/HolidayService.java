package com.project.service;

import com.project.model.Holiday;
import com.project.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {
    @Autowired
    HolidayRepository holidayRepository;

    public List<Holiday> getHoliday(){
        return holidayRepository.findAll();
    }
}
