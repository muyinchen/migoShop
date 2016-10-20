package com.migo.search.controller;

import com.migo.pojo.MigoResult;
import com.migo.search.pojo.SearchResult;
import com.migo.search.service.SearchService;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/q")
    @ResponseBody
    public MigoResult search(@RequestParam(defaultValue = "")String keyword,
                             @RequestParam(defaultValue = "1")Integer page,
                             @RequestParam(defaultValue = "30")Integer rows){
        try {
            //转换字符集
            keyword=new String(keyword.getBytes("iso8859-1"),"utf-8");
            SearchResult searchResult=searchService.search(keyword,page,rows);
            return MigoResult.ok(searchResult);
        }catch (Exception e){
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }
}
