package com.migo.sso.service;

import com.migo.pojo.MigoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author  知秋
 * Created by kauw on 2016/10/27.
 */
public interface LoginService {
    MigoResult login(String username, String password, HttpServletRequest request,
                     HttpServletResponse response);
    MigoResult getUserByToken(String token);
    MigoResult loginoutByToken(String token);
}
