package com.migo.portal.controller;

import com.migo.portal.pojo.SearchResult;
import com.migo.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Author  知秋
 * Created by kauw on 2016/10/21.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")

    public String search(@RequestParam("q")String keyword,
                         @RequestParam(defaultValue="1")Integer page,
                         @RequestParam(defaultValue="60")Integer rows, Model model) {
        //get乱码处理
        try {
            keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            keyword = "";
            e.printStackTrace();
        }

        SearchResult searchResult=searchService.search(keyword,page,rows);
        //参数传递给页面
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("page",searchResult.getCurPage());
        //返回逻辑视图
        return "search";

    }

    }
