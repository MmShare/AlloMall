package com.example.allomall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(value = "/windowsType.html")
    public String toWindowsType(){
        return "system/material-list";
    }

    @RequestMapping(value = "/material.html")
    public String toMaterial(){
        return "system/material-list";
    }
}
