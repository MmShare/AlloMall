package com.example.allomall.controller;


import com.example.allomall.repostitory.OrderRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private OrderRepostitory orderRepostitory;

    @RequestMapping(value = "/order/list.html/{typeid}")
    public String toAllOrder(){
        log.info("to order html .................................................");
        return "order/order-list";
    }



}
