package com.example.allomall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_ASSOCIATED")
@Data
public class Associated implements  Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "number",length = 50)
    private Integer number;//数量

    @Id
    @JoinColumn(name = "pid")
    @ManyToOne
    private Product product;

    @Id
    @JoinColumn(name = "mid")
    @ManyToOne
    private Material material;
}
