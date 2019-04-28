package com.example.allomall.controller;


import com.example.allomall.entity.Data;
import com.example.allomall.entity.Order;
import com.example.allomall.repostitory.OrderRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/order/show.html")
    public String toOrderShow(@Param("id") Integer id){
        return "order/order-show";
    }

    @RequestMapping(value = "/order/edit.html")
    public String toOrderEdit(@Param("id") Integer id){
        return "order/order-edit";
    }

    @RequestMapping(value = "/order/edit.json")
    @ResponseBody
    public Data doOrderEdit(Data data, Order order){
        try {
            orderRepostitory.save(order);
            data.setSuccess(true);
            data.setMsg("修改订单成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
            data.setMsg("修改订单信息失败");
        }
        return data;
    }



}
