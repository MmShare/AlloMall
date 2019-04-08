package com.example.allomall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {


    @RequestMapping("/index.html")
    public String toLogin(){
        return "index";
    }

    @RequestMapping("/console.html")
    public String toConsole(){
        return "home/console";
    }
}
