package com.example.allomall.entity;

import java.util.List;

public class Table {

    private Integer code;
    private String msg;
    private Integer count;
    private List<?> data;


    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
