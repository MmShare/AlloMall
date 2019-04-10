package com.example.allomall.controller;

import com.example.allomall.entity.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

    private static  final Logger log= LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/reg.json",method = RequestMethod.GET)
    @ResponseBody
    public Data doReg(Data data){
        log.info("开始注册ing..................................");
        data.setSuccess(true);
        data.setMsg("注册成功111");
        return data;
    }

    @RequestMapping(value = "/login.json")
    @ResponseBody
    public Data doLogin(Data data){
        log.info("开始登录ing..................................");
        data.setSuccess(true);
        data.setMsg("登录成功111");
        return data;
    }

}
