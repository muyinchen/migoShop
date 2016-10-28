package com.migo.portal.service;

import com.migo.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author  知秋
 * Created by kauw on 2016/10/28.
 */
public interface UserService {
    TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response);
    void logoutByToken(HttpServletRequest request, HttpServletResponse response);
}
