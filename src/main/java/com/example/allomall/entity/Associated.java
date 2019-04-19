package com.example.allomall.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "T_ASSOCIATED")
public class Associated implements  Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "number",length = 50)
    private Integer number;//数量

    @Column(name = "pid",length = 50)
    private Integer pid;//商品的id

    @Column(name = "mid",length = 50)
    private Integer mid;//附属材料的id

    @Column(name = "state",length = 50)
    private Integer state;//状态
}
