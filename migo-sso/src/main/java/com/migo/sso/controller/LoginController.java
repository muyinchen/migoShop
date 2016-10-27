package com.migo.sso.controller;

import com.migo.pojo.MigoResult;
import com.migo.sso.service.LoginService;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author  知秋
 * Created by kauw on 2016/10/27.
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public MigoResult login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response){
        try {
            MigoResult result = loginService.login(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
