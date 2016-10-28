package com.migo.sso.controller;

import com.migo.pojo.MigoResult;
import com.migo.sso.service.LoginService;
import com.migo.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping("/user/token/{token}")
    public Object getUserByToken(@PathVariable String token,String callback){
        try {
            MigoResult userByToken = loginService.getUserByToken(token);
            if (!StringUtils.isEmpty(callback)){

                //支持jsonp调用
                MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(userByToken);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return userByToken;
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/user/logout/{token}")
    public Object logoutByToken(@PathVariable String token,String callback){
        try{
        MigoResult result = loginService.loginoutByToken(token);
        if (!StringUtils.isEmpty(callback)){

            //支持jsonp调用
            MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    } catch (Exception e) {
        e.printStackTrace();
        return MigoResult.build(500,ExceptionUtil.getStackTrace(e));
    }
    }
}
