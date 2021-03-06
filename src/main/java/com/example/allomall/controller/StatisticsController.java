package com.example.allomall.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(value = "/statistics.html")
    public String toStatistics(){
        return "statistical/bar";
    }
}
