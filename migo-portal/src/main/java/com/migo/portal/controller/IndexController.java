package com.migo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author  知秋
 * Created by kauw on 2016/10/8.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
