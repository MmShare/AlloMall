package com.example.allomall.controller;


import com.example.allomall.entity.Data;
import com.example.allomall.entity.Order;
import com.example.allomall.entity.Table;
import com.example.allomall.repostitory.OrderRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private OrderRepostitory orderRepostitory;

    @RequestMapping(value = "/order/list.html/{typeid}")
    public String toAllOrder(ModelMap map, @PathVariable("typeid") Integer typeid){
        log.info("to order html .................................................");
        if (typeid==1){

        }else if (typeid==2){

        }else if (typeid==3){

        }
        return "order/order-list";
    }

    @RequestMapping(value = "/order/getList.json")
    @ResponseBody
    public Table getOrderTable(Table table){

        List<Order> orderList=orderRepostitory.findAll();
        table.setCount(orderList.size());
        table.setMsg("查询成功");
        table.setCode(0);
        table.setData(orderList);
        return table;
    }

    @RequestMapping(value = "/order/show.html")
    public String toOrderShow(ModelMap map,@Param("id") Integer id){
        map.put("order",orderRepostitory.findOrderById(id));
        return "order/order-show";
    }

    @RequestMapping(value = "/order/edit.html")
    public String toOrderEdit(ModelMap map,@Param("id") Integer id){
        map.put("order",orderRepostitory.findOrderById(id));
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

    @RequestMapping(value = "/order/delete.json")
    @ResponseBody
    public Data doOrderDelete(Data data,@Param("id") Integer id){
        try {
            orderRepostitory.deleteOrderById(id);
            data.setSuccess(true);
            data.setMsg("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
        }
        return data;
    }



}
