package com.migo.portal.service;

import com.migo.pojo.TbItem;

/**
 * Author  知秋
 * Created by kauw on 2016/10/25.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParamById(Long itemId);
}
