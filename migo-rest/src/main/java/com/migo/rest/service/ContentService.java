package com.migo.rest.service;

import com.migo.pojo.MigoResult;
import com.migo.pojo.TbContent;

import java.util.List;

/**
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
public interface ContentService {
    List<TbContent> getContentList(Long cid);
    MigoResult synContent(Long cid);
}
