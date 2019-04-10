package com.example.allomall.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_TYPE")
@Data
public class Type {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;

    @OneToMany(mappedBy = "type",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Product> productList;

}
