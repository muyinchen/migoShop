package com.migo.service;

import com.migo.pojo.EUTreeNode;
import com.migo.pojo.MigoResult;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/10.
 */
public interface ContentCategoryService {
    List<EUTreeNode> getCategoryList(long parentId);
    MigoResult insertCategory(Long parentId,String name);
}
