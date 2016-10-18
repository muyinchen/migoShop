package com.migo.rest.controller;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;
import com.migo.rest.service.ContentService;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/{cid}")
    @ResponseBody
    public MigoResult getContentList(@PathVariable long cid){
        try {
            List<TbContent> list=contentService.getContentList(cid);
            return MigoResult.ok(list);

        }catch (Exception e){
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));

        }
    }
    @RequestMapping("/syn/content/{cid}")
    @ResponseBody
    public MigoResult  synContent(@PathVariable("cid") Long cid){
        try {

            MigoResult result = contentService.synContent(cid);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return MigoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

}
