package com.example.allomall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: xiapq
 * @date: 2019-06-26 9:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Excel extends BaseRowModel {

    @ExcelProperty(value = {"聚福门业","客户姓名","序号"},index = 0)
    private String row0;
    @ExcelProperty(value = {"聚福门业","客户姓名","高"},index = 1)
    private String row1;
    @ExcelProperty(value = {"聚福门业","","宽"},index = 2)
    private String row2;
    @ExcelProperty(value = {"聚福门业","","门扇"},index = 3)
    private String row3;
    @ExcelProperty(value = {"聚福门业","","平方"},index = 4)
    private String row4;
    @ExcelProperty(value = {"聚福门业","","单价"},index = 5)
    private String row5;
    @ExcelProperty(value = {"聚福门业","客户姓名","名称"},index = 6)
    private String row6;
    @ExcelProperty(value = {"聚福门业","客户姓名","备注"},index = 7)
    private String row7;
    @ExcelProperty(value = {"聚福门业","客户姓名","总价"},index = 8)
    private String row8;

}
