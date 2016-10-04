package com.migo.controller;

import com.migo.pojo.PictureResult;
import com.migo.service.PicService;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author  知秋
 * Created by kauw on 2016/10/4.
 */
@Controller
public class PicController {
    @Autowired
    private PicService picService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        PictureResult result=picService.uploadPic(uploadFile);
        //把Java对象手工转换成json数据
        String json= JsonUtils.objectToJson(result);
        return json;
    }
}
