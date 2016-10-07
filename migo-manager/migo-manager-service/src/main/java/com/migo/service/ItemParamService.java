package com.migo.service;

import com.migo.pojo.EasyUIDataGridResult;
import com.migo.pojo.MigoResult;

/**
 * Author  知秋
 * Created by kauw on 2016/10/6.
 */
public interface ItemParamService {
    EasyUIDataGridResult getItemParamList(int page, int rows);
    MigoResult getItemParamByCid(Long cid);
    MigoResult insertItemParam(Long cid,String paramData);
}
