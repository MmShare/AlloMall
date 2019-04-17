package com.example.allomall.controller;

import com.example.allomall.entity.Data;
import com.example.allomall.entity.Material;
import com.example.allomall.entity.Table;
import com.example.allomall.repostitory.MaterialRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MaterialRepostitory materialRepostitory;

    @RequestMapping(value = "/windowsType/list.html")
    public String toWindowsType() {
        log.info("to windowsType 页面..................................................");
        return "system/material-list";
    }

    @RequestMapping(value = "/windowsType/add.html")
    public String toWindowsTypeAdd() {
        log.info("to material 页面......................................................");
        return "system/material-add";
    }

    @RequestMapping(value = "/material/list.html")
    public String toMaterial() {
        log.info("to material 页面......................................................");
        return "system/material-list";
    }

    @RequestMapping(value = "/material/getList.json")
    @ResponseBody
    public Table getMaterialList(Table table) {
        log.info("get material list .....................................................");
        table.setCode(0);
        table.setMsg("");
        List<Material> materialList = materialRepostitory.findAll();
        table.setCount(materialList.size());
        table.setData(materialList);
        return table;
    }

    @RequestMapping(value = "/material/add.html")
    public String toMaterialAdd() {
        log.info("to material Add 页面......................................................");
        return "system/material-add";
    }

    @RequestMapping(value = "/material/add.json")
    @ResponseBody
    public Data doMaterialAdd(Data data, Material material) {
        log.info("do material add.........................................................");

        try {
            material.setDate(sf.format(new Date()));
            material.setState(1);
            materialRepostitory.save(material);
            data.setSuccess(true);
            data.setMsg("添加材料成功");
        } catch (Exception e) {
            data.setSuccess(false);
            data.setMsg("添加材料失败");
        }
        return data;
    }
}
