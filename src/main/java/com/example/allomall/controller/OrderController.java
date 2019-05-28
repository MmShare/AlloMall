package com.example.allomall.controller;


import com.example.allomall.entity.*;
import com.example.allomall.repostitory.AssociatedRepostitory;
import com.example.allomall.repostitory.MaterialRepostitory;
import com.example.allomall.repostitory.OrderRepostitory;
import com.example.allomall.repostitory.ProductRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");

    DecimalFormat df = new DecimalFormat("#.00");//保留小数点后2位
    DecimalFormat gf = new DecimalFormat("#.0");//保留小数点后1位

    private Map<String,String> orderMap=new HashMap<>();

    @Autowired
    private OrderRepostitory orderRepostitory;

    @Autowired
    private ProductRepostitory productRepostitory;

    @Autowired
    private AssociatedRepostitory associatedRepostitory;

    @Autowired
    private MaterialRepostitory materialRepostitory;

    @RequestMapping(value = "/order/list.html/{typeid}")
    public String toAllOrder(ModelMap map, @PathVariable("typeid") Integer typeid){
        log.info("to order html .................................................");
        if (typeid==1){
            map.put("state",0);
        }else if (typeid==2){
            map.put("state",typeid);
        }else if (typeid==3){
            map.put("state",typeid);
        }else if (typeid==4){
            map.put("state",typeid);
        }else if (typeid==5){
            map.put("state",1);
        }
        return "order/order-list";
    }

    @RequestMapping(value = "/order/getList.json/{state}")
    @ResponseBody
    public Table getOrderTable(Table table,@PathVariable("state") Integer state){
        List<Order> orderList=new ArrayList<>();
        if (state==0){
            orderList=orderRepostitory.findAll();
        }else {
            orderList=orderRepostitory.findOrdersByState(String.valueOf(state));
        }
        table.setCount(orderList.size());
        table.setMsg("查询成功");
        table.setCode(0);
        table.setData(orderList);
        return table;
    }

    @RequestMapping(value = "/order/search.json")
    @ResponseBody
    public Table searchOrderTable(Table table,@Param("condition") String condition,@Param("state") String state){
        System.out.println(state);
        List<Order> orderList=orderRepostitory.findOrdersByStateOrPeopleAddressContainingOrPeopleNameContaining(state,condition,condition);
        table.setCount(orderList.size());
        table.setMsg("查询成功");
        table.setCode(0);
        table.setData(orderList);
        return table;
    }

    @RequestMapping(value = "/order/show.html/{id}")
    public String toOrderShow(ModelMap map,@PathVariable("id") Integer id,Order order){

        order=orderRepostitory.findOrderById(id);
        map.put("product",productRepostitory.findProductById(order.getPid()));
        map.put("order",order);
        return "order/order-show";
    }

    @RequestMapping(value = "/material/show.html/{id}")
    public String toOrderMaterialShow(ModelMap map,@PathVariable("id") Integer id,Order order){
        List<Material> materialList=new ArrayList<>();
        order=orderRepostitory.findOrderById(id);
        Product product=productRepostitory.findProductById(order.getPid());
        List<Associated> associatedList=associatedRepostitory.findAssociatedsByPid(order.getPid());
        for (Associated a:associatedList
             ) {
            Material m=materialRepostitory.findMaterialById(a.getMid());
            if (order.getSumType()==1){//单开门计算方式
                if (m.getMtid()==1){//光企的计算方式
                    m.setName("光");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueOneOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setName("勾");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueOneOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    m.setName("上下");
                    if (order.getState().equals("1")){//阳台门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueOneOne()))/order.getNumber())+Double.valueOf(m.getValueOneTwo()))+0.0)));
                    }else if (order.getState().equals("2")){//平推门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueOneOne()))/order.getNumber())+Double.valueOf(m.getValueOneTwo()))+0.0)));
                    }else if (order.getState().equals("3")){//衣柜门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueOneOne()))/order.getNumber()))+0.0)));
                    }
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//边框
                    m.setName("边");
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//轨道
                    m.setName("轨");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueOneOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    if (order.getState().equals("1")){//平开门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueOneOne())-Double.valueOf(m.getValueOneOne())))+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueOneOne()))/order.getNumber())+Double.valueOf(mt2.getValueOneTwo())-Double.valueOf(m.getValueOneOne())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("2")){//衣柜门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(7, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())+Double.valueOf(mt1.getValueOneOne())-Double.valueOf(m.getValueOneOne())))+"x"+String.valueOf(gf.format(Double.valueOf(order.getWidth())+Double.valueOf(mt1.getValueOneTwo())-Double.valueOf(m.getValueOneTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("3")){//衣柜门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueOneOne())-Double.valueOf(m.getValueOneOne())))+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueOneOne()))/order.getNumber())+Double.valueOf(m.getValueOneTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }
                }else if (m.getMtid()==7){//包套
                    m.setName("包");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())+Double.valueOf(m.getValueOneOne()))+"x"+String.valueOf(Double.valueOf(order.getWidth())+Double.valueOf(m.getValueOneTwo())));
                    m.setNumber(order.getNumber().toString());
                }
                materialList.add(m);

            }else if (order.getSumType()==2){//双开门计算公式
                if (m.getMtid()==1){//光企的计算方式
                    m.setName("光");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueTwoOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setName("勾");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueTwoOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    m.setName("上下");
                    if (order.getState().equals("1")){//阳台门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne()))/order.getNumber())+Double.valueOf(m.getValueTwoTwo()))+0.0)));
                    }else if (order.getState().equals("2")){//平推门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne()))/order.getNumber())+Double.valueOf(m.getValueTwoTwo()))+0.0)));
                    }else if (order.getState().equals("3")){//衣柜门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne()))/order.getNumber()))+0.0)));
                    }
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//边框
                    m.setName("边");
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//轨道
                    m.setName("轨");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    if (order.getState().equals("1")){//阳台门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueTwoOne())-Double.valueOf(m.getValueTwoOne())+0.00)+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueTwoOne()))/order.getNumber())+Double.valueOf(mt2.getValueTwoTwo())-Double.valueOf(m.getValueTwoTwo())+0.0))));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("2")){//平开门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(7, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())+Double.valueOf(mt1.getValueTwoOne())-Double.valueOf(m.getValueTwoOne())+0.00)+"x"+String.valueOf(gf.format(Double.valueOf(order.getWidth())+Double.valueOf(mt1.getValueTwoTwo())-Double.valueOf(m.getValueTwoTwo())+0.0))));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("3")){//衣柜门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueTwoOne())-Double.valueOf(m.getValueTwoOne())+0.0))+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueTwoOne()))/order.getNumber())+Double.valueOf(m.getValueTwoTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }
                }else if (m.getMtid()==7){//包套
                    m.setName("包");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())+Double.valueOf(m.getValueTwoOne()))+"x"+String.valueOf(Double.valueOf(order.getWidth())+Double.valueOf(m.getValueTwoTwo())));
                    m.setNumber(order.getNumber().toString());
                }
                materialList.add(m);

            }else if (order.getSumType()==3){//三开门计算公式
                if (m.getMtid()==1){//光企的计算方式
                    m.setName("光");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueThrOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setName("勾");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueThrOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    m.setName("上下");
                    if (order.getState().equals("1")){//阳台门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne()))/order.getNumber())+Double.valueOf(m.getValueThrTwo())+0.0))));
                    }else if (order.getState().equals("2")){//平推门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne()))/order.getNumber())+Double.valueOf(m.getValueThrTwo())+0.0))));
                    }else if (order.getState().equals("3")){//衣柜门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne()))/order.getNumber())+0.0))));
                    }
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//轨道
                    m.setName("轨道");
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//边框
                    m.setName("边");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    if (order.getState().equals("1")){//阳台门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueThrOne())-Double.valueOf(m.getValueThrOne()))+"x"+String.valueOf(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueThrOne()))/order.getNumber())+Double.valueOf(mt2.getValueThrTwo())-Double.valueOf(m.getValueThrTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("2")){//平推门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(7, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())+Double.valueOf(mt1.getValueThrOne())-Double.valueOf(m.getValueThrOne())))+"x"+String.valueOf(gf.format(Double.valueOf(order.getWidth())+Double.valueOf(mt1.getValueThrTwo())-Double.valueOf(m.getValueThrTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("3")){//衣柜门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueThrOne())-Double.valueOf(m.getValueThrOne())))+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueThrOne()))/order.getNumber())+Double.valueOf(m.getValueThrTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }
                }else if (m.getMtid()==7){//包套
                    m.setName("包");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())+Double.valueOf(m.getValueThrOne()))+"x"+String.valueOf(Double.valueOf(order.getWidth())+Double.valueOf(m.getValueThrTwo())));
                    m.setNumber(order.getNumber().toString());
                }
                materialList.add(m);

            }else if (order.getSumType()==4){//4开门计算公式
                if (m.getMtid()==1){//光企的计算方式
                    m.setName("光");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueFourOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setName("勾");
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueFourOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    m.setName("上下");
                    if (order.getState().equals("1")){//阳台门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne()))/order.getNumber())+Double.valueOf(m.getValueFourTwo()))+0.0)));
                    }else if (order.getState().equals("2")){//平推门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne()))/order.getNumber())+Double.valueOf(m.getValueFourTwo()))+0.0)));
                    }else if (order.getState().equals("3")){//衣柜门
                        m.setValueSum(String.valueOf(gf.format((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne()))/order.getNumber()))+0.0)));
                    }
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//轨道
                    m.setName("轨");
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//边框
                    m.setName("边");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    if (order.getState().equals("1")){//阳台门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueFourOne())-Double.valueOf(m.getValueFourOne())))+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueFourOne()))/order.getNumber())+Double.valueOf(mt2.getValueFourTwo())-Double.valueOf(m.getValueFourTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("2")){//平推门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(7, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())+Double.valueOf(mt1.getValueFourOne())-Double.valueOf(m.getValueFourOne())))+"x"+String.valueOf(gf.format(Double.valueOf(order.getWidth())+Double.valueOf(mt1.getValueFourTwo())+Double.valueOf(m.getValueFourTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }else if (order.getState().equals("3")){//衣柜门玻璃计算方式
                        Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                        Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                        Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                        Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                        m.setName("玻");
                        m.setValueSum(String.valueOf(gf.format(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueFourOne())-Double.valueOf(m.getValueFourOne())))+"x"+String.valueOf(gf.format(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueFourTwo()))/order.getNumber())+Double.valueOf(m.getValueFourTwo())+0.0)));
                        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                    }
                }else if (m.getMtid()==7){//包套
                    m.setName("包");
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())+Double.valueOf(m.getValueFourOne()))+"x"+String.valueOf(Double.valueOf(order.getWidth())+Double.valueOf(m.getValueFourTwo())));
                    m.setNumber(order.getNumber().toString());
                }
                materialList.add(m);
            }
        }
        map.put("materialList",materialList);
        map.put("product",product);
        map.put("order",order);
        return "order/order-material-show";
    }

    @RequestMapping(value = "/order/edit.html/{id}")
    public String toOrderEdit(ModelMap map,Order order,@PathVariable("id") Integer id){
        orderMap.put("1","未完成");
        orderMap.put("2","未安装");
        orderMap.put("3","仍欠款");
        orderMap.put("4","已结算");
        map.put("orderMap",orderMap);
        order=orderRepostitory.findOrderById(id);
        map.put("product",productRepostitory.findProductById(order.getPid()));
        map.put("order",order);
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

    @RequestMapping(value = "/order/print.html/{ids}")
    public String goOrderPrint(ModelMap map,@PathVariable("ids") String ids){
        String[] idStr = ids.split(",");
        Integer[] idInt=new Integer[idStr.length];
        Double allPrices=0.00;
        for (int i=0;i<idStr.length;i++){
            idInt[i]=Integer.valueOf(idStr[i]);
        }
        List<Order> orderList=orderRepostitory.findOrdersByIdIn(idInt);
        for (Order o:orderList
             ) {
            allPrices+=o.getPrices();
        }
        map.put("orderInformation",orderRepostitory.findOrderById(idInt[0]));
        map.put("time",sf.format(new Date()));
        map.put("orderList",orderList);
        map.put("allPrices",allPrices);
        return "order/order-print";
    }



}
