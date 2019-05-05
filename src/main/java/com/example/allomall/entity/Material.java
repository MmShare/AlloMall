package com.example.allomall.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "T_MATERIAL")
@Data
public class Material {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "valueOne",length = 50)
    private String valueOne;//数值1

    @Column(name = "valueTwo",length = 50)
    private String valueTwo;//数值2

    @Column(name = "number",length = 50)
    private String number;//数量

    @Column(name = "valueSum",length = 50)
    private String valueSum;//计算后的值

    @Column(name = "date",length = 50)
    private String date;//添加日期

    @Column(name = "state",length = 50)
    private Integer state;//状态

}
