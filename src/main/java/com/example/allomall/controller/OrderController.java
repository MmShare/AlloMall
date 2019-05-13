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

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger log= LoggerFactory.getLogger(HomeController.class);

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");

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
            //Material mt=materialRepostitory.findMaterialByMtidAndId(a.get,a.getMid());
            if (order.getSumType()==1){//单开门计算方式
                if (m.getMtid()==1){//光企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueOneOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueOneOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    m.setValueSum(String.valueOf((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueOneOne()))/order.getNumber())+Double.valueOf(m.getValueOneTwo()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//边框
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//轨道
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueOneOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                    Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                    Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                    Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueOneOne())-Double.valueOf(m.getValueOneTwo()))+"x"+String.valueOf(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueOneOne()))/order.getNumber())+Double.valueOf(mt2.getValueOneTwo())-Double.valueOf(m.getValueOneOne())));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*product.getDid()*Integer.valueOf(order.getNumber()))));
                }
                materialList.add(m);
            }else if (order.getSumType()==2){//双开门计算公式
                double sum2=0.0;
                if (m.getMtid()==1){//光企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueTwoOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueTwoOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    sum2=(((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne()))/order.getNumber())+Double.valueOf(m.getValueTwoTwo()));
                    m.setValueSum(String.valueOf((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne()))/order.getNumber())+Double.valueOf(m.getValueTwoTwo()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//边框
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//轨道
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueTwoOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                    Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                    Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                    Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueTwoOne())-Double.valueOf(m.getValueTwoTwo()))+"x"+String.valueOf(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueOneOne()))/order.getNumber())+Double.valueOf(mt2.getValueOneTwo())-Double.valueOf(m.getValueTwoOne())));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }
                materialList.add(m);

            }else if (order.getSumType()==3){//三开门计算公式
                double sum2=0.0;
                if (m.getMtid()==1){//光企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueThrOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueThrOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    sum2=(((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne()))/order.getNumber())+Double.valueOf(m.getValueThrTwo()));
                    m.setValueSum(String.valueOf((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne()))/order.getNumber())+Double.valueOf(m.getValueThrTwo()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//轨道
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//边框
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueThrOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                    Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                    Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                    Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueThrOne())-Double.valueOf(m.getValueThrTwo()))+"x"+String.valueOf(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueOneOne()))/order.getNumber())+Double.valueOf(mt2.getValueOneTwo())-Double.valueOf(m.getValueThrOne())));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }
                materialList.add(m);

            }else if (order.getSumType()==4){//4开门计算公式
                double sum2=0.0;
                if (m.getMtid()==1){//光企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueFourOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==2){//勾企的计算方式
                    m.setValueSum(String.valueOf((Double.valueOf(order.getHeight())-Double.valueOf(m.getValueFourOne()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==3){//上下方计算方式
                    sum2=(((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne()))/order.getNumber())+Double.valueOf(m.getValueFourTwo()));
                    m.setValueSum(String.valueOf((((Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne()))/order.getNumber())+Double.valueOf(m.getValueFourTwo()))));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
                }else if (m.getMtid()==4){//轨道
                    m.setValueSum(order.getHeight());
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==5){//边框
                    m.setValueSum(String.valueOf(Double.valueOf(order.getWidth())-Double.valueOf(m.getValueFourOne())));
                    m.setNumber(order.getNumber().toString());
                }else if (m.getMtid()==6){//玻璃
                    Associated as1 = associatedRepostitory.findAssociatedByNumberAndPid(1, a.getPid());
                    Material mt1 = materialRepostitory.findMaterialById(as1.getMid());
                    Associated as2 = associatedRepostitory.findAssociatedByNumberAndPid(3, a.getPid());
                    Material mt2 = materialRepostitory.findMaterialById(as2.getMid());
                    m.setValueSum(String.valueOf(Double.valueOf(order.getHeight())-Double.valueOf(mt1.getValueFourOne())-Double.valueOf(m.getValueFourTwo()))+"x"+String.valueOf(((Double.valueOf(order.getWidth())-Double.valueOf(mt2.getValueOneOne()))/order.getNumber())+Double.valueOf(mt2.getValueOneTwo())-Double.valueOf(m.getValueFourOne())));
                    m.setNumber(String.valueOf((Integer.valueOf(m.getNumber())*Integer.valueOf(order.getNumber()))));
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
        for (int i=0;i<idStr.length;i++){
            idInt[i]=Integer.valueOf(idStr[i]);
        }
        map.put("orderInformation",orderRepostitory.findOrderById(idInt[1]));
        map.put("time",sf.format(new Date()));
        map.put("orderList",orderRepostitory.findOrdersByIdIn(idInt));
        return "order/order-print";
    }



}
