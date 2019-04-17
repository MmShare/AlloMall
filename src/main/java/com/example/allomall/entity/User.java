package com.example.allomall.entity;



import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
@Data
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id

    @Column(name = "name",length = 50)
    private String name;//用户名

    @Column(name = "account",length = 50)
    private String account;//账号

    @Column(name = "pwd",length = 50)
    private String pwd;//密码

    @Column(name = "sex",length = 50)
    private String sex;//性别

    @Column(name = "phone",length = 50)
    private String phone;//电话

    @Column(name = "address",length = 50)
    private String address;//地址

    @Column(name = "state",length = 50)
    private Integer state;//状态


}
