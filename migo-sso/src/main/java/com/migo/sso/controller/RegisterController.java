package com.migo.sso.controller;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbUser;
import com.migo.sso.service.RegisterService;
import com.migo.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author  知秋
 * Created by kauw on 2016/10/27.
 */
@RestController
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping("/check/{param}/{type}")
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
        try {
            MigoResult result = registerService.checkData(param, type);
            if (StringUtils.isNoneBlank(callback)) {
                //请求为jsonp调用
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;


            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public MigoResult register(TbUser user){
        try {
            MigoResult result=registerService.register(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MigoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
}
