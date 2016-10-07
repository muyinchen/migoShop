package com.migo.controller;

import com.migo.pojo.EasyUIDataGridResult;
import com.migo.pojo.MigoResult;
import com.migo.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/10/6.
 */
@Controller
@RequestMapping("/item/param")
public class ItemparamController {
    @Autowired
    ItemParamService itemParamService;


    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemparmList(Integer page,Integer rows){

        EasyUIDataGridResult result=itemParamService.getItemParamList(page,rows);
        return result;
    }
    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public MigoResult getItemCatByCid(@PathVariable Long cid){
        MigoResult result=itemParamService.getItemParamByCid(cid);
        return result;
    }
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public MigoResult insertItemParam(@PathVariable Long cid,String paramData){
        MigoResult result=itemParamService.insertItemParam(cid,paramData);
        return result;
    }
}
