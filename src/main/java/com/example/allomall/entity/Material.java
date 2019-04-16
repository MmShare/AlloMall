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

    @Column(name = "length",length = 50)
    private double length;//长度

    @Column(name = "height",length = 50)
    private double height;//高度

    @Column(name = "width",length = 50)
    private double width;//宽度

}
