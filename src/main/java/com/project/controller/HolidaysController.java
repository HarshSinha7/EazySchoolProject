package com.project.controller;

import com.project.model.Holiday;
import com.project.service.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class HolidaysController {
    @Autowired
    HolidayService holidayService;

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display,Model model) {
        if(null != display && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("federal")){
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("festival")){
            model.addAttribute("festival",true);
        }
        List<Holiday> festival=new ArrayList<>();
        List<Holiday> federal=new ArrayList<>();
        for(Holiday holiday:holidayService.getHoliday()){
            if(holiday.getType().toString().equals("FEDERAL")){
                federal.add(holiday);
            }
            else{
                festival.add(holiday);
            }
        }
        model.addAttribute("FESTIVAL",festival);
        model.addAttribute("FEDERAL", federal);
        return "holidays.html";
    }

}
