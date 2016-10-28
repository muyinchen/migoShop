package com.migo.portal.service.Impl;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbUser;
import com.migo.portal.service.UserService;
import com.migo.utils.CookieUtils;
import com.migo.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器service
 * 当用户提交订单时此时必须要求用户登录，可以使用拦截器来实现。
 *拦截器的处理流程：
 1、拦截请求url
 2、从cookie中取token
 3、如果没有toke跳转到登录页面。
 4、取到token，需要调用sso系统的服务查询用户信息。
 5、如果用户session已经过期，跳转到登录页面
 6、如果没有过期，放行。
 * Author  知秋
 * Created by kauw on 2016/10/28.
 */
@Service
public class UserServiceImpl implements UserService{
    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_SERVICE}")
    private String SSO_USER_TOKEN_SERVICE;
    @Value("${SSO_USERLOGOUT_TOKEN_SERVICE}")
    private String SSO_USERLOGOUT_TOKEN_SERVICE;
    @Override
    public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        //添加try块来自己管理，这样，即便有错误也不影响逻辑，返回的也只是空值
        try {
            //从cookie中取token
            String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
            //判断token是否有值
            if (StringUtils.isEmpty(token)){
                return null;
            }
            //调用sso服务查询用户信息
            String json= HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN_SERVICE+token);
            //将json转换成Java对象,先判断是否过期，过期data没有数据
            MigoResult result = MigoResult.format(json);
            if (result.getStatus()!=200){
                return null;
            }
            //取用户对象
            result= MigoResult.formatToPojo(json, TbUser.class);
            TbUser user = (TbUser) result.getData();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void logoutByToken(HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //调用sso用户登录状态退出服务
        String json= HttpClientUtil.doGet(SSO_BASE_URL+SSO_USERLOGOUT_TOKEN_SERVICE+token);
    }
}
