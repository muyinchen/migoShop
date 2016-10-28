package com.migo.portal.controller;

import com.migo.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author  知秋
 * Created by kauw on 2016/10/28.
 */
@Controller
public class UserServiceController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        userService.logoutByToken(request, response);

        return "index";
    }
}
