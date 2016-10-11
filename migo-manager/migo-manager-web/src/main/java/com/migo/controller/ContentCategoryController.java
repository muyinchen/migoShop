package com.migo.controller;

import com.migo.pojo.EUTreeNode;
import com.migo.pojo.MigoResult;
import com.migo.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/10.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0")Long parentId){
        List<EUTreeNode> list=contentCategoryService.getCategoryList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public MigoResult createNode(Long parentId,String name){
        MigoResult result=contentCategoryService.insertCategory(parentId,name);
        return result;
    }
}
