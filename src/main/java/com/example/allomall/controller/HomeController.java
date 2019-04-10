package com.example.allomall.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/index.html")
    public String toIndex(){
        log.info("进入首页");
        return "index";
    }

    @RequestMapping("/home.html")
    public String toHome(){
        return "home/home";
    }
}
