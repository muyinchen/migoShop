package com.migo.controller;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;
import com.migo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/save")
    @ResponseBody
    public MigoResult insertContent(TbContent content){
        MigoResult migoResult=contentService.insertContent(content);
        return migoResult;
    }
}
