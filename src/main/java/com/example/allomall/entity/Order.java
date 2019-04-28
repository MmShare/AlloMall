package com.example.allomall.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "T_ORDER")
@Data
public class Order {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;//订单名

    @Column(name = "orderNumber",length = 50)
    private String orderNumber;//订单号

    @Column(name = "peopleName",length = 50)
    private String peopleName;//收货人

    @Column(name = "peoplePhone",length = 50)
    private String peoplePhone;//收货人电话

    @Column(name = "peopleAddress",length = 50)
    private String peopleAddress;//订单名

    @Column(name = "havePay",length = 50)
    private String havePay;//已付

    @Column(name = "prices",length = 50)
    private double prices;//总价

    @Column(name = "createTime",length = 50)
    private String createTime;//创建时间

    @Column(name = "deliveryTime",length = 50)
    private String deliveryTime;//完成日期

    @Column(name = "state",length = 50)
    private String state;//状态

    @Column(name = "length",length = 50)
    private String length;//长度

    @Column(name = "height",length = 50)
    private String height;//高度

    @Column(name = "width",length = 50)
    private String width;//宽度

    @Column(name = "number",length = 50)
    private Integer number;//数量

    @Column(name = "attention",length = 50)
    private String attention;//注意事项

    @Column(name = "pid")
    private Integer pid;//关联商品表


}
