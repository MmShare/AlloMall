package com.example.allomall.controller;

import com.example.allomall.Config.SystemParameter;
import com.example.allomall.entity.Data;
import com.example.allomall.entity.Material;
import com.example.allomall.entity.Table;
import com.example.allomall.entity.Type;
import com.example.allomall.repostitory.MaterialRepostitory;
import com.example.allomall.repostitory.MaterialTypeRepostitory;
import com.example.allomall.repostitory.TypeRepostitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private MaterialTypeRepostitory materialTypeRepostitory;

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

    @RequestMapping(value = "/windowsType/search.json")
    @ResponseBody
    public Table searchWindowsTypeList(Table table,@Param("condition") String condition){
        try {
            List<Type> typeList=typeRepostitory.findTypeByNameContaining(condition);
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
            type.setDate(sf.format(new Date()));
            type.setState(SystemParameter.IS_USER);
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

    @RequestMapping(value = "/windowsType/show.html/{id}")
    public String toWindowsTypeShow(ModelMap map,@PathVariable("id") Integer id){
        map.put("windowsType",typeRepostitory.findTypeById(id));
        return "system/windowType-show";
    }

    @RequestMapping(value = "/windowsType/edit.html/{id}")
    public String toWindowsTypeEdit(ModelMap map, @PathVariable("id") Integer id){
        log.info("to windowsType edit 页面..............................................");
        map.put("windowsType",typeRepostitory.findTypeById(id));
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

    @RequestMapping(value = "/windowsType/delete.json")
    @ResponseBody
    public Data doWindowsTypeDelete(Data data,@Param("id") Integer id){
        typeRepostitory.deleteTypeById(id);
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

    @RequestMapping(value = "/material/search.json")
    @ResponseBody
    public Table searchMaterialList(Table table,@Param("condition") String condition) {
        log.info("search material list .....................................................");
        table.setCode(0);
        table.setMsg("");
        List<Material> materialList = materialRepostitory.findMaterialsByNameContaining(condition);
        table.setCount(materialList.size());
        table.setData(materialList);
        return table;
    }

    @RequestMapping(value = "/material/add.html")
    public String toMaterialAdd(ModelMap map) {
        log.info("to material Add 页面......................................................");
        map.put("materialTypeList",materialTypeRepostitory.findAll());
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

    @RequestMapping(value = "/material/edit.html/{id}")
    public String toMaterialEdit(ModelMap map,Material material,@PathVariable("id") Integer id){
        log.info("to material edit html ....................................................");
        try {
            map.put("materialTypeList",materialTypeRepostitory.findAll());
            material=materialRepostitory.findMaterialById(id);
            System.out.println(material.getName());
            map.put("material",material);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/material-edit";
    }

    @RequestMapping(value = "/material/edit.json")
    @ResponseBody
    public Data doMaterialEdit(Data data,Material material){
        log.info("do material edit..........................................................");
        try {
            materialRepostitory.save(material);
            data.setSuccess(true);
            data.setMsg("修改材料信息成功");
        }catch (Exception e){
            data.setSuccess(false);
            data.setMsg("修改材料信息失败");
        }
        return data;
    }

    @RequestMapping(value = "/material/show.html/{id}")
    public String toMaterialShow(ModelMap map,Material material,@PathVariable("id") Integer id){
        log.info("to material show .........................................................");
        try {
            material=materialRepostitory.findMaterialById(id);
            System.out.println(material.getName());
            map.put("material",material);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/material-show";
    }

    @RequestMapping(value = "/material/delete.json")
    @ResponseBody
    public Data doMaterialDelete(Data data,@Param("id") Integer id){
        try {
            materialRepostitory.deleteMaterialById(id);
            data.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            data.setSuccess(false);
        }
        return data;
    }
}
