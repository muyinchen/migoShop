package com.migo.portal.Interceptor;

import com.migo.pojo.TbUser;
import com.migo.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器
 * Author  知秋
 * Created by kauw on 2016/10/28.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${SSO_LOGIN_URL}")
    private String SSO_LOGIN_URL;
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TbUser user = userService.getUserByToken(request, response);
        //如果用户session已经过期，跳转到登陆界面，因为跨系统跳转，不能用forward
        if (user==null){
            response.sendRedirect(SSO_LOGIN_URL+"?redirectURL="+request.getRequestURL());
            return false;
        }
        //没有过期就放行
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
