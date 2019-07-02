package com.example.allomall.controller;


import com.example.allomall.entity.*;
import com.example.allomall.repostitory.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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

    @Value("${system.image.path}")
    private String imagePath;

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

    @Autowired
    private ColorRepostitory colorRepostitory;

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
    public Data doUploadImage(Data data, MultipartFile file ) throws IOException {
        log.info(" do image uploading ..................................");
        File imageFile=new File(imagePath+sn.format(new Date())+".jpg");
        file.transferTo(imageFile);
        data.setMsg(sn.format(new Date())+".jpg");
        data.setSuccess(true);
        data.setCode(200);
        return data;
    }

    @RequestMapping(value = "/product/add.json")
    @ResponseBody
    public Data doProductAdd(Data data, Product product){
        log.info("do product add .....................................");
        try {
            product.setDate(sf.format(new Date()));
            product.setState(1);
            product.setUrl("http://106.13.107.8:8080/image/"+product.getUrl());
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
            product.setUrl("http://106.13.107.8:8080/image/"+product.getUrl());
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
            productRepostitory.deleteProductById(id);
            associatedRepostitory.deleteAssociatedByPid(id);
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
            Associated associatedByPidAndMid = associatedRepostitory.findAssociatedByPidAndMid(associated.getPid(), associated.getMid());
            if (associatedByPidAndMid!=null){
                data.setSuccess(false);
                data.setMsg("您已经添加过这个材料，请不要重复添加");
                return data;
            }
            Material materialById = materialRepostitory.findMaterialById(associated.getMid());
            associated.setNumber(materialById.getMtid());
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
        associatedRepostitory.deleteAssociatedByPidAndMid(pid,mid);
        data.setSuccess(true);
        data.setMsg("移除材料成功");
        return data;
    }

    @RequestMapping(value = "/product/buy.html/{id}")
    public String toProductBuy(ModelMap map,@PathVariable("id") Integer id){
        map.put("colorList",colorRepostitory.findAll());
        map.put("product",productRepostitory.findProductById(id));
        map.put("typeList",typeRepostitory.findAll());
        return "product/product-buy";
    }

    @RequestMapping(value = "/product/buy.json")
    @ResponseBody
    public Data doProductBuy(Data data, Order order,@Param("cid") String cid,@Param("bid") String bid,@Param("price") Integer price,@Param("pid") Integer pid,@Param("sumType") Integer sumType){
        Boolean isBuy=true;
        Boolean haveBoLi=false;
        Boolean haveBaoTao=false;
        Boolean haveGuangQi=false;
        Boolean haveShangXiaFang=false;
        Product productById = productRepostitory.findProductById(pid);
        List<Associated> associatedsByPidList = associatedRepostitory.findAssociatedsByPid(pid);
        if (productById.getTid()==1){
            for (Associated a:associatedsByPidList
            ) {
                if (a.getNumber()==3){
                    haveShangXiaFang=true;
                }else if (a.getNumber()==1){
                    haveGuangQi=true;
                }else if (a.getNumber()==6){
                    haveBoLi=true;
                }
            }
            if (haveBoLi&&haveGuangQi&&haveShangXiaFang){
                isBuy=true;
            }else {
                data.setSuccess(false);
                data.setMsg("请查看玻璃，光企，上下方这三个材料是否都添加上了");
                return data;
            }
        }else if (productById.getTid()==2){
            for (Associated a:associatedsByPidList
            ) {
                if (a.getNumber()==6){
                    haveBoLi=true;
                }else if (a.getNumber()==7){
                    haveBaoTao=true;
                }
            }
            if (haveBoLi&&haveBaoTao){
                isBuy=true;
            }else {
                data.setSuccess(false);
                data.setMsg("请查看玻璃，包套这两个材料是否都添加上了");
                return data;
            }
        }

        if (isBuy){
            order.setPid(pid);
            Double sq=(Double.valueOf(order.getHeight())/100)*(Double.valueOf(order.getWidth())/100);
            order.setName(productById.getName());
            order.setSquare(df.format(sq));
            order.setPrices(sq*price);
            order.setNumber(sumType);
            if (order.getWall()!=null&&order.getWall()!=""){
                BigDecimal number = new BigDecimal(price);
                if (number.compareTo(new BigDecimal(20))==1){
                    order.setPrices((int)((Double.valueOf(price)*sq)+0.50+60.00));
                }else {
                    order.setPrices((int)((Double.valueOf(price)*sq)+0.50));
                }
            }else {
                order.setPrices((int)((Double.valueOf(price)*sq)+0.50));
            }
            order.setHavePay(price.toString());
            order.setOrderNumber(sn.format(new Date()));
            order.setCreateTime(sf.format(new Date()));
            order.setAttention(cid+","+bid+","+order.getAttention());
            order.setState(productById.getTid().toString());
            orderRepostitory.save(order);
            data.setSuccess(true);
            data.setMsg("下单成功");
        }else {
            data.setSuccess(false);
            data.setMsg("下单时发生错误，请重新下单");
        }
        return data;
    }

}
