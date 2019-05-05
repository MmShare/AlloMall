package com.example.allomall.controller;


import com.example.allomall.entity.*;
import com.example.allomall.repostitory.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.boot.system.SystemProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat sn=new SimpleDateFormat("yyyyMMddhhmmss");

    DecimalFormat df = new DecimalFormat("#.00");//保留小数点后2位

    @Autowired
    private TypeRepostitory typeRepostitory;

    @Autowired
    private ProductRepostitory productRepostitory;

    @Autowired
    private AssociatedRepostitory associatedRepostitory;

    @Autowired
    private OrderRepostitory orderRepostitory;

    @Autowired
    private DoorRepository doorRepository;

    @Autowired
    private MaterialRepostitory materialRepostitory;

    @RequestMapping(value = "/product/list.html/{state}")
    public String toAllProduct(ModelMap map, @PathVariable("state") Integer state){
        log.info("product页面..................................................");
        if (state==1){
            map.put("productList",productRepostitory.findAll());

        }else if(state==2) {
            map.put("productList",productRepostitory.findProductsByState(1));
        }else {
            map.put("productList",productRepostitory.findProductsByState(0));
        }
        return "product/product-list";
    }

    @RequestMapping(value = "/product/add.html")
    public String toAddProduct(ModelMap map){
        log.info("add Product 页面.................");
        try {
            map.put("doorList",doorRepository.findAll());
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

    @RequestMapping(value = "/product/show.html/{id}")
    public String toProductShow(ModelMap map,@PathVariable("id") Integer id){
        List<Associated> associatedList=associatedRepostitory.findAssociatedsByPid(id);
        List<Material> materialList = new ManagedList<>();
        for (Associated a:associatedList
             ) {
            materialList.add(materialRepostitory.findMaterialById(a.getMid()));
        }
        map.put("materialList",materialList);
        map.put("product",productRepostitory.findProductById(id));
        return "product/product-show";
    }


    @RequestMapping(value = "/product/edit.html/{id}")
    public String toProductEdit(@PathVariable("id") Integer id,ModelMap map){
        map.put("doorList",doorRepository.findAll());
        map.put("product",productRepostitory.findProductById(id));
        map.put("typeList",typeRepostitory.findAll());
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
    public Data doProductDelete(Data data,Product product,@Param("id") Integer id){
        try {
//            product=productRepostitory.findProductById(id);
//            product.setState(0);
//            productRepostitory.save(product);
            productRepostitory.deleteProductById(id);
            data.setSuccess(true);
            data.setMsg("删除商品成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("删除商品时发生错误");
        }
        return data;
    }

    @RequestMapping(value = "/product/additional.html/{id}")
    public String toProductAdditional(ModelMap map,@PathVariable("id") Integer id){
        map.put("materialList",materialRepostitory.findAll());
        map.put("product",productRepostitory.findProductById(id));
        return "product/product-additional-material";
    }

    @RequestMapping(value = "/product/getAdditonalList.json/{pid}")
    @ResponseBody
    public Table getAdditionalList(Table table,@PathVariable("pid") Integer pid){
        List<Material> materialList=new ArrayList<>();
        List<Associated> associatedList=associatedRepostitory.findAssociatedsByPid(pid);
        for (Associated a:associatedList
             ) {
            materialList.add(materialRepostitory.findMaterialById(a.getMid()));
        }
        table.setCode(0);
        table.setMsg("读取成功");
        table.setCount(materialList.size());
        table.setData(materialList);
        return table;
    }
    @RequestMapping(value = "/product/additional.json")
    @ResponseBody
    public Data doProductAdditional(Data data,Associated associated){
        try {
            associated.setState(1);
            associatedRepostitory.save(associated);
            data.setSuccess(true);
            data.setMsg("附加材料成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("附加材料的时候发生错误");
        }
        return data;
    }

    @RequestMapping(value = "/product/removeMaterial.json")
    @ResponseBody
    public Data doProductRemoveMaterial(Data data,@Param("pid") Integer pid,@Param("mid") Integer mid ){
        //associatedRepostitory.deleteAssociatedById(id);
        associatedRepostitory.deleteAssociatedByPidAndMid(pid,mid);
        data.setSuccess(true);
        data.setMsg("移除材料成功");
        return data;
    }

    @RequestMapping(value = "/product/buy.html/{id}")
    public String toProductBuy(ModelMap map,@PathVariable("id") Integer id){
        map.put("product",productRepostitory.findProductById(id));
        map.put("typeList",typeRepostitory.findAll());
        return "product/product-buy";
    }

    @RequestMapping(value = "/product/buy.json")
    @ResponseBody
    public Data doProductBuy(Data data, Order order,@Param("pid") Integer pid,@Param("sumType") String sumType){
        Product productById = productRepostitory.findProductById(pid);
        //order.setId(null);
        order.setPid(pid);
        order.setState(sumType);
        Double sq=(Double.valueOf(order.getHeight())/100)*(Double.valueOf(order.getWidth())/100);
        order.setSquare(df.format(sq));
        order.setPrices(sq*Integer.valueOf(productById.getPrice()));
        order.setNumber(productById.getDid());
        order.setPrices((int)((Double.valueOf(productById.getPrice())*Double.valueOf(order.getSquare()))+0.50));
        order.setHavePay(productById.getPrice());
        order.setOrderNumber(sn.format(new Date()));
        order.setCreateTime(sf.format(new Date()));
        orderRepostitory.save(order);
        data.setSuccess(true);
        data.setMsg("下单成功");
        return data;
    }



}
