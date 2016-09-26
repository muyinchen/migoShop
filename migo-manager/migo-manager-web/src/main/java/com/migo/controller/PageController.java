package com.migo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
@Controller
public class PageController {
    /*打开首页*/
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
/**
* @author kauw 2016/9/23
* @time 10:48
* @version V1.0.0
* @description 展示其他页面
*/
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
