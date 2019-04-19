package com.example.allomall.controller;

import com.example.allomall.entity.Data;
import com.example.allomall.entity.Material;
import com.example.allomall.entity.Table;
import com.example.allomall.entity.Type;
import com.example.allomall.repostitory.MaterialRepostitory;
import com.example.allomall.repostitory.TypeRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    private TypeRepostitory typeRepostitory;

    @RequestMapping(value = "/windowsType/list.html")
    public String toWindowsType() {
        log.info("to windowsType 页面..................................................");
        return "system/windowType-list";
    }

    @RequestMapping(value = "/windowsType/getList.json")
    @ResponseBody
    public Table getWindowsTypeList(Table table){
        try {
            List<Type> typeList=typeRepostitory.findAll();
            table.setCode(0);
            table.setMsg("读取成功");
            table.setCount(typeList.size());
            table.setData(typeList);
        }catch (Exception e){
            log.info("读取门窗类型的时候发生错误");
        }
        return table;
    }

    @RequestMapping(value = "/windowsType/add.html")
    public String toWindowsTypeAdd() {
        log.info("to material 页面......................................................");
        return "system/windowType-add";
    }

    @RequestMapping(value = "/windowsType/add.json")
    @ResponseBody
    public Data doWindosTypeAdd(Data data,Type type){
        log.info("do windos add .....................................................");
        try {
            typeRepostitory.save(type);
            data.setSuccess(true);
            data.setMsg("新增门窗类型成功");
        }catch (Exception e){
            log.info("新增门窗类型发生错误");
            data.setSuccess(false);
            data.setMsg("新增门窗失败");
        }
        return data;
    }

    @RequestMapping(value = "/windowsType/edit.html")
    public String toWindowsTypeEdit(ModelMap map, @Param("id") Integer id){
        log.info("to windowsType edit 页面..............................................");
        return "system/windowType-edit";
    }

    @RequestMapping(value = "/windowsType/edit.json")
    @ResponseBody
    public Data doWindowsTypeEdit(Data data,Type type){
        log.info("do windowsType edit json .....................................");
        try {
            typeRepostitory.save(type);
            data.setSuccess(true);
            data.setMsg("修改门窗类型成功");
        }catch (Exception e){
            log.info("修改门窗类型时发生错误");
            data.setSuccess(false);
            data.setMsg("修改门窗类型时发生错误");
        }
        return data;
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
            log.info("添加材料时发生错误");
            data.setSuccess(false);
            data.setMsg("添加材料失败");
        }
        return data;
    }

    @RequestMapping(value = "/material/edit.html")
    public String toMaterialEdit(ModelMap map){
        log.info("to material edit html ....................................................");
        return "system/material-edit";
    }
}
