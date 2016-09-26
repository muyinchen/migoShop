package com.migo.controller;

import com.migo.pojo.EUTreeNode;
import com.migo.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getItemCatlist(@RequestParam(value = "id", defaultValue = "0")Long parentId){

        List<EUTreeNode> list=itemCatService.getItemCatList(parentId);
        return list;
    }
}
