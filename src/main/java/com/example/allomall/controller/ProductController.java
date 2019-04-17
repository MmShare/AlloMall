package com.example.allomall.controller;


import com.example.allomall.repostitory.TypeRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);


    @Autowired
    private TypeRepostitory typeRepostitory;

    @RequestMapping(value = "/product.html/{typeid}")
    public String toAllProduct(){
        log.info("product页面..................................................");
        return "product/product-list";
    }

    @RequestMapping(value = "/add.html")
    public String toAddProduct(){
        log.info("add Product 页面.................");
        return "product/product-add";
    }

}
