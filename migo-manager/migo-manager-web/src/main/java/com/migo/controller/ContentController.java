package com.migo.controller;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;
import com.migo.service.ContentService;
import com.migo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYN_URL}")
    private String REST_CONTENT_SYN_URL;
    @Autowired
    private ContentService contentService;

    @RequestMapping("/save")
    @ResponseBody
    public MigoResult insertContent(TbContent content){
        MigoResult migoResult=contentService.insertContent(content);
        //调用rest模块发布的服务，同步缓存
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYN_URL+content.getCategoryId());
        return migoResult;
    }
}
