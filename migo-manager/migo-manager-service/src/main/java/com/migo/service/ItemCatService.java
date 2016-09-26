package com.migo.service;

import com.migo.pojo.EUTreeNode;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/9/23.
 */
public interface ItemCatService {
    List<EUTreeNode> getItemCatList(long parentId);
}
