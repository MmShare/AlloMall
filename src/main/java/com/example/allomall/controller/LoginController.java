package com.example.allomall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {


    @RequestMapping("/")
    public String toLogin(){
        return "index";
    }

    @RequestMapping("/home.html")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/console.html")
    public String toConsole(){
        return "home/console";
    }
}
