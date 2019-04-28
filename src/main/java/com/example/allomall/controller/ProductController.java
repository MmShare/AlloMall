package com.example.allomall.controller;


import com.example.allomall.Config.SystemParameter;
import com.example.allomall.entity.Data;
import com.example.allomall.entity.Product;
import com.example.allomall.repostitory.ProductRepostitory;
import com.example.allomall.repostitory.TypeRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TypeRepostitory typeRepostitory;

    @Autowired
    private ProductRepostitory productRepostitory;

    @RequestMapping(value = "/product/list.html/{state}")
    public String toAllProduct(ModelMap map, @PathVariable("state") Integer state){
        log.info("product页面..................................................");
        if (state==1){
            map.put("productList",productRepostitory.findAll());
        }else if (state==2){
            map.put("productList",productRepostitory.findProductsByState(SystemParameter.IS_USER));
        }else {
            map.put("productList",productRepostitory.findProductsByState(SystemParameter.NO_USER));
        }
        return "product/product-list";
    }

    @RequestMapping(value = "/product/add.html")
    public String toAddProduct(ModelMap map){
        log.info("add Product 页面.................");
        try {
        map.put("typeList",typeRepostitory.findAll());
        }catch (Exception e){
            log.info("进入添加商品页面时,读取门窗类型发生错误");
        }
        return "product/product-add";
    }

    @RequestMapping(value = "/product/upload.json")
    @ResponseBody
    public Data doUploadImage(Data data){
        log.info(" do image uploading ..................................");
        return data;
    }

    @RequestMapping(value = "/product/add.json")
    @ResponseBody
    public Data doAddProduct(Data data, Product product){
        log.info("do add product.......................................");
        try {
        product.setDate(sf.format(new Date()));
        product.setState(SystemParameter.IS_USER);
        productRepostitory.save(product);
        data.setSuccess(true);
        data.setMsg("添加商品成功");
        }catch (Exception e){

        }
        return data;
    }

}
