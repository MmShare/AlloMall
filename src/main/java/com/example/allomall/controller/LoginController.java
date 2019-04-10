package com.example.allomall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    private static  final Logger log= LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String toLogin(){
        log.info("访问项目ing........................进入登录页面");
        return "login";
    }

    @RequestMapping("/login.html")
    public String toLogin1(){
        log.info("进入登录页面ing.................................");
        return "login";
    }

    @RequestMapping("/forget.html")
    public String toForget(){
        log.info("忘记密码，进入找回密码页面................................");
        return "forget";
    }

    @RequestMapping("/reg.html")
    public String toReg(){
        log.info("没有账号，进入注册账号页面ing........................");
        return "reg";
    }
}
