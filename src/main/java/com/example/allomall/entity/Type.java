package com.example.allomall.entity;


import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "T_TYPE")
@Data
public class Type {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;//类型名字


}
