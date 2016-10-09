package com.migo.rest.controller;

import com.migo.rest.pojo.ItemCatResult;
import com.migo.rest.service.ItemCatService;
import com.migo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author  知秋
 * Created by kauw on 2016/10/9.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        ItemCatResult result=itemCatService.getItemCatList();
        if (StringUtils.isEmpty(callback)){
            //把result转换成字符串，因为返回类型是字符串
            String json= JsonUtils.objectToJson(result);

            return json;
        }
        //如果字符串不为空，需要支持jsonp调用
        //需要把result转换成字符串
        String json=JsonUtils.objectToJson(result);
        return callback+"("+json+")";
    }
}
