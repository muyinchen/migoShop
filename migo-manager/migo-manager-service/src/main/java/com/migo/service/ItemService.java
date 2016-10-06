package com.migo.service;

import com.migo.pojo.EasyUIDataGridResult;
import com.migo.pojo.MigoResult;
import com.migo.pojo.TbItem;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
public interface ItemService {
    public TbItem getItemById(long itemId);
    EasyUIDataGridResult getItemList(int page,int rows);
    MigoResult CreateItem(TbItem item,String desc);
}
