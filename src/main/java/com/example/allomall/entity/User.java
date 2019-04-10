package com.example.allomall.entity;



import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_USER")
@Data
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "account",length = 50)
    private String account;

    @Column(name = "pwd",length = 50)
    private String pwd;

    @Column(name = "sex",length = 50)
    private String sex;

    @Column(name = "phone",length = 50)
    private String phone;

    @Column(name = "address",length = 50)
    private String address;


}
