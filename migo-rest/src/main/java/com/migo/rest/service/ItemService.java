package com.migo.rest.service;

import com.migo.pojo.TbItem;
import com.migo.pojo.TbItemDesc;
import com.migo.pojo.TbItemParamItem;

/**
 * Author  知秋
 * Created by kauw on 2016/10/24.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);
    TbItemParamItem getItemparmById(Long itemId);

}
