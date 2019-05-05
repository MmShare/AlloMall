package com.example.allomall.controller;

import com.example.allomall.entity.Data;
import com.example.allomall.entity.User;
import com.example.allomall.repostitory.UserRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    private static  final Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepostitory userRepostitory;

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
    public Data doLogin(Data data, User user){
        log.info("开始登录ing..................................");
        List<User> userList = userRepostitory.findUsersByAccountAndPwd(user.getAccount(), user.getPwd());
        if (userList.size()>0){
            data.setSuccess(true);
            data.setMsg("登录成功");
        }else {
            data.setSuccess(false);
            data.setMsg("登录失败");
        }
        return data;
    }

}
