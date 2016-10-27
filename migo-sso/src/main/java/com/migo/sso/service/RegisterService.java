package com.migo.sso.service;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbUser;

/**
 * Author  知秋
 * Created by kauw on 2016/10/27.
 */
public interface RegisterService {
    MigoResult checkData(String param,int type);
    MigoResult register(TbUser user);
}
