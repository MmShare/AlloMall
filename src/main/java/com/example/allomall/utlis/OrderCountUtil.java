package com.example.allomall.utlis;


import com.example.allomall.entity.Associated;
import com.example.allomall.entity.Material;
import com.example.allomall.entity.Order;
import com.example.allomall.repostitory.AssociatedRepostitory;
import com.example.allomall.repostitory.MaterialRepostitory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class OrderCountUtil {

    DecimalFormat gf = new DecimalFormat("#.0");//保留小数点后1位

    //1.计算光企计算方式
    public Material sumGuangQi(Order o, Material m) {
        m.setName("光");
        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber()) * Integer.valueOf(o.getNumber()))));
        if (o.getSumType() == 1) {
            m.setValueSum(String.valueOf((new BigDecimal(o.getHeight()).subtract(new BigDecimal(m.getValueOneOne())))));
        } else if (o.getSumType() == 2) {

        } else if (o.getSumType() == 3) {

        } else if (o.getSumType() == 4) {

        }
        return m;
    }


    //2.计算勾企的计算方式
    public Material sumGouQi(Order o, Material m) {
        m.setName("勾");
        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber()) * Integer.valueOf(o.getNumber()))));
        if (o.getSumType() == 1) {
            m.setValueSum(String.valueOf((new BigDecimal(o.getHeight()).subtract(new BigDecimal(m.getValueOneOne())))));
        } else if (o.getSumType() == 2) {

        } else if (o.getSumType() == 3) {

        } else if (o.getSumType() == 4) {

        }
        return m;
    }

    //3.上下方的计算方式
    public Material sumShangXiaFang(Order o, Material m) {
        m.setName("上下");
        m.setNumber(String.valueOf((Integer.valueOf(m.getNumber()) * Integer.valueOf(o.getNumber()))));
        if (o.getSumType() == 1) {
            if (o.getState().equals("1")) {//阳台门
                m.setValueSum(String.valueOf(gf.format((((Double.valueOf(o.getWidth()) - Double.valueOf(m.getValueOneOne())) / o.getNumber()) + Double.valueOf(m.getValueOneTwo())))));
            } else if (o.getState().equals("2")) {//平推门
                m.setValueSum(String.valueOf(gf.format((((Double.valueOf(o.getWidth()) - Double.valueOf(m.getValueOneOne())) / o.getNumber()) + Double.valueOf(m.getValueOneTwo())))));
            } else if (o.getState().equals("3")) {//衣柜门
                m.setValueSum(String.valueOf(gf.format((((Double.valueOf(o.getWidth()) - Double.valueOf(m.getValueOneOne())) / o.getNumber())))));
            }
        } else if (o.getSumType() == 2) {

        }else if (o.getSumType() == 3) {

        }else if (o.getSumType() == 4) {

        }
        return m;
    }

    //4.边框的计算方式
    public Material sumBianKuang(Order o, Material m) {
        m.setName("边");
        m.setNumber(o.getNumber().toString());
        if (o.getSumType()==1) {
            m.setValueSum(o.getHeight());
        }else if (o.getSumType()==2){

        }else if (o.getSumType()==3){

        }else if (o.getSumType()==4){

        }
        return m;
    }

    //5.轨道的计算方式
    public Material sumGuiDao(Order o, Material m) {
        m.setName("轨");
        m.setNumber(o.getNumber().toString());
        if (o.getSumType()==1) {
            m.setValueSum(String.valueOf(Double.valueOf(o.getWidth()) - Double.valueOf(m.getValueOneOne())));
        }else if (o.getSumType()==2){

        }else if (o.getSumType()==3){

        }else if (o.getSumType()==4){

        }
        return m;
    }

    //6.玻璃的计算方式
    public Material sumBoLi(AssociatedRepostitory ar,Order o, Associated a, Material mt1, Material m){

        return m;
    }

    //6.玻璃的计算方式
    public Material sumBoLi(MaterialRepostitory mr,Order o, Associated a, Material m){

        return m;
    }

    //7.包套的计算方式
    public Material sumBaoTao(Order o,Material m){
        m.setName("包");
        m.setNumber(o.getNumber().toString());
        if (o.getSumType()==1) {
            m.setValueSum(String.valueOf(Double.valueOf(o.getHeight()) + Double.valueOf(m.getValueOneOne())) + "x" + String.valueOf(Double.valueOf(o.getWidth()) + Double.valueOf(m.getValueOneTwo())));
        }else if (o.getSumType()==2){

        }else if (o.getSumType()==3){

        }else if (o.getSumType()==4){

        }
        return m;
    }

}
