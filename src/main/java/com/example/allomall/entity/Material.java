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

    @Column(name = "valueOneOne",length = 50)
    private String valueOneOne;//数值1_1

    @Column(name = "valueOneTwo",length = 50)
    private String valueOneTwo;//数值1_2

    @Column(name = "valueTwoOne",length = 50)
    private String valueTwoOne;//数值2_1

    @Column(name = "valueTwoTwo",length = 50)
    private String valueTwoTwo;//数值2_2

    @Column(name = "valueThrOne",length = 50)
    private String valueThrOne;//数值3_1

    @Column(name = "valueThrTwo",length = 50)
    private String valueThrTwo;//数值3_2

    @Column(name = "valueFourOne",length = 50)
    private String valueFourOne;//数值4_1

    @Column(name = "valueFourTwo",length = 50)
    private String valueFourTwo;//数值4_2

    @Column(name = "number",length = 50)
    private String number;//数量

    @Column(name = "valueSum",length = 50)
    private String valueSum;//计算后的值

    @Column(name = "date",length = 50)
    private String date;//添加日期

    @Column(name = "state",length = 50)
    private Integer state;//状态

}
