package com.migo.portal.controller;

import com.migo.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author  知秋
 * Created by kauw on 2016/10/8.
 */
@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/index")
    public String showIndex(Model model){
        //取大广告位内容
        String json=contentService.getAd1List();
        //加入model，传递给页面
        model.addAttribute("ad1",json);
        return "index";
    }
}
