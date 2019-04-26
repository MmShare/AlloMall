package com.example.allomall.controller;


import com.example.allomall.entity.Data;
import com.example.allomall.entity.Product;
import com.example.allomall.repostitory.ProductRepostitory;
import com.example.allomall.repostitory.TypeRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.SystemProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TypeRepostitory typeRepostitory;

    @Autowired
    private ProductRepostitory productRepostitory;

    @RequestMapping(value = "/product/list.html/{typeid}")
    public String toAllProduct(){
        log.info("product页面..................................................");
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
    public Data doProductAdd(Data data, Product product){
        log.info("do product add .....................................");
        try {
            product.setDate(sf.format(new Date()));
            product.setState(1);
            productRepostitory.save(product);
            data.setSuccess(true);
            data.setMsg("新增成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("新增时发生错误");
        }
        return data;
    }

    @RequestMapping(value = "/product/show.html")
    public String toProductShow(ModelMap map){
        return "product/product-show";
    }


    @RequestMapping(value = "/product/edit.html")
    public String toProductEdit(@Param("id") Integer id,ModelMap map){
        //map.put("Product",productRepostitory.f)
        return "product/product-edit";
    }

    @RequestMapping(value = "/product/edit.json")
    @ResponseBody
    public Data doProductEdit(Data data,Product product){
        try {
            productRepostitory.save(product);
            data.setSuccess(true);
            data.setMsg("修改商品信息成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("修改商品信息的时发生错误");
        }
        return data;
    }

    @RequestMapping(value = "/product/delete.json")
    @ResponseBody
    public Data doProductDelete(Data data,@Param("id") Integer id){
        try {
            //
            data.setSuccess(true);
            data.setMsg("删除商品成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("删除商品时发生错误");
        }
        return data;
    }

    @RequestMapping(value = "/product/additional.html")
    public String toProductAdditional(){
        return "product/additional-material";
    }

    @RequestMapping(value = "/product/additional.json")
    @ResponseBody
    public Data doProductAdditional(Data data){
        try {
            data.setSuccess(true);
            data.setMsg("附加材料成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("附加材料的时候发生错误");
        }
        return data;
    }

}
