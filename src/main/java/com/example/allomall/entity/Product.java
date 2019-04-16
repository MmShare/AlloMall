package com.example.allomall.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "T_PRODUCT")
@Data
public class Product {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;//商品名字

    @Column(name = "serialNumber",length = 50)
    private String productNumber;//编号

    @Column(name = "url",length = 150)
    private String url;//图片地址

    @Column(name = "price",length = 50)
    private double price;//价格

    @Column(name = "information",length = 50)
    private String information;//介绍

    @Column(name = "tid")
    private Integer tid;//隶属类型

}
