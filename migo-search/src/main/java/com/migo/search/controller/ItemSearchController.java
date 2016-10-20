package com.migo.search.controller;

import com.migo.pojo.MigoResult;
import com.migo.search.service.ItemSearchService;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/10/20.
 */
@Controller
public class ItemSearchController {
    @Autowired
    private ItemSearchService searchService;

    @RequestMapping("/importall")
    @ResponseBody
    public MigoResult importall(){
        try {
            MigoResult result=searchService.importItems();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
